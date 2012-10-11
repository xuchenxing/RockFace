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

	// ����sensor������
	private SensorManager mSensorManager;

	private ImageView imageView;

	private int[] pic = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
			R.drawable.p4, R.drawable.p5 };

	private int picNum = 0;

	// �ֻ���һ��λ��ʱ������Ӧ����
	private float lastX;
	private float lastY;
	private float lastZ;
	// �ϴμ��ʱ��
	private long lastUpdateTime;

	// ���ٶ���ֵ����ҡ���ٶȴﵽ��ֵ���������
	private static final int ACCELERATION_LIMIT = 5;
	// ���μ���ʱ����
	private static final int UPTATE_INTERVAL_TIME = 70;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rockface);

		// ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// ��ȡ�������������
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
		 * ��Ϊһ����������£���������ֵ������9.8~10֮�䣬ֻ������ͻȻҡ���ֻ���ʱ��˲ʱ���ٶȲŻ�ͻȻ�������١�
		 * ���ԣ�����ʵ�ʲ��ԣ�ֻ�������һ��ļ��ٶȴ���14��ʱ�򣬸ı�����Ҫ�����þ�OK��~~~
		 */
		// values[0]:X�ᣬvalues[1]��Y�ᣬvalues[2]��Z��
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
		 * // ���ڼ��ʱ�� long currentUpdateTime = System.currentTimeMillis(); //
		 * ���μ���ʱ���� long timeInterval = currentUpdateTime - lastUpdateTime; //
		 * �ж��Ƿ�ﵽ�˼��ʱ���� if (timeInterval < UPTATE_INTERVAL_TIME) return; //
		 * ���ڵ�ʱ����lastʱ�� lastUpdateTime = currentUpdateTime; // ���x,y,z���� float
		 * x = event.values[0]; float y = event.values[1]; float z =
		 * event.values[2]; // ���x,y,z�ı仯ֵ float deltaX = x - lastX; float
		 * deltaY = y - lastY; float deltaZ = z - lastZ; // �����ڵ�������last���� lastX
		 * = x; lastY = y; lastZ = z; double speed = Math.sqrt(deltaX * deltaX +
		 * deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000; Log.d(TAG,
		 * "SPEED = " + speed); // �ﵽ�ٶȷ�ֵ��������ʾ if (speed >= SPEED_SHRESHOLD) {
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