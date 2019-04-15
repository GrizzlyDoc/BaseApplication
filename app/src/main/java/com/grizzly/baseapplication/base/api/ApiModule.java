package com.grizzly.baseapplication.base.api;

import android.content.Context;
import android.support.annotation.NonNull;
import com.grizzly.baseapplication.base.model.DatabaseModel;
import com.grizzly.baseapplication.base.model.PreferenceModel;
import com.grizzly.baseapplication.base.performance.AsyncJobsObserver;
import com.grizzly.baseapplication.base.ui.PresenterConfiguration;
import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

@Module public class ApiModule {

  @NonNull private final URI serverURL;

  public ApiModule(@NonNull URI serverURL) throws URISyntaxException {
    this.serverURL = serverURL;
  }

  @Provides @NonNull @Singleton
  public AppPresenter provideAppPresenter(@NonNull EventBus eventbus,
      @NonNull Context context, @NonNull PreferenceModel preferenceModel,
      @NonNull DatabaseModel databaseModel, @NonNull
      AsyncJobsObserver asyncJobsObserver) {
    return new AppPresenter(eventbus, context,
        preferenceModel,databaseModel,asyncJobsObserver,
        PresenterConfiguration.builder()
        .ioScheduler(Schedulers.io())
        .computationScheduler(Schedulers.computation())
        .build());
  }
}
