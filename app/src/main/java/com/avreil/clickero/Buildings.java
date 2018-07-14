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
    //production
    private String prod = "production";
    private TextView[][] production,infrastructure;
    //materials
    private TextView[] amount;
    private String amo = "amount";
    //threads
    private Thread wood,stone;

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
        //production
        buildingProduction[0]= new Building("Lumber mill","Produce wood","Wood", 100,0,1);
        buildingProduction[1] = new Building("Quarry", "Mine Stone","Stone", 250,1,1);
        loadProductionBuildingData(buildingProduction[0]);
        loadProductionBuildingData(buildingProduction[1]);
        //infrastructure
        buildingInfrastructure[0] = new Building("Bank","Increases gold storage", "goldStorage",1000,0,2);
        buildingInfrastructure[1] = new Building("Wood Storehouse", "Increases wood storage","woodStorage",2000,1,2);
        buildingInfrastructure[2] = new Building("Stone Depot", "Increases stone storage","stoneStorage",4000,2,2);
        loadInfrastructureBuildingData(buildingInfrastructure[0]);
        loadInfrastructureBuildingData(buildingInfrastructure[1]);
        loadInfrastructureBuildingData(buildingInfrastructure[2]);

        loadMaterialListData();

            //initialize and preset textViews |||||
        initializeMaterialTextView();
        //production
        initializeProductionBuildingsTextView(buildingProduction[0]);
        initializeProductionBuildingsTextView(buildingProduction[1]);

        setProductionTextView(buildingProduction[0]);
        setProductionTextView(buildingProduction[1]);

        //infrastructure
        initializeInfrastructureBuildingsTextView(buildingInfrastructure[0]);
        initializeInfrastructureBuildingsTextView(buildingInfrastructure[1]);
        initializeInfrastructureBuildingsTextView(buildingInfrastructure[2]);

        setInfrastructureTextView(buildingInfrastructure[0]);
        setInfrastructureTextView(buildingInfrastructure[1]);
        setInfrastructureTextView(buildingInfrastructure[2]);

            //declare amount list TextView
        setMaterialTextViews();


            //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gold = 0;
                materials.setWood(0);
                materials.setStone(0);
                setMaterialTextViews();

                saveMaterialListData();

                buildingProduction[0].reset();
                buildingProduction[1].reset();
                saveProductionBuildingData(buildingProduction[0]);
                saveProductionBuildingData(buildingProduction[1]);
                setProductionTextView(buildingProduction[0]);
                setProductionTextView(buildingProduction[1]);

                buildingInfrastructure[0].reset();
                buildingInfrastructure[1].reset();
                buildingInfrastructure[2].reset();
                saveInfrastructureBuildingData(buildingInfrastructure[0]);
                saveInfrastructureBuildingData(buildingInfrastructure[0]);
                saveInfrastructureBuildingData(buildingInfrastructure[0]);
                setInfrastructureTextView(buildingInfrastructure[0]);
                setInfrastructureTextView(buildingInfrastructure[1]);
                setInfrastructureTextView(buildingInfrastructure[2]);


            }
        });
        //DevWood

        Button DEVGOLD = findViewById(R.id.woodAddBtn);
        DEVGOLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gold=50000;
                amount[0].setText(Integer.toString(gold));

            }
        });


        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wood.interrupt();
                stone.interrupt();
                saveMaterialListData();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("GoldBack", gold);
                setResult(RESULT_OK, resultIntent);
                finish(); }
        });


        Button ProductionBuyBtn0 = findViewById(R.id.productionBuyBtn0);
        ProductionBuyBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgrade(buildingProduction[0]);}
        });
        Button ProductionBuyBtn1 = findViewById(R.id.productionBuyBtn1);
        ProductionBuyBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    upgrade(buildingProduction[1]);}
        });

        Button InfrastructureBuyBtn0 = findViewById(R.id.infrastructureBuyBtn0);
        InfrastructureBuyBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgrade(buildingInfrastructure[0]); }
        });
        Button InfrastructureBuyBtn1 = findViewById(R.id.infrastructureBuyBtn1);
        InfrastructureBuyBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgrade(buildingInfrastructure[1]);}
        });
        Button InfrastructureBuyBtn2 = findViewById(R.id.infrastructureBuyBtn2);
        InfrastructureBuyBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgrade(buildingInfrastructure[2]);}
        });


        //threads
        wood = new Thread(){
            @Override
            public void run() {
                while(!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                materials.setWood((int)(materials.getWood()+buildingProduction[0].getPerSecond()));
                                amount[1].setText(Integer.toString(materials.getWood()));
                                saveMaterialById(0);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            }
        };
       wood.start();

         stone = new Thread(){
            @Override
            public void run() {
                while(!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                materials.setStone((int)(materials.getStone()+buildingProduction[1].getPerSecond()));
                                amount[2].setText(Integer.toString(materials.getStone()));
                                saveMaterialById(1);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        stone.start();


    }//END OF ON CREATE


    private void setMaterialTextViews(){
        amount[0].setText(Integer.toString(gold));
        amount[1].setText(Integer.toString(materials.getWood()));
        amount[2].setText(Integer.toString(materials.getStone()));
    }

    private void initializeMaterialTextView (){
        int ID;
        for (int i = 0;i<materialCounter;i++){
            ID = getResources().getIdentifier(amo+Integer.toString(i),"id",getPackageName());
            amount[i]=findViewById(ID);
        }

    }

    public void initializeInfrastructureBuildingsTextView(Building _building){

        int ID;
        for (int i=0;i<5;i++){

            ID = getResources().getIdentifier(_building.getMaterial() + Integer.toString(i), "id", getPackageName());
            infrastructure[_building.getId()][i] = findViewById(ID);

        }
    }
    public void initializeProductionBuildingsTextView(Building _building){

        int ID;
        for (int i=0;i<5;i++){

            ID = getResources().getIdentifier(_building.getMaterial() + Integer.toString(i), "id", getPackageName());
            production[_building.getId()][i] = findViewById(ID);

        }
    }


    public void setProductionTextView(Building _inputBuilding) {

        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    production[_inputBuilding.getId()][i].setText(_inputBuilding.getName());
                    break;
                case 1:
                    production[_inputBuilding.getId()][i].setText(_inputBuilding.getDesc());
                    break;
                case 2:
                    production[_inputBuilding.getId()][i].setText(_inputBuilding.getCounterString());
                    break;
                case 3:
                    production[_inputBuilding.getId()][i].setText(_inputBuilding.getPerSecondString());
                    break;
                case 4:
                    production[_inputBuilding.getId()][i].setText(_inputBuilding.getPriceString());
                    break;
            }
        }
    }
    public void setInfrastructureTextView(Building _inputBuilding) {

        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    infrastructure[_inputBuilding.getId()][i].setText(_inputBuilding.getName());
                    break;
                case 1:
                    infrastructure[_inputBuilding.getId()][i].setText(_inputBuilding.getDesc());
                    break;
                case 2:
                    infrastructure[_inputBuilding.getId()][i].setText(_inputBuilding.getCounterString());
                    break;
                case 3:
                    infrastructure[_inputBuilding.getId()][i].setText(_inputBuilding.getCapacityString());
                    break;
                case 4:
                    infrastructure[_inputBuilding.getId()][i].setText(_inputBuilding.getPriceString());
                    break;
            }
        }
    }

    public void loadProductionBuildingData(Building _building){

        _building.setCounter(buildingsSharedPref.getInt(_building.getName()+"2",0));
        _building.setPerSecond(Double.longBitsToDouble(buildingsSharedPref.getLong(_building.getName()+"3",0)));
        _building.setPrice(buildingsSharedPref.getInt(_building.getName()+"4",0));

    }

    public void saveProductionBuildingData(Building _building){
        editor.putInt(_building.getName()+"2",_building.getCounter());
        editor.putLong(_building.getName()+"3",Double.doubleToRawLongBits(_building.getPerSecond()));
        editor.putInt(_building.getName()+"4",_building.getPrice());
        editor.apply();
    }
    public void saveInfrastructureBuildingData(Building _building){
        editor.putInt(_building.getName()+"2",_building.getCounter());
        editor.putInt(_building.getName()+"3",_building.getCapacity());
        editor.putInt(_building.getName()+"4",_building.getPrice());
        editor.putInt(_building.getName()+"5",_building.getPriceWood());
        editor.putInt(_building.getName()+"6",_building.getPriceStone());
        editor.apply();
    }

    public void loadInfrastructureBuildingData (Building _building){
        _building.setCounter(buildingsSharedPref.getInt(_building.getName()+"2",0));
        _building.setCapacity(buildingsSharedPref.getInt(_building.getName()+"3",0));
        _building.setPrice(buildingsSharedPref.getInt(_building.getName()+"4",0));
        _building.setPriceWood(buildingsSharedPref.getInt(_building.getName()+"5",0));
        _building.setPriceStone(buildingsSharedPref.getInt(_building.getName()+"6",0));
    }

    public void loadMaterialListData(){

        materials.setWood(buildingsSharedPref.getInt("Wood", 0));
        materials.setStone(buildingsSharedPref.getInt("Stone",0));
    }

    public void saveMaterialListData(){
        editor.putInt("Wood",materials.getWood());
        editor.putInt("Stone",materials.getStone());
        editor.apply();
    }

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



    public void upgrade (Building _building) {
        if (gold >= _building.getPrice() && materials.getWood()>=_building.getPriceWood() && materials.getStone()>=_building.getPriceStone()) {
            gold = gold - _building.getPrice();
            materials.setStone(materials.getStone()-_building.getPriceStone());
            materials.setWood(materials.getWood()-_building.getPriceWood());
            setMaterialTextViews();
            _building.upgradeBuilding();
            switch(_building.getType()){
                case 1:
                    setProductionTextView(_building);
                    saveProductionBuildingData(_building);
                    break;
                case 2:
                    setInfrastructureTextView(_building);
                    saveInfrastructureBuildingData(_building);
            }//switch
        }//if
    }//function




}//END OF CLASS
