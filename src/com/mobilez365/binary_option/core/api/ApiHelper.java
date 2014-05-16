package com.mobilez365.binary_option.core.api;

import android.os.Bundle;
import com.mobilez365.binary_option.screens.chart.BitCoinChart;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.TreeMap;


import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 26.03.14
 * Time: 15:32
 */
abstract class ApiHelper {

	/**
	 * Creates blank for HttpUrlConnection with timeout.
	 * (Add here new general params if needed).
	 * @param _url resource url.
	 * @return new instance of HttpUrlConnection.
	 * @throws IOException
	 */
	private static final HttpURLConnection createDefaultConn(final URL _url) throws IOException {

		final HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
		conn.setConnectTimeout(CONN_TIMEOUT);

		return conn;
	}


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONNECTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected static final HttpURLConnection createTickDataConn(final URL _url) throws IOException {

		final HttpURLConnection conn = createDefaultConn(_url);
//		conn.setDoInput(true);
//		conn.setDoOutput(true);
		conn.setRequestMethod(HttpGet.METHOD_NAME);
//		conn.setRequestProperty("Accept", "application/json");
//		conn.setRequestProperty("Content-type", "application/json");
		return conn;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected static final HashMap<Integer, Object> createTickDataResult(final HttpURLConnection _conn)
			throws IOException, JSONException, ParseException {

		final HashMap<Integer, Object> result = new HashMap<Integer, Object>();
		final String responseStr;
		final TreeMap<Long, BitCoinChart.TickData> ticks;

		final int statusCode = _conn.getResponseCode();
		switch (statusCode) {
			case 200:
				responseStr = DataWorker.readDataFromConn(_conn);
				ticks = Parser.parseTickData(responseStr);
				result.put(WM_TICK_DATA, ticks);
				break;

			default:
				final Bundle bundle = ErrorHelper.createErrorBundle(statusCode);
				result.put(M_ERROR, bundle);
				break;
		}

		return result;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}