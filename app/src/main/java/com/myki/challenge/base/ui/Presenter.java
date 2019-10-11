package com.myki.challenge.base.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Base presenter implementation.
 *
 * @param <V> view.
 */
public class Presenter<V> {

  @NonNull private final CompositeDisposable disposablesToDisposeOnUnbindView =
      new CompositeDisposable();

  @Nullable private volatile V view;

  @CallSuper public void bindView(@NonNull V view) {
    final V previousView = this.view;

    if (previousView != null) {
      throw new IllegalStateException(
          "Previous view is not unbounded! previousView = " + previousView);
    }

    this.view = view;
  }

  @Nullable protected V view() {
    return view;
  }

  protected final void disposeOnUnbindView(@NonNull Disposable disposable,
      @NonNull Disposable... disposables) {
    disposablesToDisposeOnUnbindView.add(disposable);

    for (Disposable d : disposables) {
      disposablesToDisposeOnUnbindView.add(d);
    }
  }

  @CallSuper public void unbindView(@NonNull V view) {
    final V previousView = this.view;

    if (previousView == view) {
      this.view = null;
    } else {
      Timber.v("Unexpected view! previousView = " + previousView + ", view to unbind = " + view);

      //      throw new IllegalStateException(
      //          "Unexpected view! previousView = " + previousView + ", view to unbind = " + view);
    }

    // Dispose all disposables that need to be disposed in this lifecycle state.
    disposablesToDisposeOnUnbindView.clear();
  }
}