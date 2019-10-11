package com.myki.challenge;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.myki.challenge.base.api.ApiModule;
import com.myki.challenge.base.api.AppService;
import com.myki.challenge.base.model.PreferenceModel;
import io.realm.Realm;
import java.net.URI;
import java.net.URISyntaxException;
import timber.log.Timber;

public class BaseApplication extends Application {

  private AppComponent appComponent;
  private AppService appService;

  // Prevent need in a singleton (global) reference to the application object.
  @NonNull public static BaseApplication get(@NonNull Context context) {
    return (BaseApplication) context.getApplicationContext();
  }

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      AndroidDevMetrics.initWith(this);
      Timber.plant(new Timber.DebugTree());
    }

    Realm.init(this);

    try {
      appComponent = prepareAppComponent().build();

      PreferenceModel preferenceModel = appComponent.preferenceModel();

    } catch (URISyntaxException e) {
      Timber.e(e, "Please correct the server url in the build.gradle file!");
    }
  }

  @NonNull protected DaggerAppComponent.Builder prepareAppComponent() throws URISyntaxException {
    return DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .apiModule(new ApiModule(new URI(BuildConfig.SERVER_URL)));
  }

  @NonNull public AppComponent appComponent() {
    return appComponent;
  }

  public void stopService() {
    if (appService != null) appService.stopSelf();
  }

  public void startService() {
    Intent intent = new Intent(this, AppService.class);
    startService(intent);
  }

  public void setCurrentService(@NonNull AppService appService) {
    this.appService = appService;
  }

}
