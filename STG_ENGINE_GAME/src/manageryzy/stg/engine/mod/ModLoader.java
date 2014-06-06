package manageryzy.stg.engine.mod;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author manageryzy
 *
 */
public class ModLoader {
	static ModLoader theModLoader;
	Map<String,Mods> ModsList;
	
	public ModLoader()
	{
		StgMod MainMod=null;
		try {
			MainMod=(StgMod)Class.forName("THE_MAIN_GAME_MOD").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.getCause();
			e.printStackTrace();
			Logger.getGlobal().log(Level.WARNING,"Failed to load the main mod! The Engine is going to exit!");
			System.exit(-1);
		}
		
		ModsList=new HashMap<String,Mods>();
		
	}
	
	
}
