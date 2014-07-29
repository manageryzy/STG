package manageryzy.game.stg;

import java.util.logging.Level;
import java.util.logging.Logger;

import manageryzy.stg.engine.GameMain.StgGameMain;
import manageryzy.stg.engine.hal.StgHal;
import manageryzy.stg.engine.*;

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
//		StgHal.theStgHal.BGMEngine.addMusic("Music1", "��ɫС�� �� Colorful Path.wav", 0, 0);
//		StgHal.theStgHal.BGMEngine.addMusic("Music2", "���O����g���� the Ultimate Truth.wav", 0, 0);
//		StgHal.theStgHal.SoundEffect.PlaySound("Sound1");
//		StgHal.theStgHal.BGMEngine.PlayAtOnce("Music1");
//		StgHal.theStgHal.initWindow("STG game");
		StgGameMain.GameMainThread();
		Logger.getGlobal().log(Level.INFO, "luncher exit");
		
	}

}
