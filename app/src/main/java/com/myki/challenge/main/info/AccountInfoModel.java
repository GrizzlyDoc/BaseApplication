package com.myki.challenge.main.info;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.model.DatabaseModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.socket.client.Socket;

public class AccountInfoModel {
  @NonNull private final RealmConfiguration realmConfiguration;
  @NonNull private final Realm realmUi;
  @NonNull private final Socket socket;
  @NonNull private final DatabaseModel databaseModel;

  AccountInfoModel(@NonNull RealmConfiguration realmConfiguration, @NonNull Realm realmUi,
      @NonNull Socket socket, @NonNull DatabaseModel databaseModel) {
    this.realmConfiguration = realmConfiguration;
    this.realmUi = realmUi;
    this.socket = socket;
    this.databaseModel = databaseModel;
  }

  @UiThread Account getAccount(@NonNull String uuid) {
    return realmUi.where(Account.class).equalTo("uuid", uuid).findFirst();
  }
}
