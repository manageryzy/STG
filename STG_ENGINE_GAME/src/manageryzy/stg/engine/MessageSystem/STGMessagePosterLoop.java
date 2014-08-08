package manageryzy.stg.engine.MessageSystem;

/**
 * just the loop of message poster
 * @author manageryzy
 *
 */
public class STGMessagePosterLoop
{
	/**
	 * the engine will post message to objects by this thread
	 */
	static public Thread MessagePosterThread;
	public static void loop()
	{
		MessagePosterThread = new Thread(new Runnable() { 
			
            @SuppressWarnings("deprecation")
			@Override 
            public void run() { 
            	while(true) 
                { 
                    try { 
                    	if(!STGMessageQueue.ObjectMessageQueue.postMessage(true))
                    	{
                    		//the message queue was empty!
                    		STGMessageWait.Wait(this, this);
                    	}
                    } catch (Exception e) {
                    	System.err.print("something wrong in the messgae loop");
                    	e.getCause();
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
		MessagePosterThread.start(); 
	}
}