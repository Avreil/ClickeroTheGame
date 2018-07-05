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

    private Integer gold,multiplier,counter1=0,price1;
    private float priceFloat1=10;
    private  TextView moneyAmmount;
    private String goldS;
    private TextView upgradeNameLordClick;
    private TextView boughtCount1;
    private TextView unitPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lord_upgrade);

        Intent intent = getIntent();
        moneyAmmount = findViewById(R.id.moneyAmmount);
        boughtCount1 = findViewById(R.id.boughtCount1);
        unitPrice = findViewById(R.id.unitPrice1);
        gold = intent.getIntExtra("GoldToLord", 0);
        multiplier = intent.getIntExtra("MultiplierToLord", 0);
        goldS = Integer.toString(gold);
        if (counter1 == 0) {
                boughtCount1.setText("");
        } else{ boughtCount1.setText(Integer.toString(counter1)); }

        price1=(int)priceFloat1;
        unitPrice.setText(Integer.toString(price1));

        //upgrade names control
        upgradeNameLordClick = findViewById(R.id.upgradeNameLordClick);
        upgradeNameLordClick.setText("Power of Taxes");


        moneyAmmount.setText(goldS);


        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            Intent resultIntent = new Intent();
            resultIntent.putExtra("GoldBack", gold);
            resultIntent.putExtra("MultiplierBack",multiplier);
            setResult(RESULT_OK, resultIntent);
            finish();

            }
        });




        Button buy = findViewById(R.id.buyBtn);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            counter1++;
            multiplier=multiplier*2;
            moneyAmmount.setText(Integer.toString(gold));
            boughtCount1.setText(Integer.toString(counter1));

            }
        });






    }
}
