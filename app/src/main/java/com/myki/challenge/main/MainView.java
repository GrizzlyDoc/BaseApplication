package com.myki.challenge.main;

import android.support.annotation.NonNull;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.performance.AnyThread;
import io.realm.RealmResults;

public interface MainView {

  @AnyThread void loadItems(@NonNull RealmResults<Account> accounts);
  @AnyThread void displayAccountsSize(@NonNull int size);
}
