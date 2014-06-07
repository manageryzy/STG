/**
 * 
 */
package manageryzy.stg.engine.mod;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**These Code works pretty like forge mod loader
 * @author manageryzy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StgMod  {
	
	String ModID();
	String ModName() default "UnNamedMod";
	String Version() default "0";
	String Author() default "nobody";
	
	/**
	 * will be called when the mod is loaded
	 * @return
	 * return true if succeed or the game system will not load it
	 */
	String ModEventListener() default "onModEvent";
	
	
	
}
