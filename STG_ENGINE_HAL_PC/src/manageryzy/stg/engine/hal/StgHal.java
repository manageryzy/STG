/************************
 * @the STG HAL engine
 * @author manageryzy
 * *********************/
package manageryzy.stg.engine.hal;

import java.util.logging.Level;
import java.util.logging.Logger;

import manageryzy.stg.engine.hal.DataBase.StgDataBase;
import manageryzy.stg.engine.hal.Sound.MusicEngine;
import manageryzy.stg.engine.hal.Sound.SoundEngine;
import manageryzy.stg.engine.hal.basicDrawing.MainWindow;

public class StgHal {
	/**
	 * the method to call the HAL
	 * @author manageryzy
	 */
	 public static StgHal theStgHal = null;
	 /**
	  * <b>notice:<p>this interface only work on pc !!!!</b> 
	  * @see HAL of other platform
	  * @author manageryzy
	  */
	 public MainWindow theWindow = null;
	 /**
	  * the interface to call database
	  * @author manageryzy
	  */
	 public StgDataBase GameDataBase = null;
	 /**
	  * the interface to call sound engine
	  * @author manageryzy
	  */
	 public SoundEngine SoundEffect = null; 
	 /**
	  * the interface to call BGM engine
	  * @author manageryzy
	  */
	 public MusicEngine BGMEngine = null;
	 
	 /**
	  * Init the HAL.
	  * @param dbName
	  * the name of the database contains user's data and other information
	  * @author manageryzy
	  */
	 public StgHal(String dbName)
	 {
		 Logger.getGlobal().log(Level.INFO, "HAL initing");
		 GameDataBase=new StgDataBase(dbName);
		 SoundEffect = new SoundEngine();
		 BGMEngine = new MusicEngine();
		 Logger.getGlobal().log(Level.INFO, "HAL init finish");
	 }
	 
	 /**
	  * init and show the window
	  * <b>NOTICE:<p>this interface only exist on the PC</b>
	  * @param WindowName
	  * the title of the window
	  * @see HAL on other platforms
	  * @author manageryzy
	  */
	 public void initWindow(String WindowName)
	 {
		 if(theWindow==null)
		 {
			 Logger.getGlobal().log(Level.INFO, "HAL initing window");
			 theWindow=new MainWindow(WindowName);
		 }
	 }
	 
}