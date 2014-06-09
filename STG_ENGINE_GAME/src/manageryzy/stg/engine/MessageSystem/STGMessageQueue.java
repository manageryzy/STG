package manageryzy.stg.engine.MessageSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * you could use it to post message without waiting it is dealed
 * @author manageryzy
 * @see #ModMessageQueue
 * @see #ObjectMessageQueue
 * @see #addEvent(StgEvent, Object, Object)
 */
public class STGMessageQueue {
	
	
	/**
	 * the message queue for the mods
	 */
	public static STGMessageQueue ModMessageQueue=new STGMessageQueue();
	
	/**
	 * the message queue for Objects in the world
	 */
	public static STGMessageQueue ObjectMessageQueue=new STGMessageQueue();
	
	/**
	 * an lock to avoid reading and writing in the same time
	 */
	public boolean canAddMessage ;
	
	Queue<STGMessage> MessageQueue=null;
	List<Object> MessageListeners=null;
	
	Map<String,Object> MessageListenerName=null;
	
	/**
	 * init the message queue
	 * <p>this should be done by the system
	 * @author manageryzy
	 */
	public STGMessageQueue() {
		this.MessageQueue=new LinkedList<STGMessage>();
		this.MessageListeners=new ArrayList<Object>();
		this.MessageListenerName= new HashMap<String,Object>();
		canAddMessage=true;
	}
	
	/**
	 * add an event to the queue
	 * @param eventObj
	 * the event object you want to send
	 * @param from
	 * the object who send the message,you should send <b>this</b> object to it
	 * @param to
	 * the object who will receive the message<p>
	 * notice:the object must subscribe message
	 * @return
	 * return true if succeed
	 * @author manageryzy
	 * @see StgEvent
	 * @see STGMessage 
	 * STGMessage is the Object that will be received
	 */
	public boolean addEvent(StgEvent eventObj,Object from,Object to)
	{
		if(eventObj==null||to==null)
			return false;
		
		//loop until it can send message
		while(!this.canAddMessage);
			
		STGMessage message=new STGMessage(eventObj, from, to);
		
		this.MessageQueue.add(message);
		return true;
	}
	
	
}
