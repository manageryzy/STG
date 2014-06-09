package manageryzy.stg.engine.MessageSystem;

/**
 * just a pack for the message
 * @author manageryzy
 * @see #StgEvent(String, Object)
 * @see #appendData
 * @see #EventType
 */
public class StgEvent {
	
	/**
	 * an string to describe what kind of message it is
	 */
	public String EventType;
	/**
	 * an object to contain extra data
	 */
	public Object appendData;
	
	/**
	 * the event in the STGMessage ,just an quick methood to init it
	 * @param Type 
	 * a string stands for the type of the message.you could define your own type
	 * @param Data
	 * the appended data for the message.
	 * @author manageryzy
	 */
	public StgEvent(String Type,Object Data)
	{
		EventType=Type;
		appendData=Data;
	}
}
