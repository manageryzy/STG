package manageryzy.stg.engine.hal.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StgDataBase {
	Statement stat ;
	Connection conn;
	String dbName;
	public StgDataBase(String theDbName)
	{
		dbName=theDbName;
		try 
		{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:"+dbName);
			stat = conn.createStatement();
		} 
		catch (Exception e) 
		{
			Logger.getGlobal().log(Level.WARNING, "luncher start");
			e.printStackTrace();
			e.getMessage();
			e.getCause();
		}
		
		
		try {
			stat.executeUpdate("CREATE TABLE IF NOT EXISTS config (name, value);");
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getGlobal().log(Level.WARNING, "ERROR in Creating Table in DataBase!");
		}
		
		loadConfig();
	}
	
	public boolean saveConfig()
	{
		try {
			stat.executeUpdate("drop table if exists config;");
			stat.executeUpdate("create table config (name, value);");
			PreparedStatement prep = conn
					.prepareStatement("insert into config values (?, ?);");
			
			
			@SuppressWarnings("rawtypes")
			Iterator it=StgConfig.theConfig.ConfigMap.keySet().iterator();    
			while(it.hasNext()){    
			     String key;    
			     String value;    
			     key=it.next().toString();    
			     value=StgConfig.theConfig.ConfigMap.get(key);    

			     prep.setString(1, key);
			     prep.setString(2, value);
			     prep.addBatch();
			}   

			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getGlobal().log(Level.WARNING,"error in logging config");
		}
		return true;
	}
	
	public boolean loadConfig()
	{
		
		try {
			ResultSet rs = stat.executeQuery("select * from config ;");
			while (rs.next()) {
				StgConfig.theConfig.addConfig(rs.getString("name"), rs.getString("value"));
			}
			rs.close();
//			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
