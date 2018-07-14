package com.avreil.clickero;



/*type
1 - production
2 - infrastructure
*/
public class Building {
    protected Integer TEMPbaseprice;
    protected int type;

    String name, desc,material;
    Integer counter,price,capacity;
    Double perSecond;
    int id;

    public Building(String _name, String _desc, String _material,Integer _basePrice,int _id,int _type){
        name = _name;
        desc = _desc;
        price = _basePrice;
        TEMPbaseprice=_basePrice;
        counter = 0;
        perSecond = 0.0 ;
        id=_id;
        material=_material;
        capacity = 10000;
        type = _type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void raiseCapacity(){
        this.capacity=10000+(10000*(this.counter*this.counter));
    }

    public void raisePerSecond(){
        this.perSecond = ((double)((int)(100*(this.perSecond+1.0+(0.2*this.perSecond)))))/100;
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
        this.counter = this.counter + 1;
        this.price = this.price*2;
        switch (this.type){
            case 1:
                this.raisePerSecond();
                break;
            case 2:
                this.raiseCapacity();
                break;
        }
    }

    public void reset(){
        price = TEMPbaseprice;
        counter = 0;
        perSecond = 0.0 ;
        capacity = 10000;

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

