package com.mobilez365.binary_option.screens.chart;

import java.util.ArrayList;

/**
 * User: ZOG
 * Date: 16.05.14
 * Time: 11:54
 */
public final class TickDataList {

	private ArrayList<TickData> mTickDatas = new ArrayList<TickData>();

	public final void add(final TickData _tickData) {
		mTickDatas.add(_tickData);
	}

	public final void addAll(final ArrayList<TickData> _tickDatas) {
		mTickDatas.addAll(_tickDatas);
	}

	public final boolean contains(final long _time) {
		return mTickDatas.contains(new TickData(_time));
	}

	public final int indexOf(final long _time) {
		return mTickDatas.indexOf(new TickData(_time));
	}

	public final TickData getTickData(final int _pos) {
		return mTickDatas.get(_pos);
	}

	public final int getCount() {
		return mTickDatas.size();
	}

	public final ArrayList<TickData> getList() {
		return mTickDatas;
	}
}