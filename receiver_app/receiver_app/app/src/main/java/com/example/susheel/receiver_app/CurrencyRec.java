package com.example.susheel.receiver_app;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;


public class CurrencyRec extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.susheel.sender_app")) {
            String message1 = intent.getExtras().getString("CURRENCY");
            String message2 = intent.getExtras().getString("AMOUNT");
            PackageManager pm = context.getPackageManager();
            Intent launchIntent = pm.getLaunchIntentForPackage("com.example.susheel.receiver_app");
            launchIntent.putExtra("CURRENCY", message1);
            launchIntent.putExtra("AMOUNT", message2);
            context.startActivity(launchIntent);
        }
    }
}
