package manageryzy.stg.engine.hal.basicDrawing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import OBJLoader.OBJModel;
import android.opengl.GLSurfaceView.Renderer;

public class MyRenderer implements Renderer {
    // 关于0x10000注明:Thus 0x10000 means a hexadecimal memory address 10000
    // 就像cai_huan25大哥说的，大家把它理解为google画图单位就ok 
    static int one = 0x10000;
    // Int型的缓冲区，下面定义了四个分别是三角形，正方形，三角形颜色，正方形颜色的缓冲区 
    private static IntBuffer triangleBuffer;
    private static IntBuffer quaterBuffer;
    private static IntBuffer color1Buffer;
    private static IntBuffer color2Buffer;
    // 三角形顶点，分别对应x,y,z,因此程序为二维，所以z轴上皆为0
    private int[] vertices = new int[] { 
            0, one, 0,
            -one, -one, 0,
            one, -one, 0 
    };
    //以正方形为例，说明坐标轴：坐标中心位于正方形中心，下面四个顶点分别是右上角，左上角，右下角，左下角 
    private int[] quater = new int[] { 
            one, one, 0,
            -one, one, 0,
            one, -one, 0,
            -one, -one, 0 
    };
    //定义每个顶点的颜色，每个顶点的颜色皆由(r,g,b,a)
    //三角形各顶点颜色(三个顶点)
    private int[] color = new int[] { 
            one, 0, 0,one,
            0, one,0, one,
            0,0,one, one 
    };
    //正方形各顶点颜色(4个顶点)
    private int[] color2 = new int[] { 
            one, 0, 0, 0,
            one, one, 0, 0,
            one,one,one, 0,
            0, one, one, 0 
    };
    
    OBJModel modle;

    //实现Renderer接口的方法onDrawFrame
    @Override
    public void onDrawFrame(GL10 gl) {
        //因为在glVertexPointer或者是glColorPointer方法中要求传入一个直接的Buffer，
        //所以下面的vbb1,vbb2,colorvbb1,colorvbb2皆为创建一个直接的Buffer
        //以第一个为例说明，首先用ByteBuffer的allocateDirect方法来分配新的直接字节缓冲区。
        //因1个Int有4个byte，所以将vertices的长度乘以4
        // order(ByteOrder.nativeOrder)方法以本机字节顺序来修改此缓冲区的字节顺序
        //然后用asIntBuffer方法创建此字节缓冲区的视图，作为 int 缓冲区。
        //put方法将给定 int 写入此缓冲区的当前位置，然后该位置递增。 
        //position方法设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。 
        
        // triangle的ByteBuffer
        ByteBuffer vbb1 = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb1.order(ByteOrder.nativeOrder());
        triangleBuffer = vbb1.asIntBuffer();
        triangleBuffer.put(vertices);
        triangleBuffer.position(0);

        // quater的ByteBuffer
        ByteBuffer vbb2 = ByteBuffer.allocateDirect(quater.length * 4);
        vbb2.order(ByteOrder.nativeOrder());
        quaterBuffer = vbb2.asIntBuffer();
        quaterBuffer.put(quater);
        quaterBuffer.position(0);

        // color的ByteBuffer
        ByteBuffer colorvbb1 = ByteBuffer.allocateDirect(color.length * 4);
        colorvbb1.order(ByteOrder.nativeOrder());
        color1Buffer = colorvbb1.asIntBuffer();
        color1Buffer.put(color);
        color1Buffer.position(0);

        // color2的ByteBuffer
        ByteBuffer colorvbb2 = ByteBuffer.allocateDirect(color2.length * 4);
        colorvbb2.order(ByteOrder.nativeOrder());
        color2Buffer = colorvbb2.asIntBuffer();
        color2Buffer.put(color2);
        color2Buffer.position(0);

        // 绘制Triangles
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();

        //以下两步为绘制颜色与顶点前必做操作
        //(颜色可采用另一种简单方式，说见http://blog.csdn.net/Simdanfeg/archive/2011/03/17/6255932.aspx)
        // 允许设置顶点
        //GL10.GL_VERTEX_ARRAY顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 允许设置颜色
        //GL10.GL_COLOR_ARRAY颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        // 左移1.5单位，并移入屏幕6.0
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);

        //GL_FIXED,GL_FLOAT,GL_UNSIGNED_BYTE
        //更多信息见  http://www.devx.com/wireless/Article/32879/1954
        //参数中的GL_FIXED表示我们之前定义的one为单位长度 
        // 设置三角形
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, triangleBuffer);
        // 设置三角形颜色
        gl.glColorPointer(4, GL10.GL_FIXED, 0, color1Buffer);
        // 绘制三角形
        //GL10.GL_TRIANGLES:把每三个顶点作为一个独立的三角形。顶点3n-2，3n-1和3n定义了第n个三角形，总共绘制N/3个三角形。 
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // 重置当前模型的观察矩阵
        gl.glLoadIdentity();

        // 左移1.5单位，并移入屏幕6.0
        gl.glTranslatef(1.5f, 0.0f, -6.0f);
        // 设置正方形
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
        // 设置正方形颜色
        gl.glColorPointer(4, GL10.GL_FIXED, 0, color2Buffer);
        //GL_TRIANGLE_STRIP：绘制一组相连的三角形。对于奇数点n，顶点n，n+1和n+2定义了第n个三角形；
        //对于偶数n，顶点n+1，n和n+2定义了第n个三角形，总共绘制N-2个三角形。 
        // 绘制正方形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        // 取消颜色设置
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        // 取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

    //实现Renderer接口的方法onSurfaceChanged
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小 
        gl.glViewport(0, 0, width, height);
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 重置投影矩阵
        gl.glLoadIdentity();
        // 设置视口的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        //以下两句告诉opengl es，以后所有的变换都将影响这个模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    //实现Renderer接口的方法onSurfaceCreated
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    	modle=new OBJModel("heli", 4.0, gl, true);
        // 告诉系统对透视进行修正(选择效率优先还是速度优先，这里我们选择速度优先)
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        // 用黑色来清除屏幕颜色
        gl.glClearColor(0, 0, 0, 0);
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);

        // 设置深度缓存
        gl.glClearDepthf(1.0f);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 所做深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);

    }
}