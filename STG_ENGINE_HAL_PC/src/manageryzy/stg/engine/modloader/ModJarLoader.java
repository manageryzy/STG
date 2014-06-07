package manageryzy.stg.engine.modloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import manageryzy.stg.engine.hal.DataBase.StgConfig;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * the mod jar loader for PC
 * @author manageryzy
 * 
 */
public class ModJarLoader {
	public List<modInfo> modList;
	static ClassLoader cl;
	
	/**
	 * get ready to load mods<p>get mod xml
	 * @author manageryzy
	 */
	public ModJarLoader()
	{
		modList=new ArrayList<modInfo>();
		String FileName=StgConfig.theConfig.getConfig("ModListPath");
		File ModListFile=new File(FileName);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(ModListFile);
			doc.normalize();
		} catch (Exception e1) {
			Logger.getGlobal().log(Level.WARNING, "Can't load mod file list");
			e1.getCause();
			e1.printStackTrace();
			System.exit(-1);
		}
		
		for(int i=0;;i++)
		{	String ModPath=null;
			String ModClass=null;
			Node nowNode=doc.getElementsByTagName("mod").item(i);
			if (nowNode==null)
				break;
			for(int j=0;;j++)
			{
				Node modNode = nowNode.getChildNodes().item(j);
				
				if(modNode==null)
					break;
				
				if(modNode.getNodeName().equals("modPath"))
				{
					ModPath=modNode.getNodeValue();
					if(ModClass!=null)
					{
						modList.add(new modInfo(ModPath,ModClass));
					}
				}
				
				if(modNode.getNodeName().equals("modClass"))
				{
					ModClass=modNode.getNodeValue();
					if(ModPath!=null)
					{
						modList.add(new modInfo(ModPath,ModClass));
					}
				}
			}
			
		}
	}
	
	/**
	 * load the mod class from the jar file
	 * @param path
	 * the path of the jar file
	 * @param className
	 * the full name of the class
	 * @return
	 * the class of the jar file
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static Class LoadJar(String path,String className)
	{
		File file  = new File(path);
		URL url = null;
		try {
			url = file.toURL();
			URL[] urls = new URL[]{url};
			cl = new URLClassLoader(urls);

			Class cls = cl.loadClass(className);
			
			return cls;
		} catch (Exception e) {
			Logger.getGlobal().log(Level.WARNING,"can't load mod in file:"+path+" for class:"+className);
			e.printStackTrace();
		}
		
		return null;
	}
	
	class modInfo
	{
		public String Path;
		public String Cls;
		public modInfo(String ModPath,String ModClass)
		{
			Path=ModPath;
			Cls=ModClass;
		}
	}
}
