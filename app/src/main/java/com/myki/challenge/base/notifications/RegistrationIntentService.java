package com.myki.challenge.base.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.myki.challenge.BaseApplication;
import com.myki.challenge.base.api.BaseAppView;
import com.myki.challenge.base.model.PreferenceModel;
import javax.inject.Inject;
import timber.log.Timber;

public class RegistrationIntentService extends IntentService implements BaseAppView {

  public static final String REGISTRATION_COMPLETE = "registrationComplete";

  @Inject PreferenceModel preferenceModel;

  public RegistrationIntentService() {
    super("RegIntentService");
    Timber.v("Constructing RegistrationIntentService");
  }

  @Override public void onCreate() {
    super.onCreate();
    BaseApplication.get(this).appComponent().inject(this);

    preferenceModel.checkDeviceUuid();
  }

  @Override public void onHandleIntent(Intent intent) {
    Timber.v("onHandleIntent in RegistrationIntentService");
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    saveFCMRegistrationToken(refreshedToken);
    preferenceModel.setTokenSentToServer(true);

    // Notify UI that registration has completed,e so the progress indicator can be hidden.
    Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
    LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
  }

  // Send Registration Token to server
  public void saveFCMRegistrationToken(@NonNull String token) {
    Timber.v("Sending Registration Token to SERVER");
    preferenceModel.setFCMToken(token);
  }

  @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void displayNotification(@NonNull String title, @NonNull String message,
      int notificationId) {
    // No-op
  }
 }