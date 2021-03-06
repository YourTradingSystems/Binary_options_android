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

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * todo: rework to millis when displaying
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
	private long mCurrTimePos 					= -1;
	private long mTimeStep 						= -1;
	private long mTimeInterval 					= -1;

	private PriceExtremesHandler mPriceExtremesHandler		= null;
	private TickDataList mTickDataList 			= null;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private Path mChartPath 					= null;
	private Path mFillPath 						= null;
	//endregion

	//region Getters and Setters
	public final int getVerticalLinesCount() {
		return mVerticalLinesCount;
	}

	public final void setVerticalLinesCount(final int _count) {
		mVerticalLinesCount = _count;
	}
	//endregion

	//region Plot data
	/**
	 * Returns curr position in seconds.
	 * @return time in seconds.
	 */
	public final long getCurrTimePos() {
		return mCurrTimePos;
	}

	/**
	 * Set curr position is seconds.
	 * @param _currPos time in seconds.
	 */
	public final void setCurrTimePos(final long _currPos) {
		mCurrTimePos = _currPos;
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

		mPriceExtremesHandler = new PriceExtremesHandler();
		mTickDataList = new TickDataList();

		initGridPaint();
		initTimePaint();
		initPricePaint();
		initPlotStrokePaint();
		initPlotFillPaint();

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
		mPlotStrokePaint.setColor(Color.WHITE);
		mPlotStrokePaint.setStrokeWidth(2);
		mPlotStrokePaint.setAntiAlias(true);
		mPlotStrokePaint.setPathEffect(new CornerPathEffect(5));
	}

	private final void initPlotFillPaint() {
		mPlotFillPaint = new Paint();
		mPlotFillPaint.setStyle(Paint.Style.FILL);
		mPlotFillPaint.setColor(Color.parseColor("#7fffffff"));
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
					incCurrTimePos();
				} else {
					decCurrTimePos();
				}
				invalidate();
				break;

			case MotionEvent.ACTION_UP:
				downPos = 0;
				break;
		}

		return true;
	}

	public final void incCurrTimePos() {
		mCurrTimePos += 4;
	}

	public final void decCurrTimePos() {
		mCurrTimePos -= 4;
	}

	@Override
	protected final void onDraw(final Canvas _canvas) {
		WIDTH = getWidth();
		HEIGHT = getHeight();

		final int[] colors = { Color.parseColor("#e4e89e"), Color.parseColor("#edbb92"),
				Color.parseColor("#ba7b6f"), Color.parseColor("#744363") };

		//draw bg
		final Paint bgPaint = new Paint();
		bgPaint.setShader(new LinearGradient(WIDTH, 0, WIDTH, HEIGHT,
				colors, null, Shader.TileMode.CLAMP));
		_canvas.drawPaint(bgPaint);
//		_canvas.drawColor(Color.WHITE);

		calcGridSize();

		drawVerticalLinesAndTime(_canvas);
		if (!mTickDataList.isEmpty()) drawPlot(_canvas);
		drawHorizontalLinesAndPrice(_canvas);
	}

	/**
	 * Calculates displayed grid size on view and bottom space for time.
	 * todo: add bool variable to calc once
	 */
	private final void calcGridSize() {
		mGridWidth = WIDTH;
		mGridHeight = HEIGHT - HEIGHT * SPACE_FOR_TIME;

		mBottomSpaceHeight = HEIGHT - mGridHeight;
	}

	/**
	 * Draws vertical lines on chart every specified time interval.
	 * Also draws time below the lines.
	 * @param _canvas Canvas to drawing.
	 */
	private final void drawVerticalLinesAndTime(final Canvas _canvas) {
		int iterationCount = 0; //debug

		//offset to first vertical line (in seconds)
		final long offset = mTimeStep - (mCurrTimePos + mTimeStep) % mTimeStep;

		//last visible time position on chart
		final long endTimePos = mCurrTimePos + mTimeInterval;

		//current position: first greater by offset from start pos, than increasing by time step
		long currTimePos = mCurrTimePos + offset;
		while (currTimePos < endTimePos) {
			final float x1 = (currTimePos - mCurrTimePos) / (float) mTimeInterval * mGridWidth;
			final float y1 = 0;
			final float x2 = x1;
			final float y2 = mGridHeight;

			_canvas.drawLine(x1, y1, x2, y2, mGridPaint);

			final float fontOffsetY = Math.abs(mTimePaint.ascent() + mTimePaint.descent()) / 2;
			final float textX = x2;
			final float textY = y2 + mBottomSpaceHeight / 2 + fontOffsetY;
			final String time = DateHelper.getTimeString(currTimePos * 1000);
			_canvas.drawText(time, textX, textY, mTimePaint);

			currTimePos += mTimeStep;

			iterationCount++;	//debug
		}

		Log.d(TAG_CHART, "drawVerticalLinesAndTime, iteration count = " + iterationCount);
	}

	/**
	 * Draws horizontal lines on chart and price below lines.
	 * @param _canvas Canvas to drawing.
	 */
	private final void drawHorizontalLinesAndPrice(final Canvas _canvas) {
		int iterationCount = 0;	//debug

		mPriceExtremesHandler.checkExtremes();

		final double priceStep = (mPriceExtremesHandler.getMaxPrice() - mPriceExtremesHandler.getMinPrice()) / mVerticalLinesCount;
		double currPrice = mPriceExtremesHandler.getMaxPrice() - priceStep;

		final float distBetweenLines = mGridHeight / mVerticalLinesCount;
		for (int i = 1; i <= mVerticalLinesCount; i++, currPrice -= priceStep) {
			final float x1 = 0;
			final float y1 = distBetweenLines * i;
			final float x2 = mGridWidth;
			final float y2 = y1;

			_canvas.drawLine(x1, y1, x2, y2, mGridPaint);

			final float fontOffsetX = mPricePaint.descent();
			final float fontOffsetY = mPricePaint.descent();
			final float textX = x2 - fontOffsetX;
			final float textY = y2 - fontOffsetY;
			_canvas.drawText(String.format("%.6f", currPrice), textX, textY, mPricePaint);

			iterationCount++;	//debug
		}

		Log.d(TAG_CHART, "drawHorizontalLinesAndPrice, iteration count = " + iterationCount);
	}

	/**
	 *
	 * @param _canvas
	 */
	private final void drawPlot(final Canvas _canvas) {
		final ArrayList<TickData> tickDatas = mTickDataList.prepareDataForDraw(this);

		mPriceExtremesHandler.calcPriceExtremes(tickDatas);

		//clear pathes
		mChartPath.rewind();
		mFillPath.rewind();

		for (int i = 0; i < tickDatas.size(); i++) {
			final TickData tickData = tickDatas.get(i);
			final long time = tickData.getTime();
			final double price = tickData.getPrice();

			final float x = (time - mCurrTimePos) / (float) mTimeInterval * mGridWidth;
			final float y = (float) ((1 - (price - mPriceExtremesHandler.getMinPrice()) /
					(mPriceExtremesHandler.getMaxPrice() - mPriceExtremesHandler.getMinPrice())) * mGridHeight);

			//temp: draw lines at max and min value
			final Paint paint = new Paint();
			paint.setStrokeWidth(2);
			paint.setTextSize(13 * mDensityMultiplier);
			if (price == mPriceExtremesHandler.getTempMax()) {
				paint.setColor(Color.BLUE);
				_canvas.drawLine(0, y, WIDTH, y, paint);
				_canvas.drawText("max", 10, y - paint.descent(), paint);
			} else if (price == mPriceExtremesHandler.getTempMin()) {
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
			if (i == tickDatas.size() - 1) mFillPath.lineTo(x, mGridHeight);
		}

		if (mChartPath.isEmpty()) return;

		mChartPath.close();
		mFillPath.close();
		_canvas.drawPath(mFillPath, mPlotFillPaint);
		_canvas.drawPath(mChartPath, mPlotStrokePaint);
	}

	public final void addNewDatas(final ArrayList<TickData> _tickDatas) {
		mTickDataList.addAll(_tickDatas);
		invalidate();
	}

}