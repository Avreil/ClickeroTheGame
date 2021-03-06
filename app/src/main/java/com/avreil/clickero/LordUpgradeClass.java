package com.avreil.clickero;

public class LordUpgradeClass {

    public Integer price, basePrice, counter, limit;
    public String idNumber;

    public LordUpgradeClass(Integer _basePrice, Integer _limit, String _idNumber)
    {
        basePrice=_basePrice;
        idNumber=_idNumber;
        price=_basePrice;
        limit=_limit;
        counter=0;

    }

    public void resetData(){

        this.setPrice(basePrice);
        this.setCounter(0);
    }


    public void riseCounter(){
        counter++;
    }


    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }
}
