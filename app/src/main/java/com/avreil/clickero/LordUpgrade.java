package com.avreil.clickero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LordUpgrade extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lord_upgrade);

        final ClickAdder cash = (ClickAdder)getIntent().getParcelableExtra("Cash");
        final  TextView moneyAmmount = findViewById(R.id.moneyAmmount);



        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopLordUpgrades= new Intent(getApplicationContext(),MainGameScreen.class);
                stopLordUpgrades.putExtra("CashBack",cash);
                stopLordUpgrades.putExtra("ControlValue", 1);
                startActivity(stopLordUpgrades);
            }
        });

        Button buy = findViewById(R.id.buyBtn);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cash.setGold(10);
            moneyAmmount.setText(cash.getGoldString());
            }
        });



        moneyAmmount.setText(cash.getGoldString());


    }
}
