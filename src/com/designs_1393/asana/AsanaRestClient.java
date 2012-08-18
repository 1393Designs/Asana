package com.designs_1393.asana;

import com.loopj.android.http.*;
import org.apache.http.auth.AuthScope;

public class AsanaRestClient
{
	private static final String BASE_URL = "https://app.asana.com/api/1.0/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
	{
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
	{
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl)
	{
		return BASE_URL + relativeUrl;
	}

	/* -------------------------------------------------------------------------
	 * Everything above ^^ this ^^ line (except BASE_URL) is taken from the
	 * loopj android-async-http documentation's TwitterRestClient class.
	 * Everything below is used to inject additional Asana-specific
	 * functionality.
	 */

	public static void setAPIkey(String APIkey)
	{
		// we use the API key as the username and an empty password, as per the
		// Asana API docs.
		client.setBasicAuth(APIkey, "", new AuthScope("asana.com", 80, AuthScope.ANY_REALM));
	}
}
