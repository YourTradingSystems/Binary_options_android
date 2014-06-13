package com.mobilez365.binary_option.screens.chart;

import java.util.ArrayList;

/**
 * User: ZOG
 * Date: 06.06.14
 * Time: 11:48
 */
final class PriceExtremesHandler {

	private double mMinPrice					= Integer.MAX_VALUE;
	private double mMaxPrice					= Integer.MIN_VALUE;

	private double mTempMax, mTempMin;


	protected final double getMinPrice() { return mMinPrice; }
	protected final double getMaxPrice() { return mMaxPrice; }
	protected final double getTempMax() { return mTempMax; }
	protected final double getTempMin() { return mTempMin; }


	protected final void calcPriceExtremes(final ArrayList<TickData> _list) {
		clearPriceExtremes();
		for (int i = 0; i < _list.size(); i++) {
			final TickData tickData = _list.get(i);
			final double price = tickData.getPrice();

			updatePriceExtremes(price);
		}

		//for min and max lines (temporary)
		mTempMax = mMaxPrice;
		mTempMin = mMinPrice;

		//increasing distance between price's extremes
		mMaxPrice += (mMaxPrice - mMinPrice) * 0.1;
		mMinPrice -= (mMaxPrice - mMinPrice) * 0.1;
		if (mMinPrice < 0) mMinPrice = 0;
	}

	protected final void clearPriceExtremes() {
		mMinPrice = Integer.MAX_VALUE;
		mMaxPrice = Integer.MIN_VALUE;
	}

	protected final void updatePriceExtremes(final double _price) {
		if (_price > mMaxPrice) mMaxPrice = _price;
		if (_price < mMinPrice) mMinPrice = _price;
	}

	protected final void checkExtremes() {
		if (mMinPrice == Integer.MAX_VALUE) {
			mMinPrice = 0;
		}
		if (mMaxPrice == Integer.MIN_VALUE) {
			mMaxPrice = 0;
		}
	}
}
