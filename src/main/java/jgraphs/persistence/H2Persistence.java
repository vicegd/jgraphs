package jgraphs.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONArray;
import org.slf4j.Logger;

import jgraphs.core.structure.IStructure;
import jgraphs.logging.Logging;
import jgraphs.serialization.ISerializer;
import jgraphs.utils.Config;

public class H2Persistence implements IPersistence {
	private static HashMap<String, String> config = Config.getInstance().getConfig(H2Persistence.class);
	private static Logger log = Logging.getInstance().getLogger(H2Persistence.class);
	private String JDBC_DRIVER;   
	private String DB_URL;  
	private String USER; 
	private String PASS; 

	public H2Persistence() {
		this.JDBC_DRIVER = config.get("JDBC_DRIVER");  
		this.DB_URL = config.get("DB_URL");  
		this.USER = config.get("USER");  
		this.PASS = config.get("PASS");
	}
	
	@Override
	public void saveStructure(String key, ISerializer serializer, IStructure structure) {
		Connection conn = null; 
	    Statement stmt = null; 
	    try { 
	    	Class.forName(this.JDBC_DRIVER); 
	        conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);  
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
	    	Class.forName(this.JDBC_DRIVER); 
	        conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);  
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
