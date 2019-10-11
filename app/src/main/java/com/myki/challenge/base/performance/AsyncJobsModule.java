package com.myki.challenge.base.performance;

import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class AsyncJobsModule {

  @Provides @NonNull @Singleton AsyncJobsObserver provideAsyncJobsObserver() {
    return new AsyncJobsObserverImpl();
  }
}
