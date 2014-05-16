package com.mobilez365.binary_option.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.util.Log;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 13.05.14
 * Time: 17:25
 */
public final class WebRequestService extends Service {

	private WRSBinder myBinder;

	public WebRequestService() {
		myBinder = new WRSBinder();

		Log.d(TAG_SERVICE, "WebRequestService: constructor");
	}

	@Override
	public final IBinder onBind(final Intent _intent) {
		Log.d(TAG_SERVICE, "WebRequestService: onBind()");
		return myBinder;
	}

	@Override
	public final void onRebind (final Intent _intent) {
		Log.d(TAG_SERVICE, "WebRequestService: onRebind()");
	}

	@Override
	public final boolean onUnbind (final Intent _intent) {
		Log.d(TAG_SERVICE, "WebRequestService: onUnbind()");
		return super.onUnbind(_intent);
	}

//	@Override
//	public final int onStartCommand(final Intent _intent, final int _flags, final int _startId) {
//		Log.d(TAG_SERVICE, "WebRequestService: onStartCommand()");
////		sendMessage(0);
////		stopSelf();
//
//		return START_NOT_STICKY;
//	}

	@Override
	public final void onDestroy() {
		Log.d(TAG_SERVICE, "WebRequestService: onDestroy()");
		super.onDestroy();
	}
}