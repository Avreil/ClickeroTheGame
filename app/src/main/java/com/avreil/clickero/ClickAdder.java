package com.avreil.clickero;

import android.os.Parcel;
import android.os.Parcelable;

public class ClickAdder implements Parcelable{
    Integer gold=0;
    String goldS;




    public ClickAdder(Integer _gold){

        gold=_gold;
        goldS = getGoldString();
    }


    protected ClickAdder(Parcel in) {
        if (in.readByte() == 0) {
            gold = null;
        } else {
            gold = in.readInt();
        }
        goldS = in.readString();
    }

    public static final Creator<ClickAdder> CREATOR = new Creator<ClickAdder>() {
        @Override
        public ClickAdder createFromParcel(Parcel in) {
            return new ClickAdder(in);
        }

        @Override
        public ClickAdder[] newArray(int size) {
            return new ClickAdder[size];
        }
    };

    public Integer getGold(){

        return gold;
    }


    public String getGoldString(){

        goldS = Integer.toString(gold);

        return goldS;

    }

    public void setGold(int _gold){

        gold=_gold;
    }

    public Integer raiseGold(){
        gold++;
        return gold;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (gold == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(gold);
        }
        parcel.writeString(goldS);
    }
}
