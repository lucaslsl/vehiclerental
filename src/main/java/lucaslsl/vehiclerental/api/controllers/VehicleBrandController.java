/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.VehicleBrand;
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
public class VehicleBrandController implements ApiController{
    
    @Override
    public Route create(){
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            VehicleBrand vehicleBrand = new VehicleBrand();
            vehicleBrand.fromMap(JsonHelper.toMap(req.body()));
            vehicleBrand.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(vehicleBrand, VehicleBrand.class.getSimpleName());
            res.status(201);
            return vehicleBrand.toJson(false);
        };
    }
    
    @Override
    public Route read() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            VehicleBrand vehicleBrand = VehicleBrand.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":vehicleBrandId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(vehicleBrand, VehicleBrand.class.getSimpleName());
            res.status(200);
            return vehicleBrand.toJson(false);
        }; 
    }
    
    @Override
    public Route update() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            VehicleBrand vehicleBrand = VehicleBrand.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":vehicleBrandId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(vehicleBrand, VehicleBrand.class.getSimpleName());
            vehicleBrand.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            vehicleBrand.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(vehicleBrand, VehicleBrand.class.getSimpleName());
            res.status(200);
            return vehicleBrand.toJson(false);
        };
    }
    
    @Override
    public Route destroy() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            VehicleBrand vehicleBrand = VehicleBrand.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":vehicleBrandId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(vehicleBrand, VehicleBrand.class.getSimpleName());
            vehicleBrand.setIsActive(false);
            vehicleBrand.setIsDeleted(true);
            Base.open(cp.getDataSource());
            vehicleBrand.save();
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
            LazyList<VehicleBrand> vehicleBrands = VehicleBrand
                    .find("is_deleted=?",false)
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            Base.close();
            Base.open(cp.getDataSource());
            Long totalCount = VehicleBrand.count("is_deleted=?",false);
            Base.close();
            Base.open(cp.getDataSource());
            String vehicleBrandsJson = vehicleBrands.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return vehicleBrandsJson;
        };
    }
    
    public Route search() {
        return (Request req, Response res) -> {
            ResourcePagination pagination = new ResourcePagination(req);
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            LazyList<VehicleBrand> vehicleBrands = VehicleBrand
                    .where("is_deleted=? and unaccent(\"name\") ILIKE unaccent(?)",false,"\'%" + req.queryMap("name").value() + "%\'")
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            Base.close();
            Base.open(cp.getDataSource());
            Long totalCount = VehicleBrand.count("is_deleted=?",false);
            Base.close();
            Base.open(cp.getDataSource());
            String vehicleBrandsJson = vehicleBrands.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return vehicleBrandsJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/vehicle_brands",                    new HasValidContentType());
        before("/api/v1/vehicle_brands/:vehicleBrandId",    new HasValidContentType());
        post("/api/v1/vehicle_brands",                      this.create());
        get("/api/v1/vehicle_brands/search",                this.search());
        get("/api/v1/vehicle_brands/:vehicleBrandId",       this.read());
        patch("/api/v1/vehicle_brands/:vehicleBrandId",     this.update());
        delete("/api/v1/vehicle_brands/:vehicleBrandId",    this.destroy());       
        get("/api/v1/vehicle_brands",                       this.list());
    }
    
}
