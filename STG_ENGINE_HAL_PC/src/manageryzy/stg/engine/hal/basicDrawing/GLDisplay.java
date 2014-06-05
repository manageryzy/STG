package manageryzy.stg.engine.hal.basicDrawing;
  
  
import com.jogamp.opengl.util.FPSAnimator;  
  

import javax.swing.*;  
import javax.media.opengl.*;  

import java.awt.*;  
import java.awt.event.*;  
import java.io.PrintWriter;  
import java.io.StringWriter;  
import java.util.ArrayList;  

import javax.media.opengl.awt.GLCanvas;  

import manageryzy.stg.engine.hal.DataBase.StgConfig;
  
/** 
 * @author Pepijn Van Eeckhoudt 
 *这个类是使用OpenGL操作的主要类，本课程的都是基于这个类之上进行构建的。 
 */  
public class GLDisplay {  
    //默认的程序的宽  
    private static final int DEFAULT_WIDTH = 640;  
    //默认的程序的高  
    private static final int DEFAULT_HEIGHT = 480;  
  
    private static final int DONT_CARE = -1;  
  
    //带有标题的窗口  
    private JFrame frame;  
    //用来绘制图形的画布  
    private GLCanvas glCanvas;  
    //帧频动画类，程序的图形绘制频率将由此类控制  
    private FPSAnimator animator;  
    //是否全屏  
    private boolean fullscreen;  
    //程序窗口的宽  
    private int width;  
     //程序窗口的高  
    private int height;  
    //当前的图形环境中使用的图形设备。  
    private GraphicsDevice usedDevice;  
  
    //帮助监听类  
    private MyHelpOverlayGLEventListener helpOverlayGLEventListener = new MyHelpOverlayGLEventListener();  
   //异常代理类  
    private MyExceptionHandler exceptionHandler = new MyExceptionHandler();  
  
    //创建GLDisplay类  
    public static GLDisplay createGLDisplay( String title ) {  
        return createGLDisplay( title, new GLCapabilities(null) );  
    }  
    /** 
     *  
     * @param title 窗口标题 
     * @param caps OpenGL的渲染的上下文设置 
     * @return 
     */  
    public static GLDisplay createGLDisplay( String title, GLCapabilities caps ) {  
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        boolean fullscreen ;
        if (StgConfig.theConfig.getConfig("ifFullScreen").equals("true")) {
        	fullscreen = true;  
        }
        else
        {
        	fullscreen = false;
        }
        if (StgConfig.theConfig.getConfig("ifAskFullScreen").equals("true")) {
			if (device.isFullScreenSupported()) {
				int selectedOption = JOptionPane.showOptionDialog(null,
						"Fullscreen or Window?", title,
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new Object[] {
								"Fullscreen", "Windowed" }, "Windowed");
				fullscreen = selectedOption == 0;
			}
		}
		return new GLDisplay( title, DEFAULT_WIDTH, DEFAULT_HEIGHT, fullscreen, caps );  
    }  
  
    /** 
     * GLDisplay的构造器 
     * @param title 窗口的标题 
     * @param width 窗口的宽 
     * @param height 窗口的高 
     * @param fullscreen 是否全屏 
     * @param caps OpenGL的渲染设置的上下文 
     */  
    private GLDisplay(String title, int width, int height, boolean fullscreen, GLCapabilities caps) {  
        //创建GL绘制画布  
        glCanvas = new GLCanvas(caps);  
        //设置画布的大小  
        glCanvas.setSize( width, height );  
        //设置是否应该忽略从操作系统接受的绘制消息。这里设置为true即不接收操作系统  
        //的绘制消息  
        glCanvas.setIgnoreRepaint( true );  
        //画布注册帮助监听类  
        glCanvas.addGLEventListener( helpOverlayGLEventListener );  
        //创建窗口  
        frame = new JFrame( title );  
        //设置窗口的布局 为 边界布局  
        frame.getContentPane().setLayout( new BorderLayout() );  
        //将画布设置的位置为边界布局的中央  
        frame.getContentPane().add( glCanvas, BorderLayout.CENTER );  
       //注册键盘监听器  
        addKeyListener( new MyKeyAdapter() );  
  
        this.fullscreen = fullscreen;  
        this.width = width;  
        this.height = height;  
        //创建帧频动画对象  
        animator = new FPSAnimator( glCanvas, 60 );  
        //设置是否快速刷新  
        //animator.setRunAsFastAsPossible(false);  
        animator.setFPS(60);
    }  
  
