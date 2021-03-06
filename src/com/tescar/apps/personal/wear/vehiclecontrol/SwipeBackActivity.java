package com.tescar.apps.personal.wear.vehiclecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.ingenic.iwds.common.api.ServiceClient;
import com.tescar.apps.personal.views.SwipeBackLayout;

/** 
 * 想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager 
 * 只需要调用SwipeBackLayout的setViewPager()方法即可 
 *  
 * @author xiaanming 
 * 
 */  
public class SwipeBackActivity extends Activity {
	protected SwipeBackLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
				R.layout.base, null);
		layout.attachToActivity(this);
	}
	
	
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
	}




	// Press the back button in mobile phone
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}


	public void onConnected(ServiceClient serviceClient) {
		// TODO Auto-generated method stub
		
	}


	public void onDisconnected(ServiceClient serviceClient, boolean unexpected) {
		// TODO Auto-generated method stub
		
	}


}
