package com.myki.challenge.base.api;

import android.support.annotation.NonNull;
import com.myki.challenge.base.performance.AnyThread;

public interface BaseAppView {

  @AnyThread void displayNotification(@NonNull String title, @NonNull String message,
      int notificationId);

}
