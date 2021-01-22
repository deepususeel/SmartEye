package com.example.myapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.Locale;

import static java.lang.Thread.sleep;

public class Dialer extends AppCompatActivity implements View.OnClickListener {
    private EditText screenn;
    TextToSpeech toSpeech;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        initializeView();
       toSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = toSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                         //toSpeech.speak("double press on name and Long press to call", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });


        Intent intent = getIntent();
//get the attached extras from the intent
//we should use the same key as we used to attach the data.
        String user_name = intent.getStringExtra("phoneno");


          screenn = (EditText)findViewById(R.id.screen);
          screenn.setText(user_name);



    }

    private void initializeView() {
        screenn = (EditText)findViewById(R.id.screen);
        int idList[] = {R.id.btn1,R.id.btn2,R.id.btn3,
                R.id.btn4,R.id.btn5,R.id.btn6,
                R.id.btn7,R.id.btn8,R.id.btn9,
                R.id.btnDial,R.id.btnDel,R.id.btnStar,
                R.id.btnZero,R.id.btnHash};

        for(int d: idList){
            View v = (View)findViewById(d);
            v.setOnClickListener(this);
        }
    }

    public void display(String val){
        screenn.append(val);
    }

    private boolean checkCallPermission(){
        String permission = "android.permission.CALL_PHONE";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn1:
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                } else {
                   // text = screen.getText().toString();
                    toSpeech.speak("1", TextToSpeech.QUEUE_FLUSH, null);
                }

                display("1");
                break;
            case R.id.btn2:
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                } else {
                    // text = screen.getText().toString();
                    toSpeech.speak("2", TextToSpeech.QUEUE_FLUSH, null);
                }
                display("2");
                break;
            case R.id.btn3:
                toSpeech.speak("3", TextToSpeech.QUEUE_FLUSH, null);
                display("3");
                break;
            case R.id.btn4:
                toSpeech.speak("4", TextToSpeech.QUEUE_FLUSH, null);
                display("4");
                break;
            case R.id.btn5:
                toSpeech.speak("5", TextToSpeech.QUEUE_FLUSH, null);
                display("5");
                break;
            case R.id.btn6:
                toSpeech.speak("6", TextToSpeech.QUEUE_FLUSH, null);
                display("6");
                break;
            case R.id.btn7:
                toSpeech.speak("7", TextToSpeech.QUEUE_FLUSH, null);
                display("7");
                break;
            case R.id.btn8:
                toSpeech.speak("8", TextToSpeech.QUEUE_FLUSH, null);
                display("8");
                break;
            case R.id.btn9:
                toSpeech.speak("9", TextToSpeech.QUEUE_FLUSH, null);
                display("9");
                break;
            case R.id.btnZero:
                toSpeech.speak("0", TextToSpeech.QUEUE_FLUSH, null);
                display("0");
                break;
            case R.id.btnStar:
                toSpeech.speak("star", TextToSpeech.QUEUE_FLUSH, null);
                display("*");
                break;
            case R.id.btnHash:
                toSpeech.speak("#", TextToSpeech.QUEUE_FLUSH, null);
                display("#");
                break;
            case R.id.btnDial:
                toSpeech.speak("DIAL", TextToSpeech.QUEUE_FLUSH, null);
                try {
                    sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(screenn.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter some digits", Toast.LENGTH_SHORT).show();
                else if(checkCallPermission())
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+screenn.getText())));
                break;
            case R.id.btnDel:
                toSpeech.speak("Delete", TextToSpeech.QUEUE_FLUSH, null);
                if(screenn.getText().toString().length()>=1) {
                    String newScreen = screenn.getText().toString().substring(0, screenn.getText().toString().length() - 1);
                    screenn.setText(newScreen);
                }
                break;
            default:
                break;
        }
    }
}