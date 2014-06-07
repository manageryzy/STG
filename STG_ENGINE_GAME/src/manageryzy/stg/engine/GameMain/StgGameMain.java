package manageryzy.stg.engine.GameMain;

import manageryzy.stg.engine.mod.ModLoader;

public final class StgGameMain {
	public static void GameMainThread()
	{
		//load the mods 
		ModLoader.theModLoader = new ModLoader();
		
		
	}
}
