package com.example.gabor.mybudget.Model.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gabor on 2017. 05. 22..
 */

public class Item implements Parcelable {
    private final long id;
    private final String name;
    private final long categoryId;
    private final long lastValue;
    private final boolean isIncome;

    public Item(long id, String name, long categoryId, long lastValue, boolean isIncome) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.lastValue = lastValue;
        this.isIncome = isIncome;
    }

    public long getId() {
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

    @Override
    public String toString() {
        return getName();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        categoryId = in.readLong();
        lastValue = in.readLong();
        isIncome = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(categoryId);
        dest.writeLong(lastValue);
        dest.writeByte((byte) (isIncome ? 1 : 0));
    }
}
