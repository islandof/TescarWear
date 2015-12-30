package com.tescar.apps.personal.wear.vehiclecontrol;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageButton;

import com.ingenic.iwds.DeviceDescriptor;
import com.ingenic.iwds.datatransactor.DataTransactor;
import com.ingenic.iwds.datatransactor.DataTransactor.DataTransactResult;
import com.ingenic.iwds.datatransactor.DataTransactor.DataTransactorCallback;
import com.tescar.apps.personal.wear.vehiclecontrol.R;
import com.ingenic.iwds.utils.IwdsLog;

public class ViewPagerActivity extends SwipeBackActivity implements DataTransactorCallback, OnClickListener {
	private ViewPager viewPager;
	private List<View> list = new ArrayList<View>();
	// private String m_uuid = "a1dc19e2-17a4-0797-9362-68a0dd4bfb6f";
	// 手表
	private String m_uuid = "d1dc19e2-17a4-0797-9362-68a0dd4bfb6f";
	// 手机
	// private String m_uuid = "e1dc19e2-17a4-0797-9362-68a0dd4bfb6f";
	private DataTransactor m_transactor;

	Button button1;

	ImageButton btn_back_lock;
	ImageButton btn_door_lock;
	ImageButton btn_flash_light;
	ImageButton btn_woo_sound;
	ImageButton btn_air_condition;
	ImageButton btn_unlock_run;
	ImageButton btn_shutdown_engine;
	ImageButton btn_car_scan;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);

		viewPager = (ViewPager) findViewById(R.id.frame_main_viewpage);

		// for (int i = 0; i < 5; i++) {
		// }
		View v = LayoutInflater.from(this).inflate(R.layout.activity_normal, null);
		View v2 = LayoutInflater.from(this).inflate(R.layout.activity_normal2, null);
		list.add(v);
		list.add(v2);
		btn_back_lock = (ImageButton) v.findViewById(R.id.back_lock);
		btn_back_lock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("尾箱锁");
			}
		});
		btn_door_lock = (ImageButton) v.findViewById(R.id.door_lock);
		btn_door_lock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("车门锁");
			}
		});
		btn_flash_light = (ImageButton) v.findViewById(R.id.flash_light);
		btn_flash_light.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("闪光灯");
			}
		});
		btn_woo_sound = (ImageButton) v.findViewById(R.id.woo_sound);
		btn_woo_sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("鸣笛");
			}
		});
		btn_air_condition = (ImageButton) v2.findViewById(R.id.air_condition);
		btn_air_condition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("打开空调");
			}
		});
		btn_unlock_run = (ImageButton) v2.findViewById(R.id.unlock_run);
		btn_unlock_run.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("解锁");
			}
		});
		btn_shutdown_engine = (ImageButton) v2.findViewById(R.id.shutdown_engine);
		btn_shutdown_engine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("关闭引擎");
			}
		});
		btn_car_scan = (ImageButton) v2.findViewById(R.id.car_scan);
		btn_car_scan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 m_transactor.send("汽车检测");
			}
		});

	
		
		

		System.out.println(list.size());

		viewPager.setAdapter(new Adapter(this, list));
		// button1 = (Button) findViewById(R.id.button1);
		//
		if (m_transactor == null) {
			m_transactor = new DataTransactor(this, this, m_uuid);
		}

	}

	public class Adapter extends PagerAdapter {
		private List<View> list;

		public Adapter(Context context, List<View> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v = list.get(position);
			((ViewPager) container).addView(v);
			return v;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// switch (v.getId()) {
		// case R.id.button1:
		// m_transactor.send("尾箱锁");
		// break;
		// }

	}

	@Override
	protected void onStart() {
		super.onStart();

		IwdsLog.i(this, "onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();

		IwdsLog.i(this, "onStop");
	}

	@Override
	protected void onPause() {
		super.onPause();

		m_transactor.stop();

		IwdsLog.i(this, "onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();

		m_transactor.start();

		IwdsLog.i(this, "onResume");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		IwdsLog.i(this, "onDestroy");
	}

	/*
	 * 监控蓝牙传输通道状态，isAvailable 为 true 表示传输通道可用，这时才能传输数据。
	 */
	@Override
	public void onChannelAvailable(boolean isAvailable) {
		if (isAvailable) {
			IwdsLog.i(this, "Data channel is available.");

			/* Channel 有效时传输 m_weather 对象 */
			// m_transactor.send(m_weather);
			m_transactor.send("Tescar connected");

		} else {
			IwdsLog.i(this, "Data channel is unavaiable.");
		}
	}

	/*
	 * 传输是否成功。传输结束后发送端和接收端都会发生。
	 */
	@Override
	public void onSendResult(DataTransactResult result) {
		if (result.getResultCode() == DataTransactResult.RESULT_OK) {
			IwdsLog.i(this, "Send success");
		} else {
			IwdsLog.i(this, "Send failed by error code: " + result.getResultCode());
		}
	}

	/*
	 * 数据接收完成。传输结束后接收端发生
	 */
	@Override
	public void onDataArrived(Object object) {

		Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show();
		// WeatherInfoArray array = (WeatherInfoArray) object;
		//
		// int N = array.data.length;
		// for (int i = 0; i < N; i++)
		// IwdsLog.d(this, "array.data[" + i + "]:" + array.data[i].toString());
	}

	/*
	 * 监控设备间的连接状态变化。isConnected 为 true 表示两个设备连接成功。
	 */
	@Override
	public void onLinkConnected(DeviceDescriptor descriptor, boolean isConnected) {
		if (isConnected) {
			IwdsLog.i(this, "Link connected: " + descriptor.toString());

		} else {
			IwdsLog.i(this, "Link disconnected: " + descriptor.toString());
		}
	}

	@Override
	public void onRecvFileProgress(int progress) {

	}

	@Override
	public void onSendFileProgress(int progress) {

	}

}
