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
 *�������ʹ��OpenGL��������Ҫ�࣬���γ̵Ķ��ǻ��������֮�Ͻ��й����ġ� 
 */  
public class GLDisplay {  
    //Ĭ�ϵĳ���Ŀ�  
    private static final int DEFAULT_WIDTH = 640;  
    //Ĭ�ϵĳ���ĸ�  
    private static final int DEFAULT_HEIGHT = 480;  
  
    private static final int DONT_CARE = -1;  
  
    //���б���Ĵ���  
    private JFrame frame;  
    //��������ͼ�εĻ���  
    private GLCanvas glCanvas;  
    //֡Ƶ�����࣬�����ͼ�λ���Ƶ�ʽ��ɴ������  
    private FPSAnimator animator;  
    //�Ƿ�ȫ��  
    private boolean fullscreen;  
    //���򴰿ڵĿ�  
    private int width;  
     //���򴰿ڵĸ�  
    private int height;  
    //��ǰ��ͼ�λ�����ʹ�õ�ͼ���豸��  
    private GraphicsDevice usedDevice;  
  
    //����������  
    private MyHelpOverlayGLEventListener helpOverlayGLEventListener = new MyHelpOverlayGLEventListener();  
   //�쳣������  
    private MyExceptionHandler exceptionHandler = new MyExceptionHandler();  
  
    //����GLDisplay��  
    public static GLDisplay createGLDisplay( String title ) {  
        return createGLDisplay( title, new GLCapabilities(null) );  
    }  
    /** 
     *  
     * @param title ���ڱ��� 
     * @param caps OpenGL����Ⱦ������������ 
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
     * GLDisplay�Ĺ����� 
     * @param title ���ڵı��� 
     * @param width ���ڵĿ� 
     * @param height ���ڵĸ� 
     * @param fullscreen �Ƿ�ȫ�� 
     * @param caps OpenGL����Ⱦ���õ������� 
     */  
    private GLDisplay(String title, int width, int height, boolean fullscreen, GLCapabilities caps) {  
        //����GL���ƻ���  
        glCanvas = new GLCanvas(caps);  
        //���û����Ĵ�С  
        glCanvas.setSize( width, height );  
        //�����Ƿ�Ӧ�ú��ԴӲ���ϵͳ���ܵĻ�����Ϣ����������Ϊtrue�������ղ���ϵͳ  
        //�Ļ�����Ϣ  
        glCanvas.setIgnoreRepaint( true );  
        //����ע�����������  
        glCanvas.addGLEventListener( helpOverlayGLEventListener );  
        //��������  
        frame = new JFrame( title );  
        //���ô��ڵĲ��� Ϊ �߽粼��  
        frame.getContentPane().setLayout( new BorderLayout() );  
        //���������õ�λ��Ϊ�߽粼�ֵ�����  
        frame.getContentPane().add( glCanvas, BorderLayout.CENTER );  
       //ע����̼�����  
        addKeyListener( new MyKeyAdapter() );  
  
        this.fullscreen = fullscreen;  
        this.width = width;  
        this.height = height;  
        //����֡Ƶ��������  
        animator = new FPSAnimator( glCanvas, 60 );  
        //�����Ƿ����ˢ��  
        //animator.setRunAsFastAsPossible(false);  
        animator.setFPS(60);
    }  
  
    public void start() {  
        try {  
            //��Ļ�Ĵ�С  
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
            //���ô���װ��  
            frame.setUndecorated( fullscreen );  
  
            //ע�ᴰ�����  
            frame.addWindowListener( new MyWindowAdapter() );  
  
            if ( fullscreen ) {  
                //�����ȫ���Ļ���ͼ���豸�������������Ϊȫ��  
                usedDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();  
                usedDevice.setFullScreenWindow( frame );  
                //������ʾ�豸��ʾģʽ  
                usedDevice.setDisplayMode(  
                        findDisplayMode(  
                                usedDevice.getDisplayModes(),  
                                width, height,  
                                usedDevice.getDisplayMode().getBitDepth(),  
                                usedDevice.getDisplayMode().getRefreshRate()  
                        )  
                );  
            } else {  
                //�������ȫ�������ô����С  
                frame.setSize( frame.getContentPane().getPreferredSize() );  
                frame.setLocation(  
                        ( screenSize.width - frame.getWidth() ) / 2,  
                        ( screenSize.height - frame.getHeight() ) / 2  
                );  
                frame.setVisible( true );  
            }  
           //������ȡ����  
            glCanvas.requestFocus();  
  
            animator.start();  
        } catch ( Exception e ) {  
            exceptionHandler.handleException( e );  
        }  
    }  
  
    //�����˳��Ĵ���Ĵ���  
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
  
    //�õ���ǰ����ʵģʽ  
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
        //���̵�ĳЩ������  
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
  
    //�쳣������  
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
     * ��ǰ�İ��������� 
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
  
        //��ʾ���������������ע���˵ļ�������������ʾ����  
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
        //������ע���˵ļ���������г�ʼ��  
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