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
public class AdditionalInformation extends Model{
    
    static{
        validatePresenceOf("key","value");
    }
        
    public String getKey(){
        return this.getString("key");
    }
    
    public void setKey(String value){
        this.setString("key", value);
    }
    
    public String getValue(){
        return this.getString("value");
    }
    
    public void setValue(String value){
        this.setString("value", value);
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
