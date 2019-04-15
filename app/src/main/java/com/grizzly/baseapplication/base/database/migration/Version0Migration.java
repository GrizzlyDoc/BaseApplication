package com.grizzly.baseapplication.base.database.migration;

import android.support.annotation.NonNull;
import io.realm.DynamicRealm;
import io.realm.RealmObjectSchema;
import timber.log.Timber;

class Version0Migration extends BaseVersionMigration implements VersionMigration {
  /************************************************
   // Version 0
   class UserItem
   String uuid //added
   ************************************************/
  @Override public void migrate(@NonNull DynamicRealm realm, long oldVersion) {
    //Timber.d("Performing migration from v%d", oldVersion);
    //if (oldVersion == 0) {
    //  RealmObjectSchema userItemSchema = getObjectSchema(realm, USER_ITEM);
    //  if (userItemSchema != null) {
    //    userItemSchema.addField("uuid", String.class).transform(obj -> obj.setString("uuid", ""));
    //  }
    //
    //  Timber.d("Migration complete");
    //}
  }
}