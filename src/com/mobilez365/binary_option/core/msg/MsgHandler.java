package com.mobilez365.binary_option.core.msg;

import android.os.Bundle;
import android.os.Message;
import com.mobilez365.binary_option.core.api.ErrorHelper;
import com.mobilez365.binary_option.core.api.ResponseWorker;
import com.mobilez365.binary_option.core.thread.ThreadPool;
import com.mobilez365.binary_option.core.thread.WebRequest;
import com.mobilez365.binary_option.screens.chart.BitCoinChart;

import java.util.TreeMap;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 28.03.14
 * Time: 13:34
 */
abstract class MsgHandler {

	protected static final void handleWM_tickData(final Message _msg) {
		final int action = _msg.arg1;
		if (action == MA_START) {
			ThreadPool.webRequestTask = WebRequest.create();
			ThreadPool.webRequestTask.setMsgCode(WM_TICK_DATA);
			ThreadPool.webRequestTask.execute(_msg.getData());
		} else if (action == MA_END) {
			final TreeMap<Long, BitCoinChart.TickData> ticks = (TreeMap<Long, BitCoinChart.TickData>) _msg.obj;
			ResponseWorker.responseApiTickData(ticks);
		}
	}

	protected static final void handleM_error(final Message _msg) {
		final Bundle bundle = _msg.getData();
		ErrorHelper.showErrorDialog(bundle);
	}
}