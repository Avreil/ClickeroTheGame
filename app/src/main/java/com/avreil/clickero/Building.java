package com.avreil.clickero;

public class Building {
    protected Integer TEMPbaseprice;


    String name, desc,material;
    Integer counter,price,capacity;
    Double perSecond;
    int id;

    public Building(String _name, String _desc, String _material,Integer _basePrice,int _id){
        name = _name;
        desc = _desc;
        price = _basePrice;
        TEMPbaseprice=_basePrice;
        counter = 0;
        perSecond = 0.0 ;
        id=_id;
        material=_material;
        capacity = 10000;
    }

    public void raiseCapacity(){
        capacity=10000+(10000*(counter*counter));
    }
    public String getCapacityString(){ return Integer.toString(capacity); }
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCounterString(){
        return Integer.toString(counter);

    }


    public String getPriceString(){
        return Integer.toString(price);
    }

    public String getPerSecondString(){
        return Double.toString(perSecond);

    }

    public void upgradeBuilding() {
        counter = counter + 1;
        price = price*2;
        perSecond = ((double)((int)(100*(perSecond+1.0+(0.2*perSecond)))))/100;
    }

    public void reset(){
        price = TEMPbaseprice;
        counter = 0;
        perSecond = 0.0 ;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void setPerSecond(Double perSecond) {
        this.perSecond = perSecond;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getPerSecond() {
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

