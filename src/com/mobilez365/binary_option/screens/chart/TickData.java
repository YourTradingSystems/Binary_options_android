package com.mobilez365.binary_option.screens.chart;

/**
 * Class for holding data for one tick: time and prices.
 * Time saved in milliseconds (long variable), prices as double.
 * And some additional data (not used).
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
	 * @param _time
	 */
	public TickData(final long _time) {
		mTime = _time;
	}

	/**
	 * General constructor. Used when parsing data from server.
	 * @param _time
	 * @param _openBid
	 * @param _openAsk
	 * @param _highBid
	 * @param _highAsk
	 * @param _lowBid
	 * @param _lowAsk
	 * @param _closeBid
	 * @param _closeAsk
	 * @param _volume
	 * @param _complete
	 */
	public TickData(final long _time,
					final double _openBid,	final double _openAsk,
					final double _highBid,	final double _highAsk,
					final double _lowBid,	final double _lowAsk,
					final double _closeBid,	final double _closeAsk,
					final int _volume,		final boolean _complete) {

		mTime = _time;
		mOpenBid	= _openBid;
		mOpenAsk	= _openAsk;
		mHighBid	= _highBid;
		mHighAsk	= _highAsk;
		mLowBid		= _lowBid;
		mLowAsk		= _lowAsk;
		mCloseBid	= _closeBid;
		mCloseAsk	= _closeAsk;
		mVolume		= _volume;
		mComplete	= _complete;
	}

	/**
	 * Returns current time in milliseconds.
	 * @return time in milliseconds.
	 */
	public final long getTime() {
		return mTime;
	}

	/**
	 * Returns current price. Change here for another price type.
	 * @return price.
	 */
	public final double getPrice() {
		return mOpenBid;
	}

	@Override
	public final boolean equals(final Object _obj) {
		if (!(_obj instanceof TickData)) return false;

		final TickData tickData = (TickData) _obj;

		return tickData.getTime() == this.mTime;
	}

	@Override
	public final int hashCode() {
		final int hash = 17 * 7 + this.hashCode() * 17 + (int) mTime;
		return hash;
	}
}