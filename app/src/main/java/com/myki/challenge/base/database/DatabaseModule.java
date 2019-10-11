package com.myki.challenge.base.database;

import android.support.annotation.NonNull;
import android.util.Base64;
import com.myki.challenge.base.database.migration.Migration;
import com.myki.challenge.base.database.migration.VersionMigration;
import com.myki.challenge.base.model.PreferenceModel;
import com.myki.challenge.base.utils.StringUtil;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.Map;
import javax.inject.Provider;
import javax.inject.Singleton;
import timber.log.Timber;

@Module public class DatabaseModule {

  @NonNull @Provides @Singleton RealmConfiguration provideRealmConfiguration(
      @NonNull Map<Integer, Provider<VersionMigration>> versionMigrations,
      @NonNull PreferenceModel preferenceModel) {
    String realmKi = preferenceModel.getRealmKey();
    if (StringUtil.isNullOrEmpty(realmKi)) {
      preferenceModel.setRealmKey(realmKi);
    }

    RealmConfiguration realmConfiguration = null;

    String finalRealmKi = realmKi;
    while (realmConfiguration == null) {
      try {
        realmConfiguration =
            new RealmConfiguration.Builder().schemaVersion(Migration.SCHEMA_VERSION)
                .migration(new Migration(versionMigrations))
                .build();
      } catch (Exception e) {
        Timber.e(e, "Couldn't decrypt realmKi");

        try {
          Thread.sleep(300);
        } catch (InterruptedException e1) {
          Timber.e(e1, "Couldn't sleep");
        }
      }
    }

    return realmConfiguration;
  }

  @Provides @Singleton Realm provideRealm(@NonNull RealmConfiguration realmConfiguration) {
    return Realm.getInstance(realmConfiguration);
  }

  @NonNull @Provides @Singleton RealmConfigurationFactory provideRealmConfigurationFactory(
      @NonNull Map<Integer, Provider<VersionMigration>> versionMigrations,
      @NonNull PreferenceModel preferenceModel) {
    return new RealmConfigurationFactory(versionMigrations, preferenceModel);
  }
}