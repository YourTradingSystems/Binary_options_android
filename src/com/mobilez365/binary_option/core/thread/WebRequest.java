package com.mobilez365.binary_option.core.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import com.mobilez365.binary_option.core.api.ApiWorker;
import com.mobilez365.binary_option.core.api.ErrorHelper;
import com.mobilez365.binary_option.core.msg.MsgSender;
import com.mobilez365.binary_option.screens.chart.BitCoinChart;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.TreeMap;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * class for sending request to server
 */
public final class WebRequest extends AsyncTask<Bundle, Void, HashMap<Integer, Object>> {

    private int mMsgCode;

    /**
     * factory method
     * @return
     */
    public static final WebRequest create() {
        return new WebRequest();
    }

    /**
     * constructor for request
     */
    private WebRequest() {
    }

	public final void setMsgCode(final int _msgCode) {
		mMsgCode = _msgCode;
	}

    @Override
    protected final HashMap<Integer, Object> doInBackground(final Bundle... _bundles) {

		HashMap<Integer, Object> result = new HashMap<Integer, Object>();

        if (isCancelled()) return null;

        try {
			switch (mMsgCode) {

				case WM_TICK_DATA: result = ApiWorker.apiTickData(_bundles[0]); break;

				default:
					final Bundle bundle = ErrorHelper.createErrorBundle(mMsgCode);
					result.put(M_ERROR, bundle);
					break;
			}

        } catch (final IOException _e) {
            final Bundle bundle = ErrorHelper.createErrorBundle(_e.toString());
			result.put(M_ERROR, bundle);
            _e.printStackTrace();

		} catch (final JSONException _e) {
			final Bundle bundle = ErrorHelper.createErrorBundle(_e.toString());
			result.put(M_ERROR, bundle);
            _e.printStackTrace();

		} catch (final Throwable _t) {
			final Bundle bundle = ErrorHelper.createErrorBundle(_t.toString());
			result.put(M_ERROR, bundle);
			_t.printStackTrace();
		} finally {
            return result;
        }
    }

    @Override
    public final void onPreExecute() {
//        if (!Network.isInternetConnectionAvailable(Variables.activity)) return;

        ProgressDialogFragment.startProgressDialog(mMsgCode);
    }

    @Override
    public final void onPostExecute(final HashMap<Integer, Object> _result) {

		if (_result == null) return;


		if (_result.containsKey(WM_TICK_DATA)) {
			final TreeMap<Long, BitCoinChart.TickData> ticks = (TreeMap<Long, BitCoinChart.TickData>) _result.get(WM_TICK_DATA);
			MsgSender.prepareAndSendMsg_apiTickData(MA_END, ticks);

		} else if (_result.containsKey(M_ERROR)) {
			final Bundle bundle = (Bundle) _result.get(M_ERROR);
			MsgSender.prepareAndSendMsg_ApiError(bundle);
		}

        ProgressDialogFragment.stopProgressDialog(mMsgCode);
    }
}