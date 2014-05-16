package com.mobilez365.binary_option.core.api;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * User: ZOG
 * Date: 26.03.14
 * Time: 15:01
 */
abstract class HttpHelper {
	/**
	 * create http client with timeouts
	 * @return
	 */
	protected static final DefaultHttpClient prepareHttpClient() {
		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);           //15 sec timeout
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		return new DefaultHttpClient(httpParams);
	}
}