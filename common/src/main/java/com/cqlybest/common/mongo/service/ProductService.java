package com.cqlybest.common.mongo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqlybest.common.Constant;
import com.cqlybest.common.mongo.bean.ImageMeta;
import com.cqlybest.common.mongo.bean.Product;
import com.cqlybest.common.mongo.bean.ProductBriefTrip;
import com.cqlybest.common.mongo.bean.ProductMaldives;
import com.cqlybest.common.mongo.bean.ProductPriceCalendar;
import com.cqlybest.common.mongo.bean.ProductTransportation;
import com.cqlybest.common.mongo.bean.QueryResult;
import com.cqlybest.common.mongo.dao.MongoDb;
import com.googlecode.mjorm.query.DaoQuery;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

@Service("mongoProductService")
public class ProductService {

  @Autowired
  private MongoDb mongoDb;

  public Product addProduct(String type, String name) {
    Product product = new Product();
    product.setId(UUID.randomUUID().toString());
    product.setType(type);
    product.setName(name);

    Date now = new Date();
    product.setCreatedTime(now);
    product.setLastUpdated(now);
    return mongoDb.createObject("Product", product);
  }

  public int updateProduct(String productId, String property, Object value) {
    Object _value = value;
    if ("briefTrip".equals(property)) {
      if (StringUtils.isNotBlank(value.toString())) {
        List<Object> trips = new ArrayList<>();
        Matcher matcher = Pattern.compile("(\\d+)([^\\d]+)").matcher(value.toString());
        while (matcher.find()) {
          ProductBriefTrip trip = new ProductBriefTrip();
          trip.setNum(Integer.valueOf(matcher.group(1)));
          trip.setUnit(matcher.group(2));
          trips.add(mongoDb.unmap(trip));
        }
        _value = trips;
      }
    }
    WriteResult result =
        mongoDb.createQuery("Product").eq("_id", productId).modify().set(property, _value)
            .set("published", Boolean.FALSE).set("lastUpdated", new Date()).update();
    return result.getN();
  }

  public Product getProduct(String id) {
    return mongoDb.findById("Product", Product.class, id);
  }

  public QueryResult<Product> queryProduct(int page, int pageSize) {
    QueryResult<Product> result = new QueryResult<>(page, pageSize);
    DaoQuery query = mongoDb.createQuery("Product");
    result.setTotal(query.countObjects());

    query.setFirstDocument(result.getStart());
    query.setMaxDocuments(result.getPageSize());
    result.setItems(query.findObjects(Product.class).readAll());

    return result;
  }

  public void addTransportation(String productId, String name) {
    ProductTransportation transportation = new ProductTransportation();
    transportation.setId(UUID.randomUUID().toString());
    transportation.setName(name);
    mongoDb.createQuery("Product").eq("_id", productId).modify().push("transportations",
        mongoDb.unmap(transportation)).update();
  }

  public void updateTransportation(String id, String property, Object value) {
    Object _value = value;
    if ("detail".equals(property)) {
      _value = mongoDb.findById("Transportation", (String) value);
    }
    mongoDb.createQuery("Product").eq("transportations.id", id).modify().set(
        "transportations.$." + property, _value).update();
  }

  public void deleteTransportation(String id) {
    Map<String, String> transportation = new HashMap<>();
    transportation.put("id", id);
    mongoDb.createQuery("Product").eq("transportations.id", id).modify()
        .pull("transportations", transportation).update();
  }

  public void addMaldivesDetail(String productId, String name) {
    ProductMaldives detail = new ProductMaldives();
    detail.setId(UUID.randomUUID().toString());
    detail.setName(name);
    mongoDb.createQuery("Product").eq("_id", productId).modify().push("maldivesDetails",
        mongoDb.unmap(detail)).update();
  }

  public void updateMaldivesDetail(String id, String property, Object value) {
    Object _value = value;
    if ("island".equals(property)) {
      Properties fields = new Properties();
      fields.put("rooms", Boolean.FALSE); // 不包含房型
      fields.put("dinings", Boolean.FALSE); // 不包含餐饮设施
      _value = mongoDb.findById("MaldivesIsland", (String) value, fields);
      mongoDb.createQuery("Product").eq("maldivesDetails.id", id).modify().set(
          "maldivesDetails.$." + property, _value).set("maldivesDetails.$.room", null).update();
    } else if ("room".equals(property)) {
      Properties fields = new Properties();
      fields.put("rooms.$", Boolean.TRUE); // 只查询房型
      DBObject result =
          mongoDb.createQuery("MaldivesIsland").eq("rooms.id", value).findObject(
              mongoDb.unmap(fields));
      _value = ((BasicDBList) result.get("rooms")).get(0);
      mongoDb.createQuery("Product").eq("maldivesDetails.id", id).modify().set(
          "maldivesDetails.$." + property, _value).update();
    } else {
      mongoDb.createQuery("Product").eq("maldivesDetails.id", id).modify().set(
          "maldivesDetails.$." + property, _value).update();
    }
  }

