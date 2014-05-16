package com.mobilez365.binary_option.global;

/**
 * User: ZOG
 * Date: 13.05.14
 * Time: 11:01
 */
public abstract class Constants {


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DEBUG ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String TAG_SERVICE				= "tag_service";
	public static final String TAG_MSG					= "tag_msg";
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SERVER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String TICK_DATA_URL			= "http://api-sandbox.oanda.com/v1/candles";
	public static final String URL_TAG_COUNT 			= "count";
	public static final String URL_TAG_INSTRUMENT 		= "instrument";

	public static final String KEY_INSTRUMENT			= "instrument";
	public static final String KEY_GRANULARITY			= "granularity";
	public static final String KEY_CANDLES				= "candles";
	public static final String KEY_TIME					= "time";
	public static final String KEY_OPEN_BID				= "openBid";
	public static final String KEY_OPEN_ASK				= "openAsk";
	public static final String KEY_HIGH_BID				= "highBid";
	public static final String KEY_HIGH_ASK				= "highAsk";
	public static final String KEY_LOW_BID				= "lowBid";
	public static final String KEY_LOW_ASK				= "lowAsk";
	public static final String KEY_CLOSE_BID			= "closeBid";
	public static final String KEY_CLOSE_ASK			= "closeAsk";
	public static final String KEY_VOLUME				= "volume";
	public static final String KEY_COMPLETE				= "complete";
//	public static final String KEY_						= "";
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MESSAGES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/*
		what:
		M	- Message
		WM 	- Web message
		AM	- Application message

		arg1:
		MA - Message action (START, END)
	*/
	public static final int WM_TICK_DATA				= 10;

	public static final int M_ERROR 					= -1000;

	public static final int MA_START 					= 1;
	public static final int MA_END 						= 0;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


	//~~~~~~~~~~~~~~~~~~~~~~~~~ INNER-USED KEYS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String KEY_ERROR_DESCRIPTION	= "error_description";
	public static final int CONN_TIMEOUT				= 15000;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


}