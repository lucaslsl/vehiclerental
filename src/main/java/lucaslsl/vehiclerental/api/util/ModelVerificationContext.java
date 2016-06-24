/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import org.javalite.activejdbc.Model;

/**
 *
 * @author lucaslsl
 */
public class ModelVerificationContext {
    
    private ModelVerificationStrategy strategy;
    
    public void setVerificationStrategy(ModelVerificationStrategy strategy){
        this.strategy = strategy;
    }
    
    public void executeStrategy(Model obj,String resource){
        this.strategy.verify(obj, resource);
    }
    
}
