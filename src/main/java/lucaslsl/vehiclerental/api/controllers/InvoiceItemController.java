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
import lucaslsl.vehiclerental.api.models.Invoice;
import lucaslsl.vehiclerental.api.models.InvoiceItem;
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
public class InvoiceItemController implements ApiController{
    
   @Override
    public Route create() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            Invoice invoice = Invoice.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":invoiceId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(invoice, Invoice.class.getSimpleName());
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.fromMap(JsonHelper.toMap(req.body()));
            invoiceItem.setInvoiceId(invoice.getLongId());
            Base.open(cp.getDataSource());
            invoiceItem.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(invoiceItem, InvoiceItem.class.getSimpleName());
            res.status(201);
            return invoiceItem.toJson(false);
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
            Base.open(cp.getDataSource());
            InvoiceItem invoiceItem = InvoiceItem.findFirst("invoice_id=? and id=?", invoice.getLongId(),Long.valueOf(req.params(":itemId")));
            Base.close();
            ctx.executeStrategy(invoiceItem, InvoiceItem.class.getSimpleName());
            res.status(200);
            return invoiceItem.toJson(false);
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
            Base.open(cp.getDataSource());
            InvoiceItem invoiceItem = InvoiceItem.findFirst("invoice_id=? and id=?", invoice.getLongId(),Long.valueOf(req.params(":itemId")));
            Base.close();
            ctx.executeStrategy(invoiceItem, InvoiceItem.class.getSimpleName());
            invoiceItem.fromMap(JsonHelper.toMap(req.body()));
            Base.open(cp.getDataSource());
            invoiceItem.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(invoiceItem, InvoiceItem.class.getSimpleName());
            res.status(200);
            return invoiceItem.toJson(false);
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
            Base.open(cp.getDataSource());
            InvoiceItem invoiceItem = InvoiceItem.findFirst("invoice_id=? and id=?", invoice.getLongId(),Long.valueOf(req.params(":itemId")));
            Base.close();
            ctx.executeStrategy(invoiceItem, InvoiceItem.class.getSimpleName());
            Base.open(cp.getDataSource());
            invoiceItem.delete();
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
            Invoice invoice = Invoice.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":invoiceId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(invoice, Invoice.class.getSimpleName());
            Base.open(cp.getDataSource());
            LazyList<InvoiceItem> invoiceItems = InvoiceItem
                    .find("invoice_id=?",invoice.getLongId())
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = InvoiceItem.count("invoice_id=?",invoice.getLongId());
            String invoiceItemsJson = invoiceItems.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.status(200);
            return invoiceItemsJson;
        };
    }
    
    @Override
    public void initEndpoints(){
        before("/api/v1/invoices/:invoiceId/items",          new HasValidContentType());
        before("/api/v1/invoices/:invoiceId/items/:itemId",  new HasValidContentType());
        post("/api/v1/invoices/:invoiceId/items",            this.create());        
        get("/api/v1/invoices/:invoiceId/items/:itemId",     this.read());
        patch("/api/v1/invoices/:invoiceId/items/:itemId",   this.update());
        delete("/api/v1/invoices/:invoiceId/items/:itemId",  this.destroy());
        get("/api/v1/invoices/:invoiceId/items",             this.list());
    }
    
}
