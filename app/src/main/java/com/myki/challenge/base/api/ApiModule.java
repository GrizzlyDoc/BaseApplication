package com.myki.challenge.base.api;

import android.content.Context;
import android.support.annotation.NonNull;
import com.myki.challenge.base.model.DatabaseModel;
import com.myki.challenge.base.model.PreferenceModel;
import com.myki.challenge.base.performance.AsyncJobsObserver;
import com.myki.challenge.base.ui.PresenterConfiguration;
import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmConfiguration;
import io.socket.client.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

@Module public class ApiModule {

  @NonNull private final URI serverURL;

  public ApiModule(@NonNull URI serverURL) throws URISyntaxException {
    this.serverURL = serverURL;
  }

  @NonNull @Provides @Singleton Socket provideSocket(@NonNull Context context,
      @NonNull RealmConfiguration realmConfiguration) {
    return AppSocket.initSocket(context, serverURL, realmConfiguration);
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
