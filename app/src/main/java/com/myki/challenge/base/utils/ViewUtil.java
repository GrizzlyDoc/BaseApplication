package com.myki.challenge.base.utils;

import android.animation.Animator;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.myki.challenge.R;
import com.myki.challenge.base.ui.BaseActivity;
import com.myki.challenge.base.ui.BaseFragment;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.saket.bettermovementmethod.BetterLinkMovementMethod;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ViewUtil {

  @NonNull public static Drawable tintMyDrawable(@NonNull Drawable drawable, @ColorInt int color) {
    drawable = DrawableCompat.wrap(drawable);
    DrawableCompat.setTint(drawable, color);
    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
    return drawable;
  }

  public static boolean isRTL() {
    return isRTL(Locale.getDefault());
  }

  public static boolean isRTL(@NonNull Locale locale) {
    final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
    return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT
        || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
  }

  public static void switchContent(@NonNull Context context, int id,
      @NonNull BaseFragment fragment) {
    if (context instanceof BaseActivity) {
      BaseActivity baseActivity = (BaseActivity) context;
      baseActivity.switchContent(id, fragment);
    }
  }

  // Load drawable dynamically from country code
  @DrawableRes public static int getFlagResId(@NonNull Context context,
      @NonNull String countryCode) {
    String drawableName = countryCode.toLowerCase() + "_flag";
    return context.getResources()
        .getIdentifier(drawableName.toLowerCase(Locale.ENGLISH), "mipmap",
            context.getPackageName());
  }

  @StringRes public static int getStringResId(@NonNull Context context, @NonNull String code) {
    return context.getResources().getIdentifier(code, "string", context.getPackageName());
  }

  public static void createBoldText(@NonNull AssetManager assetManager, @NonNull String fontPath,
      @NonNull TextView textView, @NonNull String string) {
    final Pattern pattern = Pattern.compile("<b>(.+?)</b>");
    final Matcher matcher = pattern.matcher(string);
    if (matcher.find()) {
      String matchedStr = matcher.group().trim();
      String cleanStr = StringUtil.cleanHtmlString(string);

      matchedStr = matchedStr.substring(3, matchedStr.length() - 4).trim();

      int start = cleanStr.indexOf(matchedStr);
      int end = start + matchedStr.length();

      SpannableString spannableString = new SpannableString(cleanStr);
      spannableString.setSpan(Typeface.createFromAsset(assetManager, fontPath), start, end,
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end,
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      textView.setText(spannableString);
    } else {
      textView.setText(string);
    }
  }

  public static void createColoredText(@NonNull Context context, @NonNull TextView textView,
      @NonNull String string, int color) {
    createColoredText(context, textView, string, color, false, "fonts/AvenirNext-Bold.ttf");
  }

  public static void createColoredText(@NonNull Context context, @NonNull TextView textView,
      @NonNull String string, int color, boolean isBold) {
    createColoredText(context, textView, string, color, isBold, "fonts/AvenirNext-Bold.ttf");
  }

  public static void createColoredText(@NonNull Context context, @NonNull TextView textView,
      @NonNull String string, int color, boolean isBold, @NonNull String fontPath) {
    final Pattern pattern = Pattern.compile("<b>(.+?)</b>");
    final Matcher matcher = pattern.matcher(string);
    if (matcher.find()) {
      String matchedStr = matcher.group().trim();
      String cleanStr = StringUtil.cleanHtmlString(string);

      matchedStr = matchedStr.substring(3, matchedStr.length() - 4).trim();

      int start = cleanStr.indexOf(matchedStr);
      int end = start + matchedStr.length();

      SpannableString spannableString = new SpannableString(cleanStr);
      spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)),
          start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      if (isBold) {
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(Typeface.createFromAsset(context.getAssets(), fontPath), start, end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      textView.setText(spannableString);
    } else {
      textView.setText(string);
    }
  }

  public static void createClickableText(@NonNull Context context, @NonNull TextView textView,
      @NonNull String string, int color, boolean isBold, @NonNull Runnable runnable) {
    createClickableText(context, "fonts/AvenirNext-Bold.ttf", textView, string, color, isBold,
        runnable);
  }

  public static void createClickableText(@NonNull Context context, @NonNull String fontPath,
      @NonNull TextView textView, @NonNull String string, int color, boolean isBold,
      @NonNull Runnable runnable) {
    final Pattern pattern = Pattern.compile("<b>(.+?)</b>");
    final Matcher matcher = pattern.matcher(string);
    if (matcher.find()) {
      String matchedStr = matcher.group().trim();
      String cleanStr = StringUtil.cleanHtmlString(string);

      matchedStr = matchedStr.substring(3, matchedStr.length() - 4).trim();

      int start = cleanStr.indexOf(matchedStr);
      int end = start + matchedStr.length();

      _createClickableText(context, fontPath, textView, cleanStr, start, end, color, isBold,
          runnable);
    } else {
      textView.setText(string);
    }
  }

  private static void _createClickableText(@NonNull Context context, @NonNull String fontPath,
      @NonNull TextView textView, @NonNull String string, int start, int end, int color,
      boolean isBold, @NonNull Runnable runnable) {
    SpannableString spannableString = new SpannableString(string);
    ClickableSpan clickableSpan = new ClickableSpan() {
      @Override public void onClick(View textView) {
        runnable.run();
      }

      @Override public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
      }
    };

    spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start,
        end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    if (isBold) {
      spannableString.setSpan(Typeface.createFromAsset(context.getAssets(), fontPath), start, end,
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end,
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    textView.setText(spannableString);
    textView.setMovementMethod(BetterLinkMovementMethod.getInstance());
  }

  public static Bitmap textAsBitmap(@NonNull AssetManager assetManager, @NonNull String fontPath,
      @NonNull String text, float textSize, int textColor) {
    Typeface typeface = Typeface.createFromAsset(assetManager, fontPath);
    Paint paint = new Paint(ANTI_ALIAS_FLAG);
    paint.setTextSize(textSize);
    paint.setColor(textColor);
    paint.setTypeface(typeface);
    paint.setTextAlign(Paint.Align.LEFT);
    float baseline = -paint.ascent(); // ascent() is negative
    int width = (int) (paint.measureText(text) + 0.0f); // round
    int height = (int) (baseline + paint.descent() + 0.0f);
    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(image);
    canvas.drawText(text, 0, baseline, paint);
    return image;
  }

  @Nullable public static Bitmap drawableToBitmap(@Nullable Drawable drawable) {
    if (drawable != null) {
      if (drawable instanceof BitmapDrawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        if (bitmapDrawable.getBitmap() != null) {
          return bitmapDrawable.getBitmap();
        }
      }

      Bitmap bitmap;
      if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
        bitmap = Bitmap.createBitmap(1, 1,
            Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
      } else {
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888);
      }

      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);
      return bitmap;
    } else {
      return null;
    }
  }

  public static boolean isColorDark(int color) {
    return (1
        - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255)
        >= 0.4;
  }

  public static void showFab(@NonNull FloatingActionButton fab) {
    if (fab.getVisibility() == GONE) {
      fab.setVisibility(VISIBLE);
      fab.setAlpha(0f);
      fab.setScaleX(0f);
      fab.setScaleY(0f);
      fab.animate()
          .alpha(1)
          .scaleX(1)
          .scaleY(1)
          .setDuration(300)
          .setInterpolator(new OvershootInterpolator())
          .setListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {

            }

            @Override public void onAnimationEnd(Animator animation) {
              fab.animate().setInterpolator(new LinearOutSlowInInterpolator()).start();
            }

            @Override public void onAnimationCancel(Animator animation) {

            }

            @Override public void onAnimationRepeat(Animator animation) {

            }
          })
          .start();
    }
  }

  public static void hideFab(@Nullable FloatingActionButton fab) {
    if (fab == null) return;
    if (fab.getVisibility() == VISIBLE) {
      fab.animate()
          .alpha(0)
          .scaleX(0)
          .scaleY(0)
          .setDuration(300)
          .setInterpolator(new LinearOutSlowInInterpolator())
          .setListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {

            }

            @Override public void onAnimationEnd(Animator animation) {
              fab.setVisibility(GONE);
            }

            @Override public void onAnimationCancel(Animator animation) {
              fab.setVisibility(GONE);
            }

            @Override public void onAnimationRepeat(Animator animation) {

            }
          })
          .start();
    }
  }

  public static void launchCustomTab(@NonNull Context context, @NonNull String url) {
    new CustomTabsIntent.Builder().setToolbarColor(
        ContextCompat.getColor(context, R.color.colorPrimary))
        .setShowTitle(true)
        .setStartAnimations(context,
            ViewUtil.isRTL() ? R.anim.slide_from_left : R.anim.slide_from_right,
            ViewUtil.isRTL() ? R.anim.slide_to_right : R.anim.slide_from_left)
        .setExitAnimations(context,
            ViewUtil.isRTL() ? R.anim.slide_from_right : R.anim.slide_from_left,
            ViewUtil.isRTL() ? R.anim.slide_from_left : R.anim.slide_to_right)
        .build()
        .launchUrl(context, Uri.parse(url));
  }

  public static void hideKeyboard(@NonNull Context context) {
    if (context instanceof BaseActivity) {
      BaseActivity baseActivity = (BaseActivity) context;
      View view = baseActivity.getCurrentFocus();
      if (view != null) {
        ((InputMethodManager) baseActivity.getSystemService(
            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
  }
}
