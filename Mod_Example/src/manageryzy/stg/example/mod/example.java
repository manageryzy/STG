package manageryzy.stg.example.mod;

import manageryzy.stg.engine.MessageSystem.STGMessage;
import manageryzy.stg.engine.MessageSystem.STGMessageListener;
import manageryzy.stg.engine.mod.StgMod;

@StgMod(ModID = example.ModID,Author=example.Author,Version=example.Version
	,ModName=example.ModName,ModEventListener="onModEvent")
public class example {
	public static final String ModID="ExampleMod";
	public static final String Author="managryzy";
	public static final String Version="0.0.1";
	public static final String ModName="Manageryzy's example mod";
	
	@STGMessageListener
	public static boolean onModEvent(STGMessage msg) {
		System.out.print("Example Mod Loaded \n");
		example2 exp2=new example2();
		return true;
	}
}
