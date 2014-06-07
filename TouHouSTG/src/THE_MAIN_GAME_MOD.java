import java.lang.annotation.Annotation;

import manageryzy.stg.engine.MessageSystem.STGMessage;
import manageryzy.stg.engine.MessageSystem.STGMessageListener;
import manageryzy.stg.engine.mod.StgMod;


@StgMod(ModID = "THE_MAIN_MOD")
public class THE_MAIN_GAME_MOD {

	@STGMessageListener
	public static boolean onModEvent(STGMessage msg) {
		System.out.print("Mod Loaded \n");
		return true;
	}
}
