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

    public Integer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lord_upgrade);

        final Intent intent = getIntent();
        final TextView moneyAmmount = findViewById(R.id.moneyAmmount);
        final Integer gold = intent.getIntExtra("GoldToLord",0);
        final String goldS = Integer.toString(gold);

        //initialize variable to work with
        result = gold;


        moneyAmmount.setText(goldS);


        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            Intent resultIntent = new Intent();
            resultIntent.putExtra("GoldBack", result);
            setResult(RESULT_OK, resultIntent);
            finish();

            }
        });

        Button buy = findViewById(R.id.buyBtn);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            result = 10;
            moneyAmmount.setText(Integer.toString(result));
            }
        });






    }
}
