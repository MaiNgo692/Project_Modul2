package com.ra.entity;

import java.util.Scanner;

public interface IProduct {
    float MIN_INTEREST_RATE = 0.2f;
    void inputData(Scanner scanner, String select);
    void displayData();
    void calProfit();
}
