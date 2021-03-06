package com.mobilez365.binary_option.core.msg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import com.mobilez365.binary_option.screens.chart.BitCoinChart;
import com.mobilez365.binary_option.screens.chart.TickData;

import java.util.ArrayList;
import java.util.TreeMap;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 04.04.14
 * Time: 16:48
 */
public abstract class MsgSender {

	/**
	 * Prepare and send message for api get tick data.
	 * Used for starting web request.
	 */
	public static final void prepareAndSendMsg_apiTickData(final int _msgAction /* add other data*/) {
		final Bundle bundle = new Bundle();
		bundle.putString(URL_TAG_COUNT, "50");
		bundle.putString(URL_TAG_INSTRUMENT, "EUR_USD");
		//put data in bundle

		final Message msg = MsgCore.create(WM_TICK_DATA, _msgAction);
		MsgCore.sendMessage(msg, bundle);
	}

	/**
	 * Prepare and send message for api get tick data.
	 * Used for sendint result from web request.
	 */
	public static final void prepareAndSendMsg_apiTickData(final int _msgAction,
														   final ArrayList<TickData> _ticks) {
		final Message msg = MsgCore.create(WM_TICK_DATA, _msgAction);
		MsgCore.sendMessage(msg, _ticks);
	}

	public static final void prepareAndSendMsg_ApiError(final Bundle _bundle) {
		final Message msg = MsgCore.create(M_ERROR);
		MsgCore.sendMessage(msg, _bundle);
	}
}