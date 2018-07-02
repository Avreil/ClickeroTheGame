package com.avreil.clickero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainGameScreen extends AppCompatActivity {

    //game core
    final ClickAdder cash = new ClickAdder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_game_screen);


        final String SAVE = "SavedGameFile";
        final String playerGold = " savedPlayerGold";
        final SharedPreferences saveGame = getSharedPreferences(SAVE, 0);
        final SharedPreferences loadGame = getSharedPreferences(SAVE, 0);
        final SharedPreferences.Editor editor = saveGame.edit();


        cash.setGold(loadGame.getInt(playerGold, 0));
        final TextView goldDisplay = findViewById(R.id.goldAmmount);
        goldDisplay.setText(cash.getGoldString());

        ConstraintLayout gameLayout = findViewById(R.id.MainGameScreen);
        gameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.raiseGold();
                goldDisplay.setText(cash.getGoldString());
                editor.putInt(playerGold, cash.getGold());
                editor.apply();

            }
        });

        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.setGold(0);
                goldDisplay.setText(cash.getGoldString());
                editor.putInt(playerGold, cash.getGold());
                editor.apply();

            }
        });

        Button lordUp = findViewById(R.id.lordUpgrades);
        lordUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLordUpgrades= new Intent(getApplicationContext(), LordUpgrade.class);
                startActivity(startLordUpgrades);

            }
        });
    }
}