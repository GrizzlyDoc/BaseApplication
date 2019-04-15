package com.grizzly.baseapplication.base.database.migration;

import android.support.annotation.NonNull;
import io.realm.DynamicRealm;

public interface VersionMigration {
  void migrate(@NonNull final DynamicRealm realm, long oldVersion);
}