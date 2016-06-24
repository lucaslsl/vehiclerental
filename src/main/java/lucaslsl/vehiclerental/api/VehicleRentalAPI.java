/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api;

import java.sql.SQLException;
import lucaslsl.vehiclerental.api.controllers.*;
import lucaslsl.vehiclerental.api.db.ConnectionProvider;
import lucaslsl.vehiclerental.api.db.RedisConnectionProvider;
import lucaslsl.vehiclerental.api.util.ErrorsHandlers;
import static spark.Spark.port;

/**
 *
 * @author lucaslsl
 */
public class VehicleRentalAPI {
    
    public static void main(String[] args) throws SQLException {           
           

        port(Integer.valueOf(System.getenv("PORT")));
        
        
        // INITIALIZE DATABASE POOL
        ConnectionProvider.getInstance()          .init();
        
        // INITIALIZE CACHE
        RedisConnectionProvider.getInstance();
        
        // INITIALIZE CONTROLLERS AND ENDPOINTS
        new UserController()                      .initEndpoints();
        new FileController()                      .initEndpoints();
        new CustomerController()                  .initEndpoints();
        new CustomerPhoneController()             .initEndpoints();
        new CustomerAddressController()           .initEndpoints();
        new CustomerDocumentController()          .initEndpoints();
        new CustomerInfoController()              .initEndpoints();
        new VehicleClassController()              .initEndpoints();
        new VehicleBrandController()              .initEndpoints();
        new VehicleController()                   .initEndpoints();
        new VehicleInfoController()               .initEndpoints();
        new InsuranceController()                 .initEndpoints();
        new BillableItemController()              .initEndpoints();
        new ReservationController()               .initEndpoints();
        new RentalController()                    .initEndpoints();
        new AccidentController()                  .initEndpoints();
        new InvoiceController()                   .initEndpoints();
        new InvoiceItemController()               .initEndpoints();
            
        // INITILIZE ERRORS HANDLERS
        new ErrorsHandlers()                      .initHandlers();
        
    
    }
    
}
