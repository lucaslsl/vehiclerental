/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.models;

import java.sql.Timestamp;
import org.javalite.activejdbc.Model;

/**
 *
 * @author lucaslsl
 */
public class CustomerPhone extends Model{
    
    static{
        validatePresenceOf("customer_id","nickname","country_code","area_code","number");
    }
    
    public Long getCustomerId(){
        return this.getLong("customer_id");
    }
    
    public void setCustomerId(Long value){
        this.setLong("customer_id", value);
    }
    
    public String getNickname(){
        return this.getString("nickname");
    }
    
    public void setNickname(String value){
        this.setString("nickname", value);
    }
    
    public String getCountryCode(){
        return this.getString("country_code");
    }
    
    public void setCountryCode(String value){
        this.setString("country_code", value);
    }
    
    public String getAreaCode(){
        return this.getString("area_code");
    }
    
    public void setAreaCode(String value){
        this.setString("area_code", value);
    }
    
    public String getNumber(){
        return this.getString("number");
    }
    
    public void setNumber(String value){
        this.setString("number", value);
    }
    
    public Boolean getIsDefault(){
        return this.getBoolean("is_default");
    }
    
    public void setIsDefault(Boolean value){
        this.setBoolean("is_default", value);
    }
    
    public Timestamp getCreatedAt(){
        return this.getTimestamp("created_at");
    }
    
    public void setCreatedAt(Timestamp value){
        this.setTimestamp("created_at", value);
    }
    
    public Timestamp getUpdatedAt(){
        return this.getTimestamp("updated_at");
    }
    
    public void setUpdatedAt(Timestamp value){
        this.setTimestamp("updated_at", value);
    }
    
}
