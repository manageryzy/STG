/************************
 * @the stg hal engine
 * *********************/
package manageryzy.stg.engine.hal;

import android.util.Log;
import manageryzy.stg.engine.hal.DataBase.StgDataBase;

public class StgHal {
	
	 public static StgHal theStgHal = null;
	 public static StgDataBase GameDataBase = null;
	 
	 public StgHal(String dbName)
	 {
		 Log.i("StgHal","HAL initing");
		 GameDataBase=new StgDataBase(dbName);
		 
		 Log.i("StgHal", "HAL init finish");
	 }

	 
}