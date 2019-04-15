package com.grizzly.baseapplication.base.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import com.grizzly.baseapplication.R;
import com.grizzly.baseapplication.base.utils.ViewUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;
import static com.grizzly.baseapplication.AppModule.SHARED_PREFERENCES;

public abstract class BaseActivity extends AppCompatActivity {

  public static final int LEFT = 0;
  public static final int RIGHT = 1;
  public static final int TOP = 2;
  public static final int BOTTOM = 3;

  private int direction = LEFT;

  @Nullable private Toolbar toolbar;

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    setupToolbar();
  }

  @Override public void setContentView(View view) {
    super.setContentView(view);
    setupToolbar();
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    setupToolbar();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SharedPreferences sharedPreferences =
        getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }

  private void setupToolbar() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  @Nullable protected Toolbar toolbar() {
    return toolbar;
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransitionExit(direction);
    //Fragment currFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
    //if (currFragment != null && currFragment instanceof ClearOrSignInFragment) {
    //
    //} else {
    //  super.onBackPressed();
    //  overridePendingTransitionExit(direction);
    //}
  }

  @Override public void finish() {
    super.finish();
  }

  @Override public void startActivity(Intent intent) {
    super.startActivity(intent);
    overridePendingTransitionEnter(direction);
  }

  // pass context to Calligraphy
  @Override protected void attachBaseContext(Context context) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
  }

  /**
   * Overrides the pending Activity transition by performing the "Enter" animation.
   */
  protected void overridePendingTransitionEnter(int direction) {
    switch (direction) {
      case LEFT:
        if (ViewUtil.isRTL()) {
          overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        } else {
          overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
        break;
      case RIGHT:
        if (ViewUtil.isRTL()) {
          overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else {
          overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
        break;
      case TOP:
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
        break;
      case BOTTOM:
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
        break;
    }
  }

  /**
   * Overrides the pending Activity transition by performing the "Exit" animation.
   */
  protected void overridePendingTransitionExit(int direction) {
    switch (direction) {
      case LEFT:
        if (ViewUtil.isRTL()) {
          overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else {
          overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
        break;
      case RIGHT:
        if (ViewUtil.isRTL()) {
          overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        } else {
          overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
        break;
      case TOP:
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
        break;
      case BOTTOM:
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
        break;
    }
  }

  public void switchContent(int id, @NonNull BaseFragment fragment) {
    switchContent(id, fragment, LEFT);
  }

  public void switchContent(int id, @NonNull BaseFragment fragment, int direction) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

    switch (direction) {
      case LEFT:
        if (ViewUtil.isRTL()) {
          ft.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right,
              R.anim.slide_from_right, R.anim.slide_to_left);
        } else {
          ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left,
              R.anim.slide_from_left, R.anim.slide_to_right);
        }
        break;
      case RIGHT:
        if (ViewUtil.isRTL()) {
          ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left,
              R.anim.slide_from_left, R.anim.slide_to_right);
        } else {
          ft.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right,
              R.anim.slide_from_right, R.anim.slide_to_left);
        }
        break;
      case TOP:
        ft.setCustomAnimations(R.anim.slide_from_top, R.anim.slide_to_bottom,
            R.anim.slide_from_bottom, R.anim.slide_to_top);
        break;
      case BOTTOM:
        ft.setCustomAnimations(R.anim.slide_from_bottom, R.anim.slide_to_top, R.anim.slide_from_top,
            R.anim.slide_to_bottom);
        break;
    }

    ft.replace(id, fragment, fragment.toString());
    ft.addToBackStack(null);
    try {
      ft.commit();
    } catch (IllegalStateException ignored) {
      // TODO Refer to this http://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html
    }
  }

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }

  public void goToMainFragment() {
    int count = getSupportFragmentManager().getBackStackEntryCount();
    for (int i = 0; i < count; i++) {
      getSupportFragmentManager().popBackStack();
    }
  }

  public void goFromCreateCustomTemplateToItemPage() {
    int count = getSupportFragmentManager().getBackStackEntryCount();
    for (int i = 0; i < 2; i++) {
      getSupportFragmentManager().popBackStack();
    }
  }
}
