package com.avreil.clickero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.time.TimeTCPClient;

import java.io.IOException;


public class BuildingActivity extends AppCompatActivity {
    private int productionBuildingsCounter = 3;
    private int materialCounter=4;
    private int infrastructureBuildingsCounter=3;
    private Integer gold;
    private SharedPreferences buildingsSharedPref;
    private SharedPreferences.Editor editor;
    private MaterialsClass materialsClass;
    private BuildingClass[] buildingClassProduction, buildingClassInfrastructure;
    private TextView[][] production,infrastructure;
    private TextView[] amount;
    private String amo = "amount";
    private Thread product;
    private Button[] productionBuyBtn,infrastructureBuyBtn;
    private String TIME_SERVER = "time-a.nist.gov";
    private TimeInfo timeInfo;


    private long openTime,closeTime, elapsedTime;


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

/*
checkTheTime DONE
closeTime   DONE
openTime
elapsedTime == openTime-CloseTime
wood and stone production sum
 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basicActivitySetup();       //set GameScreen
        initializeAndSetGameCore(); //initialize Intent/workClasses/TextViews[][]/Buttons[]
        buildClasses();             //build classes
        loadAll();                  //load Data
        initializeAll();            //initialize textViews
        materialsForElapsedTime();  //Add the materials for offline play
        setAll();                   //set textViews
        production();               //start per second thread
        goBack();                   //back to previous activity
        developer();                //buttons to delete after development finish


        Button devHideBtn = findViewById(R.id.devHideBtn);
        devHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView infrastructure = findViewById(R.id.Infrastructure);
                infrastructure.setVisibility(View.INVISIBLE);


            }
        });
        Button devShowBtn = findViewById(R.id.devShowBtn);
        devShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView infrastructure = findViewById(R.id.Infrastructure);
                infrastructure.setVisibility(View.VISIBLE);
            }
        });




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

        buildingClassProduction = new BuildingClass[productionBuildingsCounter];
        buildingClassInfrastructure = new BuildingClass[infrastructureBuildingsCounter];
        materialsClass = new MaterialsClass();

        productionBuyBtn = new Button[productionBuildingsCounter];
        infrastructureBuyBtn = new Button[infrastructureBuildingsCounter];

    }
    private void basicActivitySetup(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buildings);
    }


    private void buildClasses(){
        prepareProductionBuildings();
        prepareInfrastructureBuildings();
    }
    private void prepareProductionBuildings(){
        buildingClassProduction[0]= new BuildingClass("Lumber mill","Produce wood","Wood", 100,0,1);
        buildingClassProduction[1]= new BuildingClass("Quarry", "Gather Stone","Stone", 250,1,1);
        buildingClassProduction[2]= new BuildingClass("Coal mine","Mine Coal","Coal",500,2,1);

    }
    private void prepareInfrastructureBuildings(){
        buildingClassInfrastructure[0] = new BuildingClass("Bank","Increases gold storage", "goldStorage",1000,0,2);
        buildingClassInfrastructure[1] = new BuildingClass("Wood Storehouse", "Increases wood storage","woodStorage",2000,1,2);
        buildingClassInfrastructure[2] = new BuildingClass("Stone Depot", "Increases stone storage","stoneStorage",4000,2,2);


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
                    upgrade(buildingClassProduction[k]);}
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
                    upgrade(buildingClassInfrastructure[k]);}
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
        amount[1].setText(Integer.toString(materialsClass.getWood()));
        amount[2].setText(Integer.toString(materialsClass.getStone()));
        amount[3].setText(Integer.toString(materialsClass.getCoal()));
    }
    private void saveMaterialListData(){
        editor.putInt("Wood", materialsClass.getWood());
        editor.putInt("Stone", materialsClass.getStone());
        editor.putInt("Coal",materialsClass.getCoal());
        editor.apply();
    }
    private void loadMaterialListData(){

        materialsClass.setWood(buildingsSharedPref.getInt("Wood", materialsClass.getWood()));
        materialsClass.setStone(buildingsSharedPref.getInt("Stone",materialsClass.getStone()));
        materialsClass.setCoal(buildingsSharedPref.getInt("Coal",materialsClass.getCoal()));
    }

    private void initializeProductionBuildingsTextView(){

        int ID;
        for(int j = 0; j<productionBuildingsCounter;j++) {
            for (int i = 0; i < 5; i++) {

                ID = getResources().getIdentifier(buildingClassProduction[j].getMaterial() + Integer.toString(i), "id", getPackageName());
                production[buildingClassProduction[j].getId()][i] = findViewById(ID);

            }
        }
    }
    private void setProductionTextView() {

        for (int j = 0; j<productionBuildingsCounter;j++) {
            for (int i = 0; i < 5; i++) {
                switch (i) {
                    case 0:
                        production[buildingClassProduction[j].getId()][i].setText(buildingClassProduction[j].getName());
                        break;
                    case 1:
                        production[buildingClassProduction[j].getId()][i].setText(buildingClassProduction[j].getDesc());
                        break;
                    case 2:
                        production[buildingClassProduction[j].getId()][i].setText("Level:  " + buildingClassProduction[j].getCounterString());
                        break;
                    case 3:
                        production[buildingClassProduction[j].getId()][i].setText(buildingClassProduction[j].getPerSecondString());
                        break;
                    case 4:
                        production[buildingClassProduction[j].getId()][i].setText(buildingClassProduction[j].getPriceString());
                        break;
                }
            }
        }
    }
    private void saveProductionBuildingData(){
        for(int i = 0;i<productionBuildingsCounter;i++) {
            editor.putInt(buildingClassProduction[i].getName() + "2", buildingClassProduction[i].getCounter());
            editor.putLong(buildingClassProduction[i].getName() + "3", Double.doubleToRawLongBits(buildingClassProduction[i].getPerSecond()));
            editor.putInt(buildingClassProduction[i].getName() + "4", buildingClassProduction[i].getPrice());
            editor.apply();
        }
    }
    private void loadProductionBuildingData(){
        for (int i = 0;i<productionBuildingsCounter;i++) {
            buildingClassProduction[i].setCounter(buildingsSharedPref.getInt(buildingClassProduction[i].getName() + "2", buildingClassProduction[i].getCounter()));
            buildingClassProduction[i].setPerSecond(Double.longBitsToDouble(buildingsSharedPref.getLong(buildingClassProduction[i].getName() + "3", 0)));
            buildingClassProduction[i].setPrice(buildingsSharedPref.getInt(buildingClassProduction[i].getName() + "4", buildingClassProduction[i].getPrice()));
        }
    }

    private void initializeInfrastructureBuildingsTextView(){

        int ID;
        for (int j=0;j<infrastructureBuildingsCounter;j++) {
            for (int i = 0; i < 5; i++) {

                ID = getResources().getIdentifier(buildingClassInfrastructure[j].getMaterial() + Integer.toString(i), "id", getPackageName());
                infrastructure[buildingClassInfrastructure[j].getId()][i] = findViewById(ID);

            }
        }
    }
    private void setInfrastructureTextView() {
        for(int j=0;j<infrastructureBuildingsCounter;j++){
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    infrastructure[buildingClassInfrastructure[j].getId()][i].setText(buildingClassInfrastructure[j].getName());
                    break;
                case 1:
                    infrastructure[buildingClassInfrastructure[j].getId()][i].setText(buildingClassInfrastructure[j].getDesc());
                    break;
                case 2:
                    infrastructure[buildingClassInfrastructure[j].getId()][i].setText("Level:  " + buildingClassInfrastructure[j].getCounterString());
                    break;
                case 3:
                    infrastructure[buildingClassInfrastructure[j].getId()][i].setText(buildingClassInfrastructure[j].getCapacityString());
                    break;
                case 4:
                    infrastructure[buildingClassInfrastructure[j].getId()][i].setText(buildingClassInfrastructure[j].getPriceString());
                    break;
            }
        }
        }
    }
    private void saveInfrastructureBuildingData(){
        for(int i=0;i<infrastructureBuildingsCounter;i++) {
            editor.putInt(buildingClassInfrastructure[i].getName() + "2", buildingClassInfrastructure[i].getCounter());
            editor.putInt(buildingClassInfrastructure[i].getName() + "3", buildingClassInfrastructure[i].getCapacity());
            editor.putInt(buildingClassInfrastructure[i].getName() + "4", buildingClassInfrastructure[i].getPrice());
            editor.putInt(buildingClassInfrastructure[i].getName() + "5", buildingClassInfrastructure[i].getPriceWood());
            editor.putInt(buildingClassInfrastructure[i].getName() + "6", buildingClassInfrastructure[i].getPriceStone());
            editor.apply();
        }
    }
    private void loadInfrastructureBuildingData (){
        for(int i = 0; i< infrastructureBuildingsCounter;i++) {
            buildingClassInfrastructure[i].setCounter(buildingsSharedPref.getInt(buildingClassInfrastructure[i].getName() + "2", buildingClassInfrastructure[i].getCounter()));
            buildingClassInfrastructure[i].setCapacity(buildingsSharedPref.getInt(buildingClassInfrastructure[i].getName() + "3", buildingClassInfrastructure[i].getCapacity()));
            buildingClassInfrastructure[i].setPrice(buildingsSharedPref.getInt(buildingClassInfrastructure[i].getName() + "4", buildingClassInfrastructure[i].getPrice()));
            buildingClassInfrastructure[i].setPriceWood(buildingsSharedPref.getInt(buildingClassInfrastructure[i].getName() + "5", buildingClassInfrastructure[i].getPriceWood()));
            buildingClassInfrastructure[i].setPriceStone(buildingsSharedPref.getInt(buildingClassInfrastructure[i].getName() + "6", buildingClassInfrastructure[i].getPriceStone()));
        }
    }

    private void saveTime(){

    closeTime = getDateFromInternet();
    editor.putLong("CloseTime",closeTime );
    editor.apply();
    }
    private void loadTime(){
    closeTime = buildingsSharedPref.getLong("CloseTime",0);
}
    private void upgrade (BuildingClass _buildingClass) {
        if (upgradeCheck(_buildingClass)) {
            upgradeChanges(_buildingClass);
            setMaterialTextViews();
            _buildingClass.upgradeBuilding();
            switch(_buildingClass.getType()){
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
                                if (materialsClass.getWood()< buildingClassInfrastructure[1].getCapacity()) {
                                    materialsClass.setWood((int) (materialsClass.getWood() + buildingClassProduction[0].getPerSecond()));
                                    amount[1].setText(Integer.toString(materialsClass.getWood())); }
                                if (materialsClass.getStone() < buildingClassInfrastructure[2].getCapacity()) {
                                    materialsClass.setStone((int) (materialsClass.getStone() + buildingClassProduction[1].getPerSecond()));
                                    amount[2].setText(Integer.toString(materialsClass.getStone())); }
                            }
                        });
                    } catch (InterruptedException e) {

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
        saveTime();
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
        loadTime();
    }
    private void initializeAll(){
        initializeInfrastructureBuildingsTextView();
        initializeMaterialTextView();
        initializeProductionBuildingsTextView();
        initializeAndSetProductionButtons();
        initializeAndSetInfrastructureButtons();
    }

    private void calculateElapsedTime(){
        openTime = getDateFromInternet();
        elapsedTime = openTime - closeTime;

        System.out.println("DEV---------OpenTime"+openTime);
        System.out.println("DEV---------Close Time"+closeTime);
        System.out.println("DEV---------ElapsedTime"+elapsedTime);

    }
    private long getDateFromInternet(){
        Thread thr = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    try {
                        TimeTCPClient client = new TimeTCPClient();
                        try {

                            do {
                                client.setDefaultTimeout(60000);
                                client.connect("time.nist.gov");
                                materialsClass.setTime(client.getTime());
                            }while (materialsClass.getTime()==0);
                            System.out.println("DEV---------CurrentSecondsClassThread"+materialsClass.getTime());
                        } finally { client.disconnect(); }
                    } catch (IOException e) {  }
                } catch (Exception e) {  } }});
        thr.start();

        try {thr.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("DEV---------CurrentSeondsClass"+materialsClass.getTime());
        return materialsClass.getTime();

    }
    public void materialsForElapsedTime(){
        calculateElapsedTime();
        materialsClass.setWood((int) (materialsClass.getWood() + (buildingClassProduction[0].getPerSecond()*elapsedTime)));

    }

    @Override
    public void onBackPressed(){
        product.interrupt();
        saveAll();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("GoldBack", gold);
        resultIntent.putExtra("GoldCapacityBack", buildingClassInfrastructure[0].getCapacity());
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    private void upgradeChanges(BuildingClass _buildingClass){
        gold = gold - _buildingClass.getPrice();
        materialsClass.setStone(materialsClass.getStone()- _buildingClass.getPriceStone());
        materialsClass.setWood(materialsClass.getWood()- _buildingClass.getPriceWood());
        materialsClass.setCoal(materialsClass.getCoal()- _buildingClass.getPriceCoal());
    }
    private boolean upgradeCheck(BuildingClass _buildingClass){
        boolean result=false;
                if(
                                   gold >= _buildingClass.getPrice()
                                && materialsClass.getWood()>= _buildingClass.getPriceWood()
                                && materialsClass.getStone()>= _buildingClass.getPriceStone()
                                && materialsClass.getCoal()>= _buildingClass.getPriceCoal()
                        ){
                    result=true;
                }
                return result;
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
                resultIntent.putExtra("GoldCapacityBack", buildingClassInfrastructure[0].getCapacity());
                setResult(RESULT_OK, resultIntent);
                finish(); }});

    }
    private void developer(){
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gold = 0;
                materialsClass.reset();
                for (int i=0;i<productionBuildingsCounter;i++){
                    buildingClassProduction[i].reset(); }
                for (int i=0;i<infrastructureBuildingsCounter;i++){
                    buildingClassInfrastructure[i].reset();}
                saveAll();
                setAll(); }});

        Button devRise = findViewById(R.id.woodAddBtn);
        devRise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { gold=500000;
                materialsClass.setWood(50000);
                materialsClass.setStone(50000);
                materialsClass.setCoal(50000);
                setMaterialTextViews(); }});

    }

}//END OF CLASS



// legacy
/* MaterialById
public void saveMaterialById(int _id){
        switch (_id){
            case 0:
                editor.putInt("Wood",materialsClass.getWood());
                editor.apply();
                break;
            case 1:
                editor.putInt("Stone",materialsClass.getStone());
                editor.apply();
                break;
        }
    }
 */