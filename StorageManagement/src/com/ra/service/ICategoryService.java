package com.ra.service;

import com.ra.entity.impl.Category;

import java.util.List;

public interface ICategoryService extends IService<Category>{
    void statisticProductInCategory();
}
