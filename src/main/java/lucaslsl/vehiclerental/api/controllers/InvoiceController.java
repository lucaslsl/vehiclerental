/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.Invoice;
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
public class InvoiceController implements ApiController{
    
    @Override
    public Route create(){
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Invoice invoice = new Invoice();
            invoice.fromMap(JsonHelper.toMap(req.body()));
            invoice.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(invoice, Invoice.class.getSimpleName());
            res.status(201);
            return invoice.toJson(false);
        };
    }
    
    @Override
    public Route read() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Invoice invoice = Invoice.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":invoiceId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(invoice, Invoice.class.getSimpleName());
            res.status(200);
            return invoice.toJson(false);
        }; 
    }
    
    @Override
    public Route update() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Invoice invoice = Invoice.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":invoiceId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(invoice, Invoice.class.getSimpleName());
            invoice.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            invoice.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(invoice, Invoice.class.getSimpleName());
            res.status(200);
            return invoice.toJson(false);
        };
    }
    
    @Override
    public Route destroy() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Invoice invoice = Invoice.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":invoiceId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(invoice, Invoice.class.getSimpleName());
            invoice.setIsActive(false);
            invoice.setIsDeleted(true);
            Base.open(cp.getDataSource());
            invoice.save();
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
            
            LazyList<Invoice> invoices = Invoice
                    .find("is_deleted=?",false)
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = Invoice.count("is_deleted=?",false);
            String invoicesJson = invoices.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return invoicesJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/invoices",               new HasValidContentType());
        before("/api/v1/invoices/:invoiceId",    new HasValidContentType());
        post("/api/v1/invoices",                 this.create());        
        get("/api/v1/invoices/:invoiceId",       this.read());
        patch("/api/v1/invoices/:invoiceId",     this.update());
        delete("/api/v1/invoices/:invoiceId",    this.destroy());
        get("/api/v1/invoices",                  this.list());
    }
    
}
