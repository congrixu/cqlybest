package com.cqlybest.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqlybest.common.bean.Product;
import com.cqlybest.common.dao.ProductDao;

@Service
@Transactional(readOnly = true)
public class ProductService {

  @Autowired
  private ProductDao productDao;

  public void addProduct(Product newProduct) {
    productDao.saveOrUpdate(newProduct);
  }

  public void deleteProduct(Integer productId) {
    Product product = new Product();
    product.setId(productId);
    productDao.delete(product);
  }

  public List<Product> queryProduct() {
    return productDao.findAll();
  }

  public void modifyProduct(Product updatedProduct) {
    productDao.saveOrUpdate(updatedProduct);
  }

}
