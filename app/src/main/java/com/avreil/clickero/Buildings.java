package com.avreil.clickero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Buildings extends AppCompatActivity {
    private int productionBuildingsCounter = 2;
    private int materialCounter=3;
    private int infrastructureBuildingsCounter=3;
    private Integer gold;
    private SharedPreferences buildingsSharedPref;
    private SharedPreferences.Editor editor;
    private Materials materials;
    private Building[] buildingProduction,buildingInfrastructure;
    private TextView[][] production,infrastructure;
    private TextView[] amount;
    private String amo = "amount";
    private Thread product;
    private Button[] productionBuyBtn,infrastructureBuyBtn;


/*
Material List TextViews
0 gold
1 wood
2 stone
 */

    /*

TEXT VIEWS ON PRODUCTION LIST
name 0
description 1
counter 2
perSecond/storage 3
price 4
 */

/*
ID's
Production
0 = wood
1 = Stone

 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buildings);
        initializeAndSetGameCore(); //initialize Intent/workClasses/TextViews/Buttons
        buildClasses();             //build classes
        loadAll();                  //load Data
        initializeAll();            //initialize textViews
        setAll();                   //set textViews
        production();               //start per second thread
        goBack();                   //back to previous activity
        developer();                //buttons to delete after development finish
    }//END OF ON CREATE


    private void initializeAndSetGameCore (){
        Intent intent = getIntent();
        gold = intent.getIntExtra("GoldToActivity", 0);
        buildingsSharedPref = getSharedPreferences("BuildingsUpgradeInfo",0);
        editor = buildingsSharedPref.edit();

        //work classes
        production = new TextView[productionBuildingsCounter][5];
        infrastructure = new TextView[infrastructureBuildingsCounter][5];
        amount = new TextView[materialCounter];

        buildingProduction = new Building[productionBuildingsCounter];
        buildingInfrastructure = new Building[infrastructureBuildingsCounter];
        materials = new Materials();

        productionBuyBtn = new Button[productionBuildingsCounter];
        infrastructureBuyBtn = new Button[infrastructureBuildingsCounter];

    }

    private void buildClasses(){
        prepareProductionBuildings();
        prepareInfrastructureBuildings();
    }
    private void prepareProductionBuildings(){
        buildingProduction[0]= new Building("Lumber mill","Produce wood","Wood", 100,0,1);
        buildingProduction[1] = new Building("Quarry", "Mine Stone","Stone", 250,1,1);

    }
    private void prepareInfrastructureBuildings(){
        buildingInfrastructure[0] = new Building("Bank","Increases gold storage", "goldStorage",1000,0,2);
        buildingInfrastructure[1] = new Building("Wood Storehouse", "Increases wood storage","woodStorage",2000,1,2);
        buildingInfrastructure[2] = new Building("Stone Depot", "Increases stone storage","stoneStorage",4000,2,2);

    }


    private void initializeAndSetProductionButtons(){
        String btn="productionBuyBtn";
        int ID;
        for (int i = 0;i<productionBuildingsCounter;i++){
            final int k = i;
            ID = getResources().getIdentifier(btn+Integer.toString(i),"id",getPackageName());
            productionBuyBtn[i] =findViewById(ID);
            productionBuyBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upgrade(buildingProduction[k]);}
            });
        }
    }
    private void initializeAndSetInfrastructureButtons(){
        String btn="infrastructureBuyBtn";
        int ID;
        for (int i = 0;i<productionBuildingsCounter;i++){
            final int k = i;
            ID = getResources().getIdentifier(btn+Integer.toString(i),"id",getPackageName());
            infrastructureBuyBtn[i] =findViewById(ID);
            infrastructureBuyBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upgrade(buildingInfrastructure[k]);}
            });
        }
    }

    private void initializeMaterialTextView (){
        int ID;
        for (int i = 0;i<materialCounter;i++){
            ID = getResources().getIdentifier(amo+Integer.toString(i),"id",getPackageName());
            amount[i]=findViewById(ID);
        }

    }
    private void setMaterialTextViews(){
        amount[0].setText(Integer.toString(gold));
        amount[1].setText(Integer.toString(materials.getWood()));
        amount[2].setText(Integer.toString(materials.getStone()));
    }
    private void saveMaterialListData(){
        editor.putInt("Wood",materials.getWood());
        editor.putInt("Stone",materials.getStone());
        editor.apply();
    }
    private void loadMaterialListData(){

        materials.setWood(buildingsSharedPref.getInt("Wood", 0));
        materials.setStone(buildingsSharedPref.getInt("Stone",0));
    }

    private void initializeProductionBuildingsTextView(){

        int ID;
        for(int j = 0; j<productionBuildingsCounter;j++) {
            for (int i = 0; i < 5; i++) {

                ID = getResources().getIdentifier(buildingProduction[j].getMaterial() + Integer.toString(i), "id", getPackageName());
                production[buildingProduction[j].getId()][i] = findViewById(ID);

            }
        }
    }
    private void setProductionTextView() {

        for (int j = 0; j<productionBuildingsCounter;j++) {
            for (int i = 0; i < 5; i++) {
                switch (i) {
                    case 0:
                        production[buildingProduction[j].getId()][i].setText(buildingProduction[j].getName());
                        break;
                    case 1:
                        production[buildingProduction[j].getId()][i].setText(buildingProduction[j].getDesc());
                        break;
                    case 2:
                        production[buildingProduction[j].getId()][i].setText("Level:  " + buildingProduction[j].getCounterString());
                        break;
                    case 3:
                        production[buildingProduction[j].getId()][i].setText(buildingProduction[j].getPerSecondString());
                        break;
                    case 4:
                        production[buildingProduction[j].getId()][i].setText(buildingProduction[j].getPriceString());
                        break;
                }
            }
        }
    }
    private void saveProductionBuildingData(){
        for(int i = 0;i<productionBuildingsCounter;i++) {
            editor.putInt(buildingProduction[i].getName() + "2", buildingProduction[i].getCounter());
            editor.putLong(buildingProduction[i].getName() + "3", Double.doubleToRawLongBits(buildingProduction[i].getPerSecond()));
            editor.putInt(buildingProduction[i].getName() + "4", buildingProduction[i].getPrice());
            editor.apply();
        }
    }
    private void loadProductionBuildingData(){
        for (int i = 0;i<productionBuildingsCounter;i++) {
            buildingProduction[i].setCounter(buildingsSharedPref.getInt(buildingProduction[i].getName() + "2", 0));
            buildingProduction[i].setPerSecond(Double.longBitsToDouble(buildingsSharedPref.getLong(buildingProduction[i].getName() + "3", 0)));
            buildingProduction[i].setPrice(buildingsSharedPref.getInt(buildingProduction[i].getName() + "4", 0));
        }
    }

    private void initializeInfrastructureBuildingsTextView(){

        int ID;
        for (int j=0;j<infrastructureBuildingsCounter;j++) {
            for (int i = 0; i < 5; i++) {

                ID = getResources().getIdentifier(buildingInfrastructure[j].getMaterial() + Integer.toString(i), "id", getPackageName());
                infrastructure[buildingInfrastructure[j].getId()][i] = findViewById(ID);

            }
        }
    }
    private void setInfrastructureTextView() {
        for(int j=0;j<infrastructureBuildingsCounter;j++){
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    infrastructure[buildingInfrastructure[j].getId()][i].setText(buildingInfrastructure[j].getName());
                    break;
                case 1:
                    infrastructure[buildingInfrastructure[j].getId()][i].setText(buildingInfrastructure[j].getDesc());
                    break;
                case 2:
                    infrastructure[buildingInfrastructure[j].getId()][i].setText("Level:  " + buildingInfrastructure[j].getCounterString());
                    break;
                case 3:
                    infrastructure[buildingInfrastructure[j].getId()][i].setText(buildingInfrastructure[j].getCapacityString());
                    break;
                case 4:
                    infrastructure[buildingInfrastructure[j].getId()][i].setText(buildingInfrastructure[j].getPriceString());
                    break;
            }
        }
        }
    }
    private void saveInfrastructureBuildingData(){
        for(int i=0;i<infrastructureBuildingsCounter;i++) {
            editor.putInt(buildingInfrastructure[i].getName() + "2", buildingInfrastructure[i].getCounter());
            editor.putInt(buildingInfrastructure[i].getName() + "3", buildingInfrastructure[i].getCapacity());
            editor.putInt(buildingInfrastructure[i].getName() + "4", buildingInfrastructure[i].getPrice());
            editor.putInt(buildingInfrastructure[i].getName() + "5", buildingInfrastructure[i].getPriceWood());
            editor.putInt(buildingInfrastructure[i].getName() + "6", buildingInfrastructure[i].getPriceStone());
            editor.apply();
        }
    }
    private void loadInfrastructureBuildingData (){
        for(int i = 0; i< infrastructureBuildingsCounter;i++) {
            buildingInfrastructure[i].setCounter(buildingsSharedPref.getInt(buildingInfrastructure[i].getName() + "2", 0));
            buildingInfrastructure[i].setCapacity(buildingsSharedPref.getInt(buildingInfrastructure[i].getName() + "3", 0));
            buildingInfrastructure[i].setPrice(buildingsSharedPref.getInt(buildingInfrastructure[i].getName() + "4", 0));
            buildingInfrastructure[i].setPriceWood(buildingsSharedPref.getInt(buildingInfrastructure[i].getName() + "5", 0));
            buildingInfrastructure[i].setPriceStone(buildingsSharedPref.getInt(buildingInfrastructure[i].getName() + "6", 0));
        }
    }


    private void upgrade (Building _building) {
        if (gold >= _building.getPrice() && materials.getWood()>=_building.getPriceWood() && materials.getStone()>=_building.getPriceStone()) {
            gold = gold - _building.getPrice();
            materials.setStone(materials.getStone()-_building.getPriceStone());
            materials.setWood(materials.getWood()-_building.getPriceWood());
            setMaterialTextViews();
            _building.upgradeBuilding();
            switch(_building.getType()){
                case 1:
                    setProductionTextView();
                    break;
                case 2:
                    setInfrastructureTextView();
                    break;
                }//switch
        }//if

    }
    private void production() {
        product = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (materials.getWood()< buildingInfrastructure[1].getCapacity()) {
                                    materials.setWood((int) (materials.getWood() + buildingProduction[0].getPerSecond()));
                                    amount[1].setText(Integer.toString(materials.getWood())); }
                                if (materials.getStone() < buildingInfrastructure[2].getCapacity()) {
                                    materials.setStone((int) (materials.getStone() + buildingProduction[1].getPerSecond()));
                                    amount[2].setText(Integer.toString(materials.getStone())); }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        product.start();
    }
    private void saveAll(){
        saveMaterialListData();
        saveProductionBuildingData();
        saveInfrastructureBuildingData();

    }
    private void setAll(){
        setMaterialTextViews();
        setInfrastructureTextView();
        setProductionTextView();
    }
    private void loadAll(){
        loadMaterialListData();
        loadInfrastructureBuildingData();
        loadProductionBuildingData();
    }
    private void initializeAll(){
        initializeInfrastructureBuildingsTextView();
        initializeMaterialTextView();
        initializeProductionBuildingsTextView();
        initializeAndSetProductionButtons();
        initializeAndSetInfrastructureButtons();
    }


    private void goBack(){
        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.interrupt();
                saveAll();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("GoldBack", gold);
                resultIntent.putExtra("GoldCapacityBack",buildingInfrastructure[0].getCapacity());
                setResult(RESULT_OK, resultIntent);
                finish(); }});

    }
    private void developer(){
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gold = 0;
                materials.reset();
                for (int i=0;i<productionBuildingsCounter;i++){
                    buildingProduction[i].reset(); }
                for (int i=0;i<infrastructureBuildingsCounter;i++){
                    buildingInfrastructure[i].reset();}
                saveAll();
                setAll(); }});

        Button devRise = findViewById(R.id.woodAddBtn);
        devRise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { gold=500000;
                materials.setWood(50000);
                materials.setStone(50000);
                setMaterialTextViews(); }});

    }

}//END OF CLASS



// legacy
/* MaterialById
public void saveMaterialById(int _id){
        switch (_id){
            case 0:
                editor.putInt("Wood",materials.getWood());
                editor.apply();
                break;
            case 1:
                editor.putInt("Stone",materials.getStone());
                editor.apply();
                break;
        }
    }
 */