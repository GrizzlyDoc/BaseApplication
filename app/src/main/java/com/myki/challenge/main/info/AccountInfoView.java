package com.myki.challenge.main.info;

import android.support.annotation.NonNull;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.performance.AnyThread;

public interface AccountInfoView {

  @AnyThread void displayAccountInfo(@NonNull Account account);

}
