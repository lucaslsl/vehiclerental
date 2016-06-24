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
public class CustomerDocument extends Model{
    
    static{
        validatePresenceOf("customer_id","name","number","issue_date");
    }
    
    public Long getCustomerId(){
        return this.getLong("customer_id");
    }
    
    public void setCustomerId(Long value){
        this.setLong("customer_id", value);
    }
    
    public String getName(){
        return this.getString("name");
    }
    
    public void setName(String value){
        this.setString("name", value);
    }
    
    public String getNumber(){
        return this.getString("number");
    }
    
    public void setNumber(String value){
        this.setString("number", value);
    }
    
    public String getAuthority(){
        return this.getString("authority");
    }
    
    public void setAuthority(String value){
        this.setString("authority", value);
    }
    
    public Date getIssueDate(){
        return this.getDate("issue_date");
    }
    
    public void setIssueDate(Date value){
        this.setDate("issue_date", value);
    }
    
    public String getObservations(){
        return this.getString("observations");
    }
    
    public void setObservations(String value){
        this.setString("observations", value);
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
