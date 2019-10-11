package com.myki.challenge.base.api;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.myki.challenge.base.model.DatabaseModel;
import com.myki.challenge.base.model.PreferenceModel;
import com.myki.challenge.base.performance.AsyncJobsObserver;
import com.myki.challenge.base.ui.Presenter;
import com.myki.challenge.base.ui.PresenterConfiguration;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import timber.log.Timber;

public class AppPresenter extends Presenter<BaseAppView> {
  @NonNull private final EventBus eventBus;
  @NonNull private final Context context;
  @NonNull private final PreferenceModel preferenceModel;
  @NonNull private final DatabaseModel databaseModel;
  @NonNull private final AsyncJobsObserver asyncJobsObserver;
  @NonNull private final PresenterConfiguration presenterConfiguration;

  @Nullable private JSONObject actionRequest;

  AppPresenter(@NonNull final EventBus eventBus,
      @NonNull final Context context, @NonNull final PreferenceModel preferenceModel,
      @NonNull final DatabaseModel databaseModel,
      @NonNull AsyncJobsObserver asyncJobsObserver,
      @NonNull PresenterConfiguration presenterConfiguration) {
    this.eventBus = eventBus;
    this.context = context;
    this.preferenceModel = preferenceModel;
    this.databaseModel = databaseModel;
    this.asyncJobsObserver = asyncJobsObserver;
    this.presenterConfiguration = presenterConfiguration;
  }

  public static boolean isAppIsInBackground(Context context) {
    boolean isInBackground = true;
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
      List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
      if (runningProcesses != null) {
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
          if (processInfo.importance
              == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            for (String activeProcess : processInfo.pkgList) {
              if (activeProcess.equals(context.getPackageName())) {
                isInBackground = false;
              }
            }
          }
        }
      }
    } else {
      List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
      ComponentName componentInfo = taskInfo.get(0).topActivity;
      if (componentInfo.getPackageName().equals(context.getPackageName())) {
        isInBackground = false;
      }
    }

    return isInBackground;
  }

  @Override public void bindView(@NonNull BaseAppView view) {
    Timber.d("Registering Event Bus");
    if (!eventBus.isRegistered(this)) {
      eventBus.register(this);
    }
    super.bindView(view);
  }

  @Override public void unbindView(@NonNull BaseAppView view) {
    Timber.d("Unregistering Event Bus");
    eventBus.unregister(this);
    super.unbindView(view);
  }

  @NonNull public PreferenceModel getPreferenceModel() {
    return preferenceModel;
  }


}
