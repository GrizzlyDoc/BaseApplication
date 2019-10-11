package com.myki.challenge.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.myki.challenge.BaseApplication;
import com.myki.challenge.R;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.model.AppImageLoader;
import com.myki.challenge.base.model.DatabaseModel;
import com.myki.challenge.base.model.PreferenceModel;
import com.myki.challenge.base.performance.AsyncJobsObserver;
import com.myki.challenge.base.ui.BaseFragment;
import com.myki.challenge.base.ui.PresenterConfiguration;
import com.myki.challenge.base.utils.MyLinearLayoutManager;
import com.myki.challenge.main.list.AccountsAdapter;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.socket.client.Socket;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MainFragment extends BaseFragment implements MainView {

  @NonNull @BindView(R.id.main_fragment_title) TextView title;
  @NonNull @BindView(R.id.main_fragment_accounts_size) TextView accountsSize;
  @NonNull @BindView(R.id.add_item_fab) FloatingActionButton addItemFab;
  @Nullable @BindView(R.id.accounts_content_recycler) RecyclerView contentUiRecyclerView;

  @Inject MainPresenter mainPresenter;
  @Inject AppImageLoader imageLoader;
  @Inject EventBus eventBus;
  @Inject Realm realmUi;

  @Nullable private Unbinder unbinder;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BaseApplication.get(getContext())
        .appComponent()
        .plus(new MainFragment.MainFragmentModule())
        .inject(this);
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.main_view, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
    mainPresenter.bindView(this);

    setupUI();

    mainPresenter.loadData(getContext());
  }

  void setupUI() {
    contentUiRecyclerView.setLayoutManager(
        new MyLinearLayoutManager(getContext(), VERTICAL, false));
    contentUiRecyclerView.setHasFixedSize(true);

    addItemFab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_white));
    addItemFab.setOnClickListener(v1 -> {

    });
  }

  @Override public void onDestroyView() {
    mainPresenter.unbindView(this);
    if (unbinder != null) {
      unbinder.unbind();
    }
    super.onDestroyView();
  }

  AccountsAdapter accountsAdapter;

  @Override public void loadItems(@NonNull RealmResults<Account> accounts) {
    accountsAdapter = new AccountsAdapter(accounts,
        getActivity().getLayoutInflater(), getContext(), imageLoader, realmUi, eventBus);
    accountsAdapter.setHasStableIds(true);
    runOnUiThreadIfFragmentAlive(() -> {
      contentUiRecyclerView.setAdapter(accountsAdapter);
    });
  }

  @Override public void displayAccountsSize(@NonNull int size) {
    runOnUiThreadIfFragmentAlive(() -> {
      accountsSize.setText(size + " items");
    });
  }

  @Subcomponent(modules = MainFragment.MainFragmentModule.class)
  public interface MainFragmentComponent {
    void inject(@NonNull MainFragment mainFragment);
  }

  @Module public static class MainFragmentModule {
    @Provides @NonNull MainModel provideMainModel(
        @NonNull RealmConfiguration realmConfiguration, @NonNull Realm realm,
        @NonNull Socket socket, @NonNull PreferenceModel preferenceModel,
        @NonNull DatabaseModel databaseModel) {
      return new MainModel(realmConfiguration, realm, socket, preferenceModel, databaseModel);
    }

    @Provides @NonNull MainPresenter provideMainPresenter(@NonNull EventBus eventBus,
        @NonNull MainModel mainModel, @NonNull AsyncJobsObserver asyncJobsObserver,
        @NonNull PreferenceModel preferenceModel) {
      return new MainPresenter(PresenterConfiguration.builder()
          .ioScheduler(Schedulers.io())
          .computationScheduler(Schedulers.computation())
          .build(), eventBus, mainModel, asyncJobsObserver, preferenceModel);
    }
  }
}
