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
	Timer DelayTimer,EffectTimer1,EffectTimer2,VolumeTimer;
	public float Volume=0;
	
	public MusicEngine()
	{
		MusicMap=new HashMap<String,BGMInfo>();
		DelayTimer=new Timer();
		EffectTimer1=new Timer();
		VolumeTimer=new Timer();
		VolumeTimer.scheduleAtFixedRate(new UpdateVolume(), 0, 10);
		EffectTimer2=new Timer();
	}
	
	/**
	 * add the music to the Res list!
	 * @param  ResName 
	 * the name of the Res you load
	 * 
	 * @param  FileName
	 * the path of of the BGM file
	 * @param sp  
	 * the start point of the loop of the BGM
	 * @param  ep 
	 * the end point of the loop of the BGM
	 * @author manageryzy
	 * 
	 * @return
	 * return true if succeed 
	 * */
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
	
	/**
	 * play the music in the Res list
	 * @param  ResName 
	 * the name of the Res you load
	 * 
	 * @param Res
	 * the name of Res have been loaded
	 * @param delay
	 * how long to delay before action (ms)
	 * @param FadeOutTime
	 * the duration for the current BGM to fade out (ms)
	 * @param FadeInTime
	 * the duration for the next BGM to fade in (ms)
	 * 
	 * @author manageryzy
	 * 
	 * @return
	 * return true if succeed 
	 * */
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
			EffectTimer2.scheduleAtFixedRate(new FadeIn(FadeInTime), delay+FadeOutTime+100, 10);
		}
		DelayTimer.schedule(new ChangeMusicTask(BGM,FadeInTime),( delay+FadeOutTime));

		
		return true;
	}
	
	/**
	 * play the BGM in the Res list
	 * present BGM will fade out in 500ms and the next BGM will fade in in 1000ms
	 * @param Res
	 * the Res name of the BGM you want to play
	 * 
	 * @author amanageryzy
	 * 
	 * @return
	 * return true if succeed
	 */
	public boolean Play(String Res)
	{
		return this.Play(Res, 0, 500, 1000);
	}
	
	/**
	 * just change BGM at once
	 * @param Res
	 * the name if res you have loaded!
	 * @return
	 * return true if succeed
	 * @author manageryzy
	 * @deprecated
	 * don't forget to set the volume by you self
	 */
	public boolean PlayAtOnce(String Res)
	{
		return this.Play(Res, 0, 0, 0);
	}
	
	/**
	 * Stop Playing the present BGM
	 * @param delay
	 * time of the delay before action
	 * @param FadeOutTime
	 * duration of the time of the present BGM to fade out
	 * @return
	 * return false if unsucceed,although it usually succeed
	 * @author manageryzy 
	 */
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
	
	/**
	 * stop the present BGM in 1000ms
	 * @return
	 * return true if succeed
	 * @author manageryzy
	 */
	public boolean Stop()
	{
		return this.Stop(0, 1000);
	}
	
	/**
	 * stop the present BGM at once
	 * @return
	 * return true if succeed
	 * @author manageryzy
	 * @deprecated 
	 * why not to fade out the BGM
	 */
	public boolean StopAtOnce()
	{
		return this.Stop(0, 0);
	}
	
	/**
	 * check whether the BGM is Playing
	 * @return
	 * return true if BGM is playing
	 * @author manageryzy
	 */
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
			if(clip!=null)
			{
				clip.stop();
				Volume=-30;
			}
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
        	Volume=(float)(-50+(50.0*nowTime/effectTime)+1.0);
        	
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
        	Volume=(float)(1.0-(50.0*nowTime/effectTime));

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
	
	class UpdateVolume extends TimerTask{ 
		
        @Override
        public void run() { 
        	try {
				if(clip!=null)
				{
					FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					volume.setValue(Volume);
				}
			} catch (Exception e) {
			}
        }
    }

	class BGMInfo
	{
		public int startPos;
		public int endPos;
		public File file;
	}
}

