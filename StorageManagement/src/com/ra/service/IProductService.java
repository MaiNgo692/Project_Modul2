package com.ra.service;

import com.ra.entity.impl.Product;

import java.util.List;

public interface IProductService extends IService<Product>{
    List<Product> sortedByName();
    List<Product> sortedByProfit();
}