    public void start() {  
        try {  
            //屏幕的大小  
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
            //启用窗体装饰  
            frame.setUndecorated( fullscreen );  
  
            //注册窗体监听  
            frame.addWindowListener( new MyWindowAdapter() );  
  
            if ( fullscreen ) {  
                //如果是全屏的话，图形设备将这个窗体设置为全屏  
                usedDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();  
                usedDevice.setFullScreenWindow( frame );  
                //设置显示设备显示模式  
                usedDevice.setDisplayMode(  
                        findDisplayMode(  
                                usedDevice.getDisplayModes(),  
                                width, height,  
                                usedDevice.getDisplayMode().getBitDepth(),  
                                usedDevice.getDisplayMode().getRefreshRate()  
                        )  
                );  
            } else {  
                //如果不是全屏，设置窗体大小  
                frame.setSize( frame.getContentPane().getPreferredSize() );  
                frame.setLocation(  
                        ( screenSize.width - frame.getWidth() ) / 2,  
                        ( screenSize.height - frame.getHeight() ) / 2  
                );  
                frame.setVisible( true );  
            }  
           //画布获取焦点  
            glCanvas.requestFocus();  
  
            animator.start();  
        } catch ( Exception e ) {  
            exceptionHandler.handleException( e );  
        }  
    }  
  
    //程序退出的窗体的处理  
    public void stop() {  
        try {  
            animator.stop();  
            if ( fullscreen ) {  
                usedDevice.setFullScreenWindow( null );  
                usedDevice = null;  
            }  
            frame.dispose();  
        } catch ( Exception e ) {  
            exceptionHandler.handleException( e );  
        } finally {  
            System.exit( 0 );  
        }  
    }  
  
    private DisplayMode findDisplayMode( DisplayMode[] displayModes, int requestedWidth, int requestedHeight, int requestedDepth, int requestedRefreshRate ) {  
        // Try to find an exact match  
        DisplayMode displayMode = findDisplayModeInternal( displayModes, requestedWidth, requestedHeight, requestedDepth, requestedRefreshRate );  
  
        // Try again, ignoring the requested bit depth  
        if ( displayMode == null )  
            displayMode = findDisplayModeInternal( displayModes, requestedWidth, requestedHeight, DONT_CARE, DONT_CARE );  
  
        // Try again, and again ignoring the requested bit depth and height  
        if ( displayMode == null )  
            displayMode = findDisplayModeInternal( displayModes, requestedWidth, DONT_CARE, DONT_CARE, DONT_CARE );  
  
        // If all else fails try to get any display mode  
        if ( displayMode == null )  
            displayMode = findDisplayModeInternal( displayModes, DONT_CARE, DONT_CARE, DONT_CARE, DONT_CARE );  
  
        return displayMode;  
    }  
  
    //得到当前的现实模式  
    private DisplayMode findDisplayModeInternal( DisplayMode[] displayModes, int requestedWidth, int requestedHeight, int requestedDepth, int requestedRefreshRate ) {  
        DisplayMode displayModeToUse = null;  
        for ( int i = 0; i < displayModes.length; i++ ) {  
            DisplayMode displayMode = displayModes[i];  
            if ( ( requestedWidth == DONT_CARE || displayMode.getWidth() == requestedWidth ) &&  
                    ( requestedHeight == DONT_CARE || displayMode.getHeight() == requestedHeight ) &&  
                    ( requestedHeight == DONT_CARE || displayMode.getRefreshRate() == requestedRefreshRate ) &&  
                    ( requestedDepth == DONT_CARE || displayMode.getBitDepth() == requestedDepth ) )  
                displayModeToUse = displayMode;  
        }  
  
        return displayModeToUse;  
    }  
  
    public void addGLEventListener( GLEventListener glEventListener ) {  
        this.helpOverlayGLEventListener.addGLEventListener( glEventListener );  
    }  
  
    public void removeGLEventListener( GLEventListener glEventListener ) {  
        this.helpOverlayGLEventListener.removeGLEventListener( glEventListener );  
    }  
  
    public void addKeyListener( KeyListener l ) {  
        glCanvas.addKeyListener( l );  
    }  
  
    public void addMouseListener( MouseListener l ) {  
        glCanvas.addMouseListener( l );  
    }  
  
    public void addMouseMotionListener( MouseMotionListener l ) {  
        glCanvas.addMouseMotionListener( l );  
    }  
  
    public void removeKeyListener( KeyListener l ) {  
        glCanvas.removeKeyListener( l );  
    }  
  
    public void removeMouseListener( MouseListener l ) {  
        glCanvas.removeMouseListener( l );  
    }  
  
