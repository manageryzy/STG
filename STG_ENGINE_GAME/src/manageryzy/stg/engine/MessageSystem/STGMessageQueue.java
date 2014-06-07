package manageryzy.stg.engine.MessageSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class STGMessageQueue {
	/**
	 * the message queue for the mods
	 */
	public static STGMessageQueue ModMessageQueue=new STGMessageQueue();
	
	/**
	 * the message queue for Objects in the world
	 */
	public static STGMessageQueue ObjectMessageQueue=new STGMessageQueue();
	
	Queue<STGMessage> MessageQueue=null;
	List<Object> MessageListeners=null;
	
	Map<String,Object> MessageListenerName=null;
	
	public STGMessageQueue() {
		this.MessageQueue=new LinkedList<STGMessage>();
		this.MessageListeners=new ArrayList<Object>();
		this.MessageListenerName= new HashMap<String,Object>();
	}
	
	public boolean addEvent(Object eventObj,Object from,Object to)
	{
		if(eventObj==null||to==null)
			return false;
		STGMessage message=new STGMessage(eventObj, from, to);
		
		this.MessageQueue.add(message);
		return true;
	}
	
	
}
