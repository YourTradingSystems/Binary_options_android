package com.mobilez365.binary_option.core.api;

import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;

/**
 * User: ZOG
 * Date: 21.03.14
 * Time: 16:35
 */
public abstract class ApiWorker {

	public static final HashMap<Integer, Object> apiTickData(final Bundle _bundle)
			throws IOException, JSONException, ParseException {

		final URL url = UrlHelper.buildTickDataUrl(_bundle);

		final HttpURLConnection conn = ApiHelper.createTickDataConn(url);
		final HashMap<Integer, Object> result = ApiHelper.createTickDataResult(conn);

		conn.disconnect();

		return result;
	}
}