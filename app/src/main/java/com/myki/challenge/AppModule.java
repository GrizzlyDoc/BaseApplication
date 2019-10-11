package com.myki.challenge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.myki.challenge.base.model.AppImageLoader;
import com.myki.challenge.base.model.GlideImageLoader;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

@Module public class AppModule {

  public static final String MAIN_THREAD_HANDLER = "main_thread_handler";
  public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
  @NonNull private final Application application;

  public AppModule(@NonNull Application application) {
    this.application = application;
  }

  @Provides @NonNull @Singleton Application provideApplication() {
    return application;
  }

  @Provides @NonNull @Singleton Context provideContext() {
    return application.getBaseContext();
  }

  @Provides @NonNull @Singleton SharedPreferences provideSharedPreferences() {
    return application.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
  }

  @Provides @NonNull @Named(MAIN_THREAD_HANDLER) @Singleton Handler provideMainThreadHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides @NonNull @Singleton GlideImageLoader provideGlide(@NonNull Application application) {
    return new GlideImageLoader(application);
  }

  @Provides @NonNull @Singleton AppImageLoader provideImageLoader() {
    return new GlideImageLoader(application);
  }

  @Provides @NonNull @Singleton EventBus provideEventBus() {
    return EventBus.builder()
        .logNoSubscriberMessages(BuildConfig.DEBUG)
        .sendNoSubscriberEvent(BuildConfig.DEBUG)
        .build();
  }
}
