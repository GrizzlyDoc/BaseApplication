package com.grizzly.baseapplication.base.api;

import android.support.annotation.NonNull;
import com.grizzly.baseapplication.base.performance.AnyThread;

public interface BaseAppView {

  @AnyThread void displayNotification(@NonNull String title, @NonNull String message,
      int notificationId);

}
