package jgraphs.subsystem.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONArray;

import jgraphs.core.structure.IStructure;
import jgraphs.subsystem.logger.DefaultLogger;
import jgraphs.subsystem.logger.ILogger;
import jgraphs.subsystem.serialization.ISerializer;
import jgraphs.utils.Config;

public class H2Persistence implements IPersistence {
	protected static final HashMap<String, String> config = Config.getConfig(H2Persistence.class);
	protected static final ILogger logger = new DefaultLogger(H2Persistence.class);
	private String JDBC_DRIVER;   
	private String DB_URL;  
	private String USER; 
	private String PASS; 

	public H2Persistence() {
		this.JDBC_DRIVER = config.get(Config.H2_PERSISTENCE_JDBC_DRIVER);  
		this.DB_URL = config.get(Config.H2_PERSISTENCE_DB_URL);  
		this.USER = config.get(Config.H2_PERSISTENCE_USER);  
		this.PASS = config.get(Config.H2_PERSISTENCE_PASS);
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
	        logger.error(se.getMessage());
	    } catch(Exception e) { 
	    	logger.error(e.getMessage());
	    } finally { 
	        try{ 
	           if(stmt!=null) stmt.close(); 
	        } catch(SQLException se) { 
	        	logger.error(se.getMessage());
	        } 
	        try { 
	           if(conn!=null) conn.close(); 
	        } catch(SQLException se){ 
	        	logger.error(se.getMessage());
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
	        logger.error(se.getMessage());
	    } catch(Exception e) { 
	    	logger.error(e.getMessage());
	    } finally { 
	        try{ 
	           if(stmt!=null) stmt.close(); 
	        } catch(SQLException se) { 
	        	logger.error(se.getMessage());
	        } 
	        try { 
	           if(conn!=null) conn.close(); 
	        } catch(SQLException se){ 
	        	logger.error(se.getMessage());
	        } 
	    } 
	    return structure;
	}

}
