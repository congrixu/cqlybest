package com.cqlybest.www.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqlybest.common.bean.DepartureCity;
import com.cqlybest.common.bean.DictProductGrade;
import com.cqlybest.common.bean.DictProductType;
import com.cqlybest.common.bean.DictTraffic;
import com.cqlybest.common.bean.Keyword;
import com.cqlybest.common.bean.ProductGroup;
import com.cqlybest.common.bean.ProductGroupFilterItem;
import com.cqlybest.common.bean.ProductGroupItem;
import com.cqlybest.common.bean.template1.Template1Menu;
import com.cqlybest.common.bean.template1.Template1ProductGroup;
import com.cqlybest.common.service.DestinationService;
import com.cqlybest.common.service.DictService;
import com.cqlybest.common.service.ProductService;
import com.cqlybest.common.service.SiteService;
import com.cqlybest.common.service.Template1Service;
import com.cqlybest.common.service.TemplateService;

@Controller
public class Template1Controller {

  @Autowired
  private Template1Service template1Service;
  @Autowired
  private TemplateService templateService;
  @Autowired
  private SiteService siteService;
  @Autowired
  private ProductService productService;
  @Autowired
  private DictService dictService;
  @Autowired
  private DestinationService destinationService;

  @RequestMapping("/template1/index.html")
  public void index(Model model) {
    model.addAttribute("posters", template1Service.getPublishedPosters());// 海报
    model.addAttribute("specials", template1Service.getSpecialProduct(4));// 特价
    model.addAttribute("recommendeds", template1Service.getRecommendedProduct(2));// 推荐
    model.addAttribute("hots", template1Service.getHotProduct(10));// 热门
    List<Template1ProductGroup> items = template1Service.getAllIndexProductGroups();
    List<Map<String, Object>> groups = new ArrayList<Map<String, Object>>(items.size());
    for (Template1ProductGroup item : items) {
      ProductGroup productGroup = item.getProductGroup();
      Map<String, Object> group = new HashMap<String, Object>();
      group.put("group", productGroup);
      group.put("products", productService.queryProducts(productGroup.getGroupItems(), null, 0, 4));
      groups.add(group);
    }
    model.addAttribute("groups", groups);// 产品组合
    setCommonData(model);
  }

  @RequestMapping("/template1/register.html")
  public void register(Model model) {
    setCommonData(model);
  }

  @RequestMapping("/template1/login.html")
  public void login(Model model) {
    setCommonData(model);
  }

  @RequestMapping("/template1/group/{id}.html")
  public String group(@PathVariable String id) {
    return "forward:/template1/group/" + id + "/0-0-0-0-0-0-0-0-0.html";
  }

  @RequestMapping("/template1/group/{id}/{f0}-{f1}-{f2}-{f3}-{f4}-{f5}-{f6}-{f7}-{page}.html")
  public Object group(@PathVariable String id, @PathVariable Integer f0, @PathVariable Integer f1,
      @PathVariable Integer f2, @PathVariable Integer f3, @PathVariable Integer f4,
      @PathVariable Integer f5, @PathVariable Integer f6, @PathVariable Integer f7,
      @PathVariable Integer page, Model model) {
    Template1Menu menu = template1Service.get(id);
    if (menu == null || menu.getMenuType() != 0) {
      // 菜单不存在或者不是产品聚合菜单
      return new ResponseEntity<String>(StringUtils.EMPTY, HttpStatus.NOT_FOUND);
    }

    ProductGroup group = menu.getProductGroup();
    Set<ProductGroupItem> groupItems = group.getGroupItems();
    Set<ProductGroupFilterItem> filterItems = group.getFilterItems();
    model.addAttribute("total", productService.queryProductsTotal(groupItems, filterItems));
    model.addAttribute("products", productService.queryProducts(groupItems, filterItems, 0, 10));
    model.addAttribute("menu", menu);

    // 过滤条件
    model.addAttribute("f0", f0);
    model.addAttribute("f1", f1);
    model.addAttribute("f2", f2);
    model.addAttribute("f3", f3);
    model.addAttribute("f4", f4);
    model.addAttribute("f5", f5);
    model.addAttribute("f6", f6);
    model.addAttribute("f7", f7);
    model.addAttribute("page", page);

    // 数据字典
    model.addAttribute("traffics", dictService.getDict(DictTraffic.class));
    model.addAttribute("types", dictService.getDict(DictProductType.class));
    model.addAttribute("grades", dictService.getDict(DictProductGrade.class));
    model.addAttribute("keywords", dictService.getDict(Keyword.class));
    model.addAttribute("departureCities", dictService.getDict(DepartureCity.class));
    model.addAttribute("destinations", destinationService.getTree());
    setCommonData(model);
    return "/template1/product_group";
  }

  @RequestMapping("/template1/page/{id}.html")
  public Object page(@PathVariable String id, Model model) {
    Template1Menu menu = template1Service.get(id);
    if (menu == null || menu.getMenuType() != 1) {
      // 菜单不存在或者不是产品聚合菜单
      return new ResponseEntity<String>(StringUtils.EMPTY, HttpStatus.NOT_FOUND);
    }

    model.addAttribute("page", menu);
    setCommonData(model);
    return "/template1/page";
  }

  public void setCommonData(Model model) {
    model.addAttribute("Site", siteService.getSite());
    model.addAttribute("Menu", template1Service.getPublishedMenus());
  }

}
