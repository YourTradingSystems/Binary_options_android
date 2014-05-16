package com.mobilez365.binary_option.screens.chart;

import android.content.Context;
import android.graphics.*;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.mobilez365.binary_option.core.app.DateHelper;

import java.util.*;

/**
 * todo: rework to millis
 * User: ZOG
 * Date: 30.04.14
 * Time: 16:06
 */
public final class BitCoinChart extends View {

	private int WIDTH, HEIGHT;

	//region Variables
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constants ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private final float SPACE_FOR_TIME			= 0.1f;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


	//~~~~~~~~~~~~~~~~~~~~~~~~~~ View params ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private Paint mGridPaint 					= null;
	private TextPaint mTimePaint 				= null;
	private TextPaint mPricePaint 				= null;
	private Paint mPlotStrokePaint 				= null;
	private Paint mPlotFillPaint 				= null;

	private float mGridWidth					= -1;
	private float mGridHeight					= -1;
	private float mBottomSpaceHeight 			= -1;

	private int mVerticalLinesCount				= 5;

	private float mDensityMultiplier			= -1;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


	//~~~~~~~~~~~~~~~~~~~~~~~~~ Plot Data ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private long mStartTimePos 					= -1;
	private long mTimeStep 						= -1;
	private long mTimeInterval 					= -1;

	private double mMinPrice					= Integer.MAX_VALUE;
	private double mMaxPrice					= Integer.MIN_VALUE;

	private TickDataList mTickDataList 			= null;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private Path mChartPath 					= null;
	private Path mFillPath 						= null;
	//endregion

	//Getters and Setters
	//region View params
	public final int getVerticalLinesCount() {
		return mVerticalLinesCount;
	}

	public final void setVerticalLinesCount(final int _count) {
		mVerticalLinesCount = _count;
	}
	//endregion

	//region Plot data
	/**
	 * Returns start position in seconds.
	 * @return time in seconds.
	 */
	public final long getStartPos() {
		return mStartTimePos;
	}

	/**
	 * Set start position is seconds.
	 * @param _startPosition time in seconds.
	 */
	public final void setStartPos(final long _startPosition) {
		mStartTimePos = _startPosition;
	}

	/**
	 * Returns time step.
	 * @return time step in seconds.
	 */
	public final long getTimeStep() {
		return mTimeStep;
	}

	/**
	 * Sets time step.
	 * @param _timeStep in seconds.
	 */
	public final void setTimeStep(final long _timeStep) {
		mTimeStep = _timeStep;
	}

	/**
	 * Returns time interval.
	 * @return time interval in seconds.
	 */
	public final long getTimeInterval() {
		return mTimeInterval;
	}

	/**
	 * Sets time interval.
	 * @param _timeInterval in seconds.
	 */
	public final void setTimeInterval(final long _timeInterval) {
		mTimeInterval = _timeInterval;
	}
	//endregion

	public BitCoinChart(final Context _context, final AttributeSet _attrs) {
		super(_context, _attrs);

		init();
	}

	private final void init() {
		mDensityMultiplier = getResources().getDisplayMetrics().density;

		initGridPaint();
		initTimePaint();
		initPricePaint();
		initPlotStrokePaint();
		initPlotFillPaint();

		mTickDataList = new TickDataList();

		mChartPath = new Path();
		mFillPath = new Path();
	}

	//region Init paints
	private final void initGridPaint() {
		mGridPaint = new Paint();
		mGridPaint.setColor(Color.BLACK);
		mGridPaint.setStyle(Paint.Style.STROKE);
		mGridPaint.setStrokeWidth(1);
	}

	private final void initTimePaint() {
		mTimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mTimePaint.setColor(Color.BLACK);
		mTimePaint.setStrokeWidth(1);
		mTimePaint.setTextSize(13 * mDensityMultiplier);
		mTimePaint.setTextAlign(Paint.Align.CENTER);	//pivot point is in baseline-center position of text
	}

	private final void initPricePaint() {
		mPricePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mPricePaint.setColor(Color.BLACK);
		mPricePaint.setStrokeWidth(1);
		mPricePaint.setTextSize(13 * mDensityMultiplier);
		mPricePaint.setTextAlign(Paint.Align.RIGHT);	//pivot point is in baseline-center position of text
	}

	private final void initPlotStrokePaint() {
		mPlotStrokePaint = new Paint();
		mPlotStrokePaint.setStyle(Paint.Style.STROKE);
		mPlotStrokePaint.setColor(Color.parseColor("#006303"));
		mPlotStrokePaint.setStrokeWidth(2);
		mPlotStrokePaint.setAntiAlias(true);
		mPlotStrokePaint.setPathEffect(new CornerPathEffect(5));
	}

