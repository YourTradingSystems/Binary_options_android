package com.mobilez365.binary_option.temp;

import android.app.IntentService;
import android.content.Intent;
import android.os.*;
import android.util.Log;

import static com.mobilez365.binary_option.global.Constants.*;

/**
 * User: ZOG
 * Date: 13.05.14
 * Time: 9:16
 */
public final class IntentRequestService extends IntentService {

	private MessageHandler messageHandler;
	private Messenger messenger;

	public IntentRequestService() {
		super("intent_service");

		Log.d(TAG_SERVICE, "IntentRequestService: constructor");

		messageHandler = new MessageHandler();
		messenger = new Messenger(messageHandler);
	}

	@Override
	public final void onDestroy() {
		Log.d(TAG_SERVICE, "IntentRequestService: onDestroy()");
		super.onDestroy();
	}

	@Override
	protected final void onHandleIntent(final Intent _intent) {
		Log.d(TAG_SERVICE, "IntentRequestService: onHandleIntent()");

		sendMessage(0);
	}

	public void sendMessage(final int _info) {
		final Message message = Message.obtain();
		switch (_info) {
			case 0 :
				message.arg1 = 0;
				break;
			case 1 :
				message.arg1 = 1;
				break;
		}
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private final class MessageHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			int state = message.arg1;
			switch (state) {
				case 0:
//					Test.activity.testMethod(state);
					break;
				case 1:
//					Test.activity.testMethod(state);
					break;
			}
		}
	}
}