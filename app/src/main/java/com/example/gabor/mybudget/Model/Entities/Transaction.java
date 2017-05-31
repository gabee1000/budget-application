package com.example.gabor.mybudget.Model.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {

    private final long iD;
    private final long userId;
    private final long itemId;
    private final long value;
    private final long createdTime;
    private final boolean isIncome;

    public Transaction(long iD, long userId, long itemId, long value, long createdTime, boolean isIncome) {
        this.iD = iD;
        this.userId = userId;
        this.itemId = itemId;
        this.value = value;
        this.createdTime = createdTime;
        this.isIncome = isIncome;
    }

    protected Transaction(Parcel in) {
        iD = in.readLong();
        userId = in.readLong();
        itemId = in.readLong();
        value = in.readLong();
        createdTime = in.readLong();
        isIncome = in.readByte() != 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

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

    public long getCreatedTime() {
        return createdTime;
    }

    public boolean isIncome() {
        return isIncome;
    }

    @Override
    public String toString() {
        return "Transaction [ID=" + getId() + ", ItemID=" + getItemId() + ", Value=" + getValue() + "]" ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(iD);
        dest.writeLong(userId);
        dest.writeLong(itemId);
        dest.writeLong(value);
        dest.writeLong(createdTime);
        dest.writeByte((byte) (isIncome ? 1 : 0));
    }
}
