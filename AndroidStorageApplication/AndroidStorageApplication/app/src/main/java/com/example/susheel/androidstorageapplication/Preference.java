package com.example.susheel.androidstorageapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class Preference extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void finishPreference (View view) {
        finish();
    }

    public void savePreference(View view) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        Set<String> stringSet = new ArraySet<>(2);
        EditText editText = (EditText) findViewById(R.id.editTextPrfName);
        String bookName = editText.getText().toString();
        if (bookName.isEmpty()) {
            Toast.makeText(this, "Please enter book name", Toast.LENGTH_SHORT).show();
            return;
        }
        editText = (EditText) findViewById(R.id.editTextPrefAuth);
        stringSet.add(editText.getText().toString());
        editText = (EditText) findViewById(R.id.editTextPrefDesc);
        stringSet.add(editText.getText().toString());
        editor.putStringSet(bookName, stringSet);
        editor.commit();
        Toast.makeText(this, "Saved !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}
