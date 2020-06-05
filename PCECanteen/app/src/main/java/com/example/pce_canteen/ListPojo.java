package com.example.pce_canteen;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ListPojo implements Parcelable {
    private String title;
    private String price;
    private int images;


    public ListPojo(String title, String price, int images) {
        this.title = title;
        this.price = price;
        this.images = images;
    }

    protected ListPojo(Parcel in) {
        title = in.readString();
        price = in.readString();
        images = in.readInt();
    }

    public static final Creator<ListPojo> CREATOR = new Creator<ListPojo>() {
        @Override
        public ListPojo createFromParcel(Parcel in) {
            return new ListPojo(in);
        }

        @Override
        public ListPojo[] newArray(int size) {
            return new ListPojo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getImages() {
        return images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(price);
        dest.writeInt(images);
    }
}
