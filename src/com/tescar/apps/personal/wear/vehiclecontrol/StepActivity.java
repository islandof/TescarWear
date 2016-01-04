package com.tescar.apps.personal.wear.vehiclecontrol;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenic.iwds.common.api.ConnectFailedReason;
import com.ingenic.iwds.common.api.ServiceClient;
import com.ingenic.iwds.common.api.ServiceManagerContext;
import com.ingenic.iwds.smartsense.Sensor;
import com.ingenic.iwds.smartsense.SensorEvent;
import com.ingenic.iwds.smartsense.SensorEventListener;
import com.ingenic.iwds.smartsense.SensorServiceManager;
import com.ingenic.iwds.utils.IwdsLog;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class StepActivity extends SwipeBackActivity implements ServiceClient.ConnectionCallbacks {
	private final static String TAG = "IWDS ---> SensorTestActivity";

	private ServiceClient mClient;
	private SensorServiceManager mService;
	private Sensor mStepSensor;
	private float mStepData;
	private TextView mStepText;

	private WakeLock mWakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_step);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);

		mClient = new ServiceClient(this, ServiceManagerContext.SERVICE_SENSOR, this);

		mClient.connect();
		mStepText = (TextView) findViewById(R.id.text_step);

		mStepText.setText("步数 " + mStepData);
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");

		super.onDestroy();
		mClient.disconnect();
	}

	@Override
	public void onConnected(ServiceClient serviceClient) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Sensor service connected");

		mService = (SensorServiceManager) serviceClient.getServiceManagerContext();

		/* 获取所有Sensor列表 */
		ArrayList<Sensor> sensorList = (ArrayList<Sensor>) mService.getSensorList();

		Log.d(TAG, "=========================================");
		Log.d(TAG, "Dump Sensor List");
		for (int i = 0; i < sensorList.size(); i++) {
			Log.d(TAG, "Sensor: " + sensorList.get(i).toString());
		}
		Log.d(TAG, "=========================================");
		registerSensors();
	}

	@Override
	public void onDisconnected(ServiceClient serviceClient, boolean unexpected) {
		Log.d(TAG, "Sensor service diconnected");

		unregisterSensors();
	}

	private void registerSensors() {
		/* 通过 getDefaultSensor 获取各个 Sensor */
		mStepSensor = mService.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		if (mStepSensor != null) {
			mService.registerListener(mListener, mStepSensor, 0);
		}
	}

	private void unregisterSensors() {
		if (mStepSensor != null) {
			mService.unregisterListener(mListener, mStepSensor);
		}
	}

	private SensorEventListener mListener = new SensorEventListener() {

		/*
		 * 监控 Sensor 数据变化
		 */
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensorType == Sensor.TYPE_STEP_COUNTER) {
				Log.d(TAG, "Update Step : " + event.values[0]);
				mStepData = event.values[0];
			}
			mStepText.setText("步数 " + mStepData);
		}

		/*
		 * 监控 Sensor 数据精度变化
		 */
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			Log.d(TAG, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
			// if (sensor.getType() == Sensor.TYPE_HEART_RATE) {
			// if (accuracy ==
			// SensorServiceManager.ACCURACY_HEART_RATE_UNAVALIABLE) {
			// Toast.makeText(StepActivity.this,
			// "Make sure the watch wear well", Toast.LENGTH_SHORT).show();;
			// } else if (accuracy ==
			// SensorServiceManager.ACCURACY_HEART_RATE_AVALIABLE) {
			// Toast.makeText(StepActivity.this,
			// "Watch wear well", Toast.LENGTH_SHORT).show();;
			// }
			// }
		}

	};

	@Override
	public void onConnectFailed(ServiceClient arg0, ConnectFailedReason arg1) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Sensor service connect fail");
	}

}
