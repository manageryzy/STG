import manageryzy.stg.engine.MessageSystem.STGMessage;
import manageryzy.stg.engine.MessageSystem.STGMessageListener;
import manageryzy.stg.engine.MessageSystem.STGMessageQueue;
import manageryzy.stg.engine.MessageSystem.STGMessageReceiver;
import manageryzy.stg.engine.MessageSystem.StgEvent;
import manageryzy.stg.engine.mod.StgMod;


@StgMod(ModID = "MAIN_GAME_MOD")
public class THE_MAIN_GAME_MOD {

	@STGMessageListener
	public static boolean onModEvent(STGMessage msg) {
		System.out.print("Mod Loaded \n");
		THE_MAIN_GAME_MOD m = new THE_MAIN_GAME_MOD();
		STGMessageReceiver.theReceiverList.Subscribe(m,"onElseEvent");
		STGMessageQueue.ObjectMessageQueue.addEvent(new StgEvent("init", null), null,m);
		return true;
	}
	
	@STGMessageListener
	public boolean onElseEvent(STGMessage msg)
	{
		System.out.print("called \n"+msg.getEvent().EventType);
		return true;
	}
}
