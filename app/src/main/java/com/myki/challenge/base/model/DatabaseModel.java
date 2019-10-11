package com.myki.challenge.base.model;

import android.support.annotation.NonNull;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.utils.StringUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.UUID;

public class DatabaseModel {

  @NonNull private final RealmConfiguration realmConfiguration;
  @NonNull private final PreferenceModel preferenceModel;

  public DatabaseModel(@NonNull final RealmConfiguration realmConfiguration,
      @NonNull PreferenceModel preferenceModel) {
    this.realmConfiguration = realmConfiguration;
    this.preferenceModel = preferenceModel;
  }

  @NonNull
  public void addAccount(@NonNull String nickname, @NonNull String username,
      @NonNull String password, @NonNull String url) {
    Account account = new Account().setUuid(UUID.randomUUID().toString())
        .setNickname(nickname)
        .setUsername(username)
        .setAccountName(StringUtil.urlToAccountName(url))
        .setPassword(password)
        .setUrl(url)
        .setDateAdded(System.currentTimeMillis());
    Realm realm = Realm.getInstance(realmConfiguration);

    Account[] dbAccount = new Account[1];
    realm.executeTransaction(realm1 -> {
      dbAccount[0] = realm1.copyFromRealm(realm1.copyToRealmOrUpdate(account));
    });

    realm.close();
  }
}