	private final void initPlotFillPaint() {
		mPlotFillPaint = new Paint();
		mPlotFillPaint.setStyle(Paint.Style.FILL);
		mPlotFillPaint.setColor(Color.parseColor("#99A8F0B9"));
		mPlotFillPaint.setStrokeWidth(2);
		mPlotFillPaint.setAntiAlias(true);
		mPlotFillPaint.setPathEffect(new CornerPathEffect(5));
	}
	//endregion

	private float downPos = 0;
	@Override
	public final boolean onTouchEvent(final MotionEvent _event) {
		switch (_event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downPos = _event.getRawX();
				break;

			case MotionEvent.ACTION_MOVE:
				final float movePos = _event.getRawX();
				if (movePos < downPos) {
					incStartPos();
				} else {
					decStartPos();
				}
				invalidate();
				break;

			case MotionEvent.ACTION_UP:
				downPos = 0;
				break;
		}

		return true;
	}

	public final void incStartPos() {
		mStartTimePos += 4;
	}

	public final void decStartPos() {
		mStartTimePos -= 4;
	}

	@Override
	protected final void onDraw(final Canvas _canvas) {
		WIDTH = getWidth();
		HEIGHT = getHeight();

		//draw bg
		final Paint bgPaint = new Paint();
		bgPaint.setShader(new RadialGradient(WIDTH / 2, HEIGHT / 2, WIDTH * 0.5f,
				Color.parseColor("#D9FFDA"), Color.parseColor("#19FF21"), Shader.TileMode.MIRROR));
//		_canvas.drawPaint(bgPaint);
		_canvas.drawColor(Color.WHITE);

		calcGridSize();

		drawVerticalLinesAndTime(_canvas);
		drawPlot(_canvas);
		drawHorizontalLinesAndPrice(_canvas);
	}

	private final void calcGridSize() {
		//todo: add bool variable to calc once
		mGridWidth = WIDTH;
		mGridHeight = HEIGHT - HEIGHT * SPACE_FOR_TIME;

		mBottomSpaceHeight = HEIGHT - mGridHeight;
	}

	private final void drawVerticalLinesAndTime(final Canvas _canvas) {
		long currTimePos = mStartTimePos;
		while (currTimePos < mStartTimePos + mTimeInterval) {
			if (currTimePos % mTimeStep == 0) {
				final float x1 = (currTimePos - mStartTimePos) / (float) mTimeInterval * mGridWidth;
				final float y1 = 0;
				final float x2 = x1;
				final float y2 = mGridHeight;

				_canvas.drawLine(x1, y1, x2, y2, mGridPaint);

				final float textOffsetY = Math.abs(mTimePaint.ascent() + mTimePaint.descent()) / 2;
				final float textX = x2;
				final float textY = y2 + mBottomSpaceHeight / 2 + textOffsetY;
				final String time = DateHelper.getTimeString(currTimePos * 1000);
				_canvas.drawText(time, textX, textY, mTimePaint);
			}

			currTimePos++;
		}
	}

	private final void drawHorizontalLinesAndPrice(final Canvas _canvas) {
		checkExtremes();

		final double priceStep = (mMaxPrice - mMinPrice) / mVerticalLinesCount;
		double currPrice = mMaxPrice - priceStep;

		final float distBetweenLines = mGridHeight / mVerticalLinesCount;
		for (int i = 1; i <= mVerticalLinesCount; i++, currPrice -= priceStep) {
			final float x1 = 0;
			final float y1 = distBetweenLines * i;
			final float x2 = mGridWidth;
			final float y2 = y1;

			_canvas.drawLine(x1, y1, x2, y2, mGridPaint);

			final float textOffsetX = mPricePaint.descent();
			final float textOffsetY = mPricePaint.descent();
			final float textX = x2 - textOffsetX;
			final float textY = y2 - textOffsetY;
			_canvas.drawText(String.format("%.6f", currPrice), textX, textY, mPricePaint);
		}
	}

