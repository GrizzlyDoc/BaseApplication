package com.grizzly.baseapplication.base.api;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import com.grizzly.baseapplication.BaseApplication;
import com.grizzly.baseapplication.base.model.AppImageLoader;
import javax.inject.Inject;
import timber.log.Timber;

public class AppService extends IntentService implements BaseAppView {

  private static final int NOTIFICATION_ID = 234897;
  private static final int JOB_ID = 1001;

  @Inject AppPresenter appPresenter;
  @Inject AppImageLoader imageLoader;

  private boolean portalConnected;

  public AppService() {
    super("AppService");
  }

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public AppService(String name) {
    super(name);
  }

  @Override public void onCreate() {
    super.onCreate();
    BaseApplication.get(this).appComponent().inject(this);
    BaseApplication.get(this).setCurrentService(this);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Timber.d("onStartCommand");
    try {
      appPresenter.bindView(this);
    } catch (IllegalStateException e) {
      Timber.w("Service view didn't unbind!");
    }
    return START_REDELIVER_INTENT;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    Timber.d("onDestroy");
    appPresenter.unbindView(this);
  }
  @Override public IBinder onBind(Intent intent) {
    return null;
  }


  @Override protected void onHandleIntent(Intent intent) {
    Timber.d("onHandleIntent");
  }

  @Override public void displayNotification(@NonNull String title, @NonNull String message,
      int notificationId) {

  }
}
