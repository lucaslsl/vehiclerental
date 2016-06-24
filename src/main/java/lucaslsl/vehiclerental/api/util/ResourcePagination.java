/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucaslsl.vehiclerental.api.util;

import spark.Request;

/**
 *
 * @author lucaslsl
 */
public class ResourcePagination {
    
    private int defaultPerPage = 25;
    private int maxPerPage  = 50;
    private int defaultPage = 1;
    private int perPage = 25;
    private int page = 1;
    private int offset = 0;
    private int limit = 25;
    private String uri;
    
    public ResourcePagination(Request req){
        this.uri = req.url();        
        Integer perPagePassed = req.queryMap("per_page").integerValue();
        Integer pagePassed = req.queryMap("page").integerValue();
        
        if(perPagePassed!=null){
            if(perPagePassed<=this.maxPerPage && perPagePassed>=1){
                this.perPage = perPagePassed;
            }
        }
        if(pagePassed!=null){
            if(pagePassed>=1){
                this.page = pagePassed;
            }
        }
        
        this.limit = this.perPage;
        this.offset = this.perPage * (this.page - 1);
    }
    
    public String generateJsonLinks(int totalCount){
            
        String nextPage = "null";
        if( (this.page+1) <= ((totalCount - 1) / this.perPage + 1) ){
            nextPage = "\"" +  this.uri + "?per_page=" + this.perPage + "&page=" + (this.page + 1) + "\"";
        }
        
        int lastPg = (((totalCount - 1) / this.perPage + 1) <= 0) ? 1 : ((totalCount - 1) / this.perPage + 1);
        String lastPage = "\"" + this.uri + "?per_page=" + this.perPage + "&page=" + lastPg + "\"";
        
        String firstPage = "\"" + this.uri + "?per_page=" + this.perPage + "&page=" + 1 + "\"";
        
        String prevPage = "null";
        
        if( ((page-1) > 0) && (page<= ((totalCount - 1) / this.perPage + 1)) ){
            prevPage = "\"" + this.uri + "?per_page=" + this.perPage + "&page=" + (this.page-1) + "\"";
        }
        
        
        String paginationLinks = "{" +
                "\"next\":" + nextPage + "," +
                "\"last\":" + lastPage + "," +
                "\"first\":" + firstPage + "," +
                "\"prev\":" + prevPage +
                "}";       
        
        return paginationLinks;
    }
    
    public String generateLinkHeader(int totalCount){
            
        String nextPage = "null";
        if( (this.page+1) <= ((totalCount - 1) / this.perPage + 1) ){
            nextPage = this.uri + "?per_page=" + this.perPage + "&page=" + (this.page + 1);
        }
        
        int lastPg = (((totalCount - 1) / this.perPage + 1) <= 0) ? 1 : ((totalCount - 1) / this.perPage + 1);
        String lastPage = this.uri + "?per_page=" + this.perPage + "&page=" + lastPg;
        
        String firstPage = this.uri + "?per_page=" + this.perPage + "&page=" + 1;
        
        String prevPage = "null";
        
        if( ((page-1) > 0) && (page<= ((totalCount - 1) / this.perPage + 1)) ){
            prevPage = this.uri + "?per_page=" + this.perPage + "&page=" + (this.page-1);
        }
        
        
        String linkHeader = "<" + nextPage + ">; " + "rel=\"next\", " + 
                            "<" + lastPage + ">; " + "rel=\"last\", " +
                            "<" + firstPage + ">; " + "rel=\"first\", " +
                            "<" + prevPage + ">; " + "rel=\"prev\"";
        
        return linkHeader;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
    
    
    
}
