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

public class MainGameScreen extends AppCompatActivity {


    private String multiplier ="Multiplier",playerGold="Player Gold",critical="Critical";
    private TextView goldDisplay;
    private SharedPreferences mainGameSharedPref;
    private SharedPreferences.Editor editor;
    private ClickAdder cash;
    private Toast criticalToast;






    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game_screen);
        mainGameSharedPref = getSharedPreferences("MainGameInfo", MODE_PRIVATE);
        editor = mainGameSharedPref.edit();
        cash = new ClickAdder(0);
        loadData(cash);

        //mainGme TextViews
        goldDisplay = findViewById(R.id.goldAmmount);
        goldDisplay.setText(Integer.toString(cash.getGold()));




        //GamePlay
        ConstraintLayout gameLayout = findViewById(R.id.MainGameScreen);
        gameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((int)(Math.random()*100)) <= cash.critical) {
                    criticalToast.makeText(getApplicationContext(), "Critical Hit!",
                            Toast.LENGTH_SHORT).show();
                    cash.raiseGoldCrit();

                }else{
                    cash.raiseGold();}

                goldDisplay.setText(Integer.toString(cash.getGold()));
                saveData();

            }
        });

        //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.setGold(0);
                cash.setMultiplier(1);
                cash.setCritical(0);
                goldDisplay.setText(Integer.toString(cash.getGold()));
                saveData();

            }
        });


        //lordUpgrade activity
        Button lordUp = findViewById(R.id.lordUpgrades);
        lordUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLordUpgrades= new Intent(getApplicationContext(), LordUpgrade.class);
                startLordUpgrades.putExtra("GoldToActivity", cash.getGold());
                startLordUpgrades.putExtra("MultiplierToLord", cash.getMultiplier());
                startLordUpgrades.putExtra("CriticalToLord",cash.getCritical());

                startActivityForResult(startLordUpgrades,1);


            }
        });
        Button buildUp = findViewById(R.id.buildings);
        buildUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startBuildingUpgrades= new Intent(getApplicationContext(), Buildings.class);
                startBuildingUpgrades.putExtra("GoldToActivity", cash.getGold());
                startActivityForResult(startBuildingUpgrades,1);


            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                cash.setGold(data.getIntExtra("GoldBack", cash.getGold()));
                cash.setMultiplier(data.getIntExtra("MultiplierBack",cash.getMultiplier()));
                cash.setCritical(data.getIntExtra("CriticalBack",cash.getCritical()));
                goldDisplay.setText(Integer.toString(cash.getGold()));
                //save after coming back
                saveData();



    }

    public void saveData(){
        editor.putInt(multiplier, cash.getMultiplier());
        editor.putInt(playerGold, cash.getGold());
        editor.putInt(critical, cash.getCritical());
        editor.apply();
    }

    public void loadData(ClickAdder _cash){
        _cash.setGold(mainGameSharedPref.getInt(playerGold,0));
        _cash.setGold(mainGameSharedPref.getInt(multiplier,0));
        _cash.setCritical(mainGameSharedPref.getInt(critical,0));
    }

}