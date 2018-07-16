package com.avreil.clickero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainGameScreenActivity extends AppCompatActivity {


    private String multiplier ="Multiplier",playerGold="Player Gold",critical="Critical",capacity="Capacity";
    private TextView goldDisplay,multiplierDisplay;
    private SharedPreferences mainGameSharedPref;
    private SharedPreferences.Editor editor;
    private MainGameScreenClass cash = new MainGameScreenClass();
    private Toast criticalToast,bankFull;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basicActivitySetup();           //set GameScreen
        setMainGameSharedPref();        //Prepare save Functionality
        loadData();                     //Load game state
        initializeAndPreSetTextViews(); //Prepare the mainGameActivity TextViews
        getClickInput();                //Main Feature
        startActivities();              //Proceed to the upgrade Lord/Town Activities
        devBtn();                       //buttons to delete after development finish
    }//END OF ON CREATE


    private void setMainGameSharedPref(){
        mainGameSharedPref = getSharedPreferences("MainGameInfo", MODE_PRIVATE);
        editor = mainGameSharedPref.edit();

    }
    private void initializeAndPreSetTextViews(){
        goldDisplay = findViewById(R.id.goldAmmount);
        goldDisplay.setText(Integer.toString(cash.getGold()));
        multiplierDisplay = findViewById(R.id.multiplierAmount);
        multiplierDisplay.setText("Per Click: "+Integer.toString(cash.getMultiplier()));

    }

    private void startActivities(){
        startLordUpgradeActivityBtn();
        startBuildingActivity();
    }
    private void startBuildingActivity(){
        Button buildUp = findViewById(R.id.buildings);
        buildUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { buildingUpgradeActivity(); }}); }
    private void startLordUpgradeActivityBtn(){
        Button lordUp = findViewById(R.id.lordUpgrades);
        lordUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { lordUpgradeActivity(); }}); }
    private void buildingUpgradeActivity(){
        Intent startBuildingUpgrades= new Intent(getApplicationContext(), BuildingActivity.class);
        startBuildingUpgrades.putExtra("GoldToActivity", cash.getGold());
        startActivityForResult(startBuildingUpgrades,1);

    }
    private void lordUpgradeActivity(){
        Intent startLordUpgrades= new Intent(getApplicationContext(), LordUpgradeActivity.class);
        startLordUpgrades.putExtra("GoldToActivity", cash.getGold());
        startLordUpgrades.putExtra("MultiplierToLord", cash.getMultiplier());
        startLordUpgrades.putExtra("CriticalToLord",cash.getCritical());

        startActivityForResult(startLordUpgrades,1);}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cash.setCapacity(data.getIntExtra("GoldCapacityBack", cash.getCapacity()));
        cash.setGold(data.getIntExtra("GoldBack", cash.getGold()));
        cash.setMultiplier(data.getIntExtra("MultiplierBack",cash.getMultiplier()));
        cash.setCritical(data.getIntExtra("CriticalBack",cash.getCritical()));
        goldDisplay.setText(Integer.toString(cash.getGold()));
        multiplierDisplay.setText("Per Click: "+Integer.toString(cash.getMultiplier()));
        //save after coming back
        saveData();



    }

    public void getClickInput(){
        ConstraintLayout gameLayout = findViewById(R.id.MainGameScreen);
        gameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cash.getGold()<cash.getCapacity()){
                    effectOfClick();
                }else{
                    bankFull.makeText(getApplicationContext(),"Bank is Full",Toast.LENGTH_SHORT).show();
                }
            } }); }
    public void effectOfClick(){
            int i = (int)(Math.random()*100);
            if(i <= cash.critical && i != 0) {
                criticalToast.makeText(getApplicationContext(), "Critical Hit! +"+Integer.toString(5*cash.getMultiplier()),
                        Toast.LENGTH_SHORT).show();
                cash.raiseGoldCrit();

            }else{
                cash.raiseGold();}

            goldDisplay.setText(Integer.toString(cash.getGold()));
            saveData();

    }

    public void saveData(){
        editor.putInt(multiplier, cash.getMultiplier());
        editor.putInt(playerGold, cash.getGold());
        editor.putInt(critical, cash.getCritical());
        editor.putInt(capacity, cash.getCapacity());
        editor.apply();

    }
    public void loadData(){
        cash.setGold(mainGameSharedPref.getInt(playerGold,0));
        cash.setMultiplier(mainGameSharedPref.getInt(multiplier,1));
        cash.setCritical(mainGameSharedPref.getInt(critical,0));
        cash.setCapacity(mainGameSharedPref.getInt(capacity,10000));
    }

    public void devBtn(){
        devGoldBtn();
        devResetBtn(); }
    public void devResetBtn(){
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.setGold(0);
                cash.setMultiplier(1);
                cash.setCritical(0);
                goldDisplay.setText(Integer.toString(cash.getGold()));
                multiplierDisplay.setText("Per Click: "+Integer.toString(cash.getMultiplier()));

                saveData();
                }}); }
    public void devGoldBtn(){
        Button devGoldBtn = findViewById(R.id.devGoldBtn);
        devGoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cash.setGold(10000);
                goldDisplay.setText(Integer.toString(cash.getGold()));
                saveData();
            }
        });

    }

    private void basicActivitySetup(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game_screen);}}//END OF CLASS