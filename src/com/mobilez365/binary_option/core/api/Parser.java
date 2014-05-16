package com.mobilez365.binary_option.core.api;

import com.mobilez365.binary_option.core.app.DateHelper;
import com.mobilez365.binary_option.screens.chart.BitCoinChart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.TreeMap;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 26.03.14
 * Time: 15:54
 */
abstract class Parser {

	protected static final TreeMap<Long, BitCoinChart.TickData> parseTickData(final String _response)
			throws JSONException, ParseException {

		final TreeMap<Long, BitCoinChart.TickData> ticks = new TreeMap<Long, BitCoinChart.TickData>();

		final JSONObject jsonObj = new JSONObject(_response);
		final String instrument = jsonObj.getString(KEY_INSTRUMENT);
		final String granularity = jsonObj.getString(KEY_GRANULARITY);

		final JSONArray candles = jsonObj.getJSONArray(KEY_CANDLES);

		for (int i = 0; i < candles.length(); i++) {
			final JSONObject candle = candles.getJSONObject(i);

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

			final BitCoinChart.TickData tickData
					= new BitCoinChart.TickData(time
//					,
//												openBid,	openAsk,
//												highBid,	highAsk,
//												lowBid,		lowAsk,
//												closeBid,	closeAsk,
//												volume,		complete
			);

			ticks.put(time, tickData);
		}

		return ticks;
	}
}