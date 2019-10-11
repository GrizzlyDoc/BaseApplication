package com.myki.challenge.main.info;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.myki.challenge.BaseApplication;
import com.myki.challenge.BuildConfig;
import com.myki.challenge.R;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.model.AppImageLoader;
import com.myki.challenge.base.model.DatabaseModel;
import com.myki.challenge.base.performance.AsyncJobsObserver;
import com.myki.challenge.base.ui.BaseFragment;
import com.myki.challenge.base.ui.PresenterConfiguration;
import com.myki.challenge.base.utils.ViewUtil;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.socket.client.Socket;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public class AccountInfoFragment extends BaseFragment implements AccountInfoView {

  public static final String ACCOUNT_UUID = "myki.challenge.account_uuid";

  @NonNull @BindView(R.id.ad_account_info_nickname_text_view) TextView nickname;
  @Nullable @BindView(R.id.ad_account_info_username_text_view) TextView usernameView;
  @Nullable @BindView(R.id.ad_account_info_password_view) View passwordContainerView;
  @Nullable @BindView(R.id.ad_account_info_password_text_view) TextView passwordView;
  @Nullable @BindView(R.id.ad_account_info_domain_text_view) TextView domainView;
  @Nullable @BindView(R.id.account_detail_icon_image_view) ImageView iconView;
  @Nullable @BindView(R.id.account_detail_name_text_view) TextView nameView;
  @Nullable @BindView(R.id.account_detail_username_text_view) TextView usernameInfoView;
  @Nullable @BindView(R.id.account_detail_collapsing_toolbar) CollapsingToolbarLayout
      collapsingToolbarLayout;

  @Inject AccountInfoPresenter accountInfoPresenter;
  @Inject AppImageLoader imageLoader;
  @Inject EventBus eventBus;
  @Inject Realm realmUi;

  @Nullable private Unbinder unbinder;

  String accountUuid;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle bundle = getArguments();
    accountUuid = bundle.getString(ACCOUNT_UUID);

    BaseApplication.get(getContext())
        .appComponent()
        .plus(new AccountInfoFragment.AccountInfoFragmentModule())
        .inject(this);
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.account_info_view, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
    accountInfoPresenter.bindView(this);

    setupUI();
    accountInfoPresenter.loadData(accountUuid);
  }

  void setupUI() {

  }

  @Override public void onDestroyView() {
    accountInfoPresenter.unbindView(this);
    if (unbinder != null) {
      unbinder.unbind();
    }
    super.onDestroyView();
  }

  @Override public void displayAccountInfo(@NonNull Account account) {
    runOnUiThreadIfFragmentAlive(() -> {
      nickname.setText(account.getNickname());
      usernameView.setText(account.getUsername());
      passwordView.setText(account.getPassword());
      domainView.setText(account.getUrl());
      nameView.setText(account.getAccountName());
      usernameInfoView.setText(account.getUsername());

      imageLoader.downloadInto(
          BuildConfig.IMAGE_SERVER_URL + account.getUrl(),
          new BitmapImageViewTarget(iconView) {
            @Override public void onResourceReady(Bitmap bitmap, Transition transition) {
              //noinspection unchecked
              super.onResourceReady(bitmap, transition);
              if (getView() != null) {
                setProperColors();
              }
            }
          });
    });
  }

  private void setProperColors() {
    if (iconView != null) {
      Bitmap iconViewBitmap = ViewUtil.drawableToBitmap(iconView.getDrawable());
      if (iconViewBitmap != null) {
        Palette.Swatch swatch = Palette.from(iconViewBitmap).generate().getDominantSwatch();

        if (swatch != null) {
          collapsingToolbarLayout.setBackgroundColor(swatch.getRgb());
          nameView.setTextColor(ContextCompat.getColor(getContext(),
              ViewUtil.isColorDark(swatch.getRgb()) ? R.color.white : R.color.black));
          usernameInfoView.setTextColor(ContextCompat.getColor(getContext(),
              ViewUtil.isColorDark(swatch.getRgb()) ? R.color.white : R.color.black));
        }
      }
    }
  }

  @Subcomponent(modules = AccountInfoFragment.AccountInfoFragmentModule.class)
  public interface AccountInfoFragmentComponent {
    void inject(@NonNull AccountInfoFragment accountInfoFragment);
  }

  @Module public static class AccountInfoFragmentModule {
    @Provides @NonNull AccountInfoModel provideAccountInfoModel(
        @NonNull RealmConfiguration realmConfiguration, @NonNull Realm realm,
        @NonNull Socket socket,
        @NonNull DatabaseModel databaseModel) {
      return new AccountInfoModel(realmConfiguration, realm, socket, databaseModel);
    }

    @Provides @NonNull AccountInfoPresenter provideAccountInfoPresenter(@NonNull EventBus eventBus,
        @NonNull AccountInfoModel accountInfoModel, @NonNull AsyncJobsObserver asyncJobsObserver) {
      return new AccountInfoPresenter(PresenterConfiguration.builder()
          .ioScheduler(Schedulers.io())
          .computationScheduler(Schedulers.computation())
          .build(), eventBus, accountInfoModel, asyncJobsObserver);
    }
  }
}
