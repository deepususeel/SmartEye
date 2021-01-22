package com.example.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.Locale;

import static android.os.SystemClock.sleep;

public class emergency extends AppCompatActivity {
    Button emergb;

    TextView tv1;
    TextToSpeech tSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);



        emergb = (Button) findViewById(R.id.btnSetEmergency);
        tSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        //tSpeech.speak("Press in middle of the screen", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });



    }


    public void BtnSetEmergency_onClick(View view){

        final String button="Emergency Button";

        tSpeech = new TextToSpeech(emergency.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        tSpeech.speak(button, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });
       // sleep(1000);



        String number = "9930757331";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(emergency.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {

            return;

        }
        startActivity(intent);
    }


    public void Tap(View view) {
        tv1=(TextView) findViewById(R.id.textView2);

        final String emer;
        emer = tv1.getText().toString();

        tSpeech = new TextToSpeech(emergency.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status){
                if (status == TextToSpeech.SUCCESS) {
                    int result = tSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        tSpeech.speak(emer, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        tSpeech.shutdown();

        //super.onDestroy();
    }


}

