package com.grizzly.baseapplication.base.database.migration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.realm.DynamicRealm;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

class BaseVersionMigration {

  static final String USER_ITEM = "UserItem";

  @Nullable RealmObjectSchema getObjectSchema(@NonNull DynamicRealm realm, @NonNull String type) {
    RealmSchema schema = realm.getSchema();
    return schema.get(type);
  }
}
