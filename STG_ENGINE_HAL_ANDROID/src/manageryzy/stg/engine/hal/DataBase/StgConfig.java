package manageryzy.stg.engine.hal.DataBase;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;
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
		if(StgHal.GameDataBase==null)
		{
			Log.w("StgHal", "the DataBase Engine is not working!");
			return false;
		}
		if(!StgHal.GameDataBase.saveConfig())
			return false;
		return true;
	}
}
