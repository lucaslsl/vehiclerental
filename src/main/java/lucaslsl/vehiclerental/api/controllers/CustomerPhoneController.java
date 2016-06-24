/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.Customer;
import lucaslsl.vehiclerental.api.models.CustomerAddress;
import lucaslsl.vehiclerental.api.models.CustomerPhone;
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
public class CustomerPhoneController implements ApiController{
    
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
            CustomerPhone customerPhone = new CustomerPhone();
            customerPhone.fromMap(JsonHelper.toMap(req.body()));
            customerPhone.setCustomerId(customer.getLongId());
            Base.open(cp.getDataSource());
            customerPhone.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerPhone, CustomerPhone.class.getSimpleName());
            res.status(201);
            return customerPhone.toJson(false);
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
            Base.open(cp.getDataSource());
            CustomerPhone customerPhone = CustomerPhone.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":phoneId")));
            Base.close();
            ctx.executeStrategy(customerPhone, CustomerAddress.class.getSimpleName());
            res.status(200);
            return customerPhone.toJson(false);
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
            Base.open(cp.getDataSource());
            CustomerPhone customerPhone = CustomerPhone.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":phoneId")));
            Base.close();
            ctx.executeStrategy(customerPhone, CustomerPhone.class.getSimpleName());
            customerPhone.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            customerPhone.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerPhone, CustomerPhone.class.getSimpleName());
            res.status(200);
            return customerPhone.toJson(false);
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
            Base.open(cp.getDataSource());
            CustomerPhone customerPhone = CustomerPhone.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":phoneId")));
            Base.close();
            ctx.executeStrategy(customerPhone, CustomerPhone.class.getSimpleName());
            Base.open(cp.getDataSource());
            customerPhone.delete();
            Base.close();
            res.status(204);
            return "";
        };
    }

    @Override
    public Route list() {
        return (Request req, Response res) -> {
            ResourcePagination pagination = new ResourcePagination(req);
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Customer customer = Customer.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":customerId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(customer, Customer.class.getSimpleName());
            Base.open(cp.getDataSource());
            LazyList<CustomerPhone> customerPhones = CustomerPhone
                    .find("customer_id=?",customer.getLongId())
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = CustomerPhone.count("customer_id=?",customer.getLongId());
            String customerPhonesJson = customerPhones.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return customerPhonesJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/customers/:customerId/phones",          new HasValidContentType());
        before("/api/v1/customers/:customerId/phones/:phoneId", new HasValidContentType());
        post("/api/v1/customers/:customerId/phones",            this.create());        
        get("/api/v1/customers/:customerId/phones/:phoneId",    this.read());
        patch("/api/v1/customers/:customerId/phones/:phoneId",  this.update());
        delete("/api/v1/customers/:customerId/phones/:phoneId", this.destroy());
        get("/api/v1/customers/:customerId/phones",             this.list());
    }
    
}
