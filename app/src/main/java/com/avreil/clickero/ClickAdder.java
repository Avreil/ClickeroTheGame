package com.avreil.clickero;

import android.os.Parcel;
import android.os.Parcelable;

public class ClickAdder implements Parcelable{
    Integer gold;
    String goldS;




    public ClickAdder(Integer _gold){

        gold=_gold;
        goldS = getGoldString();
    }


    protected ClickAdder(Parcel in) {
            gold = in.readInt();
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

    public Integer raiseGold(int _multiplier){
        gold = gold+(1*_multiplier);
        return gold;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(gold);
        parcel.writeString(goldS);
    }
}
