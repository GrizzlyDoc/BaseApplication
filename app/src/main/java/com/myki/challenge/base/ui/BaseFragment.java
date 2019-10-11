package com.myki.challenge.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import com.myki.challenge.AppModule;
import com.myki.challenge.BaseApplication;
import com.myki.challenge.R;
import com.myki.challenge.base.utils.ViewUtil;
import java.lang.reflect.Field;
import javax.inject.Inject;
import javax.inject.Named;
import timber.log.Timber;

public abstract class BaseFragment extends Fragment {

  // Arbitrary value; set it to some reasonable default
  private static final int DEFAULT_CHILD_ANIMATION_DURATION = 250;

  @Inject @Named(AppModule.MAIN_THREAD_HANDLER) Handler mainThreadHandler;

  private static long getNextAnimationDuration(@NonNull Fragment fragment, long defValue) {
    try {
      // Attempt to get the resource ID of the next animation that
      // will be applied to the given fragment.
      Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
      nextAnimField.setAccessible(true);
      int nextAnimResource = nextAnimField.getInt(fragment);
      Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimResource);

      // ...and if it can be loaded, return that animation's duration
      return (nextAnim == null) ? defValue : nextAnim.getDuration();
    } catch (@NonNull
        NoSuchFieldException | IllegalAccessException | Resources.NotFoundException ex) {
      Timber.w("Unable to load next animation from parent.", ex);
      return defValue;
    }
  }

  protected void runOnUiThreadIfFragmentAlive(@NonNull Runnable runnable) {
    if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
      runnable.run();
    } else {
      if (mainThreadHandler == null) {
        mainThreadHandler = new Handler(Looper.getMainLooper());
      }
      mainThreadHandler.post(() -> {
        if (isFragmentAlive()) {
          runnable.run();
        }
      });
    }
  }

  @Override public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
    final Fragment parent = getParentFragment();

    // Apply the workaround only if this is a child fragment, and the parent
    // is being removed.
    if (!enter && parent != null && parent.isRemoving()) {
      // This is a workaround for the bug where child fragments disappear when
      // the parent is removed (as all children are first removed from the parent)
      // See https://code.google.com/p/android/issues/detail?id=55228
      Animation doNothingAnim = new AlphaAnimation(1, 1);
      doNothingAnim.setDuration(getNextAnimationDuration(parent, DEFAULT_CHILD_ANIMATION_DURATION));
      return doNothingAnim;
    } else {
      return super.onCreateAnimation(transit, enter, nextAnim);
    }
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    BaseActivity a = getBaseActivity();
    if (a != null) {
      Window window = a.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }
  }

  @Override public void onCreateOptionsMenu(@Nullable Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    if (menu != null) menu.clear();
  }

  private boolean isFragmentAlive() {
    return getActivity() != null
        && isAdded()
        && !isDetached()
        && getView() != null
        && !isRemoving();
  }

  public void setupToolbar(Toolbar toolbar) {
    setupToolbar(toolbar, false);
  }

  public void setupToolbar(Toolbar toolbar, boolean homeAsUpEnabled) {
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbar);

    if (activity.getSupportActionBar() != null) {
      activity.getSupportActionBar();
      activity.getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
      activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public void startActivity(Intent intent) {
    super.startActivity(intent);
    overridePendingTransitionEnter();
  }

  @Nullable public BaseActivity getBaseActivity() {
    Activity a = getActivity();
    if (a == null) {
      return null;
    }
    if (a instanceof BaseActivity) {
      return (BaseActivity) a;
    }
    throw new IllegalStateException("BaseFragment added to an activity other than BaseActivity");
  }

  /**
   * Overrides the pending Activity transition by performing the "Enter" animation.
   */
  protected void overridePendingTransitionEnter() {
    if (ViewUtil.isRTL()) {
      getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    } else {
      getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
  }

  public void goToFragment(@NonNull BaseFragment fragment, int direction) {
    BaseActivity a = getBaseActivity();
    if (a != null) {
      a.switchContent(R.id.main_content, fragment, direction);
    }
  }

  public void goToFragment(@NonNull BaseFragment fragment) {
    goToFragment(fragment, BaseActivity.LEFT);
  }

  public void goToMainFragment() {
    BaseActivity a = getBaseActivity();
    if (a != null) {
      getBaseActivity().goToMainFragment();
    }
  }

  public void goFromCreateCustomTemplateFragmentToItemPage() {
    BaseActivity a = getBaseActivity();
    if (a != null) {
      getBaseActivity().goFromCreateCustomTemplateToItemPage();
    }
  }

  public void hideKeyboard() {
    View view = getView();
    InputMethodManager imm =
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
    if (imm != null && view != null) {
      imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  public void showKeyboard() {
    View view = getView();
    InputMethodManager imm =
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
    if (imm != null && view != null) {
      imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
  }
}
