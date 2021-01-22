package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.Locale;


public class subhome extends AppCompatActivity {




    CardView msg,phone,battery,clock;
    TextView message,phonee,batt,time;

    TextToSpeech toSpeech;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        msg = (CardView) findViewById(R.id.msgcardd);
        phone = (CardView) findViewById(R.id.phonecard);

        battery = (CardView) findViewById(R.id.batterycard);
        clock = (CardView) findViewById(R.id.clockcard);


        message = (TextView)findViewById(R.id.txt);



        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                i++;
                Handler handler = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                    }
                };
                if(i==1){
                    // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runn,  400);

                    final String msg;

                msg="Check your messages here";

                toSpeech = new TextToSpeech(subhome.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = toSpeech.setLanguage(Locale.US);

                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                            } else {
                                toSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                    }
                });}
                else if(i==2) {
                    Intent ii = new Intent(subhome.this, message.class);
                    startActivity(ii);

                }
            }
        });



        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;
                Handler handler = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                    }
                };
                if(i==1) {
                    // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runn, 400);

                    final String msg;

                    msg = "Check your battery here";

                    toSpeech = new TextToSpeech(subhome.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });
                }
                else if(i==2) {
                    Intent i = new Intent(subhome.this, BatteryMain.class);
                    startActivity(i);
                }

            }
        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;
                Handler handler = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                    }
                };
                if(i==1) {
                    // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runn, 400);

                    final String msg;

                    msg = "Check Time here";

                    toSpeech = new TextToSpeech(subhome.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });
                }
                else if(i==2) {
                    Intent io = new Intent(subhome.this, clock.class);
                    startActivity(io);
                }

            }
        });


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;
                Handler handler = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                    }
                };
                if(i==1) {
                    // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runn, 400);

                    final String msg;

                    msg = "Dial your contacts";

                    toSpeech = new TextToSpeech(subhome.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });
                }
                else if(i==2) {
                    Intent io = new Intent(subhome.this, Contact.class);
                    startActivity(io);
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

}