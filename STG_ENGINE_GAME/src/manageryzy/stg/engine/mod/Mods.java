/**
 * 
 */
package manageryzy.stg.engine.mod;

/**
 * @author manageryzy
 *
 */
public class Mods {
	StgMod TheMod;
	public Mods(StgMod theMod)
	{
		TheMod=theMod;
	}
	
	public String GetModID()
	{
		return TheMod.ModID();
	}
	
	public String GetModName()
	{
		return TheMod.ModName();
	}
	
	public String GetAuthor()
	{
		return TheMod.Author();
	}
}
