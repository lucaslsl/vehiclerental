/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;
import lucaslsl.vehiclerental.api.util.ApiConfigs;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author lucaslsl
 */
public class RedisConnectionProvider {
    
    private static final RedisConnectionProvider INSTANCE;
    private final JedisPool redisPool;
    private final AtomicBoolean initialized;
    private final JedisPoolConfig poolConfig;
    
    static{
        INSTANCE = new RedisConnectionProvider();
    }
    
    private RedisConnectionProvider(){
        poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        

        try {
            URI uri = new URI(System.getenv("REDIS_URI"));            
            String host = uri.getHost();
            int port = uri.getPort();
            boolean hasPassword = false;
            String password = "";
            int database = 0;
            
            if(uri.getUserInfo()!=null){
                password = uri.getPath().split("/", 2)[1];
                hasPassword = true;
            }
                        
            if(!uri.getPath().isEmpty()){
                database = Integer.parseInt(uri.getPath().split("/", 2)[1]);
            }
            
            if(hasPassword){
                redisPool = new JedisPool(poolConfig,host,port,10000,password,database);
            }else{
                redisPool = new JedisPool(poolConfig,host,port,10000);
            }
            initialized = new AtomicBoolean();
            
        } catch (URISyntaxException | NumberFormatException | NullPointerException e) {
            throw new IllegalStateException("Could not parse REDIS_URI");
        }
        
    }
    
    public static RedisConnectionProvider getInstance() {
        return INSTANCE;
    }
    
    public Jedis getConnection(){
        return this.redisPool.getResource();
    }
      
}
