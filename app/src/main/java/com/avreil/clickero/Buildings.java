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

public class Buildings extends AppCompatActivity {

    private Integer gold;
    private SharedPreferences buildingsSharedPref;
    private SharedPreferences.Editor editor;
    private Building building1;
    private Materials materials;
    private TextView moneyAmount,stoneAmount,woodAmount;
    private TextView productionName1, productionDesc1, productionCounter1, productionPerSecond1, productionCost1;




    public void loadData(Building _building){


}

    public void saveData(Building _building){


        editor.apply();
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buildings);
            buildingsSharedPref = getSharedPreferences("BuildingsUpgradeInfo",0);
            editor = buildingsSharedPref.edit();
            materials = new Materials();
            //intent
        Intent intent = getIntent();
        gold = intent.getIntExtra("GoldToActivity", 0);


        //initialize TextView
        moneyAmount = findViewById(R.id.moneyAmmount);
        moneyAmount.setText(Integer.toString(gold));
        woodAmount = findViewById(R.id.woodAmount);
        woodAmount.setText(Integer.toString(materials.getWood()));
        stoneAmount = findViewById(R.id.stoneAmount);
        stoneAmount.setText(Integer.toString(materials.getStone()));

        //initialize production textViews
        productionName1 = findViewById(R.id.productionName1);
        productionDesc1 = findViewById(R.id.productionDesc1);
        productionCounter1 = findViewById(R.id.productionCounter1);
        productionPerSecond1 = findViewById(R.id.productionPerSecond1);
        productionCost1 = findViewById(R.id.productionCost1);





        //buildings
        building1= new Building();







        //DevReset
        Button resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gold = 0;
                moneyAmount.setText("0");
            }
        });

        Button BackButton = findViewById(R.id.backBtn);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("GoldBack", gold);
                setResult(RESULT_OK, resultIntent);
                finish(); }
        });
    }







}
