package com.cqlybest.common.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cqlybest.common.bean.Product;
import com.cqlybest.common.bean.ProductCalendar;
import com.cqlybest.common.bean.ProductComment;
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

  public void delete(String[] ids) {
    getCurrentSession().createQuery("DELETE FROM Product WHERE id IN (:ids)").setParameterList(
        "ids", ids).executeUpdate();
  }

  public void deleteTravel(Integer id) {
    getCurrentSession().createQuery("DELETE FROM ProductTravel WHERE id=?").setParameter(0, id)
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

  private void buidBooleanCondition(Criteria criteria, String prop, Boolean value) {
    if (value != null) {
      if (value) {
        criteria.add(Restrictions.eq(prop, true));
      } else {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq(prop, false));
        disjunction.add(Restrictions.isNull(prop));
        criteria.add(disjunction);
      }
    }
  }

  private void buidProductCriteria(Criteria criteria, Boolean hot, Boolean red, Boolean spe,
      Boolean pub, String name) {
    buidBooleanCondition(criteria, "popular", hot);;
    buidBooleanCondition(criteria, "recommend", red);;
    buidBooleanCondition(criteria, "specialOffer", spe);;
    buidBooleanCondition(criteria, "published", pub);;
    if (StringUtils.isNotEmpty(name)) {
      criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
    }
  }

  public Long findProductTotal(Boolean hot, Boolean red, Boolean spe, Boolean pub, String name) {
    Criteria criteria = getCurrentSession().createCriteria(entityClass);
    buidProductCriteria(criteria, hot, red, spe, pub, name);
    criteria.setProjection(Projections.rowCount());
    return (Long) criteria.uniqueResult();
  }

  public List<Product> findProductTotal(Boolean hot, Boolean red, Boolean spe, Boolean pub,
      String name, int page, int pageSize) {
    Criteria criteria = getCurrentSession().createCriteria(entityClass);
    buidProductCriteria(criteria, hot, red, spe, pub, name);
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

  public List<ProductTravel> getTravels(String id) {
    Criteria criteria = getCurrentSession().createCriteria(ProductTravel.class);
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
