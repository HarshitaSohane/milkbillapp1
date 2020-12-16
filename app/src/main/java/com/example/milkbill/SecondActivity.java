package com.example.milkbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tv5,tv6,tv7;
    Button b1;
    milkdb mb;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv5=findViewById(R.id.textView5);
        b1=findViewById(R.id.button7);
        tv6=findViewById(R.id.textView6);
        tv7=findViewById(R.id.textView7);
        mb = new milkdb(this);
        float c=mb.sum_c();
        float b=mb.sum_b();
        tv5.setText("Cost of " +c +" litres of cow milk is "+ c*40);
        tv6.setText("Cost of " +b +" litres of buffalo milk is "+ b*40);
        tv7.setText("Total amount you to pay " + (c*40+ b*40));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });





    }
}