package manageryzy.stg.engine.hal.Sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**the engine for BGM
 *
 * @author manageryzy
 */
public class MusicEngine {
	Map<String,BGMInfo> MusicMap = null;
	DataLine.Info info;
	AudioInputStream stream;
	AudioFormat format;
	Clip clip;
	Timer DelayTimer,EffectTimer1,EffectTimer2;
	float Volume=0;
	
	public MusicEngine()
	{
		MusicMap=new HashMap<String,BGMInfo>();
		DelayTimer=new Timer();
		EffectTimer1=new Timer();
		EffectTimer2=new Timer();
	}
	
	public boolean addMusic(String ResName,String FileName,int sp,int ep)
	{
		File theFile=new File(FileName);
		if(!theFile.canRead())
		{
			Logger.getGlobal().log(Level.WARNING,"ERROR:Can't load the BGM for IO ERROR in "+FileName);
			return false;
		}
		BGMInfo theBGM= new BGMInfo();
		theBGM.startPos=sp;
		theBGM.endPos=ep;
		theBGM.file=theFile;
		MusicMap.put(ResName,theBGM);
		
		return true;
	}
	
	public boolean Play(String Res,int delay,int FadeOutTime,int FadeInTime)
	{
		BGMInfo BGM=this.MusicMap.get(Res);
		if(BGM==null)
		{
			Logger.getGlobal().log(Level.WARNING, "ERROR: No BGM named "+Res);
		}
		
		if(this.clip!=null)
		{
			EffectTimer1.scheduleAtFixedRate(new FadeOut(FadeOutTime), delay,10);
			EffectTimer2.scheduleAtFixedRate(new FadeIn(FadeInTime), delay+FadeOutTime, 10);
		}
		DelayTimer.schedule(new ChangeMusicTask(BGM,FadeInTime),( delay+FadeOutTime));
		
		return true;
	}
	
	public boolean Play(String Res)
	{
		return this.Play(Res, 0, 500, 1000);
	}
	
	public boolean PlayAtOnce(String Res)
	{
		return this.Play(Res, 0, 0, 0);
	}
	
	public boolean Stop(int delay,int FadeOutTime)
	{
		if(clip==null)
		{
			Logger.getGlobal().log(Level.WARNING, "Error:can't stop BGM for it isn't playing!");
			return false;
		}
		
		DelayTimer.schedule(new StopMusic(), (delay+FadeOutTime));
		EffectTimer1.scheduleAtFixedRate(new FadeOut(FadeOutTime),FadeOutTime,10);
		//Notice: It seems that there is something wrong in the time controling.
		//TODO:Fix it
		return true;
	}
	
	public boolean Stop()
	{
		return this.Stop(0, 1000);
	}
	
	public boolean StopAtOnce()
	{
		return this.Stop(0, 0);
	}
	
	public boolean isPlaying()
	{
		if(this.clip!=null)
		{
			return clip.isActive();
		}
		return false;
	}
	
	boolean ChangeMusic(BGMInfo BGM)
	{
		try {
			stream=AudioSystem.getAudioInputStream(BGM.file);
			format=stream.getFormat();
			info=new DataLine.Info(Clip.class, format);
			
			clip=(Clip)AudioSystem.getLine(info);
			
			clip.open(stream);
			clip.setMicrosecondPosition(0);
			
			if(BGM.startPos!=BGM.endPos)
				clip.setLoopPoints(BGM.startPos, BGM.endPos);
			
			clip.start();
			Logger.getGlobal().log(Level.INFO, "BGM CHANGED!");
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			Logger.getGlobal().log(Level.WARNING, "Error in Changing the BGM!");
			e.getCause();
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	class FadeIn extends TimerTask{ 
		int effectTime;
		int RunningCount;
		public FadeIn(int t)
		{
			effectTime=t;
			RunningCount=0;
		}
		
        @Override
        public void run() { 
        	long nowTime=RunningCount++*10;
        	Volume=(float)(-(50.0*nowTime/effectTime)+1.0);
        	FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        	volume.setValue(Volume);
        	if(nowTime>effectTime)
        		this.cancel();
        }
    }
	
	class FadeOut extends TimerTask{ 
		int effectTime;
		int RunningCount;
		public FadeOut(int t)
		{
			effectTime=t;
			RunningCount=0;
		}
		
		@Override
        public void run() {
        	long nowTime=RunningCount++*10;
        	FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        	Volume=(float)(1.0-(50.0*nowTime/effectTime));

        	volume.setValue(Volume);

        	if(nowTime>effectTime)
        		this.cancel();
        }
    }
	
	class ChangeMusicTask extends TimerTask{ 
		
		BGMInfo BGM;
		public ChangeMusicTask(BGMInfo nextBGM, int fadeInTime)
		{
			BGM=nextBGM;
		}
		
        @Override
        public void run() { 
        	ChangeMusic(BGM);
        }
    }
	
	class StopMusic extends TimerTask{ 
		
        @Override
        public void run() { 
        	if(clip!=null)
        		Logger.getGlobal().log(Level.INFO, "BGM STOP!");
        		clip.stop();
        }
    }

	class BGMInfo
	{
		public int startPos;
		public int endPos;
		public File file;
	}
}

