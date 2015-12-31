package com.tescar.apps.personal.wear.vehiclecontrol;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class HearBeatActivity extends SwipeBackActivity implements ServiceClient.ConnectionCallbacks {
    private final static String TAG = "IWDS ---> SensorTestActivity";

	private ServiceClient mClient;
	private SensorServiceManager mService;
	private Sensor mHeartRateSensor;
	private float mHeartRateData;
	private TextView mHeartRateText;
	
    private WakeLock mWakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_heartbeat);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);

        mClient = new ServiceClient(this, ServiceManagerContext.SERVICE_SENSOR,
                this);

        mClient.connect();
		ImageView imageView = (ImageView) findViewById(R.id.purseimage);
		imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse));
		mHeartRateText = (TextView) findViewById(R.id.text_heart);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?")
		.setCancelable(false)
		.setPositiveButton("Sure",new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,int id) {
		}
		}).setNegativeButton("Cancel", null);
		AlertDialog alert = builder.create();
	
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

        mService = (SensorServiceManager) serviceClient
                .getServiceManagerContext();

        /* 获取所有Sensor列表 */
        ArrayList<Sensor> sensorList = (ArrayList<Sensor>) mService
                .getSensorList();

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
        mHeartRateSensor = mService.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if (mHeartRateSensor != null) {
            mService.registerListener(mListener, mHeartRateSensor, 0);
        }
    }
    private void unregisterSensors() {
        if (mHeartRateSensor != null) {
            mService.unregisterListener(mListener, mHeartRateSensor);
        }
    }
    
    private SensorEventListener mListener = new SensorEventListener() {

        /*
         * 监控 Sensor 数据变化
         */
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensorType == Sensor.TYPE_HEART_RATE) {
                Log.d(TAG, "Update Heart Rate : " + event.values[0]);
                mHeartRateData = event.values[0];
            } 
            mHeartRateText.setText("心跳频率: " + mHeartRateData);
        }

        /*
         * 监控 Sensor 数据精度变化
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(TAG, "onAccuracyChanged: " + sensor + ", accuracy: "
                    + accuracy);
            if (sensor.getType() == Sensor.TYPE_HEART_RATE) {
                if (accuracy == SensorServiceManager.ACCURACY_HEART_RATE_UNAVALIABLE) {
                    Toast.makeText(HearBeatActivity.this,
                            "Make sure the watch wear well", Toast.LENGTH_SHORT).show();;
                } else if (accuracy == SensorServiceManager.ACCURACY_HEART_RATE_AVALIABLE) {
                    Toast.makeText(HearBeatActivity.this,
                            "Watch wear well", Toast.LENGTH_SHORT).show();;
                }
            }
        }

    };

	@Override
	public void onConnectFailed(ServiceClient arg0, ConnectFailedReason arg1) {
		// TODO Auto-generated method stub
        Log.d(TAG, "Sensor service connect fail");
	}

}
