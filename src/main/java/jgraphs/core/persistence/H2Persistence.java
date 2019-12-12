package jgraphs.core.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jgraphs.core.serialization.ISerializer;
import jgraphs.core.structure.IStructure;

public class H2Persistence implements IPersistence {
	protected static Logger log = LoggerFactory.getLogger(H2Persistence.class);
	private String JDBC_DRIVER;   
	private String DB_URL;  
	private String USER; 
	private String PASS; 

	public H2Persistence() {
		this.JDBC_DRIVER = "org.h2.Driver";   
		this.DB_URL = "jdbc:h2:~/test";  
		this.USER = "sa"; 
		this.PASS = ""; 
	}
	
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
		Connection conn = null; 
	    Statement stmt = null; 
	    try { 
	    	Class.forName(JDBC_DRIVER); 
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);  
	        stmt = conn.createStatement(); 
	        String sql =  "DELETE FROM JGRAPHS WHERE KEY = '" + key + "'"; 
	        stmt.executeUpdate(sql);       
	        sql =  "INSERT INTO JGRAPHS VALUES ('" + key + "','" + serializer.serialize(structure) + "')";  
	        stmt.executeUpdate(sql);    
	        stmt.close(); 
	        conn.close(); 
	    } catch(SQLException se) { 
	        log.error(se.getMessage());
	    } catch(Exception e) { 
	    	log.error(e.getMessage());
	    } finally { 
	        try{ 
	           if(stmt!=null) stmt.close(); 
	        } catch(SQLException se) { 
	        	log.error(se.getMessage());
	        } 
	        try { 
	           if(conn!=null) conn.close(); 
	        } catch(SQLException se){ 
	        	log.error(se.getMessage());
	        } 
	    } 
	}

	@Override
	public IStructure loadStructure(String key, ISerializer serializer) {
		Connection conn = null; 
	    Statement stmt = null; 
	    IStructure structure = null;
	    try { 
	    	Class.forName(JDBC_DRIVER); 
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);  
	        stmt = conn.createStatement(); 
	        String sql =  "SELECT ISTRUCTURE FROM JGRAPHS WHERE KEY = '" + key + "'";  
	        var queryResult = stmt.executeQuery(sql); 
	        queryResult.next();
	        var json = new JSONArray(queryResult.getString("ISTRUCTURE"));
	        structure = serializer.deserialize(json);	        
	        stmt.close(); 
	        conn.close(); 
	    } catch(SQLException se) { 
	        log.error(se.getMessage());
	    } catch(Exception e) { 
	    	log.error(e.getMessage());
	    } finally { 
	        try{ 
	           if(stmt!=null) stmt.close(); 
	        } catch(SQLException se) { 
	        	log.error(se.getMessage());
	        } 
	        try { 
	           if(conn!=null) conn.close(); 
	        } catch(SQLException se){ 
	        	log.error(se.getMessage());
	        } 
	    } 
	    return structure;
	}
}
