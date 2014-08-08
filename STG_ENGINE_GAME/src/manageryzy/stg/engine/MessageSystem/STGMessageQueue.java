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
	 * the message queue for the Mods Message Pipeline
	 */
	public static STGMessageQueue ModMessageQueue=new STGMessageQueue();
	
	/**
	 * the message queue for Objects Message Pipeline in the world
	 */
	public static STGMessageQueue ObjectMessageQueue=new STGMessageQueue();
	
	/**
	 * an lock to avoid reading and writing in the same time
	 */
	public boolean canAddMessage ;
	
	
	
	Queue<STGMessage> MessageQueue=null;
	List<Object> MessageListeners=null;
	
	Map<String,Object> MessageListenerName=null;
	Map<String,STGMessageReceiver> MessageSeparator=null;
	
	/**
	 * init the message queue
	 * <p>this should be done by the system
	 * @author manageryzy
	 */
	public STGMessageQueue() {
		this.MessageQueue=new LinkedList<STGMessage>();
		this.MessageListeners=new ArrayList<Object>();
		this.MessageListenerName= new HashMap<String,Object>();
		this.MessageSeparator=new HashMap<String,STGMessageReceiver>();
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
		
		STGMessageWait.Notify(STGMessagePosterLoop.MessagePosterThread, STGMessagePosterLoop.MessagePosterThread);
		
		return true;
	}
	
	/**
	 * post message in the message queue
	 * @param ifCheckHook
	 * if the engine should check hooks like the object message pipeline
	 * it should be true.But in he mod message pipeline ,it should be false.
	 * @deprecated 
	 * this method should be called by engine.You should not call it by yourself.
	 * @author manageryzy
	 * @return return true if the queue was not empty
	 */
	public boolean postMessage(boolean ifCheckHook)
	{
		
		if(MessageQueue.isEmpty())//if there is no message,delay and return 
		{
			try{
				Thread.sleep(10);
			}
			catch(Exception e)
			{
				e.getCause();
				e.printStackTrace();
			}
			return false;
		}
		
		this.lock();
		STGMessage nowDealingMessage=MessageQueue.poll();
		this.unlock();
		if(nowDealingMessage==null)
		{
			System.err.print("unexpected null pointer!\n");
			return true;
		}
		
		STGMessageReceiver theReceiverList = this.getReceiver(nowDealingMessage.theEvent.EventType);
		
		if(!theReceiverList.isObjectListening(nowDealingMessage.Target))
		{
			System.err.print("Unexpected error:the target object do not listen message!\n");
			return true;
		}
		
		STGMessageReceiver.Receiver theReceiver=theReceiverList.getReceiver(nowDealingMessage.Target);
		
		doPostMessage(theReceiver,nowDealingMessage);
		
		return true;
	}
	
	boolean doPostMessage(STGMessageReceiver.Receiver theReceiver,STGMessage nowDealingMessage)
	{
		if(theReceiver.HookedReceiver!=null)
		{
			if(doPostMessage(theReceiver.HookedReceiver,nowDealingMessage)==false)
				return false;
		}
		
		try {
			if(theReceiver.method.invoke(theReceiver.Obj, nowDealingMessage).equals(false))
				return false;
		} catch (Exception e) {
			System.err.print("Error:some thing wrong in dealing message\n");
			e.getCause();
			e.printStackTrace();
		}
		
		return true;
	}
	
	public STGMessageReceiver getReceiver(String event)
	{
		return MessageSeparator.get(event);
	}
	
	@SuppressWarnings("deprecation")
	public boolean Subscribe(String MessageType,Object subscriber,String MethodName)
	{
		STGMessageReceiver receiver = MessageSeparator.get(MessageType);
		if(receiver==null)
		{
			receiver = new STGMessageReceiver();
			MessageSeparator.put(MessageType, receiver);
		}
		if(!receiver.Subscribe(subscriber, MethodName))return false;
		return true;
	}
	
	protected void lock()
	{
		canAddMessage = false;
	}
	
	protected void unlock ()
	{
		canAddMessage = true;
	}
	
	
}
