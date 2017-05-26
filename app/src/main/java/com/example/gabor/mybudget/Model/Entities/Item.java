package com.example.gabor.mybudget.Model.Entities;

/**
 * Created by Gabor on 2017. 05. 22..
 */

public class Item {
    private final int id;
    private final String name;
    private final long categoryId;
    private final long lastValue;
    private final boolean isIncome;

    public Item(int id, String name, long categoryId, long lastValue, boolean isIncome) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.lastValue = lastValue;
        this.isIncome = isIncome;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public long getLastValue() {
        return lastValue;
    }

    public boolean isIncome() {
        return isIncome;
    }
}
