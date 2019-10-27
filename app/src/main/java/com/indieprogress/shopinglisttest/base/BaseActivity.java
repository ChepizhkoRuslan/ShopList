package com.indieprogress.shopinglisttest.base;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {

    public void showFragment(Fragment fragment, @IdRes int container, int tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (currentFragment == null)
            fragmentTransaction.add(container, fragment, String.valueOf(tag));
        else
            fragmentTransaction.replace(container, fragment, String.valueOf(tag));

        hideFragment(fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    protected boolean isEmptyFragmentContainer(@IdRes int containerId) {
        return getSupportFragmentManager().findFragmentById(containerId) == null;
    }

    protected void showFragment(@IdRes int containerId, BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(containerId);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (currentFragment == null) fragmentTransaction.add(containerId, fragment);
        else fragmentTransaction.replace(containerId, fragment);

        fragmentTransaction.commit();
    }

    public void showFragmentOrRestore(@IdRes int containerId, Fragment fragment,
                                      String tagForRestoredFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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

    public void hideFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment2 : fragmentManager.getFragments()) {
            getSupportFragmentManager().beginTransaction().hide(fragment2).commit();
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}
