/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author lucaslsl
 */
public class JsonHelper {
    
    public static Map toMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
           return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Map[] toMaps(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } 
    }
    
}
