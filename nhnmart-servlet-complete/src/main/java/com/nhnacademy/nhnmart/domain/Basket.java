package com.nhnacademy.nhnmart.domain;

import java.util.ArrayList;

public class Basket { // 장바구니
    private final ArrayList<Food> foods = new ArrayList<>();

    public void add(Food food) {
        foods.add(food);
    }

    public ArrayList<Food> getFoods() {
        return this.foods;
    }

    public int getSumPrice() { // 장바구니 금액 확인
        int sum = 0;
        for (Food f : foods) {
            sum += f.getPrice();
        }
        return sum;
    }
}