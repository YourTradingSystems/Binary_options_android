package com.mobilez365.binary_option.core.api;

import android.os.Bundle;
import com.mobilez365.binary_option.core.app.DateHelper;
import com.mobilez365.binary_option.screens.chart.TickData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 26.03.14
 * Time: 15:54
 */
abstract class Parser {

	protected static final ArrayList<TickData> parseTickData(final String _response)
			throws JSONException, ParseException {

		final ArrayList<TickData> ticks = new ArrayList<TickData>();

		final JSONObject jsonObj = new JSONObject(_response);
		final String instrument = jsonObj.getString(KEY_INSTRUMENT);
		final String granularity = jsonObj.getString(KEY_GRANULARITY);

		final JSONArray candles = jsonObj.getJSONArray(KEY_CANDLES);

		for (int i = 0; i < candles.length(); i++) {
			final JSONObject candle = candles.getJSONObject(i);
			final Bundle bundle = new Bundle();

			final long time 		= DateHelper.getMillisFromTickDate(candle.getString(KEY_TIME));

			final double openBid	= candle.getDouble(KEY_OPEN_BID);
			final double openAsk	= candle.getDouble(KEY_OPEN_ASK);
			final double highBid	= candle.getDouble(KEY_HIGH_BID);
			final double highAsk	= candle.getDouble(KEY_HIGH_ASK);
			final double lowBid		= candle.getDouble(KEY_LOW_BID);
			final double lowAsk		= candle.getDouble(KEY_LOW_ASK);
			final double closeBid	= candle.getDouble(KEY_CLOSE_BID);
			final double closeAsk	= candle.getDouble(KEY_CLOSE_ASK);
			final int volume		= candle.getInt(KEY_VOLUME);
			final boolean complete	= candle.getBoolean(KEY_COMPLETE);

			bundle.putDouble(KEY_OPEN_BID, openBid);
			bundle.putDouble(KEY_OPEN_ASK, openAsk);
			bundle.putDouble(KEY_HIGH_BID, highBid);
			bundle.putDouble(KEY_HIGH_ASK, highAsk);
			bundle.putDouble(KEY_LOW_BID, lowBid);
			bundle.putDouble(KEY_LOW_ASK, lowAsk);
			bundle.putDouble(KEY_CLOSE_BID, closeBid);
			bundle.putDouble(KEY_CLOSE_ASK, closeAsk);
			bundle.putInt(KEY_VOLUME, volume);
			bundle.putBoolean(KEY_COMPLETE, complete);

			final TickData tickData = new TickData(time, bundle);
			ticks.add(tickData);
		}

		return ticks;
	}
}