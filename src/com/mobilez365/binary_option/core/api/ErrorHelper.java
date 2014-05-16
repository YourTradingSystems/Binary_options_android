package com.mobilez365.binary_option.core.api;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.mobilez365.binary_option.global.Variables;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 26.03.14
 * Time: 15:51
 */
public abstract class ErrorHelper {

	public static final Bundle createErrorBundle(final int _statusCode) {
		final String errorDescription = getErrorDescription(_statusCode);

		final Bundle bundle = new Bundle();
		bundle.putString(KEY_ERROR_DESCRIPTION, errorDescription);
		return bundle;
	}

	public static final Bundle createErrorBundle(final String _description) {
		final Bundle bundle = new Bundle();
		bundle.putString(KEY_ERROR_DESCRIPTION, _description);
		return bundle;
	}

	private static final String getErrorDescription(final int _errorCode) {
		switch (_errorCode) {
			//~~~~~~~~~~~~~~ User errors ~~~~~~~~~~~~~~~~~~~//
			case -1: return "Invalid credentials";
			case -2: return "Password expired";
			case -3: return "Login type is not allowed";
			case -4: return "Customer's jurisdiction is unsupported";
			case -5: return "Customer's brand is not supported or match the request";
			case -10: return "Blocked due to too many failed attempts";
			case -11: return "Blocked due to self exclusion";
			case -12: return "Blocked by Regulator";
			case -13: return "Blocked for device type";
			case -14: return "Blocked due to other reason";
			case -999: return "Unknown error";

			//~~~~~~~~~~~~~~ Server errors ~~~~~~~~~~~~~~~~~~~//
			case 400: return "Bad Request";
			case 500: return "Internal system error";

			default: return "Unexpected error: " + _errorCode;
		}
	}

	public static final void showErrorDialog(final Bundle _bundle) {
		final String errorDescription = _bundle.getString(KEY_ERROR_DESCRIPTION);

		new AlertDialog.Builder(Variables.activity)
				.setTitle("Error")
				.setMessage(errorDescription)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public final void onClick(final DialogInterface _dialog, final int _which) {
						// continue with delete
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
	}
}