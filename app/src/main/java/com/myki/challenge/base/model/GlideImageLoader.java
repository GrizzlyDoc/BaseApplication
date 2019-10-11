package com.myki.challenge.base.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.ObjectKey;
import com.myki.challenge.R;
import java.util.concurrent.ExecutionException;

public class GlideImageLoader implements AppImageLoader {

  private Context context;

  public GlideImageLoader(Context context) {
    this.context = context;
  }

  @Override public void downloadInto(@NonNull String url, @NonNull ImageView imageView) {
    Glide.with(context)
        .load(url)
        .apply(new RequestOptions()/*.placeholder(R.drawable.ic_placeholder)*/)
        .into(imageView);
  }

  @Override public void downloadInto(@NonNull String url, @DrawableRes int placeholder,
      @NonNull ImageView imageView) {
    Glide.with(context)
        .load(url)
        .apply(new RequestOptions().placeholder(placeholder))
        .into(imageView);
  }

  @Override public void downloadInto(@NonNull String url,
      @NonNull BitmapImageViewTarget bitmapImageViewTarget) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .apply(new RequestOptions()/*.placeholder(R.drawable.ic_placeholder)*/)
        .into(bitmapImageViewTarget);
  }

  @Override public void downloadInto(@NonNull String url, @NonNull ImageView imageView,
      @NonNull String signature) {
    Glide.with(context)
        .load(url)
        .apply(new RequestOptions().signature(new ObjectKey(signature))
            /*.placeholder(R.drawable.ic_placeholder)*/)
        .into(imageView);
  }

  @Override public void downloadInto(@NonNull String url, @DrawableRes int placeholder,
      @NonNull ImageView imageView, @NonNull String signature) {
    Glide.with(context)
        .load(url)
        .apply(new RequestOptions().signature(new ObjectKey(signature)).placeholder(placeholder))
        .into(imageView);
  }

  @Override public void downloadInto(@DrawableRes int resource, @NonNull ImageView imageView) {
    Glide.with(context).load(resource).into(imageView);
  }

  @NonNull @Override
  public Bitmap downloadBitmap(@NonNull String url, @DrawableRes int placeholder, int width,
      int height) {
    try {
      return Glide.with(context)
          .asBitmap()
          .load(url)
          .apply(new RequestOptions().centerCrop().placeholder(placeholder))
          .submit(width, height)
          .get();
    } catch (@NonNull InterruptedException | ExecutionException e) {
      BitmapDrawable bitmapDrawable =
          (BitmapDrawable) ContextCompat.getDrawable(context, placeholder);
      return bitmapDrawable.getBitmap();
    }
  }
}
