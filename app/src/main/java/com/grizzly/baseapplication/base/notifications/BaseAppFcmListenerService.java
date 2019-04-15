package com.grizzly.baseapplication.base.notifications;

import android.support.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.grizzly.baseapplication.BaseApplication;
import com.grizzly.baseapplication.base.api.AppPresenter;
import com.grizzly.baseapplication.base.api.BaseAppView;
import com.grizzly.baseapplication.base.model.AppImageLoader;
import com.grizzly.baseapplication.base.model.DatabaseModel;
import com.grizzly.baseapplication.base.model.PreferenceModel;
import javax.inject.Inject;
import timber.log.Timber;

public class BaseAppFcmListenerService extends FirebaseMessagingService implements BaseAppView {

  @Inject AppPresenter appPresenter;
  @Inject AppImageLoader imageLoader;
  @Inject PreferenceModel preferenceModel;
  @Inject DatabaseModel databaseModel;

  @Override public void onCreate() {
    super.onCreate();
    BaseApplication.get(this).appComponent().inject(this);
  }

  @Override public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    try {
      appPresenter.bindView(this);
    } catch (IllegalStateException e) {
      Timber.w("Service view didn't unbind!");
    }

    Timber.v("Message Title: %s", remoteMessage.getFrom());
    Timber.v("Message PeerSyncData: %s", remoteMessage.getData());
    Timber.v("remoteMessage Title: %s", remoteMessage.getFrom());
    Timber.v("remoteMessage PeerSyncData: %s", remoteMessage.getData());

  }

  @Override public void onDestroy() {
    super.onDestroy();
    Timber.d("onDestroy");
    appPresenter.unbindView(this);
  }

  @Override public void displayNotification(@NonNull String title, @NonNull String message,
      int notificationId) {

  }
}