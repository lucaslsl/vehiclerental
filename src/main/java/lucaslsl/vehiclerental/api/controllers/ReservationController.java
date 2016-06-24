/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import java.sql.Date;
import java.util.Map;
import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.db.RedisConnectionProvider;
import lucaslsl.vehiclerental.api.models.Customer;
import lucaslsl.vehiclerental.api.models.Reservation;
import lucaslsl.vehiclerental.api.models.Vehicle;
import lucaslsl.vehiclerental.api.policies.HasValidContentType;
import lucaslsl.vehiclerental.api.util.JsonHelper;
import lucaslsl.vehiclerental.api.util.ModelVerificationContext;
import lucaslsl.vehiclerental.api.util.NotFoundVerification;
import lucaslsl.vehiclerental.api.util.ResourcePagination;
import lucaslsl.vehiclerental.api.util.ValidationErrorsVerification;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import redis.clients.jedis.Jedis;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.post;

/**
 *
 * @author lucaslsl
 */
public class ReservationController implements ApiController{
    
    @Override
    public Route create(){
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Map params = JsonHelper.toMap(req.body());
            Date startDate = Date.valueOf(params.get("start_date").toString());
            Date endDate = Date.valueOf(params.get("end_date").toString());
            params.remove("start_date");
            params.remove("end_date");
            
            Base.open(cp.getDataSource());
            Reservation reservation = new Reservation();
            reservation.fromMap(params);
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(reservation, Reservation.class.getSimpleName());
            res.status(201);
            return reservation.toJson(false);
        };
    }
    
    @Override
    public Route read() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Reservation reservation = Reservation.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":reservationId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(reservation, Reservation.class.getSimpleName());
            res.status(200);
            return reservation.toJson(false);
        }; 
    }
    
    @Override
    public Route update() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Map params = JsonHelper.toMap(req.body());
            Date startDate = Date.valueOf(params.get("start_date").toString());
            Date endDate = Date.valueOf(params.get("end_date").toString());
            params.remove("start_date");
            params.remove("end_date");
            Base.open(cp.getDataSource());
            Reservation reservation = Reservation.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":reservationId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(reservation, Reservation.class.getSimpleName());
            reservation.fromMap(params);
            Base.open(cp.getDataSource());
            reservation.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(reservation, Reservation.class.getSimpleName());
            res.status(200);
            return reservation.toJson(false);
        };
    }
    
    @Override
    public Route destroy() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Reservation reservation = Reservation.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":reservationId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(reservation, Reservation.class.getSimpleName());
            reservation.setIsActive(false);
            reservation.setIsDeleted(true);
            Base.open(cp.getDataSource());
            reservation.save();
            Base.close();
            res.status(204);
            return "";
        };
    }
    
    @Override
    public Route list() {
        return (Request req, Response res) -> {
            ResourcePagination pagination = new ResourcePagination(req);
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            
            LazyList<Reservation> reservations = Reservation
                    .where("is_deleted=?",false)
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset())
                    .orderBy("id desc")
                    .include(Vehicle.class,Customer.class);
            
            Long totalCount = Reservation.count("is_deleted=?",false);
            String reservationsJson = reservations.toJson(false).replaceAll(",]", "]");
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return reservationsJson;
        };
    }
    
    public Route reservationToken() {
        return (Request req, Response res) -> {
//            String customerId = req.queryMap("customer_id").value();
            String startDate = req.queryMap("start_date").value();
            String endDate = req.queryMap("end_date").value();
            String vehicleId = req.queryMap("vehicle_id").value();
            String reservationToken = 
                    "{\"vehicle_id\":"+ vehicleId +",\"start_date\":\""+startDate+"\",\"end_date\":\""+endDate+"\"}";
            String responseToken = "{\"reserved\": false}";
            Jedis jedis = null;
            try {
                jedis = RedisConnectionProvider.getInstance().getConnection();
                boolean exists = jedis.exists(reservationToken);
                
                if(!exists){
                    jedis.set(reservationToken, reservationToken);
                    jedis.expire(reservationToken, 180);
                    responseToken = reservationToken;
                    res.status(200);
                }else{
                    res.status(422);
                }     
                
            } finally{
                jedis.close();
            }
                   
            return responseToken;
        }; 
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/token/reserve",                 new HasValidContentType());
        get("/api/v1/token/reserve",                    this.reservationToken());
        before("/api/v1/reservations",                   new HasValidContentType());
        before("/api/v1/reservations/:reservationId",    new HasValidContentType());
        post("/api/v1/reservations",                     this.create()); 
        get("/api/v1/reservations/token/reserve",        this.reservationToken());
        get("/api/v1/reservations/:reservationId",       this.read());
        patch("/api/v1/reservations/:reservationId",     this.update());
        delete("/api/v1/reservations/:reservationId",    this.destroy());
        get("/api/v1/reservations",                      this.list());
    }
    
}
