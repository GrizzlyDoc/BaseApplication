package com.myki.challenge.base.model;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public interface AppImageLoader {

  void downloadInto(@NonNull String url, @NonNull ImageView imageView);

  void downloadInto(@NonNull String url, @DrawableRes int placeholder,
      @NonNull ImageView imageView);

  void downloadInto(@NonNull String url, @NonNull BitmapImageViewTarget bitmapImageViewTarget);

  void downloadInto(@NonNull String url, @NonNull ImageView imageView, @NonNull String signature);

  void downloadInto(@NonNull String url, @DrawableRes int placeholder, @NonNull ImageView imageView,
      @NonNull String signature);

  void downloadInto(@DrawableRes int resource, @NonNull ImageView imageView);

  @NonNull Bitmap downloadBitmap(@NonNull String url, @DrawableRes int placeholder, int width,
      int height);
}