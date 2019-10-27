package com.indieprogress.shopinglisttest.base;

import android.app.ProgressDialog;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.indieprogress.shopinglisttest.R;
import java.util.Objects;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {

    private ProgressDialog mProgressDialog;

    protected void showFragmentOrRestore(@IdRes int containerId, Fragment fragment,
                                         String tagForRestoredFragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment sameFragment = fragmentManager.findFragmentByTag(tagForRestoredFragment);

        if (sameFragment == null) {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.add(containerId, fragment, tagForRestoredFragment);
        } else {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.show(sameFragment);
        }
        fragmentTransaction.commit();
    }

    protected void showFragmentOrRestoreInActivity(@IdRes int containerId, Fragment fragment,
                                                   String tagForRestoredFragment) {
        FragmentManager fragmentManager = Objects
                .requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment sameFragment = fragmentManager.findFragmentByTag(tagForRestoredFragment);

        if (sameFragment == null) {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.add(containerId, fragment, tagForRestoredFragment);
        } else {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.show(sameFragment);
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragments(FragmentManager fragmentManager,
                                  FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : fragmentManager.getFragments()) {
            fragmentTransaction.hide(fragment);
        }
    }
    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