    public void removeMouseMotionListener( MouseMotionListener l ) {  
        glCanvas.removeMouseMotionListener( l );  
    }  
  
    public void registerKeyStrokeForHelp( KeyStroke keyStroke, String description ) {  
        helpOverlayGLEventListener.registerKeyStroke( keyStroke, description );  
    }  
  
    public void registerMouseEventForHelp( int id, int modifiers, String description ) {  
        helpOverlayGLEventListener.registerMouseEvent( id, modifiers, description );  
    }  
  
    public String getTitle() {  
        return frame.getTitle();  
    }  
  
    public void setTitle( String title ) {  
        frame.setTitle( title );  
    }  
  
  
    private class MyKeyAdapter extends KeyAdapter {  
        public MyKeyAdapter() {  
            registerKeyStrokeForHelp( KeyStroke.getKeyStroke( KeyEvent.VK_F1, 0 ), "Show/hide help message" );  
            registerKeyStrokeForHelp( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), "Quit demo" );  
        }  
        //键盘的某些键按下  
        public void keyReleased( KeyEvent e ) {  
            switch ( e.getKeyCode() ) {  
                case KeyEvent.VK_ESCAPE:  
                    stop();  
                    break;  
                case KeyEvent.VK_F1:  
                    helpOverlayGLEventListener.toggleHelp();  
                    break;  
            }  
        }  
    }  
  
    private class MyWindowAdapter extends WindowAdapter {  
        public void windowClosing( WindowEvent e ) {  
            stop();  
        }  
    }  
  
    //异常处理类  
    private class MyExceptionHandler implements ExceptionHandler {  
        public void handleException( final Exception e ) {  
            SwingUtilities.invokeLater( new Runnable() {  
                public void run() {  
                    StringWriter stringWriter = new StringWriter();  
                    PrintWriter printWriter = new PrintWriter( stringWriter );  
                    e.printStackTrace( printWriter );  
                    JOptionPane.showMessageDialog( frame, stringWriter.toString(), "Exception occurred", JOptionPane.ERROR_MESSAGE );  
                    stop();  
                }  
            } );  
        }  
    }  
  
    /** 
     * 当前的帮助监听类 
     */  
    private static class MyHelpOverlayGLEventListener implements GLEventListener {  
        private java.util.List eventListeners = new ArrayList();  
        private HelpOverlay helpOverlay = new HelpOverlay();  
        private boolean showHelp = false;  
  
        public void toggleHelp() {  
            showHelp = !showHelp;  
        }  
  
        public void registerKeyStroke( KeyStroke keyStroke, String description ) {  
            helpOverlay.registerKeyStroke( keyStroke, description );  
        }  
  
        public void registerMouseEvent( int id, int modifiers, String description ) {  
            helpOverlay.registerMouseEvent( id, modifiers, description );  
        }  
  
        public void addGLEventListener( GLEventListener glEventListener ) {  
            eventListeners.add( glEventListener );  
        }  
  
        public void removeGLEventListener( GLEventListener glEventListener ) {  
            eventListeners.remove( glEventListener );  
        }  
  
        //显示方法，这里对所有注册了的监听类对象进行显示处理  
        public void display( GLAutoDrawable glDrawable ) {  
            for ( int i = 0; i < eventListeners.size(); i++ ) {  
                ( (GLEventListener) eventListeners.get( i ) ).display( glDrawable );  
            }  
            if ( showHelp )  
                helpOverlay.display( glDrawable );  
        }  
  
        public void displayChanged( GLAutoDrawable glDrawable, boolean b, boolean b1 ) {  
//            for ( int i = 0; i < eventListeners.size(); i++ ) {  
//                ( (GLEventListener) eventListeners.get( i ) ).displayChanged( glDrawable, b, b1 );  
//            }  
        }  
        //对所有注册了的监听对想进行初始化  
        public void init( GLAutoDrawable glDrawable ) {  
            for ( int i = 0; i < eventListeners.size(); i++ ) {  
                ( (GLEventListener) eventListeners.get( i ) ).init( glDrawable );  
            }  
        }  
  
        public void reshape( GLAutoDrawable glDrawable, int i0, int i1, int i2, int i3 ) {  
            for ( int i = 0; i < eventListeners.size(); i++ ) {  
                ( (GLEventListener) eventListeners.get( i ) ).reshape( glDrawable, i0, i1, i2, i3 );  
            }  
        }  
  
        public void dispose(GLAutoDrawable arg0) {  
            throw new UnsupportedOperationException("Not supported yet.");  
        }  
    }  
}  