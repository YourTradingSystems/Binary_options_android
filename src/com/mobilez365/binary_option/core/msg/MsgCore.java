package com.mobilez365.binary_option.core.msg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 21.03.14
 * Time: 11:54
 */
public abstract class MsgCore {

	public static final Message create(final int _what) {
		final Message msg = handler.obtainMessage();
		msg.what = _what;
		return msg;
	}

	public static final Message create(final int _what, final int _arg1) {
		final Message msg = handler.obtainMessage();
		msg.what = _what;
		msg.arg1 = _arg1;
		return msg;
	}

	public static final Message create(final int _what, final int _arg1, final int _arg2) {
		final Message msg = handler.obtainMessage();
		msg.what = _what;
		msg.arg1 = _arg1;
		msg.arg2 = _arg2;
		return msg;
	}

	public static final void sendMessage(final Message _msg) {
		handler.sendMessage(_msg);
	}

	public static final void sendMessage(final Message _msg, final Bundle _bundle) {
		_msg.setData(_bundle);
		handler.sendMessage(_msg);
	}

	public static final void sendMessage(final Message _msg, final Object _data) {
		_msg.obj = _data;
		handler.sendMessage(_msg);
	}

	private static final Handler handler = new Handler() {
		@Override
		public final void handleMessage(final Message _msg) {
			switch (_msg.what) {
				case WM_TICK_DATA:			MsgHandler.handleWM_tickData(_msg); break;
				case M_ERROR:				MsgHandler.handleM_error(_msg); break;
			}
		}
	};
}