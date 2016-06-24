/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.models.File;
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
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 *
 * @author lucaslsl
 */
public class FileController implements ApiController{
    
    @Override
    public Route create(){
        return (Request req, Response res) -> {
            boolean developmentEnvironment = true;
            String env = System.getenv("ENV");
            if(System.getenv("ENV")!=null){
                developmentEnvironment = env.equalsIgnoreCase("DEVELOPMENT");
            }
            ConnectionProvider cp = ConnectionProvider.getInstance();
            String fileName = "untitled";
            String parentType = "";
            Long parentId = null;
            if(req.queryMap("name").hasValue()) fileName = req.queryMap("name").value();
            if(req.queryMap("parent_id").hasValue()) parentId = req.queryMap("parent_id").longValue();
            if(req.queryMap("parent_type").hasValue()) fileName = req.queryMap("parent_type").value();
            File file = null;
            try {
                Base.open(cp.getDataSource());
                Base.openTransaction();
                file = new File();
                file.setName(fileName);
                file.setDisplayName(fileName);
                file.setSize(new Long(req.contentLength()));
                file.setMimetype(req.contentType());
                file.setParentId(parentId);
                file.setParentType(parentType);
                file.save();
                if(developmentEnvironment){
                    Path out = Paths.get("uploads/" + file.getLongId() + "-" + file.getName());
                    Files.copy(req.raw().getInputStream(),out);
                }
                Base.commitTransaction();
            } catch (Exception e) {
                Base.rollbackTransaction();
            } finally{
                Base.close();

            }
            if(file==null){
                throw new Exception();
            }
            res.type("application/json");
            res.status(201);
            return file.toJson(false);
        };
    }
    
    @Override
    public Route read() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            File file = File.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":fileId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(file, File.class.getSimpleName());
            res.type("application/json");
            res.status(200);
            return file.toJson(false);
        }; 
    }
    
    @Override
    public Route update() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            File file = File.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":fileId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(file, File.class.getSimpleName());
            
            Map params = JsonHelper.toMap(req.body());
            if(params.containsKey("display_name")){
                file.setDisplayName(params.get("display_name").toString());
            }
            
            Base.open(cp.getDataSource());
            file.save();
            Base.close();
            ctx.setVerificationStrategy(new ValidationErrorsVerification());
            ctx.executeStrategy(file, File.class.getSimpleName());
            res.type("application/json");
            res.status(200);
            return file.toJson(false);
        };
    }
    
    @Override
    public Route destroy() {
        return (Request req, Response res) -> {
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            File file = File.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":fileId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(file, File.class.getSimpleName());
            file.setIsActive(false);
            file.setIsDeleted(true);
            Base.open(cp.getDataSource());
            file.save();
            Base.close();
            res.type("application/json");
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
            
            LazyList<File> files = File
                    .find("is_deleted=?",false)
                    .limit(pagination.getLimit())
                    .offset(pagination.getOffset()).orderBy("id desc");
            
            Long totalCount = File.count("is_deleted=?",false);
            String filesJson = files.toJson(false);
            Base.close();
            res.header("Total-Count", totalCount.toString());
            res.header("Link",pagination.generateLinkHeader(totalCount.intValue()));
            res.type("application/json");
            res.status(200);
            return filesJson;
        };
    }
    
    public Route download(){
        return (Request req, Response res) -> {
            boolean developmentEnvironment = true;
            String env = System.getenv("ENV");
            if(System.getenv("ENV")!=null){
                developmentEnvironment = env.equalsIgnoreCase("DEVELOPMENT");
            }
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            File file = File.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":fileId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(file, File.class.getSimpleName());
            res.type(file.getMimetype());
            res.status(200);
            res.header("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            res.header("Content-Length", file.getSize().toString());
            if(developmentEnvironment){
                Path source = Paths.get("uploads/" + file.getLongId() + "-" + file.getName());
                Files.copy(source, res.raw().getOutputStream());
            }
            return res.raw();
        };
    }
    
    public Route replace(){
        return (Request req, Response res) -> {
            boolean developmentEnvironment = true;
            String env = System.getenv("ENV");
            if(System.getenv("ENV")!=null){
                developmentEnvironment = env.equalsIgnoreCase("DEVELOPMENT");
            }
            ModelVerificationContext ctx = new ModelVerificationContext();
            ConnectionProvider cp = ConnectionProvider.getInstance();
            Base.open(cp.getDataSource());
            File file = File.findFirst("id=? and is_deleted=?",Long.valueOf(req.params(":fileId")),false);
            Base.close();
            ctx.setVerificationStrategy(new NotFoundVerification());
            ctx.executeStrategy(file, File.class.getSimpleName());
            try {
                Base.open(cp.getDataSource());
                Base.openTransaction();
                file.setSize(new Long(req.contentLength()));
                file.save();
                if(developmentEnvironment){
                    Path out = Paths.get("uploads/" + file.getLongId() + "-" + file.getName());
                    Files.delete(out);
                    Files.copy(req.raw().getInputStream(),out);
                }
                Base.commitTransaction();
            } catch (Exception e) {
                Base.rollbackTransaction();
            } finally{
                Base.close();

            }
            if(file==null){
                throw new Exception();
            }
            res.type("application/json");
            res.status(200);
            return file.toJson(false);
        };
    }
    
    @Override
    public void initEndpoints(){
        post("/api/v1/files",                       this.create());
        get("/api/v1/files/:fileId",                this.read());
        patch("/api/v1/files/:fileId",              this.update());
        delete("/api/v1/files/:fileId",             this.destroy());
        get("/api/v1/files",                        this.list());
        get("/api/v1/files/:fileId/download",       this.download());
        put("/api/v1/files/:fileId/replace",        this.replace());
    }
    
}
