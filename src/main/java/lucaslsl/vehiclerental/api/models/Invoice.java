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
parents     = { Rental.class, Accident.class}, 
typeLabels  = {"Rental",     "Accident"}
)
public class Invoice extends Model{
    
    static{
        validatePresenceOf("customer_id","subtotal","discount","total");
    }
    
    public Long getCustomerId(){
        return this.getLong("customer_id");
    }
    
    public void setCustomerId(Long value){
        this.setLong("customer_id", value);
    }
    
    public String getDescription(){
        return this.getString("description");
    }
    
    public void setDescription(String value){
        this.setString("description", value);
    }
    
    public Long getSubtotal(){
        return this.getLong("subtotal");
    }
    
    public void setSubtotal(Long value){
        this.setLong("subtotal", value);
    }
    
    public Long getDiscount(){
        return this.getLong("discount");
    }
    
    public void setDiscount(Long value){
        this.setLong("discount", value);
    }
    
    public Long getTotal(){
        return this.getLong("total");
    }
    
    public void setTotal(Long value){
        this.setLong("total", value);
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
    
    public boolean isClosed(){
        return this.getBoolean("is_closed");
    }
    
    public void setIsClosed(boolean value){
        this.setBoolean("is_closed", value);
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
