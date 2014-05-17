package com.mobilez365.binary_option.screens.chart;

import android.widget.Toast;
import com.mobilez365.binary_option.core.app.DateHelper;
import com.mobilez365.binary_option.global.Variables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Container for TickData.
 * todo: rework to non-abstract object; add to chart method addDatas() in which invoke invalidate.
 *
 * User: ZOG
 * Date: 16.05.14
 * Time: 11:54
 */
public abstract class TickDataList {

	private static ArrayList<TickData> mTickDatas = new ArrayList<TickData>();

	/**
	 * Adds TickData object and sort data.
	 * @param _tickData TickData object with parameters.
	 */
	public static final void add(final TickData _tickData) {
		if (_tickData == null || mTickDatas == null) return;

		Toast.makeText(Variables.activity, "Added 1 item. Time: "
				+ DateHelper.getTimeString(_tickData.getTime() * 1000), Toast.LENGTH_SHORT).show();

		mTickDatas.add(_tickData);
		Collections.sort(mTickDatas, new Comparator<TickData>() {
			@Override
			public final int compare(final TickData _tickData, final TickData _tickData2) {
				return (int) (_tickData.getTime() - _tickData2.getTime());
			}
		});
	}

	/**
	 * Adds ArrayList of TickData objects and sort.
	 * @param _ticks ArrayList with TickData objects.
	 */
	public static final void addAll(final ArrayList<TickData> _ticks) {
		if (_ticks.isEmpty() || mTickDatas == null) return;

		Toast.makeText(Variables.activity, "Added " + _ticks.size() + " items. First time: "
				+ DateHelper.getTimeString(_ticks.get(0).getTime() * 1000), Toast.LENGTH_SHORT).show();

		mTickDatas.addAll(_ticks);
		Collections.sort(mTickDatas, new Comparator<TickData>() {
			@Override
			public final int compare(final TickData _tickData, final TickData _tickData2) {
				return (int) (_tickData.getTime() - _tickData2.getTime());
			}
		});
	}

	/**
	 * Checks is list contains TickData with specified time.
	 * @param _time Time is seconds.
	 * @return Boolean.
	 */
	public static final boolean contains(final long _time) {
		return mTickDatas.contains(new TickData(_time));
	}

	/**
	 * Returns number of position TickData object with specified time.
	 * @param _time Time in seconds.
	 * @return Position in list.
	 */
	private static final int indexOf(final long _time) {
		return mTickDatas.indexOf(new TickData(_time));
	}

	/**
	 * Returns TickData object by specified postion.
	 * @param _pos Position in list.
	 * @return TickData object.
	 */
	public static final TickData getTickData(final int _pos) {
		return mTickDatas.get(_pos);
	}

	/**
	 * Returns number of TickData objects in list.
	 * @return Count of objects.
	 */
	public static final int getCount() {
		return mTickDatas.size();
	}

	/**
	 * Returns inner list with objects. Mostly used for sorting procedures.
	 * @return Inner ArrayList with TickData Objects.
	 */
	public static final ArrayList<TickData> getList() {
		return mTickDatas;
	}

	public static final boolean isEmpty() {
		return mTickDatas.isEmpty();
	}

	//todo: probably need sorting here
	private static final ArrayList<TickData> getSubList(final int _start, final int _end) {
		final ArrayList<TickData> subList = new ArrayList<TickData>();
		subList.addAll(mTickDatas.subList(_start, _end));
		return subList;
	}

	private static final long findFirstTimePos(final BitCoinChart _bitCoinChart) {
		final long currTimePos = _bitCoinChart.getCurrTimePos();
		final long timeInterval = _bitCoinChart.getTimeInterval();

		long firstTimePos = currTimePos;
		for (long i = 0; i < timeInterval; i++, firstTimePos++)
			if (mTickDatas.contains(new TickData(firstTimePos))) return firstTimePos;

		return -1;
	}

	private static final long findLastTimePos(final BitCoinChart _bitCoinChart) {
		final long currTimePos = _bitCoinChart.getCurrTimePos();
		final long timeInterval = _bitCoinChart.getTimeInterval();

		long lastTimePos = currTimePos + timeInterval;
		//todo: maybe change to i >= 0
		for (long i = timeInterval; i > 0; i--, lastTimePos--)
			if (mTickDatas.contains(new TickData(lastTimePos))) return lastTimePos;

		return -1;
	}

	public static final ArrayList<TickData> prepareDataForDraw(final BitCoinChart _bitCoinChart) {
		final long firstTimePos = findFirstTimePos(_bitCoinChart);
		final long lastTimePos = findLastTimePos(_bitCoinChart);

		if (firstTimePos < 0 || lastTimePos < 0) return new ArrayList<TickData>();

		final int firstTickPos = indexOf(firstTimePos);
		final int lastTickPos = indexOf(lastTimePos);

		final ArrayList<TickData> drawList = getSubList(firstTickPos, lastTickPos);
		return drawList;
	}
}