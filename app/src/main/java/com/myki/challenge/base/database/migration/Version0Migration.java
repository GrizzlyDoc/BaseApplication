package com.myki.challenge.base.database.migration;

import android.support.annotation.NonNull;
import io.realm.DynamicRealm;
import io.realm.RealmObjectSchema;
import timber.log.Timber;

class Version0Migration extends BaseVersionMigration implements VersionMigration {
  /************************************************
   // Version 0
   class Account
   long dateAdded //added
   ************************************************/
  @Override public void migrate(@NonNull DynamicRealm realm, long oldVersion) {
    Timber.d("Performing migration from v%d", oldVersion);
    if (oldVersion == 0) {
      RealmObjectSchema accountSchema = getObjectSchema(realm, ACCOUNT);
      if (accountSchema != null) {
        accountSchema.addField("dateAdded", long.class)
            .transform(obj -> obj.setLong("dateAdded", System.currentTimeMillis()));
      }

      Timber.d("Migration complete");
    }
  }
}