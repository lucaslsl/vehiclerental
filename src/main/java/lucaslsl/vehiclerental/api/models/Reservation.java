/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.models;

import java.sql.Date;
import java.sql.Timestamp;
import org.javalite.activejdbc.Model;

/**
 *
 * @author lucaslsl
 */
public class Reservation extends Model{
    
    static{
        validatePresenceOf("customer_id","vehicle_id","start_date","end_date");
    }
    
    public Long getVehicleId(){
        return this.getLong("vehicle_id");
    }
    
    public void setVehicleId(Long value){
        this.setLong("vehicle_id", value);
    }
    
    public Long getCustomerId(){
        return this.getLong("customer_id");
    }
    
    public void setCustomerId(Long value){
        this.setLong("customer_id", value);
    }
    
    public Date getStartDate(){
        return this.getDate("start_date");
    }
    
    public void setStartDate(Date value){
        this.setDate("start_date", value);
    }
    
    public Date getEndDate(){
        return this.getDate("end_date");
    }
    
    public void setEndDate(Date value){
        this.setDate("end_date", value);
    }
    
    public String getRemarks(){
        return this.getString("remarks");
    }
    
    public void setRemarks(String value){
        this.setString("remarks", value);
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
