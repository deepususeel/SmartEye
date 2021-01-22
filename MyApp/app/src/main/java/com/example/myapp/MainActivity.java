package com.example.myapp;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.accessibilityservice.*;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.Locale;
//import java.lang.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextToSpeech toSpeech;
    EditText editText;
    Button bspeak, bstop;
    String text;
    int result;
    private Button b;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        bspeak = (Button) findViewById(R.id.bspeak);
        bstop = (Button) findViewById(R.id.bstop);
        b = (Button) findViewById(R.id.button2);


      /*  Intent intent = getIntent();
//get the attached extras from the intent
//we should use the same key as we used to attach the data.



        final String user_name = intent.getStringExtra("Message");
//if you have used any other type of data, you should use the
//particular getExtra method to extract the data from Intet

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        //tts.speak(user_name, TextToSpeech.QUEUE_FLUSH, null);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
                }

                }
        }); */


                bspeak.setOnClickListener(this);
        bstop.setOnClickListener(this);
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //Intent i= new Intent(MainActivity.this,Main2Activity.class);
                //startActivity(i);
            }

        });

        toSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = toSpeech.setLanguage(Locale.UK);
                } else {
                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                    //Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toSpeech != null) {
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bspeak:
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                } else {
                    text = editText.getText().toString();
                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case R.id.bstop:
                if (toSpeech != null) {
                    toSpeech.stop();
                }
                break;
        }

    }

    }

