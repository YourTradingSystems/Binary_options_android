package com.mobilez365.binary_option.screens.chart;

import java.util.ArrayList;

/**
 * Container for TickData.
 *
 * User: ZOG
 * Date: 16.05.14
 * Time: 11:54
 */
public final class TickDataList {

	private ArrayList<TickData> mTickDatas = new ArrayList<TickData>();

	/**
	 * Adds TickData object and sort data.
	 * @param _tickData TickData object with parameters.
	 */
	public final void add(final TickData _tickData) {
		mTickDatas.add(_tickData);
	}

	/**
	 * Adds ArrayList of TickData objects and sort.
	 * @param _tickDatas ArrayList with TickData objects.
	 */
	public final void addAll(final ArrayList<TickData> _tickDatas) {

		if (!_tickDatas.isEmpty()) {
			mTickDatas.addAll(_tickDatas);
		}
	}

	/**
	 * Checks is list contains TickData with specified time.
	 * @param _time Time is seconds.
	 * @return Boolean.
	 */
	public final boolean contains(final long _time) {
		return mTickDatas.contains(new TickData(_time));
	}

	/**
	 * Returns number of position TickData object with specified time.
	 * @param _time Time in seconds.
	 * @return Position in list.
	 */
	public final int indexOf(final long _time) {
		return mTickDatas.indexOf(new TickData(_time));
	}

	/**
	 * Returns TickData object by specified postion.
	 * @param _pos Position in list.
	 * @return TickData object.
	 */
	public final TickData getTickData(final int _pos) {
		return mTickDatas.get(_pos);
	}

	/**
	 * Returns number of TickData objects in list.
	 * @return Count of objects.
	 */
	public final int getCount() {
		return mTickDatas.size();
	}

	/**
	 * Returns inner list with objects. Mostly used for sorting procedures.
	 * @return Inner ArrayList with TickData Objects.
	 */
	public final ArrayList<TickData> getList() {
		return mTickDatas;
	}
}