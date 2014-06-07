package manageryzy.stg.engine.hal.DataBase;

import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StgDataBase {
	SQLiteDatabase db ;
	String dbName;
	public StgDataBase(Context context)
	{
		dbName="config.db";
		try 
		{
			db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().getAbsolutePath().replace("files","databases")+dbName, null);  
		} 
		catch (Exception e) 
		{
			Log.w("StgHal", "luncher start");
			e.printStackTrace();
			e.getMessage();
			e.getCause();
		}
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS config (name, value);");
		
		loadConfig();
	}
	
	public boolean saveConfig()
	{
		try {
			db.execSQL("drop table if exists config;");
			db.execSQL("create table config (name, value);");
			
			
			@SuppressWarnings("rawtypes")
			Iterator it=StgConfig.theConfig.ConfigMap.keySet().iterator();    
			while(it.hasNext()){    
			     String key;    
			     String value;    
			     key=it.next().toString();    
			     value=StgConfig.theConfig.ConfigMap.get(key);    

			     db.execSQL("INSERT INTO config VALUES (?, ?)", new Object[]{key,value});  
			}   
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.w("StgHal", "error in logging config");
		}
		return true;
	}
	
	public boolean loadConfig()
	{
		
		try {
			
			Cursor c = db.rawQuery("SELECT * FROM config", new String[]{});  
	        while (c.moveToNext()) {  
	            int _id = c.getInt(c.getColumnIndex("_id"));  
	            String name = c.getString(c.getColumnIndex("name"));  
	            int age = c.getInt(c.getColumnIndex("age"));  
	            Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);  
	            StgConfig.theConfig.addConfig(c.getString(c.getColumnIndex("name")), 
	            		c.getString(c.getColumnIndex("value")));
	        }  
	        c.close();  
	        
		} catch (Exception e) {
			Log.w("SQL error", "can't load config");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
