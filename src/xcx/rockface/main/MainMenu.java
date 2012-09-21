package xcx.rockface.main;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
	
	
	
}