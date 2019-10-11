package com.myki.challenge.base.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MyLinearLayoutManager extends LinearLayoutManager {
  /**
   * Disable predictive animations. There is a bug in RecyclerView which causes views that
   * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
   * adapter size has decreased since the ViewHolder was recycled.
   */
  @Override
  public boolean supportsPredictiveItemAnimations() {
    return false;
  }

  public MyLinearLayoutManager(Context context, @RecyclerView.Orientation int orientation,
      boolean reverseLayout) {
    super(context, orientation, reverseLayout);
  }

}