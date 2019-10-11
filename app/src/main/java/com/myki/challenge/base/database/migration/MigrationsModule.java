package com.myki.challenge.base.database.migration;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

@Module public class MigrationsModule {

  @Provides @IntoMap @IntKey(0) static VersionMigration provideVersion0Migration() {
    return new Version0Migration();
  }
}