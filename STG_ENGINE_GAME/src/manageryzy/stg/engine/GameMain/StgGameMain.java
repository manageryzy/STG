package manageryzy.stg.engine.GameMain;

import manageryzy.stg.engine.mod.ModLoader;
import manageryzy.stg.engine.MessageSystem.STGMessagePosterLoop;

public final class StgGameMain {
	
	public static void GameMainThread()
	{
		//start the message poster loop
		STGMessagePosterLoop.loop();

		//load the mods 
		ModLoader.theModLoader = new ModLoader();
	}
}
