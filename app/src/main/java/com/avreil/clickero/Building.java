package com.avreil.clickero;

public class Building {

    String name, desc;
    Integer counter,price;
    float perSecond;


    public Building(String _name, String _desc,Integer _basePrice){
        name = _name;
        desc = _desc;
        price = _basePrice;
        counter = 0;
        perSecond =0;

    }
















    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPerSecond(float perSecond) {
        this.perSecond = perSecond;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public float getPerSecond() {
        return perSecond;
    }

    public Integer getCounter() {
        return counter;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

}

