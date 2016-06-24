/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.models;

import java.sql.Timestamp;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsToPolymorphic;

/**
 *
 * @author lucaslsl
 */
@BelongsToPolymorphic(
parents     = { Vehicle.class, Customer.class, Rental.class, Accident.class}, 
typeLabels  = {"Vehicle",     "Customer",     "Rental",     "Accident"}
)
public class File extends Model{
    
    static{
        validatePresenceOf("display_name","name","mimetype","size");
    }
    
    public Long getObjectId(){
        return this.getLong("object_id");
    }
          
    public void setObjectId(Long value){
        this.setLong("object_id", value);
    }
    
    public String getDisplayName(){
        return this.getString("display_name");
    }
    
    public void setDisplayName(String value){
        this.setString("display_name", value);
    }
    
    public String getName(){
        return this.getString("name");
    }
    
    public void setName(String value){
        this.setString("name", value);
    }
    
    public String getMimetype(){
        return this.getString("mimetype");
    }
    
    public void setMimetype(String value){
        this.setString("mimetype", value);
    }
    
    public Long getSize(){
        return this.getLong("size");
    }
          
    public void setSize(Long value){
        this.setLong("size", value);
    }
    
    public Long getParentId(){
        return this.getLong("parent_id");
    }
    
    public void setParentId(Long value){
        this.setLong("parent_id", value);
    }
    
    public String getParentType(){
        return this.getString("parent_type");
    }
    
    public void setParentType(String value){
        this.setString("parent_type", value);
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
