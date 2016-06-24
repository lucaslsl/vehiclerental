/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import java.sql.Date;

/**
 *
 * @author lucaslsl
 */
public class DateParser {
    
    public static Date fromMapObject(Object obj){
        if(obj!=null){
            String datePassed = obj.toString();
            if(datePassed.matches("\\d{4}-\\d{2}-\\d{2}")){
                return Date.valueOf(datePassed);
            }
        }
        
        return null;
    }
    
}
