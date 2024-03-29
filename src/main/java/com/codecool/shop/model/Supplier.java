package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.SupplierDaoMem;

import java.util.ArrayList;


public class Supplier extends BaseModel {
    private ArrayList<Product> products;

    public Supplier(String name, String description) {
        super(name);
        this.products = new ArrayList<>();
        SupplierDaoMem.getInstance().add(this);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}