	private double tempMax, tempMin;
	private final void drawPlot(final Canvas _canvas) {
		boolean found = false;

		long firstTimePos = mStartTimePos;
		for (int i = 0; i < mTimeInterval; i++, firstTimePos++) {
			if (mTickDataList.contains(firstTimePos)) {
				found = true;
				break;
			}
		}

		if (!found) return;
		found = false;

		long lastTimePos = mStartTimePos + mTimeInterval;
		while (lastTimePos > firstTimePos) {
			if (mTickDataList.contains(lastTimePos)) {
				found = true;
				break;
			}

			lastTimePos--;
		}

		if (!found) return;

		final int firstTickPos = mTickDataList.indexOf(firstTimePos);
		final int lastTickPos = mTickDataList.indexOf(lastTimePos);

		calcExtremes(firstTickPos, lastTickPos);

		mChartPath.rewind();
		mFillPath.rewind();

		for (int i = firstTickPos; i <= lastTickPos; i++) {
			final TickData tickData = mTickDataList.getTickData(i);
			final long time = tickData.getTime();
			final double price = tickData.getPrice();

			final float x = (time - mStartTimePos) / (float) mTimeInterval * mGridWidth;
			final float y = (float) ((1 - (price - mMinPrice) / (mMaxPrice - mMinPrice)) * mGridHeight);

			//temp: draw lines at max and min value
			final Paint paint = new Paint();
			paint.setStrokeWidth(2);
			paint.setTextSize(13 * mDensityMultiplier);
			if (price == tempMax) {
				paint.setColor(Color.BLUE);
				_canvas.drawLine(0, y, WIDTH, y, paint);
				_canvas.drawText("max", 10, y - paint.descent(), paint);
			} else if (price == tempMin) {
				paint.setColor(Color.RED);
				_canvas.drawLine(0, y, WIDTH, y, paint);
				_canvas.drawText("min", 10, y - paint.ascent(), paint);
			}

			//first point
			if (mChartPath.isEmpty()) {
				mChartPath.moveTo(x, y);
				mFillPath.moveTo(x, mGridHeight);
			}
			mChartPath.lineTo(x, y);
			mFillPath.lineTo(x, y);

			//last point
			if (i == lastTickPos) mFillPath.lineTo(x, mGridHeight);
		}

		if (mChartPath.isEmpty()) return;

		mChartPath.close();
		mFillPath.close();
		_canvas.drawPath(mFillPath, mPlotFillPaint);
		_canvas.drawPath(mChartPath, mPlotStrokePaint);
	}

	private final void calcExtremes(final int _firstTickPos, final int _lastTickPos) {
		clearPriceExtremes();
		for (int i = _firstTickPos; i <= _lastTickPos; i++) {
			final TickData tickData = mTickDataList.getTickData(i);
			final double price = tickData.getPrice();

			updatePriceExtremes(price);
		}

		tempMax = mMaxPrice;
		tempMin = mMinPrice;

		mMaxPrice += (mMaxPrice - mMinPrice) * 0.1;
		mMinPrice -= (mMaxPrice - mMinPrice) * 0.1;
		if (mMinPrice < 0) mMinPrice = 0;

		Log.d("tag", "min price: " + mMinPrice + ", max price: " + mMaxPrice + ", ticks count: " + (_lastTickPos - _firstTickPos));
	}

	private final void clearPriceExtremes() {
		mMinPrice = Integer.MAX_VALUE;
		mMaxPrice = Integer.MIN_VALUE;
	}

	private final void updatePriceExtremes(final double _price) {
		if (_price > mMaxPrice) mMaxPrice = _price;
		if (_price < mMinPrice) mMinPrice = _price;
	}

	private final void checkExtremes() {
		if (mMinPrice == Integer.MAX_VALUE) {
			mMinPrice = 0;
		}
		if (mMaxPrice == Integer.MIN_VALUE) {
			mMaxPrice = 0;
		}
	}

	public final void addTickData(final TickData _tickData) {
		if (mTickDataList != null) mTickDataList.addTickData(_tickData);
		invalidate();
	}


	private final class TickDataList {
		private ArrayList<Long> mTimes				= null;
		private ArrayList<Double> mPrices			= null;

		public TickDataList() {
			mTimes = new ArrayList<Long>();
			mPrices = new ArrayList<Double>();
		}

		public final void addTickData(final TickData _tickData) {
			mTimes.add(_tickData.getTime());
			mPrices.add(_tickData.getPrice());
		}

		public final boolean contains(final long _time) {
			return mTimes.contains(_time);
		}

		public final int indexOf(final long _time) {
			return mTimes.indexOf(_time);
		}

		public final TickData getTickData(final int _pos) {
			return new TickData(mTimes.get(_pos), mPrices.get(_pos));
		}

		public final int getCount() {
			return mTimes.size();
		}
	}

	/**
	 * Class for holding data for one tick: time and prices.
	 * Time saved in milliseconds (long variable), prices as double.
	 * And some additional data (not used).
	 */
	public static final class TickData {
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

		public TickData(final long _time) {
//				,
//						final double _openBid,	final double _openAsk,
//						final double _highBid,	final double _highAsk,
//						final double _lowBid,	final double _lowAsk,
//						final double _closeBid,	final double _closeAsk,
//						final int _volume,		final boolean _complete) {

			mTime = _time;
//			mOpenBid	= _openBid;
//			mOpenAsk	= _openAsk;
//			mHighBid	= _highBid;
//			mHighAsk	= _highAsk;
//			mLowBid		= _lowBid;
//			mLowAsk		= _lowAsk;
//			mCloseBid	= _closeBid;
//			mCloseAsk	= _closeAsk;
//			mVolume		= _volume;
//			mComplete	= _complete;
		}

		public TickData(final long _time, final double _openBid) {
			mTime = _time;
			mOpenBid = _openBid;
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
}