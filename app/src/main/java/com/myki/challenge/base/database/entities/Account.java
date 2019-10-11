package com.myki.challenge.base.database.entities;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass public class Account implements RealmModel {

  @PrimaryKey private String uuid;
  private String username;
  private String nickname;
  private String password;
  private String accountName;
  private String url;
  private long dateAdded;

  public String getUuid() {
    return uuid;
  }

  public Account setUuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public Account setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getNickname() {
    return nickname;
  }

  public Account setNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public Account setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getAccountName() {
    return accountName;
  }

  public Account setAccountName(String accountName) {
    this.accountName = accountName;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Account setUrl(String url) {
    this.url = url;
    return this;
  }

  public long getDateAdded() {
    return dateAdded;
  }

  public Account setDateAdded(long dateAdded) {
    this.dateAdded = dateAdded;
    return this;
  }
}
