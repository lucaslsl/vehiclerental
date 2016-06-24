/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import org.javalite.activejdbc.Model;
import static spark.Spark.halt;

/**
 *
 * @author lucaslsl
 */
public class ValidationErrorsVerification implements ModelVerificationStrategy{

    @Override
    public void verify(Model obj, String resource) {
        if(obj==null){
            halt(404);            
        }else{
            if(!obj.errors().isEmpty()){
                String errorsList = "[";
                
                errorsList = obj.errors().entrySet().stream().map((entry) -> {
                    return "{\"code\":\"invalid_field\",\"field\":\"" + entry.getKey() +"\"},";
                }).reduce(errorsList, String::concat);
                
                errorsList = errorsList.substring(0, errorsList.length()-1);
                errorsList += "]";
                String jsonResponse = "{\"message\":\"Validation Failed\",\"errors\":" + errorsList + "}";
                halt(422,jsonResponse);
            }
        }
    }
    
}
