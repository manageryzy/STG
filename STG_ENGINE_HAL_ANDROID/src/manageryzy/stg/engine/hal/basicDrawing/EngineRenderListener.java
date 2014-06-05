package manageryzy.stg.engine.hal.basicDrawing;

import java.util.*;
import java.text.DecimalFormat;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES10;
import android.opengl.GLES20;
import OBJLoader.*;


public class EngineRenderListener implements GLEventListener 
{
  private static final float INCR_MAX = 0.45f;   // for rotation increments
  private static final float Z_DIST = -7.0f;      // for the camera position

  private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp

  private GLU glu;

  private String modelName;
  private OBJModel model;
  private float maxSize;
  private boolean doRotate;

  // rotation variables
  private float rotX, rotY, rotZ;     // total rotations in x,y,z axes
  private float incrX, incrY, incrZ;  // increments for x,y,z rotations



  public EngineRenderListener(String nm, float sz, boolean r)
  { 
    modelName = nm;
    maxSize = sz;
    doRotate = r;
  } // end of ModelLoaderGLListener


  public void init(GLAutoDrawable drawable) 
  // perform start-up tasks
  {
    GLES1 gl = drawable.getGL().getGLES1();
    glu = new GLU();

    rotX = 0;
    rotY = 0; 
    rotZ = 0;
    Random random = new Random();
    incrX = (0.5f +random.nextFloat()/2)*INCR_MAX;   // INCR_MAX/2 - INCR_MAX degrees
    incrY = (0.5f +random.nextFloat()/2)*INCR_MAX; 
    incrZ = (0.5f +random.nextFloat()/2)*INCR_MAX; 

    gl.glClearColor(0.17f, 0.65f, 0.92f, 1.0f);                  
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glShadeModel(GL2.GL_SMOOTH);    // use smooth shading
    gl.glClearDepth(1.0);
    gl.glDepthFunc(GL.GL_LEQUAL);
    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_NICEST);    
    addLight(gl);

    // load the OBJ model maxSize=4
    model = new OBJModel(modelName, maxSize, gl, true);
  } // end of init()


  private void addLight(GLES1 gl)
  // two white light sources 
  {
    // enable light sources
    gl.glEnable(GL2.GL_LIGHTING);
    gl.glEnable(GL2.GL_LIGHT0);
    gl.glEnable(GL2.GL_LIGHT1);
    float[] whiteLight = {1.0f, 1.0f, 1.0f, 1.0f};  // bright white
    float lightPos[] = {10.0f, 10.0f, -10.0f, 1.0f};       
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);    
    float lightPos1[] = {-10.0f, -10.0f, 10.0f, 1.0f};    
    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, whiteLight, 0);  // diffuse white
    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos1, 0);
  }  // end of addLight()


  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
  // called when the drawable component is moved or resized
  {
    GL10 gl =null; drawable.getGL();
    if (height == 0)
      height = 1;   
    gl.glViewport(x, y, width, height); 
    gl.glMatrixMode(GL2.GL_PROJECTION);   
    gl.glLoadIdentity();
    glu.gluPerspective(45.0, (float)width/(float)height, 1, 100); 
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  } // end of reshape()


  public void display(GLAutoDrawable drawable) 
  {
    // update the rotations (if rotations were specified)
    if (doRotate) {
      rotX = (rotX + incrX) % 360.0f;
      rotY = (rotY + incrY) % 360.0f;
      rotZ = (rotZ + incrZ) % 360.0f;
    }

    GL2 gl = drawable.getGL().getGL2();   
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    
    
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    glu.gluLookAt(1.0,1.0,-10, 1.0,1.0,0, 0,1,0);   // position camera
    
    //gl.glMatrixMode(GL2.GL_PROJECTION);
    //gl.glLoadIdentity();
    gl.glTranslatef(1.0f,1.0f,0.0f);

    // apply rotations to the x,y,z axes
    if (doRotate) {
      gl.glRotatef(rotX, 1.0f, 0.0f, 0.0f);
      gl.glRotatef(rotY, 0.0f, 1.0f, 0.0f);
      gl.glRotatef(rotZ, 0.0f, 0.0f, 1.0f);
    }
    gl.glEnable(GL2.GL_LIGHTING);
    model.draw(gl);      // draw the model
    gl.glDisable(GL2.GL_LIGHTING);
    
    gl.glBegin(GL2.GL_TRIANGLE_FAN);       // Drawing Using Triangles  
    gl.glColor3f(1.0f, 0.0f, 0.0f); 
    gl.glVertex3f(0.0f, 0.0f, 0.0f);    // 上顶点 
    gl.glColor3f(1.0f, 0.0f, 0.0f); 
    gl.glVertex3f(10.0f, 0.0f, 0.0f);  // 左下 
    gl.glColor3f(1.0f, 0.0f, 0.0f); 
    gl.glVertex3f(10.0f, 0.1f, 0.0f);  // 左下 
    gl.glColor3f(1.0f, 0.0f, 0.0f); 
    gl.glVertex3f(10.0f, 0.0f, 0.1f);  // 左下 
    gl.glEnd();             // 绘制完毕
    
    gl.glBegin(GL2.GL_TRIANGLE_FAN);       // Drawing Using Triangles  
    gl.glColor3f(0.0f, 1.0f, 0.0f); 
    gl.glVertex3f(0.0f, 0.0f, 0.0f);    // 上顶点 
    gl.glColor3f(0.0f, 1.0f, 0.0f); 
    gl.glVertex3f(0.0f, 10.0f, 0.0f);  // 左下 
    gl.glColor3f(0.0f, 1.0f, 0.0f); 
    gl.glVertex3f(0.0f, 10.0f, 0.1f);  // 左下 
    gl.glColor3f(0.0f, 1.0f, 0.0f); 
    gl.glVertex3f(0.1f, 10.0f, 0.0f);  // 左下 
    gl.glEnd();             // 绘制完毕
    
    gl.glBegin(GL2.GL_TRIANGLE_FAN);       // Drawing Using Triangles  
    gl.glColor3f(0.0f, 0.0f, 1.0f); 
    gl.glVertex3f(0.0f, 0.0f, 0.0f);    // 上顶点 
    gl.glColor3f(0.0f, 0.0f, 1.0f); 
    gl.glVertex3f(0.0f, 0.0f, 10.0f);  // 左下 
    gl.glColor3f(0.0f, 0.0f, 1.0f); 
    gl.glVertex3f(0.0f, 0.1f, 10.0f);  // 左下
    gl.glColor3f(0.0f, 0.0f, 1.0f); 
    gl.glVertex3f(0.1f, 0.0f, 10.0f);  // 左下 
    gl.glEnd();             // 绘制完毕
    
    gl.glColor3f(1.0f, 1.0f, 1.0f);  
    
    
    
    gl.glFlush();
  } // end of display


  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, 
                             boolean deviceChanged)  
  {}


@Override
public void dispose(GLAutoDrawable arg0) {
	// TODO Auto-generated method stub
	
}

 
 } 

