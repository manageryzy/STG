package manageryzy.stg.engine.MessageSystem;

/**
 * @author manageryzy
 *
 */
public class STGMessage {
	long TimeSteap;
	Object theEvent,Source,Target;
	
	/**
	 * create an Message object
	 * @param event
	 * the object of the event
	 * @param FromObj
	 * just put this or null to it 
	 * @param TargetObj
	 * the target object
	 * @author manageryzy
	 */
	public STGMessage(Object event,Object FromObj,Object TargetObj)
	{
		System.currentTimeMillis();
		Source=FromObj;
		theEvent=event;
		Target=TargetObj;
	}
	
	public long getTimeSteap()
	{
		return TimeSteap;
	}
	
	public Object getEvent()
	{
		return theEvent;
	}
	
	public Object getSource()
	{
		return Source;
	}
	
}
