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
cost 4
 */

/*
ID's
Production
0 = wood

 */

    public void loadData(Building _building){


}

    public void saveData(Building _building){


        editor.apply();
}

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
        buildingProduction[0]= new Building("Lumber mill","Produces wood","Wood", 10000,0);
        buildingProduction[1] = new Building("Quarry", "Mine Stone","Stone", 50000,1);


            //initialize and preset textViews
        initializeMaterialTextView(amount,materialCounter);
        initializeProductionTextView(production,buildingProduction[0]);
        //setTextView(production,buildingProduction[0]);

            //declare amount list TextView
        amount[0].setText(Integer.toString(gold));
        amount[1].setText(Integer.toString(materials.getWood()));
        //amount[2].setText(Integer.toString(materials.getStone()));



            //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gold = 0;
                amount[0].setText("0");
            }
        });

        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("GoldBack", gold);
                setResult(RESULT_OK, resultIntent);
                finish(); }
        });





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

            ID = getResources().getIdentifier(_building.getName() + Integer.toString(i), "id", getPackageName());
            _inputText[_building.getId()][i] = findViewById(ID);

        }
    }

    public void setTextView(TextView[][] _inputText, Building _inputBuilding) {

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



}//END OF CLASS
