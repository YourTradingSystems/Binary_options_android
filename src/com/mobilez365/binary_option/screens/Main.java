package com.mobilez365.binary_option.screens;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.mobilez365.binary_option.R;
import com.mobilez365.binary_option.core.msg.MsgCore;
import com.mobilez365.binary_option.core.service.WRSBinder;
import com.mobilez365.binary_option.core.service.WebRequestService;
import com.mobilez365.binary_option.global.Variables;
import com.mobilez365.binary_option.screens.chart.BitCoinChart;
import com.mobilez365.binary_option.screens.chart.TickData;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.mobilez365.binary_option.global.Constants.*;

public final class Main extends Activity implements View.OnClickListener {

	private Button btnStart_SM;
	private BitCoinChart bccChart_SM;

	private Context mContext					= null;
	private WRSBinder mWrsBinder				= null;
	private boolean mIsServiceBinded			= false;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public final void onCreate(final Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.screen_main);

		mContext = this;
		Variables.activity = this;

		((Button) findViewById(R.id.btnStart_SM)).setOnClickListener(this);
		bccChart_SM	= (BitCoinChart) findViewById(R.id.bccChart_SM);
		prepareChart();

	}

	private final void prepareChart() {
		bccChart_SM.setTimeInterval(3 * 60);
		bccChart_SM.setTimeStep(30);
		bccChart_SM.setStartPos(System.currentTimeMillis() / 1000);

		bccChart_SM.setVerticalLinesCount(6);
	}

	@Override
	public final void onStop() {
		super.onStop();

//		final Intent intent = new Intent(mContext, WebRequestService.class);
//		stopService(intent);
//		unbindService(serviceConnection);
	}

	public final void testMethod(final int _info) {
		Toast.makeText(mContext, "Work " + _info, Toast.LENGTH_SHORT).show();
	}

	@Override
	public final void onClick(final View _view) {

		if (!mIsServiceBinded) {
			final Intent intent = new Intent(mContext, WebRequestService.class);
			mIsServiceBinded = bindService(intent, serviceConnection, BIND_AUTO_CREATE);
		} else {
			mWrsBinder.getTickData(MA_START);
		}

//		Log.d(TAG_SERVICE, "Is binded: " + mIsServiceBinded);
	}

	private final ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public final void onServiceConnected(final ComponentName _componentName, final IBinder _iBinder) {
			Log.d(TAG_SERVICE, "Main: ServiceConnection: onServiceConnected()");
			mWrsBinder = (WRSBinder) _iBinder;

			mWrsBinder.getTickData(MA_START);
		}

		@Override
		public final void onServiceDisconnected(final ComponentName _componentName) {
			Log.d(TAG_SERVICE, "Main: ServiceConnection: onServiceDisconnected()");
		}
	};

	public final void addDataToChart(final ArrayList<TickData> _ticks) {
//		testMethod(25);
		bccChart_SM.addTickData(_ticks);
	}
}