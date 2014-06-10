package manageryzy.stg.engine.MessageSystem;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * this class is about the subscriber and the hook of the message
 * @author manageryzy
 *
 */
public class STGMessageReceiver {
	public static STGMessageReceiver theReceiverList=new STGMessageReceiver();
	
	Map<Object,Receiver> ReceiverList;
	boolean canWrite;
	
	public STGMessageReceiver()
	{
		ReceiverList=new HashMap<Object,Receiver>();
		canWrite=true;
	}
	
	/**
	 * subscribe message.only the objects who has subscribed messages would 
	 * receive message from the system.You should use this method to enable 
	 * your mod to have the ability to get the game message.
	 * @author manageryzy 
	 * @param subscriber
	 * the object who want to subscribe message.The object can be anything 
	 * in the game,such as a player, a boss or even a bomb.
	 * @param MethodName
	 * the name of the method who receive message.<p>
	 * <b>notice:</b> although nowadays,it is useless to add @STGMessageListener
	 * but
	 * @return
	 * return true if succeed
	 */
	public boolean Subscribe(Object subscriber,String MethodName)
	{
		try {
			Method met = subscriber.getClass().getMethod(MethodName,new Class[]{STGMessage.class} );
			Receiver newReceiver=new Receiver(met,subscriber);
			ReceiverList.put(subscriber, newReceiver);
		} catch (Exception e) {
			System.err.print("Error:can't subscribe message at"+subscriber.toString()+" "+MethodName+"\n");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * check whether an object is listening events
	 * @param obj
	 * the object who want check
	 * @author manageryzy 
	 * @return
	 * if the object is listening ,return true
	 */
	public boolean isObjectListening(Object obj)
	{
		if(this.ReceiverList.containsKey(obj))
			return true;
		else return false;
	}
	
	
	/**
	 * unsubscribe message
	 * @param obj
	 * the object who used to be an subscriber
	 * @return
	 * return true if succeed
	 * @author manageryzy
	 */
	public boolean unsubscribe(Object obj)
	{
		if(!this.isObjectListening(obj))
		{
			System.err.print("failed to unsubscribe for the object do not exist!\n");
			return false;
		}
		
		this.lock();
		this.ReceiverList.remove(obj);
		this.unlock();
		return true;
	}
	
	
	/**
	 * hook the message
	 * @param targetObject
	 * the target object who will be hooked
	 * @param HookObject
	 * the object to hook into it
	 * @param HookMethod
	 * a string to describe which method is the method to receive message<p>
	 * you should put the name of method into it
	 * @author manageryzy 
	 * @return
	 * return true if succeed
	 */
	public boolean AddHook(Object targetObject,Object HookObject,String HookMethod)
	{
		if(!ReceiverList.containsKey(targetObject))
		{
			System.err.print("Failed to hook message!The target do not exist!\n");
			return false;
		}
		
		Receiver r=ReceiverList.get(targetObject);
		Receiver newReceiver;
		if(r==null)
		{
			System.err.print("Failed to hook message!The target do not exist!\n");
			return false;
		}
		
		try {
			Method met = HookObject.getClass().getMethod(HookMethod,new Class[]{STGMessage.class} );
			newReceiver=new Receiver(met,HookObject);
		} catch (Exception e) {
			System.err.print("Error:can't hook message at"+HookObject.toString()+" "+HookMethod+"\n");
			e.printStackTrace();
			return false;
		}
		
		while(!this.canWrite);
		
		r.HookedReceiver=newReceiver;
		
		ReceiverList.put(targetObject, r);
		
		return true;
	}
	
	/**
	 * return the receiver object of the listener
	 * @param obj
	 * the object of the listener
	 * @return
	 * return null if failed
	 */
	public Receiver getReceiver(Object obj)
	{
		return ReceiverList.get(obj);
	}
	
	public void lock()
	{
		this.canWrite=false;
	}
	
	public void unlock()
	{
		this.canWrite=false;
	}
	
	public class Receiver
	{
		public Method method;
		public Object Obj;
		public Receiver HookedReceiver;
		
		public Receiver(Method m,Object object)
		{
			method=m;
			HookedReceiver=null;
			Obj=object;
		}
	}
}
