package manageryzy.stg.engine.MessageSystem;

/**
 * the class of the message
 * @author manageryzy
 * @see #getEvent() 
 * @see #getSource()
 * @see #getTimeSteap()
 */
public class STGMessage {
	long TimeSteap;
	StgEvent theEvent;
	Object Source,Target;
	
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
	public STGMessage(StgEvent event,Object FromObj,Object TargetObj)
	{
		System.currentTimeMillis();
		Source=FromObj;
		theEvent=event;
		Target=TargetObj;
	}
	
	/**
	 * get the time when the message was sent
	 * @return
	 * Timesteap in long
	 * @author manageryzy
	 */
	public long getTimeSteap()
	{
		return TimeSteap;
	}
	
	/**
	 * get the event object of the message
	 * @return
	 * return the event message
	 * @author manageryzy
	 * @see StgEvent
	 */
	public StgEvent getEvent()
	{
		return theEvent;
	}
	
	/**
	 * get the object who send the message
	 * @return
	 * the object who send it
	 * <p><b>notice:</b> it may be null 
	 * @author manageryzy
	 */
	public Object getSource()
	{
		return Source;
	}
	
}
