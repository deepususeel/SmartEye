package com.example.myapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.ocrreader.R;

import static com.example.myapp.BatteryReceiver.percentage;


public class clock extends AppCompatActivity {


TextToSpeech tSpeech;
    TextToSpeech Talktome;
    AnalogClock Clock2;
    TextView ReadText;

    private int mHour, mMinute;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

      //  Talktome = new TextToSpeech(this, this);

        Clock2 = (AnalogClock)findViewById(R.id.analogclock1);
        //Clock2.setOnClickListener(MyAnalogClockOnClickListener);
        ReadText = (TextView)findViewById(R.id.Text2read);

        final Calendar c = Calendar.getInstance();

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        final String myTime = "The Time is " + String.valueOf(mHour) + " Hour " + String.valueOf(mMinute) + " Minute" + " and today's Date is " + date;

        //  ReadText.setText(myTime);
        //Talktome.speak(myTime, TextToSpeech.QUEUE_FLUSH, null);
        tSpeech = new TextToSpeech(clock.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        tSpeech.speak(myTime, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        tSpeech.shutdown();
    }

}


