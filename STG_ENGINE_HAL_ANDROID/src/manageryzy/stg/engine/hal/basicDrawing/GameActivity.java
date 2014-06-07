package manageryzy.stg.engine.hal.basicDrawing;

import java.util.HashMap;
import java.util.Map;

import com.example.openfiledemo.CallbackBundle;
import com.example.openfiledemo.OpenFileDialog;

import manageryzy.stg.engine.hal.StgHal;
import manageryzy.stg.engine.hal.android.R;
import android.app.Activity;
import android.app.Dialog;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

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
        showDialog(openfileDialogId);  
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
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹  
//            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标  
//            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标  
//            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标  
//            images.put("wav", R.drawable.filedialog_wavfile);   //wav文件图标  
//            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);  
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {  
                @Override  
                public void callback(Bundle bundle) {  
                    String filepath = bundle.getString("path");  
                    setTitle(filepath); // 把文件路径显示在标题上  
                }  
            },   
            ".wav;",  
            images);  
            return dialog;  
        }  
        return null;  
    }  
}
