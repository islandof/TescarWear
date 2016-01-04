package com.tescar.apps.personal.wear.vehiclecontrol;

import java.util.ArrayList;
import java.util.List;

import com.ingenic.iwds.utils.IwdsLog;

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

public class ChaosuActivity extends SwipeBackActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chaosu);
		IwdsLog.i(this, "onCreate");
		
//		final ImageButton chaosuBtn = (ImageButton) findViewById(R.id.);
//		chaosuBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				chaosuBtn.setBackgroundResource(R.drawable.bg_all_off);
//			}
//		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
