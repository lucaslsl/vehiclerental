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
public class VehicleBrand extends Model{
    
    static{
        validatePresenceOf("name");
    }
    
    public String getName(){
        return this.getString("name");
    }
    
    public void setName(String value){
        this.setString("name", value);
    }
    
    public String getDescription(){
        return this.getString("description");
    }
    
    public void setDescription(String value){
        this.setString("description", value);
    }
    
    public boolean isActive(){
        return this.getBoolean("is_active");
    }
    
    public void setIsActive(boolean value){
        this.setBoolean("is_active", value);
    }
    
    public boolean isDeleted(){
        return this.getBoolean("is_deleted");
    }
    
    public void setIsDeleted(boolean value){
        this.setBoolean("is_deleted", value);
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
