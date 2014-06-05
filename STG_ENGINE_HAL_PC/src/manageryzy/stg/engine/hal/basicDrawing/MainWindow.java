package manageryzy.stg.engine.hal.basicDrawing;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

//	private Frame mainFrame;
//	private GameCanvas theGameCanavs;
	
	public MainWindow(String WindowName)
	{
//		mainFrame = new Frame("STG ENGINE");
//	    mainFrame.setSize(640,800);
//	    mainFrame.setLayout(new BorderLayout());
//	    mainFrame.addWindowListener(new WindowAdapter() {
//	       public void windowClosing(WindowEvent windowEvent){
//	          System.exit(0);
//	       }        
//	    });    
//	    theGameCanavs=new GameCanvas();
//	    mainFrame.add(theGameCanavs);
//	    mainFrame.setVisible(true);  
	    
	    GLDisplay neheGLDisplay = GLDisplay.createGLDisplay(WindowName);  
        neheGLDisplay.addGLEventListener(new EngineRenderListener("heli",4.0f,false));  
        neheGLDisplay.start();  
	}	
	
//	class GameCanvas extends Canvas {
//
//	      public GameCanvas () {
//	         //setBackground (Color.a);
//	         setSize(300, 300);
//	      }
//
//	      public void paint (Graphics g) {
//	         Graphics2D g2;
//	         g2 = (Graphics2D) g;
//	         g2.drawString ("It is a custom canvas area", 70, 70);
//	      }
//	   }

}
