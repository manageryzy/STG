package manageryzy.stg.engine.hal.Sound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;


/*the engine of the sound like shooting or booming
 * @author manageryzy
 * */
public class SoundEngine {
	Map<String,SoundEffect> SoundMap = null;
	
	public SoundEngine()
	{
		SoundMap=new HashMap<String,SoundEffect>();
		Logger.getGlobal().log(Level.INFO, "Sound Engine Start!");
	}
	
	public boolean LoadSound(String ResName,String FileName)
	{
		SoundEffect theSound=null;
		try {
			theSound=new SoundEffect(FileName);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getGlobal().log(Level.INFO,"Failed to load sound effect res:"+ResName
					+" at "+FileName);
			return false;
		}
		
		this.SoundMap.put(ResName, theSound);
		
		return true;
	}
	
	public boolean PlaySound(String ResName)
	{
		if(this.SoundMap.get(ResName)==null)
		{
			Logger.getGlobal().log(Level.WARNING, "Error:can't find loaded sound "+ResName);
			return false;
		}
		
		try {
			this.SoundMap.get(ResName).play();
		} catch (Exception e) {
			Logger.getGlobal().log(Level.WARNING, "Error in playing sound "+ResName);
			e.printStackTrace();
		}
		
		return true;
	}
	
	class SoundEffect
	{
		//notice: the data of the sound did not loaded in the memory!
		//it is just in the hard disk!These code do not work effectly!
		//I tried to control buffer by myself but it don't work at all
		//TODO:Load the sound effect into memory! or it would take a lot 
		//of time to read the disk
		File file;
		
		public SoundEffect(String FileName) throws Exception
		{
			file=new File(FileName);
		}
		
		public void play() throws Exception
		{
			try {
				DataLine.Info info;
				AudioInputStream stream;
				AudioFormat format;
				stream=AudioSystem.getAudioInputStream(file);
				format=stream.getFormat();
				info=new DataLine.Info(Clip.class, format);
				
				Clip clip=(Clip)AudioSystem.getLine(info);
				
				clip.open(stream);
				clip.setMicrosecondPosition(0);
				
				MyListener listener = new MyListener();	
				clip.addLineListener(listener);
				
				clip.start();
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		}
		
		class MyListener implements LineListener
		{

			@Override
			public void update(LineEvent event) {
				if(event.getType()==LineEvent.Type.STOP)
				{ 

					Clip clip=(Clip)event.getSource();

					clip.close();
				}

			}
			
		}
	}
}
