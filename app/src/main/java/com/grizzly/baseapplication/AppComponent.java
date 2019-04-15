package com.grizzly.baseapplication;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.grizzly.baseapplication.base.api.ApiModule;
import com.grizzly.baseapplication.base.api.AppService;
import com.grizzly.baseapplication.base.database.DatabaseModule;
import com.grizzly.baseapplication.base.database.migration.MigrationsModule;
import com.grizzly.baseapplication.base.model.ModelsModule;
import com.grizzly.baseapplication.base.model.PreferenceModel;
import com.grizzly.baseapplication.base.network.NetworkModule;
import com.grizzly.baseapplication.base.network.OkHttpInterceptorsModule;
import com.grizzly.baseapplication.base.notifications.BaseAppFcmListenerService;
import com.grizzly.baseapplication.base.notifications.NotificationReceiver;
import com.grizzly.baseapplication.base.notifications.RegistrationIntentService;
import com.grizzly.baseapplication.base.performance.AsyncJobsModule;
import com.grizzly.baseapplication.base.performance.AsyncJobsObserver;
import dagger.Component;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    AppModule.class, NetworkModule.class, ApiModule.class, MigrationsModule.class,
    DatabaseModule.class, OkHttpInterceptorsModule.class, AsyncJobsModule.class, ModelsModule.class
}) public interface AppComponent {

  // Provide SharedPreferences from the real app to the tests without need in injection to the test.
  @NonNull SharedPreferences sharedPreferences();

  // Provide AsyncJobObserver from the real app to the tests without need in injection to the test.
  @NonNull AsyncJobsObserver asyncJobsObserver();

  // Provide Realm database from the real app to the tests without need in injection to the test.
  @NonNull Realm realm();

  // Provide Realm configuration from the real app to the tests without need in injection to the test.
  @NonNull RealmConfiguration realmConfiguration();

  @NonNull PreferenceModel preferenceModel();

  void inject(@NonNull AppService appService);

  void inject(@NonNull RegistrationIntentService registrationIntentService);

  void inject(@NonNull BaseAppFcmListenerService baseAppFcmListenerService);

  void inject(@NonNull NotificationReceiver notificationReceiver);

  //void inject(@NonNull MykiAccessibilityService mykiAccessibilityService);

  void inject(@NonNull MainActivity mainActivity);

  //@NonNull SplashFragment.SplashFragmentComponent plus(
  //    @NonNull SplashFragment.SplashFragmentModule splashFragmentModule);

  //void inject(@NonNull SearchAccountsActivity searchAccountsActivity);

}
