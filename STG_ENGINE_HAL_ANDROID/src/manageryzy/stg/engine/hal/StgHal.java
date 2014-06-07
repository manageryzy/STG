/************************
 * @the stg hal engine
 * *********************/
package manageryzy.stg.engine.hal;

import android.content.Context;
import android.util.Log;
import manageryzy.stg.engine.hal.DataBase.StgDataBase;

public class StgHal {
	
	 public static StgHal theStgHal = null;
	 public static StgDataBase GameDataBase = null;
	 
	 public StgHal(Context context)
	 {
		 Log.i("StgHal","HAL initing");
		 GameDataBase=new StgDataBase(context);
		 
		 Log.i("StgHal", "HAL init finish");
	 }

	 
}