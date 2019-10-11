package com.myki.challenge.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.model.DatabaseModel;
import com.myki.challenge.base.model.PreferenceModel;
import io.reactivex.Completable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.socket.client.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

public class MainModel {

  @NonNull private final RealmConfiguration realmConfiguration;
  @NonNull private final Realm realmUi;
  @NonNull private final Socket socket;
  @NonNull private final PreferenceModel preferenceModel;
  @NonNull private final DatabaseModel databaseModel;

  MainModel(@NonNull RealmConfiguration realmConfiguration, @NonNull Realm realmUi,
      @NonNull Socket socket, @NonNull PreferenceModel preferenceModel,
      @NonNull DatabaseModel databaseModel) {
    this.realmConfiguration = realmConfiguration;
    this.realmUi = realmUi;
    this.socket = socket;
    this.preferenceModel = preferenceModel;
    this.databaseModel = databaseModel;
  }

  @UiThread RealmResults<Account> getAccounts() {
    return realmUi.where(Account.class).findAll();
  }

  void addAccount(@NonNull String nickname, @NonNull String username, @NonNull String passsword, @NonNull String url) {
    databaseModel.addAccount(nickname, username, passsword, url);
  }

  public Completable loadAccountFromCSV(@NonNull Context context) {
    return Completable.create(eC -> {
      AssetManager assetManager = context.getAssets();
      InputStream is = null;
      try {
        is = assetManager.open("CSV/2000_Accounts.csv");
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        eC.onError(e);
      }
      BufferedReader reader;
      reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

      String line;
      StringTokenizer st;
      try {
        int count = 0;
        while ((line = reader.readLine()) != null) {
          if (count > 0) {
            st = new StringTokenizer(line, ",");
            addAccount(st.nextToken().trim(), st.nextToken().trim(),
                st.nextToken().trim(),
                st.nextToken().trim());
          }
          count++;
        }
      } catch (IOException e) {
        e.printStackTrace();
        eC.onError(e);
      }

      eC.onComplete();
    });
  }

}
