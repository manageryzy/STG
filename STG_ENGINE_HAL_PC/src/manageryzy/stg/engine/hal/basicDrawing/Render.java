package manageryzy.stg.engine.hal.basicDrawing;  
  
/* 
 * Lesson01.java 
 */  
  
import javax.media.opengl.GL;  
import javax.media.opengl.GL2;  
import javax.media.opengl.GLAutoDrawable;  
import javax.media.opengl.GLEventListener;  
import javax.media.opengl.glu.GLU;  
  
/** 
 * Port of the NeHe OpenGL Tutorial (Lesson 1) 
 * to Java using the Jogl interface to OpenGL.  Jogl can be obtained 
 * at http://jogl.dev.java.net/ 
 * 
 *前面提过所有的OpenGL的渲染都要监听一个display事件，而所有的渲染都需要实现 
 * GLEventListener接口，这个借口定义了5个方法当前的 renderer类就是一个实现了五个 
 * 抽象方法的类 
 * 
 */  
class Renderer implements GLEventListener {  
	private GLU glu = new GLU();  
	  
    /** Called by the drawable to initiate OpenGL rendering by the client. 
     * After all GLEventListeners have been notified of a display event, the 
     * drawable will swap its buffers if necessary. 
     * @param glDrawable The GLAutoDrawable object. 
     */  
    public void display(GLAutoDrawable glDrawable) {  
        //获取到OpenGL的主接口  
        final GL2 gl = glDrawable.getGL().getGL2();  
        //清除屏幕及深度缓存  
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);  
        //重置当前的模型观察矩阵  
        gl.glLoadIdentity();  
        /** 
         * 当您调用glLoadIdentity()之后，您实际上将当前点移到了屏幕中心， 
         * X坐标轴从左至右，Y坐标轴从下至上，Z坐标轴从里至外。 
         * OpenGL屏幕中心的坐标值是X和Y轴上的0.0f点。 
         * 中心左面的坐标值是负值，右面是正值。 
         * 移向屏幕顶端是正值，移向屏幕底端是负值。 
         * 移入屏幕深处是负值，移出屏幕则是正值。 
         * glTranslatef(x, y, z)沿着 X, Y 和 Z 轴移动。 
         * 根据前面的次序，下面的代码沿着X轴左移1.5个单位，Y轴不动(0.0f)， 
         * 最后移入屏幕6.0f个单位。注意在glTranslatef(x, y, z)中当您移动的时候， 
         * 您并不是相对屏幕中心移动，而是相对与当前所在的屏幕位置。 
         */  
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);  
        /** 
         * 现在我们已经移到了屏幕的左半部分，并且将视图推入屏幕背后足够的距离以便我们 
         * 可以看见全部的场景－创建三角形。glBegin(GL_TRIANGLES)的意思是开始绘制三角 
         * 形，glEnd() 告诉OpenGL三角形已经创建好了。通常您会需要画3个顶点，可以使用 
         * GL_TRIANGLES。在绝大多数的显卡上，绘制三角形是相当快速的。如果要画四个顶点， 
         * 使用GL_QUADS的话会更方便。但据我所知，绝大多数的显卡都使用三角形来为对象着色。 
         * 最后，如果您想要画更多的顶点时，可以使用GL_POLYGON。 
         */  
        gl.glBegin(GL2.GL_TRIANGLES);       // Drawing Using Triangles  
        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // 上顶点  
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);  // 左下  
        gl.glVertex3f(1.0f, -1.0f, 0.0f);   // 右下  
        gl.glEnd();             // 绘制完毕  
          
        /** 
         * 在屏幕的左半部分画完三角形后，我们要移到右半部分来画正方形。为此要再次使用 
         * glTranslate。这次右移，所以X坐标值为正值。因为前面左移了1.5个单位， 
         * 这次要先向右移回屏幕中心(1.5个单位)，再向右移动1.5个单位。 
         * 总共要向右移3.0个单位。 
         */  
        gl.glTranslatef(3.0f, 0.0f, 0.0f);  
        gl.glBegin(GL2.GL_QUADS);               // 绘制一个四边形  
        gl.glVertex3f(-1.0f, 1.0f, 0.0f);   // 绘制左顶点  
        gl.glVertex3f(1.0f, 1.0f, 0.0f);    // 绘制右顶点  
        gl.glVertex3f(1.0f, -1.0f, 0.0f);   // 绘制右下点  
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);  // 绘制左下点  
        gl.glEnd();             // 开始绘制  
        gl.glFlush(); //清理缓存  
    }  
  
  
  
    /** 
     * 当显示模式改变的时候就会调用这个方法 
     * @param glDrawable 
     * @param modeChanged 
     * @param deviceChanged 
     */  
    public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged, boolean deviceChanged) {  
    }  
  
     /** 
     * 这个方法是在OpenGL的上下文初始化完成后立即调用这个方法进行相关的操作，一般用于在 
     * OpenGL一次初始化时设置光照和显示列表，这个方法可能被调用多次在GLautoDrawable 
     * 的OpenGL的上下文销毁和从新创建的时候。 
     * @param glDrawable 
     */  
    public void init(GLAutoDrawable glDrawable) {  
        //获取gl对象。  
        GL2 gl = glDrawable.getGL().getGL2();  
        //启动阴影平滑。阴影平滑通过多边形精细的混合色彩，并对外部光进行平滑。  
        gl.glShadeModel(GL2.GL_SMOOTH);  
        /** 
         * 设置清除屏幕时所用的颜色。如果您对色彩的工作原理不清楚的话， 
         * 我快速解释一下。色彩值的范围从0.0f到1.0f。0.0f代表最黑的情况， 
         * 1.0f就是最亮的情况。glClearColor 后的第一个参数是 
         * Red Intensity(红色分量),第二个是绿色，第三个是蓝色。 
         * 最大值也是1.0f，代表特定颜色分量的最亮情况。 
         * 最后一个参数是Alpha值。当它用来清除屏幕的时候， 
         * 我们不用关心第四个数字。现在让它为0.0f。 
         *通过混合三种原色(红、绿、蓝)，您可以得到不同的色彩。 
         */  
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background  
    }  
  
  
  
    /** 
     * 当组件的大小改变了的时候就会调用这个方法进行重绘。 
     * @param glDrawable 
     * @param x 
     * @param y 
     * @param width 
     * @param height 
     */  
    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {  
        final GL2 gl = glDrawable.getGL().getGL2();  
       //防止被零除  
        if (height <= 0) // avoid a divide by zero error!  
            height = 1;  
        final float h = (float) width / (float) height;  
        //设置视窗的大小  
        gl.glViewport(0, 0, width, height);  
        //选择投影矩阵 ,投影矩阵负责为我们的场景增加透视。  
        gl.glMatrixMode(GL2.GL_PROJECTION);  
        //重置投影矩阵;  
        gl.glLoadIdentity();  
        //设置视口的大小  
        glu.gluPerspective(45.0f, h, 1.0, 20.0);  
        //启用模型观察矩阵；模型观察矩阵中存放了我们的物体讯息。  
        gl.glMatrixMode(GL2.GL_MODELVIEW);  
        gl.glLoadIdentity();  
    }  
  
    public void dispose(GLAutoDrawable arg0) {  
        throw new UnsupportedOperationException("Not supported yet.");  
    }  
}  