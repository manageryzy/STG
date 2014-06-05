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
 *ǰ��������е�OpenGL����Ⱦ��Ҫ����һ��display�¼��������е���Ⱦ����Ҫʵ�� 
 * GLEventListener�ӿڣ������ڶ�����5��������ǰ�� renderer�����һ��ʵ������� 
 * ���󷽷����� 
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
        //��ȡ��OpenGL�����ӿ�  
        final GL2 gl = glDrawable.getGL().getGL2();  
        //�����Ļ����Ȼ���  
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);  
        //���õ�ǰ��ģ�͹۲����  
        gl.glLoadIdentity();  
        /** 
         * ��������glLoadIdentity()֮����ʵ���Ͻ���ǰ���Ƶ�����Ļ���ģ� 
         * X������������ң�Y������������ϣ�Z������������⡣ 
         * OpenGL��Ļ���ĵ�����ֵ��X��Y���ϵ�0.0f�㡣 
         * �������������ֵ�Ǹ�ֵ����������ֵ�� 
         * ������Ļ��������ֵ��������Ļ�׶��Ǹ�ֵ�� 
         * ������Ļ��Ǹ�ֵ���Ƴ���Ļ������ֵ�� 
         * glTranslatef(x, y, z)���� X, Y �� Z ���ƶ��� 
         * ����ǰ��Ĵ�������Ĵ�������X������1.5����λ��Y�᲻��(0.0f)�� 
         * ���������Ļ6.0f����λ��ע����glTranslatef(x, y, z)�е����ƶ���ʱ�� 
         * �������������Ļ�����ƶ�����������뵱ǰ���ڵ���Ļλ�á� 
         */  
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);  
        /** 
         * ���������Ѿ��Ƶ�����Ļ����벿�֣����ҽ���ͼ������Ļ�����㹻�ľ����Ա����� 
         * ���Կ���ȫ���ĳ��������������Ρ�glBegin(GL_TRIANGLES)����˼�ǿ�ʼ�������� 
         * �Σ�glEnd() ����OpenGL�������Ѿ��������ˡ�ͨ��������Ҫ��3�����㣬����ʹ�� 
         * GL_TRIANGLES���ھ���������Կ��ϣ��������������൱���ٵġ����Ҫ���ĸ����㣬 
         * ʹ��GL_QUADS�Ļ�������㡣��������֪������������Կ���ʹ����������Ϊ������ɫ�� 
         * ����������Ҫ������Ķ���ʱ������ʹ��GL_POLYGON�� 
         */  
        gl.glBegin(GL2.GL_TRIANGLES);       // Drawing Using Triangles  
        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // �϶���  
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);  // ����  
        gl.glVertex3f(1.0f, -1.0f, 0.0f);   // ����  
        gl.glEnd();             // �������  
          
        /** 
         * ����Ļ����벿�ֻ��������κ�����Ҫ�Ƶ��Ұ벿�����������Ρ�Ϊ��Ҫ�ٴ�ʹ�� 
         * glTranslate��������ƣ�����X����ֵΪ��ֵ����Ϊǰ��������1.5����λ�� 
         * ���Ҫ�������ƻ���Ļ����(1.5����λ)���������ƶ�1.5����λ�� 
         * �ܹ�Ҫ������3.0����λ�� 
         */  
        gl.glTranslatef(3.0f, 0.0f, 0.0f);  
        gl.glBegin(GL2.GL_QUADS);               // ����һ���ı���  
        gl.glVertex3f(-1.0f, 1.0f, 0.0f);   // �����󶥵�  
        gl.glVertex3f(1.0f, 1.0f, 0.0f);    // �����Ҷ���  
        gl.glVertex3f(1.0f, -1.0f, 0.0f);   // �������µ�  
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);  // �������µ�  
        gl.glEnd();             // ��ʼ����  
        gl.glFlush(); //������  
    }  
  
  
  
    /** 
     * ����ʾģʽ�ı��ʱ��ͻ����������� 
     * @param glDrawable 
     * @param modeChanged 
     * @param deviceChanged 
     */  
    public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged, boolean deviceChanged) {  
    }  
  
     /** 
     * �����������OpenGL�������ĳ�ʼ����ɺ����������������������صĲ�����һ�������� 
     * OpenGLһ�γ�ʼ��ʱ���ù��պ���ʾ�б�����������ܱ����ö����GLautoDrawable 
     * ��OpenGL�����������ٺʹ��´�����ʱ�� 
     * @param glDrawable 
     */  
    public void init(GLAutoDrawable glDrawable) {  
        //��ȡgl����  
        GL2 gl = glDrawable.getGL().getGL2();  
        //������Ӱƽ������Ӱƽ��ͨ������ξ�ϸ�Ļ��ɫ�ʣ������ⲿ�����ƽ����  
        gl.glShadeModel(GL2.GL_SMOOTH);  
        /** 
         * ���������Ļʱ���õ���ɫ���������ɫ�ʵĹ���ԭ������Ļ��� 
         * �ҿ��ٽ���һ�¡�ɫ��ֵ�ķ�Χ��0.0f��1.0f��0.0f������ڵ������ 
         * 1.0f���������������glClearColor ��ĵ�һ�������� 
         * Red Intensity(��ɫ����),�ڶ�������ɫ������������ɫ�� 
         * ���ֵҲ��1.0f�������ض���ɫ��������������� 
         * ���һ��������Alphaֵ���������������Ļ��ʱ�� 
         * ���ǲ��ù��ĵ��ĸ����֡���������Ϊ0.0f�� 
         *ͨ���������ԭɫ(�졢�̡���)�������Եõ���ͬ��ɫ�ʡ� 
         */  
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background  
    }  
  
  
  
    /** 
     * ������Ĵ�С�ı��˵�ʱ��ͻ����������������ػ档 
     * @param glDrawable 
     * @param x 
     * @param y 
     * @param width 
     * @param height 
     */  
    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {  
        final GL2 gl = glDrawable.getGL().getGL2();  
       //��ֹ�����  
        if (height <= 0) // avoid a divide by zero error!  
            height = 1;  
        final float h = (float) width / (float) height;  
        //�����Ӵ��Ĵ�С  
        gl.glViewport(0, 0, width, height);  
        //ѡ��ͶӰ���� ,ͶӰ������Ϊ���ǵĳ�������͸�ӡ�  
        gl.glMatrixMode(GL2.GL_PROJECTION);  
        //����ͶӰ����;  
        gl.glLoadIdentity();  
        //�����ӿڵĴ�С  
        glu.gluPerspective(45.0f, h, 1.0, 20.0);  
        //����ģ�͹۲����ģ�͹۲�����д�������ǵ�����ѶϢ��  
        gl.glMatrixMode(GL2.GL_MODELVIEW);  
        gl.glLoadIdentity();  
    }  
  
    public void dispose(GLAutoDrawable arg0) {  
        throw new UnsupportedOperationException("Not supported yet.");  
    }  
}  