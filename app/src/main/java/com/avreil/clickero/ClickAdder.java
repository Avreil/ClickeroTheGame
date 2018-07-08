package com.avreil.clickero;

import android.os.Parcel;
import android.os.Parcelable;

public class ClickAdder{
    Integer gold,multiplier,critical;





    public ClickAdder(Integer _gold){

        gold=_gold;
        multiplier=1;
        critical=0;
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


        gold = gold+(1*multiplier);

    }

    public void raiseGoldCrit(){
        gold=gold+(5*multiplier);

    }



}
