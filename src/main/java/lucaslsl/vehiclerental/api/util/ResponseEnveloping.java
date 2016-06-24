/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 *
 * @author lucaslsl
 */
public class ResponseEnveloping {
    
    public String envelopeJson(Map<String,String> map){
        String jsonResponse = "{";
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            jsonResponse += "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
        }
        jsonResponse = jsonResponse.substring(0, jsonResponse.length()-1);
        jsonResponse += "}";
                
        return jsonResponse;
    }
    
}
