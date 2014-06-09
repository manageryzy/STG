package manageryzy.stg.engine.MessageSystem;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class is about the subscriber and the hook of the message
 * @author manageryzy
 *
 */
@SuppressWarnings("rawtypes")
public class STGMessageReceiver {
	public static STGMessageReceiver theReceiverList=new STGMessageReceiver();
	
	Map<Object,Receiver> ReceiverList;
	
	public STGMessageReceiver()
	{
		ReceiverList=new HashMap<Object,Receiver>();
		
	}
	
	public static boolean Subscribe(Object subscriber,String MethodName)
	{
		try {
			Method a = subscriber.getClass().getMethod(MethodName,new Class[]{STGMessage.class} );
			a.getClass();
			
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.print("Error:can't subscribe message at"+subscriber.toString()+" "+MethodName+"\n");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	class Receiver
	{
		public Method method;
		public boolean hasNextReceiver;
		public Receiver NextReceiver;
		
		public Receiver(Method m)
		{
			method=m;
			hasNextReceiver=false;
			NextReceiver=null;
		}
	}
}
