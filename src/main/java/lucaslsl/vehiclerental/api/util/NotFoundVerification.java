/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import org.javalite.activejdbc.Model;
import static spark.Spark.halt;
import static spark.Spark.halt;

/**
 *
 * @author lucaslsl
 */
public class NotFoundVerification implements ModelVerificationStrategy{

    @Override
    public void verify(Model obj,String resource) {
        if(obj==null){
            String jsonResponse = "{\"message\":\"Resource Not Found\",\"errors\":[{\"resource\":\""+
                    resource +"\",\"code\":\"missing\"}]}";
            halt(404,jsonResponse);
        }
    }
    
}
