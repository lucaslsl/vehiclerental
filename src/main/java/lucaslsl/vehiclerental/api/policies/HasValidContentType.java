/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.policies;

import spark.Filter;
import spark.Request;
import spark.Response;
import static spark.Spark.halt;

/**
 *
 * @author lucaslsl
 */
public class HasValidContentType implements Filter{

    @Override
    public void handle(Request req, Response res) throws Exception {
        String contentType = req.contentType();
        String jsonResponse = "{\"message\":\"Invalid Request\",\"errors\":[{\"code\":\"invalid_header\",\"field\":\"Content-Type\"}]}";
        res.type("application/json");
        if(contentType==null){
            halt(415,jsonResponse);
        }else if(!contentType.equalsIgnoreCase("application/json")){
            halt(415,jsonResponse);
        }
        
    }

}
