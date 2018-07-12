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

    private Integer gold, multiplier, critical;
    private double calculatedPrice;
    private TextView moneyAmount;
    private TextView upgradeName1, boughtCount1, unitPrice1;
    private TextView upgradeName2, boughtCount2, unitPrice2;
    private SharedPreferences.Editor editor;
    private LordUpgrades upgrade1,upgrade2;
    private SharedPreferences lordSharedPref;
    private String counter = "counter",price="price";









    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lord_upgrade);
        lordSharedPref = getSharedPreferences("LordUpgradeInfo", MODE_PRIVATE);
        editor = lordSharedPref.edit();


        //workspace





        //load intent
        Intent intent = getIntent();
        gold = intent.getIntExtra("GoldToActivity", 0);
        multiplier = intent.getIntExtra("MultiplierToLord", 0);
        critical = intent.getIntExtra("CriticalToLord",0);


        //initialize upgrade Class'es
        upgrade1 = new LordUpgrades(10,5,"1");
        upgrade2 = new LordUpgrades(200,10,"2");

        //load upgrade data
        loadData(upgrade1);
        loadData(upgrade2);

        //initialize TextViews
        moneyAmount = findViewById(R.id.amount0);
        boughtCount1 = findViewById(R.id.boughtCount1);
        unitPrice1 = findViewById(R.id.unitPrice1);
        upgradeName1 = findViewById(R.id.upgradeName1);
        boughtCount2 = findViewById(R.id.boughtCount2);
        unitPrice2 = findViewById(R.id.unitPrice2);
        upgradeName2 = findViewById(R.id.upgradeName2);

        //set TextViews
        moneyAmount.setText(Integer.toString(gold));

        boughtCount1.setText(Integer.toString(lordSharedPref.getInt(counter+upgrade1.getIdNumber(),0)));
        unitPrice1.setText(Integer.toString(upgrade1.getPrice()));
        upgradeName1.setText("Power of Taxes");

        boughtCount2.setText(Integer.toString(lordSharedPref.getInt(counter+upgrade2.getIdNumber(),0)));
        unitPrice2.setText(Integer.toString(upgrade2.getPrice()));
        upgradeName2.setText("Critical Damage");

        //back to the MainGameScreenBtn
        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("GoldBack", gold);
                resultIntent.putExtra("MultiplierBack", multiplier);
                resultIntent.putExtra("CriticalBack",critical);
                setResult(RESULT_OK, resultIntent);
                finish(); }
        });

        //FirstUpgradeBuyBtn
        Button buy1 = findViewById(R.id.buyBtn1);
        buy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gold >= upgrade1.getPrice() && upgrade1.getCounter()<upgrade1.getLimit()) {
                    itemBought(upgrade1);
                    boughtCount1.setText(Integer.toString(upgrade1.getCounter()));
                    unitPrice1.setText(Integer.toString((upgrade1.getPrice())));
                    multiplier = multiplier *3;
                }
                }
        });
        //SecondUpgradeBuyBtn
        Button buy2 = findViewById(R.id.buyBtn2);
        buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gold >= upgrade2.getPrice() && upgrade2.getCounter()<upgrade2.getLimit()) {
                    itemBought(upgrade2);
                    boughtCount2.setText(Integer.toString(upgrade2.getCounter()));
                    unitPrice2.setText(Integer.toString((upgrade2.getPrice())));
                critical = critical +2;}
            }
        });



        //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgrade1.resetData();
                upgrade2.resetData();
                multiplier=1;
                critical=0;
                saveData(upgrade1);
                saveData(upgrade2);
                boughtCount1.setText(Integer.toString(upgrade1.getCounter()));
                unitPrice1.setText(Integer.toString(upgrade1.getPrice()));
                boughtCount2.setText(Integer.toString(upgrade2.getCounter()));
                unitPrice2.setText(Integer.toString(upgrade2.getPrice()));
            }
        });

    }


    public void loadData(LordUpgrades _upgrade){
        _upgrade.setCounter(lordSharedPref.getInt(counter+_upgrade.getIdNumber(),_upgrade.getCounter()));
        _upgrade.setPrice(lordSharedPref.getInt(price+_upgrade.getIdNumber(),_upgrade.getPrice()));
    }

    public void saveData (LordUpgrades _upgrade){
        editor.putInt(counter+_upgrade.getIdNumber(), _upgrade.getCounter());
        editor.putInt(price+_upgrade.getIdNumber(), _upgrade.getPrice());
        editor.apply();
    }

    public void itemBought(LordUpgrades _upgrade) {
        gold=gold-_upgrade.getPrice();
        moneyAmount.setText(Integer.toString(gold));
        calculatedPrice = (_upgrade.getBasePrice()+(_upgrade.getBasePrice() * _upgrade.getCounter()) + (0.4 * _upgrade.getPrice()));
        _upgrade.riseCounter();
        _upgrade.setPrice((int)calculatedPrice);
        saveData(_upgrade);

    }
}
