package com.mobilez365.binary_option.core.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * User: ZOG
 * Date: 17.01.14
 * Time: 10:43
 */
public abstract class FragmentHelper {
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;

    /**
     * save reference to fragment manager from activity
     * @param _activity
     */
    public static final void initFragmentManager(final Activity _activity) {
        mFragmentManager = _activity.getFragmentManager();
    }

    /**
     * must call only after init
     * @return reference to FragmentManager
     */
    private static final FragmentManager sharedFragmentManager() {
        return mFragmentManager;
    }

    /**
     * use this for transactions between fragments
     * @return reference to shared variable
     */
    private static final FragmentTransaction sharedFragmentTransaction() {
        return mFragmentTransaction;
    }

    /**
     * begins transaction
     * access to variable through sharedFragmentTransaction() method
     */
    private static final void beginSharedFragmentTransaction() {
        mFragmentTransaction = sharedFragmentManager().beginTransaction();
    }

    protected static final void addFragment(final int _rootView, final Fragment _fragment) {
        beginSharedFragmentTransaction();
        final FragmentTransaction transaction = FragmentHelper.sharedFragmentTransaction();
        try {
            transaction.add(_rootView, _fragment);
            transaction.addToBackStack(null);
            transaction.commit(); //todo: test method commitAllowingStateLoss()
        } catch (final IllegalStateException _e) { _e.printStackTrace(); }
    }

	protected static final void replaceFragment(final int _rootView, final Fragment _fragment) {
        beginSharedFragmentTransaction();
        final FragmentTransaction transaction = FragmentHelper.sharedFragmentTransaction();
        try {
            transaction.replace(_rootView, _fragment);
            transaction.addToBackStack(null);
            transaction.commit(); //todo: test method commitAllowingStateLoss()
        } catch (final IllegalStateException _e) { _e.printStackTrace(); }
    }

	protected static final void deleteAllFragments() {
        try {
            final FragmentManager manager = FragmentHelper.sharedFragmentManager();
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (final IllegalStateException _e) { _e.printStackTrace(); }

    }
}