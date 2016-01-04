package com.tescar.apps.personal.wear.vehiclecontrol;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.ingenic.iwds.datatransactor.elf.WeatherInfoArray;
import com.ingenic.iwds.datatransactor.elf.WeatherInfoArray.WeatherInfo;
import com.tescar.apps.personal.wear.vehiclecontrol.R;
import com.ingenic.iwds.utils.IwdsLog;


public class TraceActivity extends SwipeBackActivity implements DataTransactorCallback, OnClickListener {
	
	private String m_uuid = "d1dc19e2-17a4-0797-9362-68a0dd4bfb6f";
	// 手机
	// private String m_uuid = "e1dc19e2-17a4-0797-9362-68a0dd4bfb6f";
	private DataTransactor m_transactor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trace);
		IwdsLog.i(this, "onCreate");

		if (m_transactor == null) {
			m_transactor = new DataTransactor(this, this, m_uuid);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
	public void onRecvFileProgress(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendFileProgress(int arg0) {
		// TODO Auto-generated method stub
		
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

}
