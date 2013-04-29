package com.cqlybest.www.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cqlybest.common.service.TemplateService;

@Controller
public class IndexController {

  @Autowired
  private TemplateService templateService;

  /**
   * 首页
   */
  @RequestMapping("/index.html")
  public String index() {
    return templateService.forward("/index.html");
  }

  /**
   * 注册
   */
  @RequestMapping("/register.html")
  public String register() {
    return templateService.forward("/register.html");
  }

  @RequestMapping(method = RequestMethod.POST, value = "/register.do")
  @ResponseBody
  public void register(@RequestParam String cellPhone, @RequestParam String password,
      @RequestParam String validationCode) {
    System.out.println(cellPhone);
    System.out.println(password);
    System.out.println(validationCode);
  }

  /**
   * 聚合产品页
   */
  @RequestMapping("/pg/{id}.html")
  public String pg(@PathVariable String id) {
    return templateService.forward("/pg/" + id + ".html");
  }

  /**
   * 自定义内容页
   */
  @RequestMapping("/page/{id}.html")
  public String page(@PathVariable String id) {
    return templateService.forward("/page/" + id + ".html");
  }

}
