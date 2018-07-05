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

    private Integer gold,multiplier;
    private  TextView moneyAmmount;
    private String goldS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lord_upgrade);

        Intent intent = getIntent();
        moneyAmmount = findViewById(R.id.moneyAmmount);
        gold = intent.getIntExtra("GoldToLord",0);
        multiplier = intent.getIntExtra("MultiplierToLord",0);
        goldS = Integer.toString(gold);






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
            multiplier++;
            moneyAmmount.setText(Integer.toString(gold));
            }
        });






    }
}
