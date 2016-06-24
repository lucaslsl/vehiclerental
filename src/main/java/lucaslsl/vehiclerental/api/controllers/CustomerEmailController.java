/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.Customer;
import lucaslsl.vehiclerental.api.models.CustomerEmail;
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

/**
 *
 * @author lucaslsl
 */
public class CustomerEmailController implements ApiController{
    
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
            CustomerEmail customerEmail = new CustomerEmail();
            customerEmail.fromMap(JsonHelper.toMap(req.body()));
            customerEmail.setCustomerId(customer.getLongId());
            Base.open(cp.getDataSource());
            customerEmail.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerEmail, CustomerEmail.class.getSimpleName());
            res.status(201);
            return customerEmail.toJson(false);
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
            CustomerEmail customerEmail = CustomerEmail.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":emailId")));
            Base.close();
            ctx.executeStrategy(customerEmail, CustomerEmail.class.getSimpleName());
            res.status(200);
            return customerEmail.toJson(false);
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
            CustomerEmail customerEmail = CustomerEmail.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":emailId")));
            Base.close();
            ctx.executeStrategy(customerEmail, CustomerEmail.class.getSimpleName());
            customerEmail.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            customerEmail.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerEmail, CustomerEmail.class.getSimpleName());
            res.status(200);
            return customerEmail.toJson(false);
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
            CustomerEmail customerEmail = CustomerEmail.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":emailId")));
            Base.close();
            ctx.executeStrategy(customerEmail, CustomerEmail.class.getSimpleName());
            Base.open(cp.getDataSource());
            customerEmail.delete();
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
            LazyList<CustomerEmail> customerEmails = CustomerEmail
                    .find("customer_id=?",customer.getLongId())
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = CustomerEmail.count("customer_id=?",customer.getLongId());
            String customerEmailsJson = customerEmails.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return customerEmailsJson;
        };
    }
    
}
