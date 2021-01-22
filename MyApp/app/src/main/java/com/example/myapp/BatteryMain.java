package com.example.myapp;

import android.app.slice.SliceItem;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.Locale;

import static com.example.myapp.BatteryReceiver.percentage;

public class BatteryMain extends AppCompatActivity {

    private BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);



    TextToSpeech tSpeech;

    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery);



        RelativeLayout rl= (RelativeLayout) findViewById(R.id.rlayout);
        final TextView sl = (TextView)findViewById(R.id.statusLabel);
        final TextView pl = (TextView) findViewById(R.id.percentageLabel);
       // final TextView percentageLabel = (TextView)findViewById(R.id.percentageLabel);
       // Intent i= getIntent();

     //   int level = i.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
       // int scale = i.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        //Toast.makeText(getApplicationContext(), scale , Toast.LENGTH_SHORT).show();
    // final int percentage = level * 100 / scale;
      //  percentageLabel.setText(percentage + "%");




          //  int status = i.getIntExtra(BatteryManager.EXTRA_STATUS, -1);


          /*  switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "Full";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    message = "Not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Unknown";
                    break;
            }
*/


                final String sll,pll;

                sll= sl.getText().toString();

                pll = pl.getText().toString();
                //Toast.makeText(getApplicationContext(), pll, Toast.LENGTH_SHORT).show();



                tSpeech = new TextToSpeech(BatteryMain.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tSpeech.setLanguage(Locale.US);

                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                            } else {
                                tSpeech.speak("Your phone battery is"  + sll + "and your battery percentage is " +percentage+ " " , TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                    }
                });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tSpeech != null) {
            tSpeech.stop();
            tSpeech.shutdown();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mBatteryReceiver);
        super.onPause();
    }

}
