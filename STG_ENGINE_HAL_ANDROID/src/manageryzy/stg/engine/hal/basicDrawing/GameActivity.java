package manageryzy.stg.engine.hal.basicDrawing;

import java.util.HashMap;
import java.util.Map;

import manageryzy.stg.engine.GameMain.StgGameMain;
import manageryzy.stg.engine.hal.StgHal;
import android.app.Activity;
import android.app.Dialog;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.openfiledemo.CallbackBundle;
import com.example.openfiledemo.OpenFileDialog;

public class GameActivity extends Activity {
	
	GLSurfaceView mView;
	MyRenderer rend;
	
	static private int openfileDialogId = 0;  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StgHal.theStgHal=new StgHal(getApplicationContext());

        Log.v("main activity", "constructor");
        mView = new GLSurfaceView(this); 
        mView.setEGLContextClientVersion(2);
        rend = new MyRenderer(this);
        mView.setRenderer(rend);
        
        //rend.load(R.raw.monkey1);
        //rend.load(R.raw.cube1);
	//this is a test edit        
//        showDialog(openfileDialogId);  
        
        new Thread(){  
            public void run(){  
            	StgGameMain.GameMainThread();
            }  
        }.start();  
        
        setContentView(mView);
    }

    
    
    @Override
	protected void onResume() {
		super.onResume();
		mView.onResume();
		Log.v("main activity", "on resume");
	}



	@Override
	protected void onPause() {
		super.onPause();
		mView.onPause();
		Log.v("main activity", "on pause");
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v("main activity", "on destroy");
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	@Override  
    protected Dialog onCreateDialog(int id) {  
        if(id==openfileDialogId){  
            Map<String, Integer> images = new HashMap<String, Integer>();  
            // ���漸�����ø��ļ����͵�ͼ�꣬ ��Ҫ���Ȱ�ͼ����ӵ���Դ�ļ���  
//            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // ��Ŀ¼ͼ��  
//            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //������һ���ͼ��  
//            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //�ļ���ͼ��  
//            images.put("wav", R.drawable.filedialog_wavfile);   //wav�ļ�ͼ��  
//            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);  
            Dialog dialog = OpenFileDialog.createDialog(id, this, "���ļ�", new CallbackBundle() {  
                @Override  
                public void callback(Bundle bundle) {  
                    String filepath = bundle.getString("path");  
                    setTitle(filepath); // ���ļ�·����ʾ�ڱ�����  
                }  
            },   
            ".wav;",  
            images);  
            return dialog;  
        }  
        return null;  
    }  
}
