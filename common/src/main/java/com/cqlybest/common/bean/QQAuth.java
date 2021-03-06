package com.cqlybest.common.bean;

import java.io.Serializable;
import java.util.Date;

public class QQAuth implements Serializable {

  private static final long serialVersionUID = 5256670895383011254L;

  private String openid;
  private String token;
  private long expireIn;
  private String gender;
  private Integer level;
  private Boolean vip;
  private Boolean yellowYearVip;
  private Date createdTime;
  private Date lastUpdate;

  public QQAuth() {}

  public QQAuth(String openid, String token, long expireIn) {
    this.openid = openid;
    this.token = token;
    this.expireIn = expireIn;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
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

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Boolean getVip() {
    return vip;
  }

  public void setVip(Boolean vip) {
    this.vip = vip;
  }

  public Boolean getYellowYearVip() {
    return yellowYearVip;
  }

  public void setYellowYearVip(Boolean yellowYearVip) {
    this.yellowYearVip = yellowYearVip;
  }

}
