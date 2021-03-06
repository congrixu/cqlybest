package com.cqlybest.common.bean;

import java.io.Serializable;
import java.util.Date;

public class WeiboAuth implements Serializable {

  private static final long serialVersionUID = -2575016480564569674L;

  private String uid;
  private String token;
  private long expireIn;
  private Date createdTime;
  private Date lastUpdate;

  public WeiboAuth() {}

  public WeiboAuth(String uid, String token, long expireIn) {
    this.uid = uid;
    this.token = token;
    this.expireIn = expireIn;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getExpireIn() {
    return expireIn;
  }

  public void setExpireIn(long expireIn) {
    this.expireIn = expireIn;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
