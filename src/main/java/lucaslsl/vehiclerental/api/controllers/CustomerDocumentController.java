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
import lucaslsl.vehiclerental.api.models.CustomerDocument;
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
public class CustomerDocumentController implements ApiController{
    
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
            Map params = JsonHelper.toMap(req.body());
            Date issueDate = DateParser.fromMapObject(params.get("issue_date"));
            params.remove("issue_date");            
            CustomerDocument customerDocument = new CustomerDocument();
            customerDocument.fromMap(params);
            customerDocument.setCustomerId(customer.getLongId());
            if(issueDate!=null)customerDocument.setIssueDate(issueDate);
            Base.open(cp.getDataSource());
            customerDocument.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerDocument, CustomerDocument.class.getSimpleName());
            res.status(201);
            return customerDocument.toJson(false);
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
            CustomerDocument customerDocument = CustomerDocument.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":documentId")));
            Base.close();
            ctx.executeStrategy(customerDocument, CustomerDocument.class.getSimpleName());
            res.status(200);
            return customerDocument.toJson(false);
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
            CustomerDocument customerDocument = CustomerDocument.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":documentId")));
            Base.close();
            ctx.executeStrategy(customerDocument, CustomerDocument.class.getSimpleName());
            Map params = JsonHelper.toMap(req.body());
            Date issueDate = DateParser.fromMapObject(params.get("issue_date"));
            params.remove("issue_date");
            if(issueDate!=null)customerDocument.setIssueDate(issueDate);
            customerDocument.fromMap(params);
            Base.open(cp.getDataSource());
            customerDocument.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(customerDocument, CustomerDocument.class.getSimpleName());
            res.status(200);
            return customerDocument.toJson(false);
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
            CustomerDocument customerDocument = CustomerDocument.findFirst("customer_id=? and id=?", customer.getLongId(),Long.valueOf(req.params(":documentId")));
            Base.close();
            ctx.executeStrategy(customerDocument, CustomerDocument.class.getSimpleName());
            Base.open(cp.getDataSource());
            customerDocument.delete();
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
            LazyList<CustomerDocument> customerDocuments = CustomerDocument
                    .find("customer_id=?",customer.getLongId())
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = CustomerDocument.count("customer_id=?",customer.getLongId());
            String customerDocumentsJson = customerDocuments.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return customerDocumentsJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/customers/:customerId/documents",              new HasValidContentType());
        before("/api/v1/customers/:customerId/documents/:documentId",  new HasValidContentType());
        post("/api/v1/customers/:customerId/documents",                this.create());        
        get("/api/v1/customers/:customerId/documents/:documentId",     this.read());
        patch("/api/v1/customers/:customerId/documents/:documentId",   this.update());
        delete("/api/v1/customers/:customerId/documents/:documentId",  this.destroy());
        get("/api/v1/customers/:customerId/documents",                 this.list());
    }
    
}
