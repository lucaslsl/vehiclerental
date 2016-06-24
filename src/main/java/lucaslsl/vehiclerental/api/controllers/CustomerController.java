/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import java.sql.Date;
import java.util.Map;
import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.Customer;
import lucaslsl.vehiclerental.api.policies.HasValidContentType;
import lucaslsl.vehiclerental.api.util.DateParser;
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
public class CustomerController implements ApiController{
    
    @Override
    public Route create() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Customer customer = new Customer();
            Map params = JsonHelper.toMap(req.body());
            Date birthdate = DateParser.fromMapObject(params.get("birthdate"));
            Date drivingLicenseExpiryDate = DateParser.fromMapObject(params.get("driving_license_expiry_date"));
            params.remove("birthdate");
            params.remove("driving_license_expiry_date");
            customer.fromMap(params);
            if(birthdate!=null)customer.setBirthdate(birthdate);
            if(drivingLicenseExpiryDate!=null)customer.setDrivingLicenseExpiryDate(drivingLicenseExpiryDate);
            Base.open(cp.getDataSource());
            customer.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            res.status(201);
            return customer.toJson(false);                
        };
    }

    @Override
    public Route read() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Customer customer = Customer.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":customerId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            res.status(200);
            return customer.toJson(false);
        };
    }

    @Override
    public Route update() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Customer customer = Customer.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":customerId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            Map params = JsonHelper.toMap(req.body());
            Date birthdate = DateParser.fromMapObject(params.get("birthdate"));
            Date drivingLicenseExpiryDate = DateParser.fromMapObject(params.get("driving_license_expiry_date"));
            params.remove("birthdate");
            params.remove("driving_license_issue_date");
            customer.fromMap(params);
            if(birthdate!=null)customer.setBirthdate(birthdate);
            if(drivingLicenseExpiryDate!=null)customer.setDrivingLicenseExpiryDate(drivingLicenseExpiryDate);
            Base.open(cp.getDataSource());
            customer.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            res.status(200);
            return customer.toJson(false);
        };
    }

    @Override
    public Route destroy() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Customer customer = Customer.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":customerId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            customer.setIsActive(false);
            customer.setIsDeleted(true);
            Base.open(cp.getDataSource());
            customer.save();
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
            
            LazyList<Customer> customers = Customer
                    .find("is_deleted=?",false)
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = Customer.count("is_deleted=?",false);
            String customersJson = customers.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return customersJson;
        };
    }
    
    public Route searchByDrivingLicense() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Customer customer = Customer.findFirst("driving_license_number=? and is_deleted=?",req.queryMap("driving_license_number").value(),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            res.status(200);
            return customer.toJson(false);
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/customers",                 new HasValidContentType());
        before("/api/v1/customers/:customerId",     new HasValidContentType());
        post("/api/v1/customers",                   this.create()); 
        get("/api/v1/customers/search/license",     this.searchByDrivingLicense());
        get("/api/v1/customers/:customerId",        this.read());
        patch("/api/v1/customers/:customerId",      this.update());
        delete("/api/v1/customers/:customerId",     this.destroy());
        get("/api/v1/customers",                    this.list());
    }
    
}
