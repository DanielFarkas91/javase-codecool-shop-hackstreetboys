package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.HashMap;
import java.util.List;

public interface OrderDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);

    HashMap<Product, Integer> getAll();
    //List<Product> getAll();
    /*List<Product> getBy(Supplier supplier);
    List<Product> getBy(ProductCategory productCategory);*/
}
