package com.tescar.apps.personal.wear.vehiclecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends SwipeBackActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		Button mButtonHeart = (Button) findViewById(R.id.heart_activity);
		mButtonHeart.setOnClickListener(this);

		Button mButtonStep = (Button) findViewById(R.id.step_activity);
		mButtonStep.setOnClickListener(this);

		Button mButtonAbs = (Button) findViewById(R.id.absListview_activity);
		mButtonAbs.setOnClickListener(this);

		Button mButtonScroll = (Button) findViewById(R.id.scrollview_activity);
		mButtonScroll.setOnClickListener(this);
		
		Button mButtonViewPager = (Button) findViewById(R.id.viewpager_activity);
		mButtonViewPager.setOnClickListener(this);

		Button mButtonTrace = (Button) findViewById(R.id.trace_activity);
		mButtonTrace.setOnClickListener(this);

		Button mButtonChaosu = (Button) findViewById(R.id.chaosu_activity);
		mButtonChaosu.setOnClickListener(this);

		Button mButtonCarstatus = (Button) findViewById(R.id.carstatus_activity);
		mButtonCarstatus.setOnClickListener(this);

		Button mButtonClock = (Button) findViewById(R.id.clock_activity);
		mButtonClock.setOnClickListener(this);
		
		Button mButtonDate = (Button) findViewById(R.id.date_activity);
		mButtonDate.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View v) {
		Intent mIntent = null;
		switch (v.getId()) {
		case R.id.heart_activity:
			mIntent = new Intent(MainActivity.this, HearBeatActivity.class);
			break;
		case R.id.step_activity:
			mIntent = new Intent(MainActivity.this, StepActivity.class);
			break;
		case R.id.absListview_activity:
			mIntent = new Intent(MainActivity.this, AbsActivity.class);
			break;
		case R.id.scrollview_activity:
			mIntent = new Intent(MainActivity.this, ScrollActivity.class);
			break;
		case R.id.viewpager_activity:
			mIntent = new Intent(MainActivity.this, ViewPagerActivity.class);
			break;
		case R.id.trace_activity:
			mIntent = new Intent(MainActivity.this, TraceActivity.class);
			break;
		case R.id.chaosu_activity:
			mIntent = new Intent(MainActivity.this, ChaosuActivity.class);
			break;
		case R.id.carstatus_activity:
			mIntent = new Intent(MainActivity.this, ChaosuActivity.class);
			break;
		case R.id.clock_activity:
			mIntent = new Intent(MainActivity.this, ClockActivity.class);
			break;
		case R.id.date_activity:
			mIntent = new Intent(MainActivity.this, DateActivity.class);
			break;
		}

		startActivity(mIntent);
	}
	

}
