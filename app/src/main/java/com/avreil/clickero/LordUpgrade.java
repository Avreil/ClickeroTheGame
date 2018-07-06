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

    private Integer gold, multiplier, counter1 , price1,basePrice=10;
    private double calculatedPrice;
    private String goldS;
    private TextView upgradeNameLordClick, boughtCount1, unitPrice1, moneyAmount;
    private SharedPreferences.Editor editor;




    public void itemBought(Integer _price,Integer _basePrice,Integer _counter,String idNumber) {
        gold=gold-_price;
        moneyAmount.setText(Integer.toString(gold));
        calculatedPrice = (_basePrice+(_basePrice * _counter) + (0.4 * _price));
        _counter++;
        moneyAmount.setText(Integer.toString(gold));
        boughtCount1.setText(Integer.toString(_counter));
        unitPrice1.setText(Integer.toString((int)calculatedPrice));
        multiplier = multiplier +1;
        editor.putInt("counter"+idNumber, _counter);
        editor.putInt("price"+idNumber, _price);
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
        editor = lordSharedPref.edit();


        Intent intent = getIntent();
        gold = intent.getIntExtra("GoldToLord", 0);
        multiplier = intent.getIntExtra("MultiplierToLord", 0);

        moneyAmount = findViewById(R.id.moneyAmmount);
        boughtCount1 = findViewById(R.id.boughtCount1);
        unitPrice1 = findViewById(R.id.unitPrice1);
        upgradeNameLordClick = findViewById(R.id.upgradeNameLordClick);

        counter1 = lordSharedPref.getInt("counter"+"1",0);
        price1 = lordSharedPref.getInt("price"+"1",0);


        goldS = Integer.toString(gold);



        boughtCount1.setText(Integer.toString(lordSharedPref.getInt("counter1",0)));
        upgradeNameLordClick.setText("Power of Taxes");
        moneyAmount.setText(goldS);
        unitPrice1.setText(Integer.toString(price1));










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


        Button buy = findViewById(R.id.buyBtn);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gold >= price1 && counter1<10) {
                    itemBought(price1,basePrice,counter1,"1"); }
                }
        });

        //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter1=0;
                price1=10;
                multiplier=1;
                editor.putInt("counter1", counter1);
                editor.putInt("price1", price1);
                editor.apply();
                boughtCount1.setText(Integer.toString(counter1));
                unitPrice1.setText(Integer.toString(price1));

            }
        });

    }



}
