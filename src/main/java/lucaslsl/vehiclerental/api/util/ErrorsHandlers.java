/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.exception;
import static spark.Spark.get;

/**
 *
 * @author lucaslsl
 */
public class ErrorsHandlers {
    
    public Route notFound(){
        return (Request req, Response res) ->{
            String jsonResponse = "{\"message\":\"Not Found\",\"errors\":[]}";
            res.status(404);
            return jsonResponse;
        };
    }
    
    public ExceptionHandler internalError(){
        return (Exception exception,Request req, Response res) -> {
            boolean developmentEnvironment = true;
            String env = System.getenv("ENV");
            if(System.getenv("ENV")!=null){
                developmentEnvironment = env.equalsIgnoreCase("DEVELOPMENT");
            }
            String jsonResponse;
            if(developmentEnvironment){
                exception.printStackTrace();
                jsonResponse = "{\"message\":\"Internal Error\"," + 
                        "\"errors\":[{\"message\":\"" + exception.getMessage() + "\"}]}";
            }else{
                jsonResponse = "{\"message\":\"Internal Error\"}";
            }
            res.status(500);
            res.body(jsonResponse);
            res.type("application/json");
        };
    }
    
    public void initHandlers(){
        get("*",                   this.notFound());
        exception(Exception.class, this.internalError());
    }
    
}
