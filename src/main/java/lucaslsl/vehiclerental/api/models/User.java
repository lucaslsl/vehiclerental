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
public class User extends Model{
    
    static{
        validatePresenceOf("first_name", "last_name", "username","password");
    }
    
    public String getFirstName(){
        return this.getString("first_name");
    }
    
    public void setFirstName(String value){
        this.setString("first_name", value);
    }
    
    public String getLastName(){
        return this.getString("last_name");
    }
    
    public void setLastName(String value){
        this.setString("last_name", value);
    }
    
    public String getUsername(){
        return this.getString("username");
    }
    
    public void setUsername(String value){
        this.setString("username", value);
    }
    
    public String getPassword(){
        return this.getString("password");
    }
    
    public void setPassword(String value){
        this.setString("password", value);
    }
    
    public boolean isAdmin(){
        return this.getBoolean("is_admin");
    }
    
    public void setIsAdmin(boolean value){
        this.setBoolean("is_admin", value);
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
