/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

/**
 *
 * @author lucaslsl
 */
public class ApiConfigs {
    
    private String getEnvironment(){
        
        String env = System.getenv("ENV");
        if(env!=null){
            if(env.equalsIgnoreCase("PRODUCTION")){
                return "PRODUCTION";
            }
            if(env.equalsIgnoreCase("STAGING")){
                return "STAGING";
            }
        }
        
        return "DEVELOPMENT";
    }
    
    public boolean isDevelopment(){
        return getEnvironment().equalsIgnoreCase("DEVELOPMENT");
    }
    
    public boolean isStaging(){
        return getEnvironment().equalsIgnoreCase("STAGING");
    }
    
    public boolean isProduction(){
        return getEnvironment().equalsIgnoreCase("PRODUCTION");
    }
    
}
