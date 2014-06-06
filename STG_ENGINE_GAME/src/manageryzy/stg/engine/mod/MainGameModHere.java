package manageryzy.stg.engine.mod;

/**
 * this is the announce for the main mod
 * if the mod load fail,the whole program would break down!
 * There <b> Must</b> Be AN MAIN MOD
 * @author manageryzy
 *
 */
@StgMod(ModID = "MAIN_GAME_MOD")
public @interface MainGameModHere {
	final String getName ="THE_MAIN_GAME_MOD";
}
