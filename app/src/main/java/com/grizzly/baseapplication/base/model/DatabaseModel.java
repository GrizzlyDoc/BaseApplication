package com.grizzly.baseapplication.base.model;

import android.support.annotation.NonNull;
import io.realm.RealmConfiguration;

public class DatabaseModel {

  @NonNull private final RealmConfiguration realmConfiguration;
  @NonNull private final PreferenceModel preferenceModel;

  public DatabaseModel(@NonNull final RealmConfiguration realmConfiguration,
      @NonNull PreferenceModel preferenceModel) {
    this.realmConfiguration = realmConfiguration;
    this.preferenceModel = preferenceModel;
  }

  //@Nullable public UserAccount getUserAccountByUUID(@NonNull String uuid) {
  //  Realm realm = Realm.getInstance(realmConfiguration);
  //  UserAccount userAccountCopy = null;
  //  UserAccount userAccount = realm.where(UserAccount.class).equalTo("uuid", uuid).findFirst();
  //  if (userAccount != null) {
  //    UserItem userItem = userAccount.getUserItem();
  //    if (userItem == null) {
  //      realm.executeTransaction(realm1 -> RealmObject.deleteFromRealm(userAccount));
  //    } else {
  //      userAccountCopy = realm.copyFromRealm(userAccount);
  //    }
  //  }
  //  realm.close();
  //  return userAccountCopy;
  //}
  //
  //@NonNull public Profile addLocalProfile(@NonNull String profileName, @Nullable String profileType,
  //    @NonNull String profileUuid)
  //    throws JSONException {
  //  Random rand = new Random();
  //  int rID = 10000 + rand.nextInt(90000);
  //
  //  Profile localProfile = new Profile().setId(rID).setCompanyName(profileName)
  //      .setType(profileType).setLocal(true).setLastUpdated(System.currentTimeMillis());
  //  if (StringUtil.isNotNullOrEmpty(profileUuid)) {
  //    localProfile.setUuid(profileUuid);
  //  } else {
  //    localProfile.setUuid(UUID.randomUUID().toString());
  //  }
  //  Realm realm = Realm.getInstance(realmConfiguration);
  //
  //  Profile[] dbProfile = new Profile[1];
  //  realm.executeTransaction(realm1 -> {
  //    dbProfile[0] = realm1.copyFromRealm(realm1.copyToRealmOrUpdate(localProfile));
  //  });
  //
  //  realm.close();
  //  syncAllPeripherals();
  //
  //  return localProfile;
  //}

}