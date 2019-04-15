package com.grizzly.baseapplication.base.model;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;
import javax.inject.Singleton;

@Module public class ModelsModule {

  @Provides @NonNull @Singleton
  public PreferenceModel providesPreferenceModel(@NonNull SharedPreferences sharedPreferences) {
    return new PreferenceModel(sharedPreferences);
  }

  @Provides @NonNull @Singleton DatabaseModel provideDatabaseModel(
      @NonNull RealmConfiguration realmConfiguration, @NonNull PreferenceModel preferenceModel) {
    return new DatabaseModel(realmConfiguration, preferenceModel);
  }
}
