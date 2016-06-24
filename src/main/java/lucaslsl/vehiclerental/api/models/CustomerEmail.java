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
public class CustomerEmail extends Model{
    
    static{
        validatePresenceOf("customer_id","nickname","address");
        validateEmailOf("address");
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
    
    public String getAddress(){
        return this.getString("address");
    }
    
    public void setAddress(String value){
        this.setString("address", value);
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
