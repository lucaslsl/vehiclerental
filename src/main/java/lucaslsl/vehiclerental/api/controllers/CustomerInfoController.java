/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.AdditionalInformation;
import lucaslsl.vehiclerental.api.models.Customer;
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
public class CustomerInfoController implements ApiController{
    
    @Override
    public Route create() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Customer customer = Customer.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":customerId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            AdditionalInformation info = new AdditionalInformation();
            info.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            info.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(info, AdditionalInformation.class.getSimpleName());
            Base.open(cp.getDataSource());
            customer.add(info);
            Base.close();
            res.status(201);
            return info.toJson(false);
        };
    }
    
    @Override
    public Route read() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            AdditionalInformation info = AdditionalInformation.findFirst("id=? and parent_id=? and parent_type=?",
                    Long.valueOf(req.params(":infoId")),
                    Long.valueOf(req.params(":customerId")),
                    Customer.class.getSimpleName());
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(info, AdditionalInformation.class.getSimpleName());
            res.status(200);
            return info.toJson(false);
        }; 
    }
    
    @Override
    public Route update() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            AdditionalInformation info = AdditionalInformation.findFirst("id=? and parent_id=? and parent_type=?",
                    Long.valueOf(req.params(":infoId")),
                    Long.valueOf(req.params(":customerId")),
                    Customer.class.getSimpleName());
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(info, AdditionalInformation.class.getSimpleName());
            info.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            info.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(info, AdditionalInformation.class.getSimpleName());
            res.status(200);
            return info.toJson(false);
        };
    }
    
    @Override
    public Route destroy() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            AdditionalInformation info = AdditionalInformation.findFirst("id=? and parent_id=? and parent_type=?",
                    Long.valueOf(req.params(":infoId")),
                    Long.valueOf(req.params(":customerId")),
                    Customer.class.getSimpleName());
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(info, AdditionalInformation.class.getSimpleName());
            Base.open(cp.getDataSource());
            info.delete();
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
            
            LazyList<AdditionalInformation> infos = AdditionalInformation
                    .where("parent_id=? and parent_type=?",
                            Long.valueOf(req.params(":customerId")),
                            Customer.class.getSimpleName())
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset())
                    .orderBy("id desc");
            
            Long totalCount = AdditionalInformation.count("parent_id=? and parent_type=?",
                            Long.valueOf(req.params(":customerId")),
                            Customer.class.getSimpleName());
            String infosJson = infos.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return infosJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/customers/:customerId/infos",         new HasValidContentType());
        before("/api/v1/customers/:customerId/infos/:infoId", new HasValidContentType());
        post("/api/v1/customers/:customerId/infos",           this.create());        
        get("/api/v1/customers/:customerId/infos/:infoId",    this.read());
        patch("/api/v1/customers/:customerId/infos/:infoId",  this.update());
        delete("/api/v1/customers/:customerId/infos/:infoId", this.destroy());
        get("/api/v1/customers/:customerId/infos",            this.list());
    }
    
}
