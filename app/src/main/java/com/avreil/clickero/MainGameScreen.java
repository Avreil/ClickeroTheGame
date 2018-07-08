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

public class MainGameScreen extends AppCompatActivity {

    private int multiplier;
    private Integer goldOut;
    private String mult ="Multiplier";
    private TextView goldDisplay;
    private SharedPreferences mainGameSharedPref,saveGame, loadGame;
    private SharedPreferences.Editor editor;
    private ClickAdder cash;
    private Bundle extras;

public void loadData(ClickAdder _cash){
    
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game_screen);
        editor = mainGameSharedPref.edit();
        mainGameSharedPref = getSharedPreferences("MainGameInfo", MODE_PRIVATE);


        cash = new ClickAdder(0);
        //game core

        extras = new Bundle();



        loadGame();
        goldDisplay = findViewById(R.id.goldAmmount);
        cash.setGold(loadGame.getInt(playerGold, 0));
        goldDisplay.setText(cash.getGoldString());
        multiplier=loadGame.getInt(mult, 1);



        //GamePlay
        ConstraintLayout gameLayout = findViewById(R.id.MainGameScreen);
        gameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.raiseGold(multiplier);
                goldDisplay.setText(cash.getGoldString());
                editor.putInt(playerGold, cash.getGold());
                editor.apply();

            }
        });

        //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.setGold(0);
                multiplier=1;
                goldDisplay.setText(cash.getGoldString());
                editor.putInt(mult,multiplier);
                editor.putInt(playerGold, cash.getGold());
                editor.apply();

            }
        });


        //lordUpgrade activity
        Button lordUp = findViewById(R.id.lordUpgrades);
        lordUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLordUpgrades= new Intent(getApplicationContext(), LordUpgrade.class);
                goldOut = cash.getGold();
                startLordUpgrades.putExtra("GoldToLord", goldOut);
                startLordUpgrades.putExtra("MultiplierToLord", multiplier);

                startActivityForResult(startLordUpgrades,1);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                Integer goldFromLord = data.getIntExtra("GoldBack", 0);
                multiplier = data.getIntExtra("MultiplierBack", 0);

                cash.setGold(goldFromLord);
                goldDisplay.setText(Integer.toString(goldFromLord));



                //save after coming back
                editor.putInt(mult,multiplier);
                editor.putInt(playerGold, cash.getGold());
                editor.apply();

    }

}