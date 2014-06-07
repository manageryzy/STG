package manageryzy.stg.engine.hal.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import manageryzy.stg.engine.hal.StgHal;

public class StgConfig {
	public static StgConfig theConfig=new StgConfig();
	public Map<String,String> ConfigMap = new HashMap<String,String>();
	
	public StgConfig()
	{
		this.addConfig("height", "800");
		this.addConfig("width", "640");
		
		this.addConfig("ifAskFullScreen", "false");
		this.addConfig("ifFullScreen", "false");
		
		this.addConfig("ModListPath", "StgMod.xml");
	}
	
	public String getConfig(String name)
	{
		return ConfigMap.get(name);
	}
	
	public void addConfig(String Name,String Value)
	{
		ConfigMap.put(Name, Value);
	}
	
	public void setConfig(String Name,String Value)
	{
		ConfigMap.put(Name, Value);
	}
	
	public boolean saveConfig()
	{
		if(StgHal.theStgHal.GameDataBase==null)
		{
			Logger.getGlobal().log(Level.WARNING, "the DataBase Engine is not working!");
			return false;
		}
		if(!StgHal.theStgHal.GameDataBase.saveConfig())
			return false;
		return true;
	}
}
