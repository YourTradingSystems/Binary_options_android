package com.mobilez365.binary_option.screens.chart;

import android.os.Bundle;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * Class for holding data for one tick: time and prices.
 * Time saved in seconds (long variable), prices as double.
 * And some additional data (which not used yet).
 *
 * User: ZOG
 * Date: 16.05.14
 * Time: 11:49
 */
public final class TickData {
	private long mTime = 0;
	private double mOpenBid			= 0;
	private double mOpenAsk			= 0;
	private double mHighBid			= 0;
	private double mHighAsk			= 0;
	private double mLowBid			= 0;
	private double mLowAsk			= 0;
	private double mCloseBid		= 0;
	private double mCloseAsk		= 0;
	private int mVolume				= 0;
	private boolean mComplete		= false;

	/**
	 * Specialized constructor. Used mostly for sorting procedures.
	 * @param _time Time in seconds.
	 */
	public TickData(final long _time) {
		mTime = _time;
	}

	/**
	 * General constructor. Used when parsing data from server.
	 * @param _time Time in seconds.
	 * @param _bundle bundle with other tick data.
	 */
	public TickData(final long _time, final Bundle _bundle) {

		mTime 		= _time;
		mOpenBid	= _bundle.getDouble(KEY_OPEN_BID);
		mOpenAsk	= _bundle.getDouble(KEY_OPEN_ASK);
		mHighBid	= _bundle.getDouble(KEY_HIGH_BID);
		mHighAsk	= _bundle.getDouble(KEY_HIGH_ASK);
		mLowBid		= _bundle.getDouble(KEY_LOW_BID);
		mLowAsk		= _bundle.getDouble(KEY_LOW_ASK);
		mCloseBid	= _bundle.getDouble(KEY_CLOSE_BID);
		mCloseAsk	= _bundle.getDouble(KEY_CLOSE_ASK);
		mVolume		= _bundle.getInt(KEY_VOLUME);
		mComplete	= _bundle.getBoolean(KEY_COMPLETE);
	}

	/**
	 * Returns current time in seconds.
	 * @return Time in seconds.
	 */
	public final long getTime() {
		return mTime;
	}

	/**
	 * Returns current price. Change here for another price type.
	 * @return Current Price.
	 */
	public final double getPrice() {
		return mOpenBid;
	}

	/**
	 * Comparing two TickData objects. If times are match then objects
	 * considered as equals.
	 * @param _obj TickData object.
	 * @return Boolean.
	 */
	@Override
	public final boolean equals(final Object _obj) {
		if (!(_obj instanceof TickData)) return false;

		final TickData tickData = (TickData) _obj;

		return tickData.getTime() == this.mTime;
	}

	/**
	 * Recommended override this method if overriding equals().
	 * @return Hash code.
	 */
	@Override
	public final int hashCode() {
		final int hash = 17 * 7 + this.hashCode() * 17 + (int) mTime;
		return hash;
	}
}