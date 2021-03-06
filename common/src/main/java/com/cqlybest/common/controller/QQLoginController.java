package com.cqlybest.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqlybest.common.auth.QQAuthToken;
import com.cqlybest.common.bean.LoginUser;
import com.cqlybest.common.bean.QQAuth;
import com.cqlybest.common.service.QQConnectInitService;
import com.cqlybest.common.service.UserService;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import com.qq.connect.utils.QQConnectConfig;

public class QQLoginController {

  private static final Oauth OAUTH = new Oauth();

  @Autowired
  private UserService userService;

  @RequestMapping("/connector/qq_login")
  public String qqLogin(HttpServletRequest request) {
    String redirect = "redirect:/index.html";
    try {
      String redirectURI =
          request.getScheme() + "://" + request.getServerName() + request.getContextPath()
              + "/connector/qq";
      QQConnectConfig.updateProperties(QQConnectInitService.REDIRECT_URI, redirectURI);
      redirect = "redirect:" + OAUTH.getAuthorizeURL(request);
    } catch (QQConnectException e) {
      e.printStackTrace();
    }

    return redirect;
  }

  @RequestMapping("/connector/qq")
  public String qq(HttpServletRequest request) {
    String redirect = "redirect:/index.html";
    try {
      AccessToken accessToken = OAUTH.getAccessTokenByRequest(request);
      OpenID openId = new OpenID(accessToken.getAccessToken());
      // TODO validate accessToken
      QQAuth auth =
          new QQAuth(openId.getUserOpenID(), accessToken.getAccessToken(), accessToken
              .getExpireIn());
      UserInfo userInfo = new UserInfo(accessToken.getAccessToken(), openId.getUserOpenID());
      LoginUser user = userService.register(auth, userInfo.getUserInfo());
      SecurityContextHolder.getContext().setAuthentication(new QQAuthToken(user));
    } catch (QQConnectException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return redirect;
  }

}
