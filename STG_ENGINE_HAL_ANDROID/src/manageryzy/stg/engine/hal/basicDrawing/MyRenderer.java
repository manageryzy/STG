package manageryzy.stg.engine.hal.basicDrawing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import OBJLoader.OBJModel;
import android.opengl.GLSurfaceView.Renderer;

public class MyRenderer implements Renderer {
    // ����0x10000ע��:Thus 0x10000 means a hexadecimal memory address 10000
    // ����cai_huan25���˵�ģ���Ұ������Ϊgoogle��ͼ��λ��ok 
    static int one = 0x10000;
    // Int�͵Ļ����������涨�����ĸ��ֱ��������Σ������Σ���������ɫ����������ɫ�Ļ����� 
    private static IntBuffer triangleBuffer;
    private static IntBuffer quaterBuffer;
    private static IntBuffer color1Buffer;
    private static IntBuffer color2Buffer;
    // �����ζ��㣬�ֱ��Ӧx,y,z,��˳���Ϊ��ά������z���Ͻ�Ϊ0
    private int[] vertices = new int[] { 
            0, one, 0,
            -one, -one, 0,
            one, -one, 0 
    };
    //��������Ϊ����˵�������᣺��������λ�����������ģ������ĸ�����ֱ������Ͻǣ����Ͻǣ����½ǣ����½� 
    private int[] quater = new int[] { 
            one, one, 0,
            -one, one, 0,
            one, -one, 0,
            -one, -one, 0 
    };
    //����ÿ���������ɫ��ÿ���������ɫ����(r,g,b,a)
    //�����θ�������ɫ(��������)
    private int[] color = new int[] { 
            one, 0, 0,one,
            0, one,0, one,
            0,0,one, one 
    };
    //�����θ�������ɫ(4������)
    private int[] color2 = new int[] { 
            one, 0, 0, 0,
            one, one, 0, 0,
            one,one,one, 0,
            0, one, one, 0 
    };
    
    OBJModel modle;

    //ʵ��Renderer�ӿڵķ���onDrawFrame
    @Override
    public void onDrawFrame(GL10 gl) {
        //��Ϊ��glVertexPointer������glColorPointer������Ҫ����һ��ֱ�ӵ�Buffer��
        //���������vbb1,vbb2,colorvbb1,colorvbb2��Ϊ����һ��ֱ�ӵ�Buffer
        //�Ե�һ��Ϊ��˵����������ByteBuffer��allocateDirect�����������µ�ֱ���ֽڻ�������
        //��1��Int��4��byte�����Խ�vertices�ĳ��ȳ���4
        // order(ByteOrder.nativeOrder)�����Ա����ֽ�˳�����޸Ĵ˻��������ֽ�˳��
        //Ȼ����asIntBuffer�����������ֽڻ���������ͼ����Ϊ int ��������
        //put���������� int д��˻������ĵ�ǰλ�ã�Ȼ���λ�õ����� 
        //position�������ô˻�������λ�á��������Ѷ��岢�Ҵ����µ�λ�ã���Ҫ�����ñ�ǡ� 
        
        // triangle��ByteBuffer
        ByteBuffer vbb1 = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb1.order(ByteOrder.nativeOrder());
        triangleBuffer = vbb1.asIntBuffer();
        triangleBuffer.put(vertices);
        triangleBuffer.position(0);

        // quater��ByteBuffer
        ByteBuffer vbb2 = ByteBuffer.allocateDirect(quater.length * 4);
        vbb2.order(ByteOrder.nativeOrder());
        quaterBuffer = vbb2.asIntBuffer();
        quaterBuffer.put(quater);
        quaterBuffer.position(0);

        // color��ByteBuffer
        ByteBuffer colorvbb1 = ByteBuffer.allocateDirect(color.length * 4);
        colorvbb1.order(ByteOrder.nativeOrder());
        color1Buffer = colorvbb1.asIntBuffer();
        color1Buffer.put(color);
        color1Buffer.position(0);

        // color2��ByteBuffer
        ByteBuffer colorvbb2 = ByteBuffer.allocateDirect(color2.length * 4);
        colorvbb2.order(ByteOrder.nativeOrder());
        color2Buffer = colorvbb2.asIntBuffer();
        color2Buffer.put(color2);
        color2Buffer.position(0);

        // ����Triangles
        // �����Ļ����Ȼ���
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // ���õ�ǰ��ģ�͹۲����
        gl.glLoadIdentity();

        //��������Ϊ������ɫ�붥��ǰ��������
        //(��ɫ�ɲ�����һ�ּ򵥷�ʽ��˵��http://blog.csdn.net/Simdanfeg/archive/2011/03/17/6255932.aspx)
        // �������ö���
        //GL10.GL_VERTEX_ARRAY��������
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // ����������ɫ
        //GL10.GL_COLOR_ARRAY��ɫ����
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        // ����1.5��λ����������Ļ6.0
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);

        //GL_FIXED,GL_FLOAT,GL_UNSIGNED_BYTE
        //������Ϣ��  http://www.devx.com/wireless/Article/32879/1954
        //�����е�GL_FIXED��ʾ����֮ǰ�����oneΪ��λ���� 
        // ����������
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, triangleBuffer);
        // ������������ɫ
        gl.glColorPointer(4, GL10.GL_FIXED, 0, color1Buffer);
        // ����������
        //GL10.GL_TRIANGLES:��ÿ����������Ϊһ�������������Ρ�����3n-2��3n-1��3n�����˵�n�������Σ��ܹ�����N/3�������Ρ� 
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // ���õ�ǰģ�͵Ĺ۲����
        gl.glLoadIdentity();

        // ����1.5��λ����������Ļ6.0
        gl.glTranslatef(1.5f, 0.0f, -6.0f);
        // ����������
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
        // ������������ɫ
        gl.glColorPointer(4, GL10.GL_FIXED, 0, color2Buffer);
        //GL_TRIANGLE_STRIP������һ�������������Ρ�����������n������n��n+1��n+2�����˵�n�������Σ�
        //����ż��n������n+1��n��n+2�����˵�n�������Σ��ܹ�����N-2�������Ρ� 
        // ����������
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        // ȡ����ɫ����
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        // ȡ����������
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

    //ʵ��Renderer�ӿڵķ���onSurfaceChanged
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        // ����OpenGL�����Ĵ�С,(0,0)��ʾ�����ڲ��ӿڵ����½ǣ�(w,h)ָ�����ӿڵĴ�С 
        gl.glViewport(0, 0, width, height);
        // ����ͶӰ����
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // ����ͶӰ����
        gl.glLoadIdentity();
        // �����ӿڵĴ�С
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        //�����������opengl es���Ժ����еı任����Ӱ�����ģ��(�����ǻ��Ƶ�ͼ��)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    //ʵ��Renderer�ӿڵķ���onSurfaceCreated
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    	modle=new OBJModel("heli", 4.0, gl, true);
        // ����ϵͳ��͸�ӽ�������(ѡ��Ч�����Ȼ����ٶ����ȣ���������ѡ���ٶ�����)
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        // �ú�ɫ�������Ļ��ɫ
        gl.glClearColor(0, 0, 0, 0);
        // ������Ӱƽ��
        gl.glShadeModel(GL10.GL_SMOOTH);

        // ������Ȼ���
        gl.glClearDepthf(1.0f);
        // ������Ȳ���
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // ������Ȳ��Ե�����
        gl.glDepthFunc(GL10.GL_LEQUAL);

    }
}