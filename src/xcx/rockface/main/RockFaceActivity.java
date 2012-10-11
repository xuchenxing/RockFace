package xcx.rockface.main;

import java.util.Date;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class RockFaceActivity extends Activity implements SensorEventListener,
		OnTouchListener {

	private final String TAG = "RockFace";

	// 定义sensor管理器
	private SensorManager mSensorManager;

	private ImageView imageView;

	private int[] pic = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
			R.drawable.p4, R.drawable.p5 };

	private int picNum = 0;

	// 手机上一个位置时重力感应坐标
	private float lastX;
	private float lastY;
	private float lastZ;
	// 上次检测时间
	private long lastUpdateTime;

	// 加速度阈值，当摇晃速度达到这值后产生作用
	private static final int ACCELERATION_LIMIT = 5;
	// 两次检测的时间间隔
	private static final int UPTATE_INTERVAL_TIME = 70;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rockface);

		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 获取传感器管理服务
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		imageView = (ImageView) findViewById(R.id.imageView);

		imageView.setImageResource(pic[picNum]);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplication(), "clickImage",
						Toast.LENGTH_SHORT);
				changePic();
			}
		});

		/*
		 * Button btn = (Button) findViewById(R.id.btn);
		 * btn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * Toast.makeText(getApplication(), "clickBtn", Toast.LENGTH_SHORT);
		 * changePic(); } });
		 */

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) { 
		
		long nowTime = new Date().getTime();
		
		if(nowTime - lastUpdateTime < UPTATE_INTERVAL_TIME){
			return;
		}
		
		int sensorType = event.sensor.getType();
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			if (reachLimitByAcceleration(event)) {
				changePic();
				lastUpdateTime = nowTime;
			}
		}
	}

	private boolean reachLimitByAcceleration(SensorEvent event) {
		/*
		 * 因为一般正常情况下，任意轴数值最大就在9.8~10之间，只有在你突然摇动手机的时候，瞬时加速度才会突然增大或减少。
		 * 所以，经过实际测试，只需监听任一轴的加速度大于14的时候，改变你需要的设置就OK了~~~
		 */
		// values[0]:X轴，values[1]：Y轴，values[2]：Z轴
		float[] values = event.values;
		Log.d(TAG, "values[0]=" + values[0] + ",values[1]=" + values[1]
				+ ",values[2]=" + values[2]);
		if (Math.abs(values[0]) > ACCELERATION_LIMIT
				|| Math.abs(values[1]) > ACCELERATION_LIMIT
				|| Math.abs(values[2]) > ACCELERATION_LIMIT) {
			return true;
		} else {
			return false;
		}
	}

	private boolean reachLimitBySpeed(SensorEvent event) {
		/*
		 * // 现在检测时间 long currentUpdateTime = System.currentTimeMillis(); //
		 * 两次检测的时间间隔 long timeInterval = currentUpdateTime - lastUpdateTime; //
		 * 判断是否达到了检测时间间隔 if (timeInterval < UPTATE_INTERVAL_TIME) return; //
		 * 现在的时间变成last时间 lastUpdateTime = currentUpdateTime; // 获得x,y,z坐标 float
		 * x = event.values[0]; float y = event.values[1]; float z =
		 * event.values[2]; // 获得x,y,z的变化值 float deltaX = x - lastX; float
		 * deltaY = y - lastY; float deltaZ = z - lastZ; // 将现在的坐标变成last坐标 lastX
		 * = x; lastY = y; lastZ = z; double speed = Math.sqrt(deltaX * deltaX +
		 * deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000; Log.d(TAG,
		 * "SPEED = " + speed); // 达到速度阀值，发出提示 if (speed >= SPEED_SHRESHOLD) {
		 * changePic(); }
		 */
		return false;
	}

	private void changePic() {
		Log.d(TAG, " change pic");
		picNum++;
		if (picNum >= pic.length) {
			picNum = 0;
		}
		imageView.setImageResource(pic[picNum]);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("action=" + event.getAction() + "desc="
				+ event.describeContents());
		changePic();
		return true;
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(this);
		super.onPause();
	}

}