/************************
 * @the stg hal engine
 * *********************/
package manageryzy.stg.engine.hal;

import java.util.logging.Level;
import java.util.logging.Logger;

import manageryzy.stg.engine.hal.DataBase.StgDataBase;
import manageryzy.stg.engine.hal.Sound.SoundEngine;
import manageryzy.stg.engine.hal.basicDrawing.MainWindow;

public class StgHal {
	
	 public static StgHal theStgHal = null;
	 public MainWindow theWindow = null;
	 public StgDataBase GameDataBase = null;
	 public SoundEngine SoundEffect = null; 
	 
	 public StgHal(String dbName)
	 {
		 Logger.getGlobal().log(Level.INFO, "HAL initing");
		 GameDataBase=new StgDataBase(dbName);
		 SoundEffect = new SoundEngine();
		 Logger.getGlobal().log(Level.INFO, "HAL init finish");
	 }
	 
	 public void initWindow(String WindowName)
	 {
		 if(theWindow==null)
		 {
			 Logger.getGlobal().log(Level.INFO, "HAL initing window");
			 theWindow=new MainWindow(WindowName);
		 }
	 }
	 
}