  public void deleteMaldivesDetail(String id) {
    Map<String, String> detail = new HashMap<>();
    detail.put("id", id);
    mongoDb.createQuery("Product").eq("maldivesDetails.id", id).modify()
        .pull("maldivesDetails", detail).update();
  }

  public void updatePrice(String productId, Date startDate, Date endDate, Integer price,
      Integer childPrice, boolean special, boolean delete) {
    Date date = startDate;
    List<String> toDeleteds = new ArrayList<>();
    List<DBObject> toAdds = new ArrayList<>();
    while (date.getTime() <= endDate.getTime()) {
      String dateString = Constant.YYYYMMDD_FORMAT.format(date);
      Map<String, String> toDeleted = new HashMap<>();
      toDeleted.put("date", dateString);
      toDeleteds.add(dateString);

      if (!delete) {
        ProductPriceCalendar toAdd = new ProductPriceCalendar();
        toAdd.setDate(dateString);
        toAdd.setPrice(price);
        toAdd.setChildPrice(childPrice);
        toAdd.setSpecial(special);
        toAdds.add(mongoDb.unmap(toAdd));
      }

      date = DateUtils.addDays(date, 1);
    }

    Map<String, Object> in = new HashMap<>();
    in.put("$in", toDeleteds);
    Map<String, Object> subQuery = new HashMap<>();
    subQuery.put("date", in);
    mongoDb.createQuery("Product").eq("_id", productId).modify().pull("priceCalendar", subQuery)
        .update();
    if (!delete) {
      mongoDb.createQuery("Product").eq("_id", productId).modify().pushAll("priceCalendar", toAdds)
          .update();
    }
  }

  public void addPoster(String productId, List<String> filenames) {
    List<Object> images = new ArrayList<>();
    List<String> imageIds = new ArrayList<>();
    for (String fileName : filenames) {
      String[] tmp = fileName.split("\\.");
      imageIds.add(tmp[0]);
      ImageMeta image = new ImageMeta();
      image.setId(tmp[0]);
      image.setExtension(tmp[1]);
      images.add(mongoDb.unmap(image));
    }
    // 保存图片
    mongoDb.createQuery("Product").eq("_id", productId).modify().pushAll("posters", images)
        .update();
    // 更新原始图片的信息
    mongoDb.createQuery("Image").in("_id", imageIds).modify()
        .set("extra", Constant.IMAGE_PRODUCT_POSTER).set("extraKey", productId).updateMulti();
  }

  public void updatePoster(String imageId, String property, String value) {
    mongoDb.createQuery("Product").eq("posters.id", imageId).modify().set("posters.$." + property,
        value).update();
    // 更新原始图片的信息
    mongoDb.createQuery("Image").eq("_id", imageId).modify().set(property, value).update();
  }

  public void deletePoster(String imageId) {
    Map<String, String> image = new HashMap<>();
    image.put("id", imageId);
    mongoDb.createQuery("Product").eq("posters.id", imageId).modify().pull("posters", image)
        .update();
    mongoDb.createQuery("Image").eq("_id", imageId).modify().delete();
  }

  public void addPhoto(String productId, List<String> filenames) {
    List<Object> images = new ArrayList<>();
    List<String> imageIds = new ArrayList<>();
    for (String fileName : filenames) {
      String[] tmp = fileName.split("\\.");
      imageIds.add(tmp[0]);
      ImageMeta image = new ImageMeta();
      image.setId(tmp[0]);
      image.setExtension(tmp[1]);
      images.add(mongoDb.unmap(image));
    }
    // 保存图片
    mongoDb.createQuery("Product").eq("_id", productId).modify().pushAll("photos", images).update();
    // 更新原始图片的信息
    mongoDb.createQuery("Image").in("_id", imageIds).modify()
        .set("extra", Constant.IMAGE_PRODUCT_PHOTO).set("extraKey", productId).updateMulti();
  }

  public void updatePhoto(String imageId, String property, String value) {
    mongoDb.createQuery("Product").eq("photos.id", imageId).modify().set("photos.$." + property,
        value).update();
    // 更新原始图片的信息
    mongoDb.createQuery("Image").eq("_id", imageId).modify().set(property, value).update();
  }

  public void deletePhoto(String imageId) {
    Map<String, String> image = new HashMap<>();
    image.put("id", imageId);
    mongoDb.createQuery("Product").eq("photos.id", imageId).modify().pull("photos", image).update();
    mongoDb.createQuery("Image").eq("_id", imageId).modify().delete();
  }
}
