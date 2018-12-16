package com.airsharing.company.airsharing.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hyunju on 2017-11-06.
 */

public class Contents extends Content implements Parcelable{


    public Contents() {
        uuid = "";
        dust = "";
        content = "";
    }

    public Contents(Parcel in) {
        uuid = in.readString();
        dust = in.readString();
        content = in.readString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDust() {
        return dust;
    }

    public void setDust(String dust) {
        this.dust = dust;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(dust);
        parcel.writeString(content);
    }

    public static final Parcelable.Creator<Contents> CREATOR = new Parcelable.Creator<Contents>(){
        public Contents createFromParcel(Parcel in){
            return new Contents(in);
        }

        public Contents[] newArray(int size){
            return new Contents[size];
        }
    };
}
