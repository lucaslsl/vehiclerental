/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.ValidatorAdapter;

/**
 *
 * @author lucaslsl
 */
public class SexISOValidator extends ValidatorAdapter{

    @Override
    public void validate(Model model) {
        boolean valid = true;
        int sexCode = model.getInteger("sex");
        if(sexCode!= 0 && sexCode!=1 && sexCode!=2 && sexCode!=9){
            valid = false;
        }
        if(!valid){
            model.addValidator(this, "sex");
        }
    }
    
}
