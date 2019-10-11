package com.myki.challenge.main.info;

import android.support.annotation.NonNull;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.performance.AsyncJobsObserver;
import com.myki.challenge.base.ui.Presenter;
import com.myki.challenge.base.ui.PresenterConfiguration;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

public class AccountInfoPresenter extends Presenter<AccountInfoView> {

  @NonNull private final PresenterConfiguration presenterConfiguration;
  @NonNull private final EventBus eventBus;
  @NonNull private final AccountInfoModel accountInfoModel;
  @NonNull private final AsyncJobsObserver asyncJobsObserver;

  AccountInfoPresenter(@NonNull PresenterConfiguration presenterConfiguration,
      @NonNull EventBus eventBus, @NonNull AccountInfoModel accountInfoModel,
      @NonNull AsyncJobsObserver asyncJobsObserver) {
    this.presenterConfiguration = presenterConfiguration;
    this.eventBus = eventBus;
    this.asyncJobsObserver = asyncJobsObserver;
    this.accountInfoModel = accountInfoModel;
  }

  @Override public void bindView(@NonNull AccountInfoView view) {
    Timber.d("Registering Event Bus");
    super.bindView(view);
    //if (!eventBus.isRegistered(this)) {
    //  eventBus.register(this);
    //}
  }

  @Override public void unbindView(@NonNull AccountInfoView view) {
    Timber.d("Unregistering Event Bus");
    super.unbindView(view);
    //eventBus.unregister(this);
  }

  public void loadData(@NonNull String uuid) {
    Account account = accountInfoModel.getAccount(uuid);
    AccountInfoView view = view();
    if(view != null){
      view.displayAccountInfo(account);
    }
  }

}
