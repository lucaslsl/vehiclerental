/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.models;

import java.sql.Timestamp;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;

/**
 *
 * @author lucaslsl
 */

public class Vehicle extends Model{
    
    static{
        validatePresenceOf("vehicle_brand_id","vehicle_class_id","name","production_year","vin");
    }
    
    public Long getVehicleBrandId(){
        return this.getLong("vehicle_brand_id");
    }
    
    public void setVehicleBrandId(Long value){
        this.setLong("vehicle_brand_id", value);
    }
    
    public Long getVehicleClassId(){
        return this.getLong("vehicle_class_id");
    }
    
    public void setVehicleClassId(Long value){
        this.setLong("vehicle_class_id", value);
    }
    
    public String getName(){
        return this.getString("name");
    }
    
    public void setName(String value){
        this.setString("name", value);
    }
    
    public Integer getProductionYear(){
        return this.getInteger("production_year");
    }
    
    public void setProductionYear(Integer value){
        this.setInteger("production_year", value);
    }
    
    public String getVin(){
        return this.getString("vin");
    }
    
    public void setVin(String value){
        this.setString("vin", value);
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
