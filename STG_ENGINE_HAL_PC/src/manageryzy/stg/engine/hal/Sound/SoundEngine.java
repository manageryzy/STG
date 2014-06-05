package manageryzy.stg.engine.hal.Sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
		Clip clip;
		public SoundEffect(String FileName) throws Exception
		{
			try {
				File file=new File(FileName);
				AudioInputStream stream=AudioSystem.getAudioInputStream(file);
				AudioFormat format=stream.getFormat();
				DataLine.Info info=new DataLine.Info(Clip.class, format);
				clip=(Clip)AudioSystem.getLine(info);
				clip.open(stream);
			} catch (UnsupportedAudioFileException | IOException
					| LineUnavailableException e) {
				Logger.getGlobal().log(Level.WARNING, "error in loading sound "+FileName);
				e.getMessage();
				e.printStackTrace();
				throw e;
			}
		}
		
		public void play()
		{
			try {
				clip.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}
	}
}
