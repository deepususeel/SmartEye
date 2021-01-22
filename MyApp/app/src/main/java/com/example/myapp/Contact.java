package com.example.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.Locale;

import static android.os.SystemClock.sleep;

public class Contact extends AppCompatActivity implements OnClickListener {

    private int entries = 6;
    private String phoneNum[];
    private String buttonLabels[];
    private Button b1;
    TextToSpeech tts;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        phoneNum = new String[entries];
        buttonLabels = new String[entries];
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        tts.speak("double press on name and Long press to call", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });

        // Populate the data arrays
        populateArrays();


        // Set up buttons and attach click listeners

        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setText(buttonLabels[0]);



        button1.setOnClickListener(this);


        Button button2 = (Button) findViewById(R.id.button2);
        button2.setText(buttonLabels[1]);
        button2.setOnClickListener(this);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setText(buttonLabels[2]);
        button3.setOnClickListener(this);

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setText(buttonLabels[3]);
        button4.setOnClickListener(this);

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setText(buttonLabels[4]);
        button5.setOnClickListener(this);

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setText(buttonLabels[5]);
        button6.setOnClickListener(this);


        final Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(Locale.US);

                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                            } else {
                                tts.speak("Launch Dialer", TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                    }
                });


                }



        });


        button7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                Intent i = new Intent(Contact.this, Dialer.class);

                startActivity(i);


                return false;
            }
        });
    }


    public boolean checkCallPermission() {
        String permission = "android.permission.CALL_PHONE";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    // Launch the phone dialer

    public void launchDialer(String number) {
        String numberToDial = "tel:" + number;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));
    }


    /**
     * Method to populate the data arrays
     */

    public void populateArrays() {

        /** In a practical application the arrays phoneNum and buttonLabels could be
         * updated dynamically from the Web in this method.  For this project we just
         * hard-wire in some values to illustrate how to use such data, once obtained,
         * to make phone calls. The names and numbers are made up.*/

        phoneNum[0] = "9987031750";
        phoneNum[1] = "7021541214";
        phoneNum[2] = "9870584540";
        phoneNum[3] = "8355832869";
        phoneNum[4] = "7738362450";
        phoneNum[5] = "7208480968";

        buttonLabels[0] = "Abel";
        buttonLabels[1] = "Deepu Jio";
        buttonLabels[2] = "Monali";
        buttonLabels[3] = "Rhegi Jio";
        buttonLabels[4] = "Sushant";
        buttonLabels[5] = "Glanda";
        //buttonLabels[6] = "LAUNCH DIALER";
    }

    /**
     * Process button events
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button1:

                final Button button1 = (Button) findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        i++;
                        android.os.Handler handler = new Handler();
                        Runnable runn = new Runnable() {
                            @Override
                            public void run() {
                                i = 0;
                            }
                        };
                        if (i == 1) {
                            // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(runn, 600);

                            final String a;
                            a = button1.getText().toString();

                            tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    if (status == TextToSpeech.SUCCESS) {
                                        int result = tts.setLanguage(Locale.US);

                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                        } else {
                                            tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }

                                }
                            });


                        }

                    }
                });
                button1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        Intent i = new Intent(Contact.this, Dialer.class);
                       // String phn;
                        i.putExtra("phoneno",phoneNum[0]);
                       startActivity(i);


                        return false;
                    }
                });
             /* button1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        final String a;
                        a= button1.getText().toString();

                        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                    int result = tts.setLanguage(Locale.US);

                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                    } else {
                                        tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                                }

                            }
                        });



                    }
                }); */





                break;

            case R.id.button2:

                final Button button2 = (Button) findViewById(R.id.button2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        i++;
                        android.os.Handler handler = new Handler();
                        Runnable runn = new Runnable() {
                            @Override
                            public void run() {
                                i = 0;
                            }
                        };
                        if (i == 1) {
                            // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(runn, 600);

                            final String a;
                            a = button2.getText().toString();

                            tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    if (status == TextToSpeech.SUCCESS) {
                                        int result = tts.setLanguage(Locale.US);

                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                        } else {
                                            tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }

                                }
                            });



                        }

                    }
                });
                button2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        Intent i = new Intent(Contact.this, Dialer.class);
                        // String phn;
                        i.putExtra("phoneno",phoneNum[1]);
                        startActivity(i);


                        return false;
                    }
                });
                break;

            case R.id.button3:
                final Button button3 = (Button) findViewById(R.id.button3);
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        i++;
                        android.os.Handler handler = new Handler();
                        Runnable runn = new Runnable() {
                            @Override
                            public void run() {
                                i = 0;
                            }
                        };
                        if (i == 1) {
                            // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(runn, 600);

                            final String a;
                            a = button3.getText().toString();
                            //tts.speak(a,TextToSpeech.QUEUE_FLUSH,null);

                            tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    if (status == TextToSpeech.SUCCESS) {
                                        int result = tts.setLanguage(Locale.US);

                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                        } else {
                                            tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                                                @Override
                                                public void onInit(int status) {
                                                    if (status == TextToSpeech.SUCCESS) {
                                                        int result = tts.setLanguage(Locale.US);

                                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                                                        }
                                                    }

                                                }
                                            });
                                        }
                                    }

                                }
                            });



                        }

                    }
                });
                button3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        Intent i = new Intent(Contact.this, Dialer.class);
                        // String phn;
                        i.putExtra("phoneno",phoneNum[2]);
                        startActivity(i);


                        return false;
                    }
                });
                break;

            case R.id.button4:
                final Button button4 = (Button) findViewById(R.id.button4);
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        i++;
                        android.os.Handler handler = new Handler();
                        Runnable runn = new Runnable() {
                            @Override
                            public void run() {
                                i = 0;
                            }
                        };
                        if (i == 1) {
                            // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(runn, 600);

                            final String a;
                            a = button4.getText().toString();
                            tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    if (status == TextToSpeech.SUCCESS) {
                                        int result = tts.setLanguage(Locale.US);

                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                        } else {
                                            tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }

                                }
                            });


                        }

                    }
                });
                button4.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        Intent i = new Intent(Contact.this, Dialer.class);
                        // String phn;
                        i.putExtra("phoneno",phoneNum[3]);
                        startActivity(i);


                        return false;
                    }
                });
                break;

            case R.id.button5:

                final Button button5 = (Button) findViewById(R.id.button5);
                button5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        i++;
                        android.os.Handler handler = new Handler();
                        Runnable runn = new Runnable() {
                            @Override
                            public void run() {
                                i = 0;
                            }
                        };
                        if (i == 1) {
                            // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(runn, 600);

                            final String a;
                            a = button5.getText().toString();
                            tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    if (status == TextToSpeech.SUCCESS) {
                                        int result = tts.setLanguage(Locale.US);

                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                        } else {
                                            tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }

                                }
                            });


                        }

                    }
                });
                button5.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        Intent i = new Intent(Contact.this, Dialer.class);
                        // String phn;
                        i.putExtra("phoneno",phoneNum[4]);
                        startActivity(i);


                        return false;
                    }
                });
                break;

            case R.id.button6:
                final Button button6 = (Button) findViewById(R.id.button6);
                button6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        i++;
                        android.os.Handler handler = new Handler();
                        Runnable runn = new Runnable() {
                            @Override
                            public void run() {
                                i = 0;
                            }
                        };
                        if (i == 1) {
                            // Toast.makeText(getApplicationContext(), "Single click", Toast.LENGTH_SHORT).show();
                            handler.postDelayed(runn, 600);

                            final String a;
                            a = button6.getText().toString();
                            tts = new TextToSpeech(Contact.this, new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    if (status == TextToSpeech.SUCCESS) {
                                        int result = tts.setLanguage(Locale.US);

                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                        } else {
                                            tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }

                                }
                            });


                        }

                    }
                });
                button6.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {


                        Intent i = new Intent(Contact.this, Dialer.class);
                        // String phn;
                        i.putExtra("phoneno",phoneNum[5]);
                        startActivity(i);


                        return false;
                    }
                });
                break;
            case R.id.button7:
                final Button button7 = (Button) findViewById(R.id.button7);
                button7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent iii  = new Intent(Contact.this,Dialer.class);
                startActivity(iii);}

        });
                break;
    }
}
}
