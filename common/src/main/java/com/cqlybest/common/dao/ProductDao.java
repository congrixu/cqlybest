package com.cqlybest.common.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cqlybest.common.bean.Product;
import com.cqlybest.common.bean.ProductCalendar;
import com.cqlybest.common.bean.ProductComment;
import com.cqlybest.common.bean.ProductMaldives;
import com.cqlybest.common.bean.ProductTraffic;
import com.cqlybest.common.bean.ProductTravel;

@Repository
@SuppressWarnings("unchecked")
public class ProductDao extends AbstractDao<Product, String> {

  protected ProductDao() {
    super(Product.class);
  }

  public int update(String id, String name, Object value) {
    String hql = "UPDATE Product SET " + name + " = ?, lastUpdated = ? WHERE id = ?";
    Query query = getCurrentSession().createQuery(hql);
    query.setParameter(0, value);
    query.setParameter(1, new Date());
    query.setParameter(2, id);
    return query.executeUpdate();
  }

  public int update(String[] ids, String name, Object value) {
    String hql = "UPDATE Product SET " + name + " = ?, lastUpdated = ? WHERE id IN (:ids)";
    Query query = getCurrentSession().createQuery(hql);
    query.setParameter(0, value);
    query.setParameter(1, new Date());
    query.setParameterList("ids", ids);
    return query.executeUpdate();
  }

  public int updateTravel(Integer id, String name, String value) {
    String hql = "UPDATE ProductTravel SET " + name + " = ? WHERE id = ?";
    Query query = getCurrentSession().createQuery(hql);
    query.setParameter(0, value);
    query.setParameter(1, id);
    return query.executeUpdate();
  }

  public int updateTraffic(Integer id, String name, Object value) {
    String hql = "UPDATE ProductTraffic SET " + name + " = ? WHERE id = ?";
    Query query = getCurrentSession().createQuery(hql);
    query.setParameter(0, value);
    query.setParameter(1, id);
    return query.executeUpdate();
  }

  public int updateMaldives(Integer id, String name, Object value) {
    String hql = "UPDATE ProductMaldives SET " + name + " = ? WHERE id = ?";
    Query query = getCurrentSession().createQuery(hql);
    query.setParameter(0, value);
    query.setParameter(1, id);
    return query.executeUpdate();
  }

  public int updateMaldivesDetail(String id, String name, Object value) {
    String hql = "UPDATE ProductDetailMaldives SET " + name + " = ? WHERE id = ?";
    Query query = getCurrentSession().createQuery(hql);
    query.setParameter(0, value);
    query.setParameter(1, id);
    return query.executeUpdate();
  }

  public void delete(String[] ids) {
    getCurrentSession().createQuery("DELETE FROM Product WHERE id IN (:ids)").setParameterList(
        "ids", ids).executeUpdate();
  }

  public void deleteTravelByProduct(String[] ids) {
    getCurrentSession().createQuery("DELETE FROM ProductTravel WHERE productId IN (:ids)")
        .setParameterList("ids", ids).executeUpdate();
  }

  public void deleteTrafficByProduct(String[] ids) {
    getCurrentSession().createQuery("DELETE FROM ProductTraffic WHERE productId IN (:ids)")
        .setParameterList("ids", ids).executeUpdate();
  }

  public void deleteMaldivesByProduct(String[] ids) {
    getCurrentSession().createQuery("DELETE FROM ProductMaldives WHERE productId IN (:ids)")
        .setParameterList("ids", ids).executeUpdate();
  }

  public void deleteCommentByProduct(String[] ids) {
    getCurrentSession().createQuery("DELETE FROM ProductComment WHERE productId IN (:ids)")
        .setParameterList("ids", ids).executeUpdate();
  }

  public void deleteCalendarByProduct(String[] ids) {
    getCurrentSession().createQuery("DELETE FROM ProductCalendar WHERE productId IN (:ids)")
        .setParameterList("ids", ids).executeUpdate();
  }

  public void deleteTravel(Integer id) {
    getCurrentSession().createQuery("DELETE FROM ProductTravel WHERE id=?").setParameter(0, id)
        .executeUpdate();
  }

  public void deleteTraffic(Integer id) {
    getCurrentSession().createQuery("DELETE FROM ProductTraffic WHERE id=?").setParameter(0, id)
        .executeUpdate();
  }

  public void deleteMaldives(Integer id) {
    getCurrentSession().createQuery("DELETE FROM ProductMaldives WHERE id=?").setParameter(0, id)
        .executeUpdate();
  }

  public void deleteComment(Integer id) {
    getCurrentSession().createQuery("DELETE FROM ProductComment WHERE id=?").setParameter(0, id)
        .executeUpdate();
  }

  public void deleteCalendar(String productId, Date start, Date end) {
    getCurrentSession().createQuery(
        "DELETE FROM ProductCalendar WHERE productId=? AND date >= ? AND date <=?").setParameter(0,
        productId).setParameter(1, start).setParameter(2, end).executeUpdate();
  }

  public Long findProductTotal(List<Criterion> propertyConditions) {
    Criteria criteria = getCurrentSession().createCriteria(entityClass);
    for (Criterion condition : propertyConditions) {
      criteria.add(condition);
    }
    criteria.setProjection(Projections.rowCount());
    return (Long) criteria.uniqueResult();
  }

  public List<Product> findProductTotal(List<Criterion> propertyConditions, int page, int pageSize) {
    Criteria criteria = getCurrentSession().createCriteria(entityClass);
    for (Criterion condition : propertyConditions) {
      criteria.add(condition);
    }
    criteria.setFirstResult((Math.max(page, 1) - 1) * pageSize);
    criteria.setMaxResults(pageSize);
    return criteria.list();
  }

  public List<Product> getProducts(Disjunction or, Conjunction and, Integer page, Integer pageSize) {
    Criteria criteria = getCurrentSession().createCriteria(Product.class);
    criteria.add(or);
    criteria.add(and);
    if (pageSize != null && pageSize > 0) {
      criteria.setFirstResult((Math.max(page, 1) - 1) * pageSize);
      criteria.setMaxResults(pageSize);
    }
    return criteria.list();
  }

  public List<String> getMaldivesProductIds(String islandId, int num) {
    Criteria criteria = getCurrentSession().createCriteria(ProductMaldives.class);
    criteria.add(Restrictions.eq("islandId", islandId));
    criteria.setProjection(Projections.distinct(Projections.property("productId")));
    criteria.setMaxResults(num);
    return criteria.list();
  }

  public List<ProductTravel> getTravels(String id) {
    Criteria criteria = getCurrentSession().createCriteria(ProductTravel.class);
    criteria.add(Restrictions.eq("productId", id));
    return criteria.list();
  }

  public List<ProductTraffic> getTraffic(String id) {
    Criteria criteria = getCurrentSession().createCriteria(ProductTraffic.class);
    criteria.add(Restrictions.eq("productId", id));
    return criteria.list();
  }

  public List<ProductMaldives> getMaldives(String id) {
    Criteria criteria = getCurrentSession().createCriteria(ProductMaldives.class);
    criteria.add(Restrictions.eq("productId", id));
    return criteria.list();
  }

  public List<ProductComment> getComments(String id) {
    Criteria criteria = getCurrentSession().createCriteria(ProductComment.class);
    criteria.add(Restrictions.eq("productId", id));
    criteria.addOrder(Order.desc("commentTime"));
    return criteria.list();
  }

  public List<ProductCalendar> getCalendar(String id) {
    Criteria criteria = getCurrentSession().createCriteria(ProductCalendar.class);
    criteria.add(Restrictions.eq("productId", id));
    return criteria.list();
  }

}
