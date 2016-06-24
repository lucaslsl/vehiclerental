/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.models;

import java.sql.Date;
import java.sql.Timestamp;
import lucaslsl.vehiclerental.api.util.SexISOValidator;
import org.javalite.activejdbc.Model;

/**
 *
 * @author lucaslsl
 */
public class Customer extends Model{
    
    static{
        validatePresenceOf("first_name","last_name","sex","birthdate");
        validateNumericalityOf("sex").onlyInteger();
        validateWith(new SexISOValidator()).message("Invalid iso code for sex");
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
    
    public Integer getSex(){
        return this.getInteger("sex");
    }
    
    public void setSex(Integer value){
        this.setInteger("sex", value);
    }
    
    public Date getBirthdate(){
        return this.getDate("birthdate");
    }
    
    public void setBirthdate(Date value){
        this.setDate("birthdate", value);
    }
    
    public Boolean getIsSingle(){
        return this.getBoolean("is_single");
    }
    
    public void setIsSingle(Boolean value){
        this.setBoolean("is_single", value);
    }
    
    public String getMaritalStatusDescription(){
        return this.getString("marital_status_description");
    }
    
    public void setMaritalStatusDescription(String value){
        this.setString("marital_status_description", value);
    }
    
    public String getParentOneName(){
        return this.getString("parent_one_name");
    }
    
    public void setParentOneName(String value){
        this.setString("parent_one_name", value);
    }
    
    public String getParentTwoName(){
        return this.getString("parent_two_name");
    }
    
    public void setParentTwoName(String value){
        this.setString("parent_two_name", value);
    }
    
    public String getNationality(){
        return this.getString("nationality");
    }
    
    public void setNationality(String value){
        this.setString("nationality", value);
    }
    
    public String getBirthplace(){
        return this.getString("birthplace");
    }
    
    public void setBirthplace(String value){
        this.setString("birthplace", value);
    }
    
    public String getDrivingLicenseNumber(){
        return this.getString("driving_license_number");
    }
    
    public void setDrivingLicenseNumber(String value){
        this.setString("driving_license_number", value);
    }
    
    public Date getDrivingLicenseExpiryDate(){
        return this.getDate("driving_license_expiry_date");
    }
    
    public void setDrivingLicenseExpiryDate(Date value){
        this.setDate("driving_license_expiry_date", value);
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
