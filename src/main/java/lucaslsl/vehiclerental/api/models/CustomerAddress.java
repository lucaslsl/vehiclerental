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
public class CustomerAddress extends Model{
    
    static{
        validatePresenceOf("customer_id","nickname","formatted_address","locality");
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
    
    public String getFormattedAddress(){
        return this.getString("formatted_address");
    }
    
    public void setFormattedAddress(String value){
        this.setString("formatted_address", value);
    }
    
    public String getLocality(){
        return this.getString("locality");
    }
    
    public void setLocality(String value){
        this.setString("locality", value);
    }
    
    public String getCountry(){
        return this.getString("country");
    }
    
    public void setCountry(String value){
        this.setString("country", value);
    }
    
    public String getPostalCode(){
        return this.getString("postal_code");
    }
    
    public void setPostalCode(String value){
        this.setString("postal_code", value);
    }
    
    public String getGooglePlaceId(){
        return this.getString("google_place_id");
    }
    
    public void setGooglePlaceId(String value){
        this.setString("google_place_id", value);
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
