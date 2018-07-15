package com.avreil.clickero;

import android.os.Parcel;
import android.os.Parcelable;

public class MainGameScreenClass {
    Integer gold,multiplier,critical,capacity;






    public MainGameScreenClass(){

        gold=0;
        multiplier=1;
        critical=0;
        capacity = 10000;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }



    public Integer getGold(){

        return gold;
    }




    public void setGold(int _gold){

        gold=_gold;
    }

    public void raiseGold(){


        gold = gold+multiplier;

    }

    public void raiseGoldCrit(){
        gold=gold+(5*multiplier);

    }



}
