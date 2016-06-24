/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.controllers;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 *
 * @author lucaslsl
 */
public interface ApiController {
    
    default Route create(){
        return (Request req, Response res) -> {
            res.status(200);
            return "";
        };
    }
    
    default Route read(){
        return (Request req, Response res) -> {
            res.status(200);
            return "";
        };
    }
    
    default Route update(){
        return (Request req, Response res) -> {
            res.status(200);
            return "";
        };
    }
    
    default Route destroy(){
        return (Request req, Response res) -> {
            res.status(200);
            return "";
        };
    }
    
    default Route list(){
        return (Request req, Response res) -> {
            res.status(200);
            return "";
        };
    }
    
    default void initEndpoints(){
    }
    
}
