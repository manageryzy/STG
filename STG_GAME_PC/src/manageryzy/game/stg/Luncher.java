package manageryzy.game.stg;

import java.util.logging.Level;
import java.util.logging.Logger;

import manageryzy.stg.engine.hal.StgHal;

public class Luncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger.getGlobal().setLevel(Level.FINE);
		Logger.getGlobal().log(Level.INFO, "luncher start");
		StgHal.theStgHal=new StgHal("STG.db");
//		StgHal.theStgHal.SoundEffect.LoadSound("Sound1", "se_powerup.wav");
//		StgHal.theStgHal.BGMEngine.addMusic("Music1", "春色小径 ～ Colorful Path.wav", 0, 0);
//		StgHal.theStgHal.BGMEngine.addMusic("Music2", "究極の真実　～ the Ultimate Truth.wav", 0, 0);
//		StgHal.theStgHal.SoundEffect.PlaySound("Sound1");
//		StgHal.theStgHal.BGMEngine.PlayAtOnce("Music1");
		StgHal.theStgHal.initWindow("STG game");
		Logger.getGlobal().log(Level.INFO, "luncher exit");
		
	}

}
