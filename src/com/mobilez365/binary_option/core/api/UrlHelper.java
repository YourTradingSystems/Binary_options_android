package com.mobilez365.binary_option.core.api;

import android.os.Bundle;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 31.03.14
 * Time: 17:33
 */
abstract class UrlHelper {

	protected static final URL buildTickDataUrl(final Bundle _bundle) throws MalformedURLException {
		//get data from bundle
		final String count = _bundle.getString(URL_TAG_COUNT);
		final String instrument = _bundle.getString(URL_TAG_INSTRUMENT);

		final List<NameValuePair> urlParams = new LinkedList<NameValuePair>();
		urlParams.add(new BasicNameValuePair(URL_TAG_COUNT, count));
		urlParams.add(new BasicNameValuePair(URL_TAG_INSTRUMENT, instrument));

		final String paramStr = URLEncodedUtils.format(urlParams, "UTF-8");

		final String addr = TICK_DATA_URL + "?" + paramStr;

		final URL url = new URL(addr);
		return url;
	}
}