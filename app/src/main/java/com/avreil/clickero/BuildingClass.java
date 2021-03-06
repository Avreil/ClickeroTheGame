package com.avreil.clickero;



/*type
1 - production
2 - infrastructure
*/
public class BuildingClass {
    protected Integer TempBasePrice;
    protected int type;

    String name, desc,material;
    Integer counter,price,capacity,priceWood,priceStone,priceCoal;
    Double perSecond;
    int id;

    public BuildingClass(String _name, String _desc, String _material, Integer _basePrice, int _id, int _type){
        name = _name;
        desc = _desc;
        price = _basePrice;
        TempBasePrice =_basePrice;
        counter = 0;
        perSecond = 0.0 ;
        id=_id;
        material=_material;
        capacity=10000;
        type = _type;

        if (type == 2){
            priceWood = _basePrice/10;
            priceStone = _basePrice/20;
            priceCoal = _basePrice/25;
        }else{
            priceWood = 0;
            priceStone = 0;
            priceCoal = 0;
        }


    }



    public void upgradeBuilding() {
        this.counter = this.counter + 1;
        this.price = this.price*2;
        this.priceWood = this.priceWood*2;
        this.priceStone = this.priceStone*2;
        this.priceCoal = this.priceCoal*2;
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
        price = TempBasePrice;
        counter = 0;
        perSecond = 0.0 ;
        capacity = 10000;
        if (type == 2){
            priceWood = TempBasePrice /10;
            priceStone = TempBasePrice /20;
            priceCoal = TempBasePrice/25;
        }else{
            priceWood = 0;
            priceStone = 0;
            priceCoal = 0;
        }


    }



    public int getId() {
        return id;
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
    public Integer getPriceStone() {
        return priceStone;
    }
    public Integer getPriceWood() {
        return priceWood;
    }
    public String getCounterString(){
        return Integer.toString(counter);

    }
    public String getPriceString(){
        if(priceWood==0 && priceStone ==0){
            return ("Gold\n"+Integer.toString(price));
        }else{
            return ("Gold\tWood\tStone\tCoal\n"+Integer.toString(price)+"\t"+Integer.toString(priceWood)+"\t"+Integer.toString(priceStone)+"\t"+Integer.toString(priceCoal));
        }

    }
    public String getPerSecondString(){
        return Double.toString(perSecond);

    }
    public String getMaterial() {
        return material;
    }
    public String getCapacityString(){ return Integer.toString(capacity); }
    public Integer getCapacity() {
        return capacity;
    }
    public int getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPriceStone(Integer priceStone) {
        this.priceStone = priceStone;
    }
    public void setPriceWood(Integer priceWood) {
        this.priceWood = priceWood;
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
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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

    public Integer getPriceCoal() {
        return priceCoal;
    }

    public void setPriceCoal(Integer priceCoal) {
        this.priceCoal = priceCoal;
    }
}
