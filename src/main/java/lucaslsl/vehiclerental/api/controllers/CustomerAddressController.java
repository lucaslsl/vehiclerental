/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.Customer;
import lucaslsl.vehiclerental.api.models.CustomerAddress;
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
public class CustomerAddressController implements ApiController{
    
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
            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.fromMap(JsonHelper.toMap(req.body()));
            customerAddress.setCustomerId(customer.getLongId());
            Base.open(cp.getDataSource());
            customerAddress.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerAddress, CustomerAddress.class.getSimpleName());
            res.status(201);
            return customerAddress.toJson(false);
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
            CustomerAddress customerAddress = CustomerAddress.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":addressId")));
            Base.close();
            ctx.executeStrategy(customerAddress, CustomerAddress.class.getSimpleName());
            res.status(200);
            return customerAddress.toJson(false);
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
            CustomerAddress customerAddress = CustomerAddress.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":addressId")));
            Base.close();
            ctx.executeStrategy(customerAddress, CustomerAddress.class.getSimpleName());
            customerAddress.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            customerAddress.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerAddress, CustomerAddress.class.getSimpleName());
            res.status(200);
            return customerAddress.toJson(false);
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
            CustomerAddress customerAddress = CustomerAddress.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":addressId")));
            Base.close();
            ctx.executeStrategy(customerAddress, CustomerAddress.class.getSimpleName());
            Base.open(cp.getDataSource());
            customerAddress.delete();
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
            LazyList<CustomerAddress> customerAddresses = CustomerAddress
                    .find("customer_id=?",customer.getLongId())
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = CustomerAddress.count("customer_id=?",customer.getLongId());
            String customerAddressesJson = customerAddresses.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return customerAddressesJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/customers/:customerId/addresses",            new HasValidContentType());
        before("/api/v1/customers/:customerId/addresses/:addressId", new HasValidContentType());
        post("/api/v1/customers/:customerId/addresses",              this.create());        
        get("/api/v1/customers/:customerId/addresses/:addressId",    this.read());
        patch("/api/v1/customers/:customerId/addresses/:addressId",  this.update());
        delete("/api/v1/customers/:customerId/addresses/:addressId", this.destroy());
        get("/api/v1/customers/:customerId/addresses",               this.list());
    }
    
}
