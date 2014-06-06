package manageryzy.stg.engine.hal.basicDrawing;

import com.jogamp.common.nio.Buffers;  
  
import com.jogamp.opengl.util.gl2.GLUT;  
import javax.media.opengl.GL;  
import javax.media.opengl.GLAutoDrawable;  
import javax.media.opengl.GLEventListener;  
import javax.media.opengl.glu.GLU;  
import javax.swing.*;  
import java.awt.event.KeyEvent;  
import java.awt.event.MouseEvent;  
import java.util.ArrayList;  
import java.util.List;  
import java.nio.IntBuffer;  
import javax.media.opengl.GL2;  
  
/** 
 * @author Pepijn Van Eeckhoudt 
 * 
 */  
/** 
 * 在Java的图形开发中我们知道swing的开发都是基于事件驱动的，在这里 
 * OpenGL的图形的开发也是基于事件驱动进行设计的。所有需要的绘制图形 
 * 或者图像的类都需要实现GLEventListener接口。 
 * @author Administrator 
 */  
public class HelpOverlay implements GLEventListener {  
    /** 
     * 键盘实体 
     */  
    private List keyboardEntries = new ArrayList();  
  
    /** 
     * 鼠标实体 
     */  
    private List mouseEntries = new ArrayList();  
    /** 
     * 是否可见 
     */  
    private boolean visible = false;  
    private GLUT glut = new GLUT();  
    private GLU glu = new GLU();  
    private static final int CHAR_HEIGHT = 12;  
    private static final int OFFSET = 15;  
    private static final int INDENT = 3;  
    private static final String KEYBOARD_CONTROLS = "Keyboard controls";  
    private static final String MOUSE_CONTROLS = "Mouse controls";  
  
    public boolean isVisible() {  
        return visible;  
    }  
  
    public void setVisible(boolean visible) {  
        this.visible = visible;  
    }  
  
