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
    private int infrastructureBuildingsCounter;
    private Integer gold;
    private SharedPreferences buildingsSharedPref;
    private SharedPreferences.Editor editor;
    private Materials materials;
    private Building[] buildingProduction;
    //production
    private String prod = "production";
    private TextView[][] production;
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
perSecond 3
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
        amount = new TextView[materialCounter];
        buildingProduction = new Building[productionBuildingsCounter];
        materials = new Materials();
        buildingProduction[0]= new Building("Lumber mill","Produce wood","Wood", 100,0);
        buildingProduction[1] = new Building("Quarry", "Mine Stone","Stone", 500,1);
        loadProductionBuildingData(buildingProduction[0]);
        loadProductionBuildingData(buildingProduction[1]);
        loadMaterialListData();

            //initialize and preset textViews
        initializeMaterialTextView(amount,materialCounter);
        initializeProductionTextView(production,buildingProduction[0]);
        setProductionTextView(production,buildingProduction[0]);
        initializeProductionTextView(production,buildingProduction[1]);
        setProductionTextView(production,buildingProduction[1]);

            //declare amount list TextView
        amount[0].setText(Integer.toString(gold));
        amount[1].setText(Integer.toString(materials.getWood()));
        amount[2].setText(Integer.toString(materials.getStone()));


            //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gold = 0;
                amount[0].setText("0");
                materials.setWood(0);
                amount[1].setText("0");
                materials.setStone(0);
                amount[2].setText("0");
                buildingProduction[0].reset();
                buildingProduction[1].reset();
                saveProductionBuildingData(buildingProduction[0]);
                saveProductionBuildingData(buildingProduction[1]);
                setProductionTextView(production,buildingProduction[0]);
                setProductionTextView(production,buildingProduction[1]);
                saveMaterialListData();

            }
        });
        //DevWood

        Button woodAdd = findViewById(R.id.woodAddBtn);
        woodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materials.setWood(500);
                amount[1].setText(Integer.toString(materials.getWood()));
                saveMaterialById(0);
            }
        });


        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wood.interrupt();
                stone.interrupt();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("GoldBack", gold);
                setResult(RESULT_OK, resultIntent);
                finish(); }
        });


        Button ProductionBuyBtn0 = findViewById(R.id.productionBuyBtn0);
        ProductionBuyBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gold>=buildingProduction[0].getPrice()) {
                    gold = gold - buildingProduction[0].getPrice();
                    amount[0].setText(Integer.toString(gold));
                    buildingProduction[0].upgradeBuilding();
                    setProductionTextView(production,buildingProduction[0]);
                    saveProductionBuildingData(buildingProduction[0]);

                }
            }
        });
        Button ProductionBuyBtn1 = findViewById(R.id.productionBuyBtn1);
        ProductionBuyBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gold>=buildingProduction[1].getPrice()) {
                    gold = gold - buildingProduction[1].getPrice();
                    amount[0].setText(Integer.toString(gold));
                    buildingProduction[1].upgradeBuilding();
                    setProductionTextView(production,buildingProduction[1]);
                    saveProductionBuildingData(buildingProduction[1]);

                }
            }
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




    private void initializeMaterialTextView (TextView[] _inputText, int _elementCounter){
        int ID;
        for (int i = 0;i<_elementCounter;i++){
            ID = getResources().getIdentifier(amo+Integer.toString(i),"id",getPackageName());
            _inputText[i]=findViewById(ID);
        }

    }

    public void initializeProductionTextView(TextView[][] _inputText, Building _building){

        int ID;
        for (int i=0;i<5;i++){

            ID = getResources().getIdentifier(_building.getMaterial() + Integer.toString(i), "id", getPackageName());
            _inputText[_building.getId()][i] = findViewById(ID);

        }
    }

    public void setProductionTextView(TextView[][] _inputText, Building _inputBuilding) {

        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    _inputText[_inputBuilding.getId()][i].setText(_inputBuilding.getName());
                    break;
                case 1:
                    _inputText[_inputBuilding.getId()][i].setText(_inputBuilding.getDesc());
                    break;
                case 2:
                    _inputText[_inputBuilding.getId()][i].setText(_inputBuilding.getCounterString());
                    break;
                case 3:
                    _inputText[_inputBuilding.getId()][i].setText(_inputBuilding.getPerSecondString());
                    break;
                case 4:
                    _inputText[_inputBuilding.getId()][i].setText(_inputBuilding.getPriceString());
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

}//END OF CLASS
