package manageryzy.stg.engine.hal.basicDrawing;

import manageryzy.stg.engine.hal.StgHal;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class GameActivity extends Activity {
	
	GLSurfaceView mView;
	MyRenderer rend;
	
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
}