    public void display(GLAutoDrawable glDrawable) {  
        GL2 gl = glDrawable.getGL().getGL2(); //OPenGL的主接口  
  
        // Store old matrices  
        //设置当前要操作的矩阵 选择模型观察矩阵  
        gl.glMatrixMode(GL2.GL_MODELVIEW);  
        gl.glPushMatrix();//压入矩阵堆栈中  
        gl.glLoadIdentity();//重置模型观察矩阵  
        gl.glMatrixMode(GL2.GL_PROJECTION);//选择投影矩阵  
        gl.glPushMatrix();//压入矩阵堆栈中  
        gl.glLoadIdentity();//重置投影矩阵  
  
        //重置当前的窗口  
        gl.glViewport(0, 0, glDrawable.getWidth(), glDrawable.getHeight());  
  
        // Store enabled state and disable lighting, texture mapping and the depth buffer  
        //将属性设置入栈  
        gl.glPushAttrib(GL2.GL_ENABLE_BIT);  
        //关闭OpenGL 的相关功能  
        gl.glDisable(GL.GL_BLEND);  
        gl.glDisable(GL2.GL_LIGHTING);  
        gl.glDisable(GL2.GL_TEXTURE_2D);  
        gl.glDisable(GL.GL_DEPTH_TEST);  
  
        // Retrieve the current viewport and switch to orthographic mode  
        //检索当前视区并切换到正交模式  
        IntBuffer viewPort = Buffers.newDirectIntBuffer(4);  
        gl.glGetIntegerv(GL.GL_VIEWPORT, viewPort);  
        //设置二维坐标系统参数  
        glu.gluOrtho2D(0, viewPort.get(2), viewPort.get(3), 0);  
  
        // Render the text  
        gl.glColor3f(1, 1, 1);  
  
        int x = OFFSET;  
        int maxx = 0;  
        int y = OFFSET + CHAR_HEIGHT;  
  
       //显示键盘敲入的字符  
        if (keyboardEntries.size() > 0) {  
            //定义像素操作的光栅位置即设置位置  
            gl.glRasterPos2i(x, y);  
            //绘制这个字符串  
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, KEYBOARD_CONTROLS);  
            maxx = Math.max(maxx, OFFSET + glut.glutBitmapLength(GLUT.BITMAP_HELVETICA_12, KEYBOARD_CONTROLS));  
  
            y += OFFSET;  
            x += INDENT;  
            for (int i = 0; i < keyboardEntries.size(); i++) {  
                gl.glRasterPos2f(x, y);  
                String text = (String) keyboardEntries.get(i);  
                glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, text);  
                maxx = Math.max(maxx, OFFSET + glut.glutBitmapLength(GLUT.BITMAP_HELVETICA_12, text));  
                y += OFFSET;  
            }  
        }  
        //鼠标显示字符串  
        if (mouseEntries.size() > 0) {  
            x = maxx + OFFSET;  
            y = OFFSET + CHAR_HEIGHT;  
            gl.glRasterPos2i(x, y);  
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, MOUSE_CONTROLS);  
  
            y += OFFSET;  
            x += INDENT;  
            for (int i = 0; i < mouseEntries.size(); i++) {  
                gl.glRasterPos2f(x, y);  
                glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, (String) mouseEntries.get(i));  
                y += OFFSET;  
            }  
        }  
  
        // Restore enabled state  
        //从新设置属性状态  
        gl.glPopAttrib();  
  
        // Restore old matrices  
        //当前的矩阵出栈  
        gl.glPopMatrix();  
        //设置当前为模型观察矩阵  
        gl.glMatrixMode(GL2.GL_MODELVIEW);  
        //当前的矩阵出栈  
        gl.glPopMatrix();  
    }  
  
    public void displayChanged(GLAutoDrawable glDrawable, boolean b, boolean b1) {  
    }  
  
    public void init(GLAutoDrawable glDrawable) {  
    }  
  
    public void reshape(GLAutoDrawable glDrawable, int i, int i1, int i2, int i3) {  
    }  
  
    //注册键盘  
    public void registerKeyStroke(KeyStroke keyStroke, String description) {  
        String modifiersText = KeyEvent.getKeyModifiersText(keyStroke.getModifiers());  
        String keyText = KeyEvent.getKeyText(keyStroke.getKeyCode());  
        keyboardEntries.add(  
                (modifiersText.length() != 0 ? modifiersText + " " : "") +  
                keyText + ": " +  
                description  
        );  
    }  
   //注册鼠标事件  
    public void registerMouseEvent(int id, int modifiers, String description) {  
        String mouseText = null;  
        switch (id) {  
            case MouseEvent.MOUSE_CLICKED:  
                mouseText = "Clicked " + MouseEvent.getModifiersExText(modifiers);  
                break;  
            case MouseEvent.MOUSE_DRAGGED:  
                mouseText = "Dragged " + MouseEvent.getModifiersExText(modifiers);  
                break;  
            case MouseEvent.MOUSE_ENTERED:  
                mouseText = "Mouse enters";  
                break;  
            case MouseEvent.MOUSE_EXITED:  
                mouseText = "Mouse exits";  
                break;  
            case MouseEvent.MOUSE_MOVED:  
                mouseText = "Mouse moves";  
                break;  
            case MouseEvent.MOUSE_PRESSED:  
                mouseText = "Pressed " + MouseEvent.getModifiersExText(modifiers);  
                break;  
            case MouseEvent.MOUSE_RELEASED:  
                mouseText = "Released " + MouseEvent.getModifiersExText(modifiers);  
                break;  
        }  
        //将生成的字符串放入mouseEntries  
        mouseEntries.add(  
                mouseText + ": " + description  
        );  
  
    }  
  
    public void dispose(GLAutoDrawable arg0) {  
        throw new UnsupportedOperationException("Not supported yet.");  
    }  
}  