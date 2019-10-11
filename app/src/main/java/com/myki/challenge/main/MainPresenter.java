package com.myki.challenge.main;

import android.content.Context;
import android.support.annotation.NonNull;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.model.PreferenceModel;
import com.myki.challenge.base.other.FinishAsyncJobSubscription;
import com.myki.challenge.base.performance.AsyncJob;
import com.myki.challenge.base.performance.AsyncJobsObserver;
import com.myki.challenge.base.ui.Presenter;
import com.myki.challenge.base.ui.PresenterConfiguration;
import io.reactivex.disposables.Disposable;
import io.realm.RealmResults;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

public class MainPresenter extends Presenter<MainView> {

  @NonNull private final PresenterConfiguration presenterConfiguration;
  @NonNull private final EventBus eventBus;
  @NonNull private final MainModel mainModel;
  @NonNull private final AsyncJobsObserver asyncJobsObserver;
  @NonNull private final PreferenceModel preferenceModel;

  MainPresenter(@NonNull PresenterConfiguration presenterConfiguration,
      @NonNull EventBus eventBus, @NonNull MainModel mainModel,
      @NonNull AsyncJobsObserver asyncJobsObserver, @NonNull PreferenceModel preferenceModel) {
    this.presenterConfiguration = presenterConfiguration;
    this.eventBus = eventBus;
    this.asyncJobsObserver = asyncJobsObserver;
    this.mainModel = mainModel;
    this.preferenceModel = preferenceModel;
  }

  @Override public void bindView(@NonNull MainView view) {
    Timber.d("Registering Event Bus");
    super.bindView(view);
    //if (!eventBus.isRegistered(this)) {
    //  eventBus.register(this);
    //}
  }

  @Override public void unbindView(@NonNull MainView view) {
    Timber.d("Unregistering Event Bus");
    super.unbindView(view);
    //eventBus.unregister(this);
  }

  public void loadData(Context context) {
    RealmResults<Account> accounts = mainModel.getAccounts();
    MainView view = view();
    if (view != null) {
      view.displayAccountsSize(accounts.size());
    }
    if (accounts.size() > 0) {
      if (view != null) {
        view.loadItems(accounts);
      }
    } else {
      loadFromCSV(context);
    }
  }

  public void loadFromCSV(Context context) {
    final AsyncJob asyncJobR =
        asyncJobsObserver.asyncJobStarted("loadingAccountsFromCSV");

    final Disposable disposableR =
        mainModel.loadAccountFromCSV(context)
            .subscribeOn(presenterConfiguration.ioScheduler())
            .subscribe(() -> {
              loadData(context);
              asyncJobsObserver.asyncJobFinished(asyncJobR);
            }, error -> {
              asyncJobsObserver.asyncJobFinished(asyncJobR);
            });

    disposeOnUnbindView(disposableR,
        new FinishAsyncJobSubscription(asyncJobsObserver, asyncJobR));
  }
}
