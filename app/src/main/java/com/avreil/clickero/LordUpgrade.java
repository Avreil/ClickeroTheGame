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

public class LordUpgrade extends AppCompatActivity {

    private Integer gold, multiplier;
    private double calculatedPrice;
    private String goldS;
    private TextView upgradeNameLordClick, boughtCount1, unitPrice1, moneyAmount;
    private SharedPreferences.Editor editor;
    private LordUpgrades upgrade1;





    public void itemBought(LordUpgrades _upgrade) {
        gold=gold-_upgrade.getPrice();
        moneyAmount.setText(Integer.toString(gold));
        calculatedPrice = (_upgrade.getBasePrice()+(_upgrade.getBasePrice() * _upgrade.getCounter()) + (0.4 * _upgrade.getPrice()));
        _upgrade.riseCounter();
        moneyAmount.setText(Integer.toString(gold));
        boughtCount1.setText(Integer.toString(_upgrade.getCounter()));
        unitPrice1.setText(Integer.toString((int)calculatedPrice));
        multiplier = multiplier +1;
        editor.putInt("counter"+_upgrade.getIdNumber(), _upgrade.getCounter());
        editor.putInt("price"+_upgrade.getIdNumber(), _upgrade.getPrice());
        editor.apply();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lord_upgrade);
        SharedPreferences lordSharedPref = getSharedPreferences("LordUpgradeInfo", MODE_PRIVATE);

        //initialize upgrade1 Class
        upgrade1 = new LordUpgrades(10,"1");

        //load intent
        Intent intent = getIntent();
        gold = intent.getIntExtra("GoldToLord", 0);
        goldS = Integer.toString(gold);
        multiplier = intent.getIntExtra("MultiplierToLord", 0);


        //load upgrade1 data
        editor = lordSharedPref.edit();
        upgrade1.setCounter(lordSharedPref.getInt("counter"+"1",0));
        upgrade1.setPrice(lordSharedPref.getInt("price"+"1",0));

        //initialize TextViews
        moneyAmount = findViewById(R.id.moneyAmmount);
        boughtCount1 = findViewById(R.id.boughtCount1);
        unitPrice1 = findViewById(R.id.unitPrice1);
        upgradeNameLordClick = findViewById(R.id.upgradeNameLordClick);

        //set TextViews
        boughtCount1.setText(Integer.toString(lordSharedPref.getInt("counter1",0)));
        upgradeNameLordClick.setText("Power of Taxes");
        moneyAmount.setText(goldS);
        unitPrice1.setText(Integer.toString(upgrade1.getPrice()));


        //back to the MainGameScreenBtn
        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("GoldBack", gold);
                resultIntent.putExtra("MultiplierBack", multiplier);
                setResult(RESULT_OK, resultIntent);
                finish(); }
        });

        //FirstUpgradeBuyBtn
        Button buy = findViewById(R.id.buyBtn);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gold >= upgrade1.getPrice() && upgrade1.getCounter()<10) {
                    itemBought(upgrade1); }
                }
        });

        //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgrade1.setCounter(0);
                upgrade1.setPrice(0);
                multiplier=1;
                editor.putInt("counter1", upgrade1.getCounter());
                editor.putInt("price1", upgrade1.getPrice());
                editor.apply();
                boughtCount1.setText(Integer.toString(upgrade1.getCounter()));
                unitPrice1.setText(Integer.toString(upgrade1.getPrice()));

            }
        });

    }



}
