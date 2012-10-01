package xcx.rockface.main;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button rockFace = (Button) findViewById(R.id.rockface);
		rockFace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), RockFaceActivity.class));
			}
		});
		
		Button imageSwitcher = (Button) findViewById(R.id.imageswitcher);
		imageSwitcher.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ImageSwitcherTest.class));
			}
		});
		
	}
	
	
	private static Boolean isExit = false; 
    private static Boolean hasTask = false; 
    Timer tExit = new Timer(); 
    TimerTask task = new TimerTask() { 
        @Override
        public void run() { 
            isExit = false; 
            hasTask = true; 
        } 
    }; 
      
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        System.out.println("TabHost_Index.java onKeyDown"); 
        if (keyCode == KeyEvent.KEYCODE_BACK) { 
            if(isExit == false ) { 
                isExit = true; 
                Toast.makeText(this, "再按一次后退键退出应用程序", Toast.LENGTH_SHORT).show(); 
                if(!hasTask) { 
                    tExit.schedule(task, 2000); 
                } 
            } else { 
                finish(); 
                System.exit(0); 
            } 
        } 
        return false; 
    }
	
	
	
}