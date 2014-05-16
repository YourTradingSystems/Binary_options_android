package com.mobilez365.binary_option.core.service;

import android.os.Binder;
import android.util.Log;
import com.mobilez365.binary_option.core.msg.MsgSender;

import static com.mobilez365.binary_option.global.Constants.TAG_SERVICE;

/**
 * User: ZOG
 * Date: 14.05.14
 * Time: 13:58
 */
public final class WRSBinder extends Binder {

	public final void test() {
		Log.d(TAG_SERVICE, "WebRequestService: MyBinder: test()");
	}

	public final void getTickData(final int _msgAction) {
		MsgSender.prepareAndSendMsg_apiTickData(_msgAction);
	}

}