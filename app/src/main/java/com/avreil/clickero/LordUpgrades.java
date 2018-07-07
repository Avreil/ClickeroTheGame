package com.avreil.clickero;

public class LordUpgrades {

    public Integer price, basePrice, counter;
    public String idNumber;

    public LordUpgrades(Integer _basePrice,String _idNumber)
    {
        basePrice=_basePrice;
        idNumber=_idNumber;
    }


    public void riseCounter(){
        counter++;
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
