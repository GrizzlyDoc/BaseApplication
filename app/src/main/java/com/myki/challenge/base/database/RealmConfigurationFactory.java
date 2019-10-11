package com.myki.challenge.base.database;

import android.support.annotation.NonNull;
import com.myki.challenge.base.database.migration.Migration;
import com.myki.challenge.base.database.migration.VersionMigration;
import com.myki.challenge.base.model.PreferenceModel;
import com.myki.challenge.base.utils.StringUtil;
import io.realm.RealmConfiguration;
import java.util.Map;
import javax.inject.Provider;

public class RealmConfigurationFactory {

  @NonNull private final Map<Integer, Provider<VersionMigration>> versionMigrations;
  @NonNull private final PreferenceModel preferenceModel;

  RealmConfigurationFactory(@NonNull Map<Integer, Provider<VersionMigration>> versionMigrations,
      @NonNull PreferenceModel preferenceModel) {
    this.versionMigrations = versionMigrations;
    this.preferenceModel = preferenceModel;
  }

  public RealmConfiguration getRealmConfiguration(@NonNull String companyUuid) {
    String realmKi = preferenceModel.getRealmKey();
    if (StringUtil.isNullOrEmpty(realmKi)) {
      preferenceModel.setRealmKey(realmKi);
    }

    RealmConfiguration realmConfig;

    while (true) {
      try {
        realmConfig = new RealmConfiguration.Builder().name(companyUuid)
            .schemaVersion(Migration.SCHEMA_VERSION)
            .migration(new Migration(versionMigrations))
            .build();
        return realmConfig;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}