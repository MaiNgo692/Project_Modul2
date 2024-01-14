package com.ra.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public interface IService<T> {
    String CATEGORY_FILE ="categories.txt";
    String PRODUCTS_FILE ="products.txt";
    void add(Scanner scanner);
    boolean delete(Scanner scanner);
    List<T> findByName(String name);
    List<T>  readData() ;
    void writeData();
}
