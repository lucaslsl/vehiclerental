/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.AdditionalInformation;
import lucaslsl.vehiclerental.api.models.Vehicle;
import lucaslsl.vehiclerental.api.models.VehicleBrand;
import lucaslsl.vehiclerental.api.models.VehicleClass;
import lucaslsl.vehiclerental.api.policies.HasValidContentType;
import lucaslsl.vehiclerental.api.util.JsonHelper;
import lucaslsl.vehiclerental.api.util.ModelVerificationContext;
import lucaslsl.vehiclerental.api.util.NotFoundVerification;
import lucaslsl.vehiclerental.api.util.ResourcePagination;
import lucaslsl.vehiclerental.api.util.ValidationErrorsVerification;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
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
public class VehicleController implements ApiController{
    
    @Override
    public Route create(){
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Map params = JsonHelper.toMap(req.body());
            
            Vehicle vehicle = new Vehicle();
            Base.open(cp.getDataSource());
            vehicle.fromMap(params);
            
            // Verify uniqueness of Vehicle VIN
            Vehicle found = Vehicle.findFirst("vin=? and is_deleted=?",params.get("vin"),false);
            Base.close();
            ctx.executeStrategy(vehicle, Vehicle.class.getSimpleName());
            if(found!=null){
                vehicle.errors().put("vin", "already_exists");
            }
            ctx.executeStrategy(vehicle, Vehicle.class.getSimpleName());
            
            Base.open(cp.getDataSource());
            vehicle.save();
            Base.close();
            ctx.executeStrategy(vehicle, Vehicle.class.getSimpleName());
            res.status(201);
            return vehicle.toJson(false);
        };
    }
    
    @Override
    public Route read() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Vehicle vehicle = Vehicle.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":vehicleId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(vehicle, Vehicle.class.getSimpleName());
            res.status(200);
            return vehicle.toJson(false);
        }; 
    }
    
    @Override
    public Route update() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Vehicle vehicle = Vehicle.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":vehicleId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(vehicle, VehicleBrand.class.getSimpleName());
            Map params = JsonHelper.toMap(req.body());
            if(params.containsKey("vin"))params.remove("vin");
            vehicle.fromMap(params);
            Base.open(cp.getDataSource());
            vehicle.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(vehicle, Vehicle.class.getSimpleName());
            res.status(200);
            return vehicle.toJson(false);
        };
    }
    
    @Override
    public Route destroy() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Vehicle vehicle = Vehicle.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":vehicleId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(vehicle, Vehicle.class.getSimpleName());
            vehicle.setIsActive(false);
            vehicle.setIsDeleted(true);
            Base.open(cp.getDataSource());
            vehicle.save();
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
            LazyList<Vehicle> vehicles = Vehicle
                    .where("is_deleted=?",false)
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset())
                    .orderBy("id desc")
                    .include(VehicleClass.class,VehicleBrand.class,AdditionalInformation.class);
            Base.close();
            Base.open(cp.getDataSource());
            Long totalCount = Vehicle.count("is_deleted=?",false);
            Base.close();
            Base.open(cp.getDataSource());
            String vehiclesJson = vehicles.toJson(false).replaceAll(",]", "]");
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return vehiclesJson;
        };
    }
    
    public Route available() {
        return (Request req, Response res) -> {
            ResourcePagination pagination = new ResourcePagination(req);
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Date startDate = Date.valueOf(req.queryMap("start_date").value());
            Date endDate = Date.valueOf(req.queryMap("end_date").value());
            String query = "select * from vehicles where id not in ( " +
                                "select vehicle_id from reservations where (" + 
                                "start_date<=? and end_date>=? and is_deleted=false) or (" + 
                                "start_date<? and end_date>=? and is_deleted=false) or (" + 
                                "?<=start_date and ?>=start_date and is_deleted=false)) " + 
                                "and ?<=? and is_deleted=false";
            Base.open(cp.getDataSource());
            LazyList<Vehicle> vehicles = Vehicle
                    .findBySQL(query + " order by id desc limit ? offset ?",
                            startDate,startDate,endDate,endDate,startDate,endDate,startDate,endDate,
                            pagination.getLimit(),pagination.getOffset());
            Base.close();
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, startDate);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            stmt.setDate(4, endDate);
            stmt.setDate(5, startDate);
            stmt.setDate(6, endDate);
            stmt.setDate(7, startDate);
            stmt.setDate(8, endDate);
            ResultSet result = stmt.executeQuery();
            Long totalCount = new Long(0);
            while(result.next()){
                totalCount = result.getLong(1);
            }
            result.close();
            stmt.close();
            conn.close();
            Base.open(cp.getDataSource());
            String vehiclesJson = vehicles.toJson(false).replaceAll(",]", "]");
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return vehiclesJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/vehicles",                          new HasValidContentType());
        before("/api/v1/vehicles/:vehicleId",               new HasValidContentType());
        post("/api/v1/vehicles",                            this.create());
        get("/api/v1/vehicles/available",                   this.available());
        get("/api/v1/vehicles/:vehicleId",                  this.read());
        patch("/api/v1/vehicles/:vehicleId",                this.update());
        delete("/api/v1/vehicles/:vehicleId",               this.destroy());
        get("/api/v1/vehicles",                             this.list());
    }
    
}
