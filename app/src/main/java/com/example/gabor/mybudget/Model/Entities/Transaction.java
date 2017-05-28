package com.example.gabor.mybudget.Model.Entities;

/**
 * Created by Gabor on 2017. 05. 28..
 */

public class Transaction {

    private final long iD;
    private final long userId;
    private final long itemId;
    private final long value;
    private final String createdTime;
    private final boolean isIncome;

    public Transaction(long iD, long userId, long itemId, long value, String createdTime, boolean isIncome) {
        this.iD = iD;
        this.userId = userId;
        this.itemId = itemId;
        this.value = value;
        this.createdTime = createdTime;
        this.isIncome = isIncome;
    }

    public long getId() {
        return iD;
    }

    public long getUserId() {
        return userId;
    }

    public long getItemId() {
        return itemId;
    }

    public long getValue() {
        return value;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public boolean isIncome() {
        return isIncome;
    }
}
