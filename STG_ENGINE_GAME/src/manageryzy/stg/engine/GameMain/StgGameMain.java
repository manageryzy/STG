package manageryzy.stg.engine.GameMain;

import manageryzy.stg.engine.MessageSystem.STGMessageQueue;
import manageryzy.stg.engine.mod.ModLoader;

public final class StgGameMain {
	
	/**
	 * the engine will post message to objects by this thread
	 */
	static Thread MessagePosterThread;
	
	public static void GameMainThread()
	{
		//start the message poster loop
		MessagePosterThread = new Thread(new Runnable() { 
            
            @SuppressWarnings("deprecation")
			@Override 
            public void run() { 
                for(int i = 0;i<10;i++) 
                { 
                    try { 
                        STGMessageQueue.ObjectMessageQueue.postMessage(true);
                    } catch (Exception e) {
                    	System.err.print("something wrong in the messgae loop");
                    	e.getCause();
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
		MessagePosterThread.start(); 
		

		//load the mods 
		ModLoader.theModLoader = new ModLoader();
	}
}
