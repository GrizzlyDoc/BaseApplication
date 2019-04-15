package com.grizzly.baseapplication.base.ui;

import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;
import io.reactivex.Scheduler;

@AutoValue public abstract class PresenterConfiguration {

  @NonNull public static Builder builder() {
    return new AutoValue_PresenterConfiguration.Builder();
  }

  @NonNull public abstract Scheduler ioScheduler();

  @NonNull public abstract Scheduler computationScheduler();

  @AutoValue.Builder public static abstract class Builder {

    @NonNull public abstract Builder ioScheduler(@NonNull Scheduler ioScheduler);

    @NonNull public abstract Builder computationScheduler(@NonNull Scheduler computationScheduler);

    @NonNull public abstract PresenterConfiguration build();
  }
}
