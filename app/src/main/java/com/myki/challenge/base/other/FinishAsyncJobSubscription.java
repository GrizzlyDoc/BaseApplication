package com.myki.challenge.base.other;

import android.support.annotation.NonNull;
import com.myki.challenge.base.performance.AsyncJob;
import com.myki.challenge.base.performance.AsyncJobsObserver;

public class FinishAsyncJobSubscription extends DisposableSubscription {

  @SuppressWarnings("PMD.EmptyCatchBlock")
  public FinishAsyncJobSubscription(@NonNull AsyncJobsObserver asyncJobsObserver,
      @NonNull AsyncJob asyncJob) {
    super(() -> {
      try {
        asyncJobsObserver.asyncJobFinished(asyncJob);
      } catch (IllegalArgumentException possible) {
        // Do nothing, async job was probably already finished normally.
      }
    });
  }
}
