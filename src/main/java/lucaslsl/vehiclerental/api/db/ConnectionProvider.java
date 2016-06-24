/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.db;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;

/**
 *
 * @author lucaslsl
 */
public class ConnectionProvider {
    
    private static final ConnectionProvider INSTANCE;
    private final HikariDataSource dataSource;
    private final AtomicBoolean initialized;
    
    static{
        INSTANCE = new ConnectionProvider();
    }
    
    private ConnectionProvider() {
        dataSource = new HikariDataSource();
        initialized = new AtomicBoolean();
    }
    
    public static ConnectionProvider getInstance() {
        return INSTANCE;
    }
    
    public void init(){
        if (initialized.compareAndSet(false, true)) {
            dataSource.setJdbcUrl(System.getenv("DB_URL"));
            dataSource.setUsername(System.getenv("DB_USERNAME"));
            dataSource.setPassword(System.getenv("DB_PASSWORD"));
            boolean developmentEnvironment = true;
            String env = System.getenv("ENV");
            if(System.getenv("ENV")!=null){
                developmentEnvironment = env.equalsIgnoreCase("DEVELOPMENT");
            }
            if(!developmentEnvironment){
                dataSource.addDataSourceProperty("sslmode", "require");
            }
        }
        else {
           throw new IllegalStateException("Connection provider already initialized.");
        }
    }
    
    public Connection getConnection() throws SQLException{
        if(initialized.get()){
            return dataSource.getConnection();
        }
        throw new UnsupportedOperationException("Connection provider not initialized.");
    }
    
    public DataSource getDataSource(){
        if(initialized.get()){
            return dataSource;
        }
        throw new UnsupportedOperationException("Connection provider not initialized.");
    }
    
    
    
}
