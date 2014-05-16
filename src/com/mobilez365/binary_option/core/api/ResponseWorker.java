package com.mobilez365.binary_option.core.api;

import android.os.Bundle;
import com.mobilez365.binary_option.global.Variables;
import com.mobilez365.binary_option.screens.chart.BitCoinChart;
import com.mobilez365.binary_option.screens.chart.TickData;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * User: ZOG
 * Date: 04.04.14
 * Time: 16:40
 */
public abstract class ResponseWorker {

	public static final void responseApiTickData(ArrayList<TickData> _ticks) {
		Variables.activity.addDataToChart(_ticks);
	}
}