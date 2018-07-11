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
    private Building building1,building2;
    private Materials materials;
    private TextView moneyAmount,stoneAmount,woodAmount;
    private TextView productionName1, productionDesc1, productionCounter1, productionPerSecond1, productionCost1;


    public void declareText(TextView[] _input){
       // https://stackoverflow.com/questions/30228411/using-findviewbyid-inside-a-for-loop-for-multiple-checkboxes
        //https://stackoverflow.com/questions/4730100/android-and-getting-a-view-with-id-cast-as-a-string
        //https://stackoverflow.com/questions/3937010/array-of-imagebuttons-assign-r-view-id-from-a-variable/3937078#3937078
    }

    public void initializeArray(int _i,TextView[] _inputText, String button){
        int i;
        int ID;
        for ( i=0;i<=_i;i++){
            ID = getResources().getIdentifier("production" + Integer.toString(i),
                    "id", getPackageName());
            _inputText[i] = findViewById(ID);
        }

    }

    public void setText(){

    }


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
        Intent intent = getIntent();
        gold = intent.getIntExtra("GoldToActivity", 0);
        buildingsSharedPref = getSharedPreferences("BuildingsUpgradeInfo",0);
        editor = buildingsSharedPref.edit();
        //intent


        //work classes
            materials = new Materials();
            building1= new Building("Lumber mill","Produces wood", 10000);
            building2 = new Building("Quarry", "Mine Stone", 50000);




            //initialize textViews
            initialize();


        //declare list TextView
        moneyAmount.setText(Integer.toString(gold));
        woodAmount.setText(Integer.toString(materials.getWood()));
        stoneAmount.setText(Integer.toString(materials.getStone()));

        //declare production textViews

        productionName1.setText(building1.getName());
        productionDesc1.setText(building1.getDesc());
        productionCounter1.setText("Level:\n "+Integer.toString(building1.getCounter()));
        productionPerSecond1.setText(Double.toString(building1.getPerSecond()));
        productionCost1.setText(Integer.toString(building1.getPrice()));














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





    }//END OF ON CREATE


    private void initialize(){
        //list of materials
        moneyAmount = findViewById(R.id.moneyAmmount);
        woodAmount = findViewById(R.id.woodAmount);
        stoneAmount = findViewById(R.id.stoneAmount);


        //production buildings
        productionName1 = findViewById(R.id.productionName1);
        productionDesc1 = findViewById(R.id.productionDesc1);
        productionCounter1 = findViewById(R.id.productionCounter1);
        productionPerSecond1 = findViewById(R.id.productionPerSecond1);
        productionCost1 = findViewById(R.id.productionCost1);


        //Infrastructure buildings


    }//END OF INITIALIZE




}//END OF CLASS
