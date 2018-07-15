package com.avreil.clickero;

public class Materials {

    Integer Stone,Wood;

    public Materials(){
        Stone=0;
        Wood=0;
    }

    public void reset (){
        this.Stone = 0;
        this.Wood = 0;
    }
    public Integer getStone() {
        return Stone;
    }

    public void setStone(Integer _stone) {
        Stone = _stone;
    }

    public Integer getWood() {
        return Wood;
    }

    public void setWood(Integer _wood) {
        Wood = _wood;
    }

}
