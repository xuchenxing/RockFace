package xcx.rockface.main;

import java.security.KeyStore.LoadStoreParameter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class ImageSwitcherTest extends Activity implements
		OnItemSelectedListener, ViewFactory {
	
	private String TAG = this.getClass().getName();
	
	private ImageSwitcher is;
	private Gallery gallery;
	private Integer[] mThumbIds = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
			R.drawable.p4, R.drawable.p5 };
	private Integer[] mImageIds = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
			R.drawable.p4, R.drawable.p5 };
	private int downX, upX;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.imageswitcher);
		is = (ImageSwitcher) findViewById(R.id.switcher);
		is.setFactory(this);
		is.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		is.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));
		//增加滑动切换
		is.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN) {
					downX = (int)event.getX(); //取得按下时的x坐标
					return true;
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					upX = (int)event.getX(); //取得松开时的x坐标
					int index = gallery.getSelectedItemPosition();
					int count = gallery.getCount() ;
					Log.d(TAG,"downX = " + downX + "; upX = " + upX);
					Log.d(TAG, "index = " + index + "; count = " + count);
					if(downX - upX > 100){
						if(index == count - 1){//后翻
							index = 0;
						}else{
							index ++; 
						}
						is.setInAnimation(getApplicationContext(), R.layout.slide_in_right);
						is.setOutAnimation(getApplicationContext(), R.layout.slide_out_left);						
					}else if(upX - downX > 100){
						if(index == 0){//前翻
							index = count - 1;
						}else {
							index --;
						}
						//http://www.cnblogs.com/yourancao520/archive/2012/03/26/2417498.html
						//默认情况下 android.R.anim.slide_in_right 和 android.R.anim.slide_out_left是私有的,也就是说通过.属性是找不到的.
						//解决办法:slide_in_right和slide_out_left这两个xml都可以在sdk里找到的，修改后作为自己的资源调用就好了
						is.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
						is.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);

					}
					Log.d(TAG, "index = " + index);
					gallery.setSelection(index,true);
					return true;
				}
				return false;
			}
		});
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemSelectedListener(this);
	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
			i.setImageResource(mThumbIds[position]);
			i.setAdjustViewBounds(true);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//i.setBackgroundResource(R.drawable.);
			return i;
		}

		private Context mContext;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		is.setImageResource(mImageIds[position]);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}
	
	
	
	
}