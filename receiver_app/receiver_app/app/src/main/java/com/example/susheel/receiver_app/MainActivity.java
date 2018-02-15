package com.example.susheel.receiver_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String currency;
    public static String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onStart() {
        super.onStart();
        currency = getIntent().getExtras().getString("CURRENCY");
        amount = getIntent().getExtras().getString("AMOUNT");
        TextView tv = (TextView)findViewById(R.id.textViewAmount);
        tv.setText("Dollar Amount: $" + amount);
        tv = (TextView)findViewById(R.id.textViewCurrency);
        tv.setText("Convert To: " + currency);
    }



    public void sendBroadcastReply (View view) {
        Log.d("Debug","Entering sendBroadcastReply");
        Double amt = Double.parseDouble(amount);
        Double target = 0.0;
        if (currency.equals("British Pound")) target = amt*0.74;
        else if (currency.equals("Indian Rupee")) target = amt*65;
        else target = amt*0.84;
        String amountConvert = target.toString();
        Intent intent = new Intent();
        intent.setAction("com.example.susheel.receiver_app");
        intent.putExtra("CURRENCY", currency);
        intent.putExtra("AMOUNT", amountConvert);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
        finish();
    }
    public void finishActivityA(View v) {
        MainActivity.this.finish();
    }
}
