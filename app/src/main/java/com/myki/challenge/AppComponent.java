package com.myki.challenge;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.myki.challenge.base.api.ApiModule;
import com.myki.challenge.base.api.AppService;
import com.myki.challenge.base.database.DatabaseModule;
import com.myki.challenge.base.database.migration.MigrationsModule;
import com.myki.challenge.base.model.ModelsModule;
import com.myki.challenge.base.model.PreferenceModel;
import com.myki.challenge.base.network.NetworkModule;
import com.myki.challenge.base.network.OkHttpInterceptorsModule;
import com.myki.challenge.base.notifications.BaseAppFcmListenerService;
import com.myki.challenge.base.notifications.NotificationReceiver;
import com.myki.challenge.base.notifications.RegistrationIntentService;
import com.myki.challenge.base.performance.AsyncJobsModule;
import com.myki.challenge.base.performance.AsyncJobsObserver;
import com.myki.challenge.main.MainFragment;
import com.myki.challenge.main.info.AccountInfoFragment;
import dagger.Component;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.socket.client.Socket;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    AppModule.class, NetworkModule.class, ApiModule.class, MigrationsModule.class,
    DatabaseModule.class, OkHttpInterceptorsModule.class, AsyncJobsModule.class, ModelsModule.class
}) public interface AppComponent {

  @NonNull SharedPreferences sharedPreferences();

  @NonNull AsyncJobsObserver asyncJobsObserver();

  @NonNull Realm realm();

  @NonNull RealmConfiguration realmConfiguration();

  @NonNull Socket socket();

  @NonNull PreferenceModel preferenceModel();

  void inject(@NonNull AppService appService);

  void inject(@NonNull RegistrationIntentService registrationIntentService);

  void inject(@NonNull BaseAppFcmListenerService baseAppFcmListenerService);

  void inject(@NonNull NotificationReceiver notificationReceiver);

  void inject(@NonNull MainActivity mainActivity);

  @NonNull MainFragment.MainFragmentComponent plus(
      @NonNull MainFragment.MainFragmentModule mainFragmentModule);

  @NonNull AccountInfoFragment.AccountInfoFragmentComponent plus(
      @NonNull AccountInfoFragment.AccountInfoFragmentModule accountInfoFragmentModule);
}
