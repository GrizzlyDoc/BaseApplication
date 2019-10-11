package com.myki.challenge.base.model;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.myki.challenge.base.utils.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PreferenceModel {

  //private final String RSA_PUBLIC_KEY = "com.brainattica.RSA_PUBLIC_KEY";
  //private final String RSA_PRIVATE_KEY = "com.brainattica.RSA_PRIVATE_KEY";

  private static final String REALM_KEY = "mjollnir";
  private static final String USER_UUID = "userUuid";
  private static final String DEVICE_UUID = "deviceUuid";
  private static final String USER_ID = "userId";
  private static final String PHONE_NUMBER = "phoneNumber";
  private static final String FIRST_NAME = "firstName";
  private static final String LAST_NAME = "lastName";
  private static final String MASTER_EMAIL = "masterEmail";
  private static final String PROFILE_PICTURE = "profilePicture";
  private static final String COUNTRY_CODE = "countryCode";
  private static final String FCM_TOKEN = "FCMToken";
  private static final String NOTIFICATION_ID = "notificationId";
  private static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";


  @NonNull private final SharedPreferences sharedPreferences;
  private int lastVersion;

  public PreferenceModel(@NonNull final SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }


  @Nullable public String getRealmKey() {
    return sharedPreferences.getString(REALM_KEY, null);
  }

  public void setRealmKey(@NonNull String secretKey) {
    sharedPreferences.edit().putString(REALM_KEY, secretKey).apply();
  }


  @NonNull public String getFirstName() {
    return sharedPreferences.getString(FIRST_NAME, "");
  }

  public void setFirstName(@NonNull String firstName) {
    sharedPreferences.edit().putString(FIRST_NAME, firstName).apply();
  }

  public int getUserId() {
    return sharedPreferences.getInt(USER_ID, -1);
  }

  public void setUserId(int userId) {
    sharedPreferences.edit().putInt(USER_ID, userId).apply();
  }

  @Nullable public String getUserUuid() {
    return sharedPreferences.getString(USER_UUID, null);
  }

  public void setUserUuid(@NonNull String userUuid) {
    sharedPreferences.edit().putString(USER_UUID, userUuid).apply();
  }

  @Nullable public String getDeviceUuid() {
    if (sharedPreferences.getString(DEVICE_UUID, "") == null) {
      return "";
    } else {
      return sharedPreferences.getString(DEVICE_UUID, "");
    }
  }

  public void setDeviceUuid(@NonNull String deviceUuid) {
    sharedPreferences.edit().putString(DEVICE_UUID, deviceUuid).apply();
  }

  @NonNull public String getPhoneNumber() {
    return sharedPreferences.getString(PHONE_NUMBER, "");
  }

  public void setPhoneNumber(@NonNull String phoneNumber) {
    sharedPreferences.edit().putString(PHONE_NUMBER, phoneNumber).apply();
  }

  @NonNull public String getLastName() {
    return sharedPreferences.getString(LAST_NAME, "");
  }

  public void setLastName(@NonNull String lastName) {
    sharedPreferences.edit().putString(LAST_NAME, lastName).apply();
  }

  @NonNull public String getMasterEmail() {
    return sharedPreferences.getString(MASTER_EMAIL, "");
  }

  public void setMasterEmail(@NonNull String masterEmail) {
    sharedPreferences.edit().putString(MASTER_EMAIL, masterEmail).apply();
  }

  @NonNull public String getProfilePicture() {
    return sharedPreferences.getString(PROFILE_PICTURE, "");
  }

  public void setProfilePicture(@NonNull String profilePicture) {
    sharedPreferences.edit().putString(PROFILE_PICTURE, profilePicture).apply();
  }

  @NonNull public String getCountryCode() {
    return sharedPreferences.getString(COUNTRY_CODE, "");
  }

  public void setCountryCode(@NonNull String countryCode) {
    sharedPreferences.edit().putString(COUNTRY_CODE, countryCode).apply();
  }

  public void setTokenSentToServer(boolean bool) {
    sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, bool).apply();
  }

  @NonNull public String getFCMToken() {
    return sharedPreferences.getString(FCM_TOKEN, "not available");
  }

  public void setFCMToken(@NonNull String token) {
    sharedPreferences.edit().putString(FCM_TOKEN, token).apply();
  }

  public int getNotificationId() {
    return sharedPreferences.getInt(NOTIFICATION_ID, 0);
  }

  public void setNotificationId(int notificationId) {
    sharedPreferences.edit().putInt(NOTIFICATION_ID, notificationId).apply();
  }

  public void wipeAllData() {
    setFirstName("");
    setLastName("");
    setUserId(-1);
    setPhoneNumber("");
    setMasterEmail("");
    setCountryCode("");
  }

  public String checkDeviceUuid() {
    if (!StringUtil.isNotNullOrEmpty(getDeviceUuid())) {
      String userUuid = getUserUuid();
      if (StringUtil.isNotNullOrEmpty(userUuid)) {
        setDeviceUuid(userUuid);
      } else {
        setDeviceUuid(UUID.randomUUID().toString());
      }
    }
    return getDeviceUuid();
  }

  public String getDeviceName() {
    return android.os.Build.MODEL;
  }

  public String getOs() {
    return "Android "
        + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName()
        .charAt(0);
  }

  public String getPlatform() {
    return "Android";
  }

  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }

}
