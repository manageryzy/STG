package manageryzy.stg.engine.MessageSystem;

/**
 * just a pack for the message
 * @author manageryzy
 *
 */
public class StgEvent {
	public String EventType;
	public Object appendData;
	
	/**
	 * the event in the STGMessage
	 * @param Type 
	 * a string stands for the type of the message.you could define your own type
	 * @param Data
	 * the appended data for the message.
	 */
	public StgEvent(String Type,Object Data)
	{
		EventType=Type;
		appendData=Data;
	}
}
