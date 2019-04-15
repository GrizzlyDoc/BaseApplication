package com.grizzly.baseapplication.base.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.grizzly.baseapplication.BaseApplication;
import com.grizzly.baseapplication.base.api.AppPresenter;
import javax.inject.Inject;
import timber.log.Timber;

public class NotificationReceiver extends BroadcastReceiver {

  @Inject AppPresenter appPresenter;

  @Override public void onReceive(@NonNull final Context context, @NonNull final Intent intent) {
    Timber.v("Received notification intent in NotificationReceiver");
    BaseApplication.get(context).appComponent().inject(this);

    // Dismiss Notification
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    if (notificationManager != null) {
      //notificationManager.cancel(notificationId);
    }

    if (intent.getAction() != null) {
      switch (intent.getAction()) {
      }
    }
  }
}