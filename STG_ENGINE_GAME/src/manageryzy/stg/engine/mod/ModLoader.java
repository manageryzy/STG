package manageryzy.stg.engine.mod;

import java.util.HashMap;
import java.util.Map;
import manageryzy.stg.engine.MessageSystem.STGMessage;
import manageryzy.stg.engine.MessageSystem.StgEvent;
import manageryzy.stg.engine.modloader.ModJarLoader;

/**
 * @author manageryzy
 *
 */
public class ModLoader {
	public static ModLoader theModLoader;
	Map<String,Mods> ModsList;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModLoader()
	{
		ModsList=new HashMap<String,Mods>();
		
		//load the main mod of the game
		Class<?> MainModClass=null;
		Mods MainMod ;
		
		try {
			MainModClass=Class.forName("THE_MAIN_GAME_MOD");//load the mod class
			MainModClass.getAnnotation(StgMod.class);//check whether the class is a mod
		} catch (Exception e) {
			e.getMessage();
			e.getCause();
			e.printStackTrace();
			System.err.print("Failed to load the main mod! The Engine is going to exit!\n");
			System.exit(-1);
		}
		
		MainMod	=new Mods(MainModClass);
		try {
			if(MainMod.getEventListener().invoke(MainModClass,new STGMessage(new StgEvent("onModLoad", null), this, null)).equals(true))
			{
				ModsList.put("MainMod", MainMod);
			}
			else
			{
				System.err.print("Main mod didn't loaded correctly! The Engine is going to exit!");
				System.exit(-1);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		//load the other mods of the game
		ModJarLoader JarLoader=new ModJarLoader();
		for(int i=0;i<JarLoader.modList.size();i++)
		{
			Class modClass=ModJarLoader.LoadJar(JarLoader.modList.get(i).Path, JarLoader.modList.get(i).Cls);
			if(modClass!=null)
			{
				modClass.getAnnotation(StgMod.class);//check whether the class is a mod
			}
			
			Mods TheMod	=new Mods(modClass);
			try {
				if(TheMod.getEventListener().invoke(TheMod,new STGMessage(new StgEvent("onModLoad", null), this, null)).equals(true))
				{
					ModsList.put(TheMod.GetModID(), TheMod);	
				}
				else
				{
					System.err.print(TheMod.GetModName()+" didn't loaded correctly!\n");
				}
			} catch (Exception e) {
				System.err.print("Something wrong when loading other mods.\n");
				e.printStackTrace();
			}
		}
		
	}
	
	
}
