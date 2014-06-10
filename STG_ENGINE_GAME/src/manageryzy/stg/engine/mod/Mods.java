/**
 * 
 */
package manageryzy.stg.engine.mod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import manageryzy.stg.engine.MessageSystem.STGMessage;

/**
 * @author manageryzy
 *
 */
public class Mods {
	public Class<?> TheMod;
	public Mods(Class<?> mainMod)
	{
		TheMod=mainMod;
	}
	
	public String GetModID() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return TheMod.getAnnotation(StgMod.class).ModID();
	}
	
	public String GetModName() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return TheMod.getAnnotation(StgMod.class).ModName();
	}
	
	public String GetAuthor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return TheMod.getAnnotation(StgMod.class).Author();
	}
	
	public Method getEventListener() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		return TheMod.getMethod(TheMod.getAnnotation(StgMod.class).ModEventListener(),new Class[]{STGMessage.class});
	}
}
