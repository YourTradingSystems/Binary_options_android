package com.mobilez365.binary_option.core.thread;


/**
 * User: Vasja
 * Date: 04.11.13
 * Time: 16:14
 */
public abstract class ThreadPool {
    //use this for cancelling get status request on game screen when going to choose table screen
    public static WebRequest webRequestTask;

    public static final void cancelRequestTask() {
        if (webRequestTask != null) {
            webRequestTask.cancel(false);
        }
    }
}