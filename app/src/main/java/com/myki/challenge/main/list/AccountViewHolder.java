package com.myki.challenge.main.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.myki.challenge.BuildConfig;
import com.myki.challenge.R;
import com.myki.challenge.base.database.entities.Account;
import com.myki.challenge.base.model.AppImageLoader;
import com.myki.challenge.base.utils.StringUtil;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountViewHolder extends RecyclerView.ViewHolder {

  @NonNull private final Context context;
  @NonNull private final AppImageLoader imageLoader;

  @Nullable @BindView(R.id.account_item_view) LinearLayout accountRowLayout;
  @Nullable @BindView(R.id.account_item_image_view) CircleImageView iconImageView;
  @Nullable @BindView(R.id.account_item_title_text_view) TextView titleTextView;
  @Nullable @BindView(R.id.account_item_username_text_view) TextView usernameTextView;
  @Nullable @BindView(R.id.account_item_divider) View divider;

  AccountViewHolder(@NonNull View itemView, @NonNull Context context,
      @NonNull AppImageLoader imageLoader) {
    super(itemView);
    this.context = context;
    this.imageLoader = imageLoader;
    ButterKnife.bind(this, itemView);
  }

  public void bind(@NonNull Account userAccount) {
    imageLoader.downloadInto(BuildConfig.IMAGE_SERVER_URL + StringUtil.cleanUrl(userAccount.getUrl()),
        R.drawable.ic_user_placeholder, iconImageView);

    usernameTextView.setText(userAccount.getUsername().isEmpty() ? "-" : userAccount.getUsername());
    titleTextView.setText(userAccount.getAccountName());
  }
}
