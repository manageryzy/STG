import java.lang.annotation.Annotation;

import manageryzy.stg.engine.MessageSystem.STGMessage;
import manageryzy.stg.engine.MessageSystem.STGMessageListener;
import manageryzy.stg.engine.MessageSystem.STGMessageReceiver;
import manageryzy.stg.engine.mod.StgMod;


@StgMod(ModID = "THE_MAIN_MOD")
public class THE_MAIN_GAME_MOD {

	@STGMessageListener
	public static boolean onModEvent(STGMessage msg) {
		System.out.print("Mod Loaded \n");
		STGMessageReceiver.theReceiverList.Subscribe(new THE_MAIN_GAME_MOD(),"onElseEvent");
		return true;
	}
	
	@STGMessageListener
	public boolean onElseEvent(STGMessage msg)
	{
		System.out.print("called \n");
		return true;
	}
}
