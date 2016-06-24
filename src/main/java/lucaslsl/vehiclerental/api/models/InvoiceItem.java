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
public class InvoiceItem extends Model{
    
    static{
        validatePresenceOf("invoice_id","name","cost");
        validateNumericalityOf("cost");
    }
    
    public Long getInvoiceId(){
        return this.getLong("invoice_id");
    }
    
    public void setInvoiceId(Long value){
        this.setLong("invoice_id", value);
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
    
    public Long getCost(){
        return this.getLong("cost");
    }
    
    public void setCost(Long value){
        this.setLong("cost", value);
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
