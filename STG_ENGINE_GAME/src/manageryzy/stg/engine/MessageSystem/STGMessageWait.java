package manageryzy.stg.engine.MessageSystem;

public class STGMessageWait {
	public static boolean Wait(Object thread,Object lock)
	{
		synchronized(lock)
		{
			try {
				thread.wait(10);;
			} catch (Exception e) {
				e.getCause();
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public static boolean Notify(Object thread,Object lock)
	{
		synchronized(lock)
		{
			thread.notify();
		}
		return true;
	}
}
