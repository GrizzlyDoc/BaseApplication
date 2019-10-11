package com.myki.challenge.main.list;

import android.support.annotation.NonNull;
import android.widget.Filter;

class AccountsFilter extends Filter {
  private final AccountsAdapter adapter;

  AccountsFilter(AccountsAdapter adapter) {
    super();
    this.adapter = adapter;
  }

  @NonNull @Override protected FilterResults performFiltering(CharSequence constraint) {
    return new FilterResults();
  }

  @Override protected void publishResults(@NonNull CharSequence constraint, FilterResults results) {
    adapter.filterResults(constraint.toString());
  }
}