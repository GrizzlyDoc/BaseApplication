package com.myki.challenge.main.list;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.myki.challenge.R;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.model.AppImageLoader;
import com.myki.challenge.base.utils.ViewUtil;
import com.myki.challenge.main.info.AccountInfoFragment;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import org.greenrobot.eventbus.EventBus;

import static com.myki.challenge.main.info.AccountInfoFragment.ACCOUNT_UUID;

public class AccountsAdapter extends RealmRecyclerViewAdapter<Account, AccountViewHolder>
    implements Filterable {

  @NonNull private final LayoutInflater layoutInflater;
  @NonNull private final Context context;
  @NonNull private final AppImageLoader imageLoader;
  @NonNull private final Realm realmUi;
  @NonNull private final EventBus eventBus;

  private long itemLastClickTime = 0;

  @NonNull private OrderedRealmCollection<Account> accounts;

  public AccountsAdapter(@NonNull OrderedRealmCollection<Account> accounts,
      @NonNull LayoutInflater layoutInflater, @NonNull Context context,
      @NonNull AppImageLoader imageLoader, @NonNull Realm realmUi, @NonNull EventBus eventBus) {
    super(accounts, true);
    this.accounts = accounts;
    this.layoutInflater = layoutInflater;
    this.imageLoader = imageLoader;
    this.context = context;
    this.realmUi = realmUi;
    this.eventBus = eventBus;
  }

  @Override @NonNull
  public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new AccountViewHolder(layoutInflater.inflate(R.layout.account_item, parent, false),
        context, imageLoader);
  }

  @Override public void onBindViewHolder(@NonNull AccountViewHolder viewHolder, int position) {
    final Account account = accounts.get(position);
    viewHolder.bind(account);

    viewHolder.itemView.setOnClickListener(view -> fragmentJump(account));
  }

  public int getSize() {
    return accounts.size();
  }

  public int filterResults(@Nullable String text) {

    return 0;
  }

  @NonNull public Filter getFilter() {
    return new AccountsFilter(this);
  }

  private void fragmentJump(@Nullable Account selectedAccount) {
    // mis-clicking prevention, using threshold of 1000 ms
    if (SystemClock.elapsedRealtime() - itemLastClickTime < 1000) {
      return;
    }
    itemLastClickTime = SystemClock.elapsedRealtime();

    if (selectedAccount != null) {
      Account dbAccount = realmUi.where(Account.class)
          .equalTo("uuid", selectedAccount.getUuid())
          .findFirst();
      if (dbAccount != null) {
        Account selectedAccountCopy = realmUi.copyFromRealm(dbAccount);
        if (selectedAccountCopy != null) {
          AccountInfoFragment accountInfoFragment = new AccountInfoFragment();
          Bundle args = new Bundle();
          args.putString(ACCOUNT_UUID, selectedAccountCopy.getUuid());
          accountInfoFragment.setArguments(args);

          ViewUtil.switchContent(context, R.id.main_content, accountInfoFragment);
        }
      }
    }
  }
}
