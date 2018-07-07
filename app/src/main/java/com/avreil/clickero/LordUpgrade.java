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
    private TextView upgradeName1, boughtCount1, unitPrice1, moneyAmount;
    private SharedPreferences.Editor editor;
    private LordUpgrades upgrade1;
    private SharedPreferences lordSharedPref;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lord_upgrade);
        lordSharedPref = getSharedPreferences("LordUpgradeInfo", MODE_PRIVATE);
        editor = lordSharedPref.edit();



        //load intent
        Intent intent = getIntent();
        gold = intent.getIntExtra("GoldToLord", 0);
        multiplier = intent.getIntExtra("MultiplierToLord", 0);


        //initialize upgrade Class'es
        upgrade1 = new LordUpgrades(10,5,"1");

        //load upgrade data
        loadData(upgrade1);


        //initialize TextViews
        moneyAmount = findViewById(R.id.moneyAmmount);
        boughtCount1 = findViewById(R.id.boughtCount1);
        unitPrice1 = findViewById(R.id.unitPrice1);
        upgradeName1 = findViewById(R.id.upgradeName1);

        //set TextViews
        boughtCount1.setText(Integer.toString(lordSharedPref.getInt("counter1",0)));
        upgradeName1.setText("Power of Taxes");
        moneyAmount.setText(Integer.toString(gold));
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
        Button buy1 = findViewById(R.id.buyBtn1);
        buy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gold >= upgrade1.getPrice() && upgrade1.getCounter()<upgrade1.getLimit()) {
                    itemBought(upgrade1); }
                }
        });

        //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgrade1.resetData();
                multiplier=1;
                saveData(upgrade1);
                boughtCount1.setText(Integer.toString(upgrade1.getCounter()));
                unitPrice1.setText(Integer.toString(upgrade1.getPrice()));

            }
        });

    }


    public void loadData(LordUpgrades _upgrade){
        _upgrade.setCounter(lordSharedPref.getInt("counter"+_upgrade.getIdNumber(),_upgrade.getCounter()));
        _upgrade.setPrice(lordSharedPref.getInt("price"+_upgrade.getIdNumber(),_upgrade.getPrice()));
    }

    public void saveData (LordUpgrades _upgrade){
        editor.putInt("counter"+_upgrade.getIdNumber(), _upgrade.getCounter());
        editor.putInt("price"+_upgrade.getIdNumber(), _upgrade.getPrice());
        editor.apply();
    }

    public void itemBought(LordUpgrades _upgrade) {
        gold=gold-_upgrade.getPrice();
        moneyAmount.setText(Integer.toString(gold));
        calculatedPrice = (_upgrade.getBasePrice()+(_upgrade.getBasePrice() * _upgrade.getCounter()) + (0.4 * _upgrade.getPrice()));
        _upgrade.riseCounter();
        _upgrade.setPrice((int)calculatedPrice);
        moneyAmount.setText(Integer.toString(gold));
        boughtCount1.setText(Integer.toString(_upgrade.getCounter()));
        unitPrice1.setText(Integer.toString((_upgrade.getPrice())));
        multiplier = multiplier +1;
        saveData(_upgrade);

    }
}
