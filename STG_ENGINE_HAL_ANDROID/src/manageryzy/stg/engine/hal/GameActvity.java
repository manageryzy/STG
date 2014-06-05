/**
 * 
 */
package manageryzy.stg.engine.hal;


import manageryzy.stg.engine.hal.basicDrawing.MyRenderer;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
/**
 * @author manageryzy
 *
 */
public class GameActvity extends Activity  {

	private GLSurfaceView glSurfaceView;
    private MyRenderer renderer;
    
	@Override
	public void onCreate(final Bundle state) {
		 super.onCreate(state);
		 
		// 实例化MyRenderer,GLSurfaceView类
	    renderer = new MyRenderer();
	    glSurfaceView = new GLSurfaceView(this);
	    // 设置渲染器
	    glSurfaceView.setRenderer(renderer);
	    // 为当前Activity类指定视图
	    setContentView(glSurfaceView);

//		 Toast.makeText(getApplicationContext(), "默认Toast样式",
//			     Toast.LENGTH_SHORT).show();
//		    final GLCapabilities caps =
//		      new GLCapabilities(GLProfile.get(GLProfile.GLES2));
//		    final GLWindow gl_window = GLWindow.create(caps);
//		    gl_window.setFullscreen(true);
//
//		    this.setContentView(this.getWindow(), gl_window);
//
//		    gl_window.addMouseListener(new MouseAdapter() {
//		      @Override public void mousePressed(
//		        final MouseEvent e)
//		      {
//		        if (e.getPressure(false) > 2f) { // show Keyboard
//		          ((com.jogamp.newt.Window) e.getSource()).setKeyboardVisible(true);
//		        }
//		      }
//		    });
//
//		    EngineRenderListener example = new EngineRenderListener("heli",4.0f,false);

//		    demo.enableAndroidTrace(true);
//		    gl_window.addGLEventListener(example);
//		    gl_window.getScreen().addScreenModeListener(new ScreenModeListener() {
//		      @SuppressWarnings("unused") public void screenModeChangeNotify(
//		        final ScreenMode sm)
//		      {
//		        // Nothing.
//		      }
//
//		      @SuppressWarnings("unused") public void screenModeChanged(
//		        final ScreenMode sm,
//		        final boolean success)
//		      {
//		        System.err.println("ScreenMode Changed: " + sm);
//		      }
//		    });

//		    final Animator animator = new Animator(gl_window);
//		    this.setAnimator(animator);
//
//		    gl_window.setVisible(true);
//		    
//		    animator.setUpdateFPSFrames(60, System.err);
//		    animator.resetFPSCounter();
//		    gl_window.resetFPSCounter();

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// TODO Auto-generated method stub

	}

}
