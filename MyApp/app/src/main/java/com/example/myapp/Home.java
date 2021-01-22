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


public class Home extends AppCompatActivity {
    //private TextView resultTEXT;
    TextView txtview;
    TextView ocrr;
    TextView emer,code,objectt;
    CardView msg,ocr,emergency,brcode,obj;

    TextToSpeech toSpeech;
    int i=0;
    //GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        msg =(CardView)findViewById(R.id.msgcard);
        txtview = (TextView) findViewById(R.id.txt);





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

                    final String txtt;

                    txtt = txtview.getText().toString();

                    toSpeech = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(txtt,
                                            TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });


                }
                else if(i==2)
                {
                    //Toast.makeText(getApplicationContext(), "Double click", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Home.this,subhome.class);

                   // intent.putExtra("Message", txtt);
                    startActivity(intent);


                }

/*
                final String txtt;

                txtt = txtview.getText().toString();

                toSpeech = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = toSpeech.setLanguage(Locale.US);

                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                            } else {
                                toSpeech.speak(txtt,
                                        TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                    }
                });



                Intent intent=new Intent(Home.this,subhome.class);

                intent.putExtra("Message", txtt);
                startActivity(intent);

               /* Toast.makeText(getApplicationContext(), "Feature not supported in your deviced", Toast.LENGTH_LONG).show();*/
        }

        });

        ocr =(CardView) findViewById(R.id.ocrcard);
        ocrr = (TextView)findViewById(R.id.textocr);



        ocr.setOnClickListener(new View.OnClickListener() {
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
                    //Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runn, 400);

                    final String ocr;

                    ocr = "Read your documents here and Tap on the text to read";

                    toSpeech = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(ocr, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });

                } else if(i==2) {
                    Intent intent = new Intent(Home.this, com.google.android.gms.samples.vision.ocrreader.OcrCaptureActivity.class);
                    startActivity(intent);
                }

               }
        });


       emergency=(CardView) findViewById(R.id.emergencycard);
        emer = (TextView)findViewById(R.id.textemergency);
        emergency.setOnClickListener(new View.OnClickListener() {
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

                    final String emergency;

                    emergency = emer.getText().toString();

                    toSpeech = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(emergency, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });
                }
                else if(i==2) {
                    Intent intent = new Intent(Home.this, com.example.myapp.emergency.class);


                    startActivity(intent);
                }



            }
        });





        obj=(CardView) findViewById(R.id.objectcard);
        objectt = (TextView)findViewById(R.id.object);
        obj.setOnClickListener(new View.OnClickListener() {
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
                    //Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runn, 400);

                    final String barcodee;

                    barcodee = objectt.getText().toString();

                    toSpeech = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(barcodee, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });
                }
                else if(i==2){
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.tensorflow.lite.examples.detection");
                    if (launchIntent != null) {
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    }

                }




            }
        });



        brcode=(CardView) findViewById(R.id.barcodecard);
        code = (TextView)findViewById(R.id.barcode);
        brcode.setOnClickListener(new View.OnClickListener() {
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
                    //Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runn, 400);

                    final String barcodee;

                    barcodee = code.getText().toString();

                    toSpeech = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak(barcodee, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });
                }
                else if(i==2){
                    Intent intent=new Intent(Home.this,Barcodeact.class);

                    startActivity(intent);}




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
