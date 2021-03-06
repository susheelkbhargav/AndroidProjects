package com.example.android.asynctaskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView display;
    EditText readings;
    MyAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button generate = (Button) findViewById(R.id.button);
        display = (TextView) findViewById(R.id.textView11);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readings = (EditText) findViewById(R.id.editText);
                int count = Integer.parseInt(readings.getText().toString());
                task = new MyAsyncTask();
                task.execute(count);
            }
        });

        Button cancel = (Button) findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
            }
        });

    }

    public void UIUpdate(MyObject object) {
        TextView t = (TextView) findViewById(R.id.textView6);
        TextView h = (TextView) findViewById(R.id.textView7);
        TextView a = (TextView) findViewById(R.id.textView8);
        t.setText("" + object.temperature + " F");
        h.setText("" + object.humidity + " %");
        a.setText("" + object.activity);
        display.setText(display.getText() + "Output " + object.count + ": \r\n" +
                "Temperature: " + object.temperature + " F\r\n" +
                "Humidity: " + object.humidity + " %\r\n" +
                "Activity:" + object.activity + "\r\n");
    }

    private class MyAsyncTask extends AsyncTask<Integer, MyObject, Void> {
        @Override
        protected void onPreExecute() {
            display.setText("");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... number) {
            int length = (int) number[0];

            for (int i = 1; i <= length; ++i) {
                if (isCancelled())
                    break;
                Random rand = new Random();
                publishProgress(new MyObject(rand.nextInt(256), rand.nextInt(100), rand.nextInt(1000), i));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(MyObject... values) {
            UIUpdate(values[0]);
            super.onProgressUpdate(values);
        }
    }
}


