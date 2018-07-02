package com.avreil.clickero;

public class ClickAdder {
    public Integer gold;
    public String goldS;

    public ClickAdder(){
        gold = 0 ;
    }

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



}
