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
public interface ModelVerificationStrategy {
    public void verify(Model obj,String resource);
}
