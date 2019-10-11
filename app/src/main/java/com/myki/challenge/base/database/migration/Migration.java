package com.myki.challenge.base.database.migration;

import android.support.annotation.NonNull;
import dagger.Reusable;
import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

@Reusable public class Migration implements RealmMigration {

  public static final long SCHEMA_VERSION = 1;

  private Map<Integer, Provider<VersionMigration>> versionMigrations;

  @Inject public Migration(Map<Integer, Provider<VersionMigration>> versionMigrations) {
    this.versionMigrations = versionMigrations;
  }

  @Override
  public void migrate(@NonNull final DynamicRealm realm, long oldVersion, long newVersion) {
    for (int i = (int) oldVersion; i < newVersion; i++) {
      final Provider<VersionMigration> provider = versionMigrations.get(i);
      if (provider != null) {
        VersionMigration versionMigration = provider.get();
        versionMigration.migrate(realm, i);
      }
    }
  }

  public int hashCode() {
    return Migration.class.hashCode();
  }

  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    return object instanceof Migration;
  }
}
