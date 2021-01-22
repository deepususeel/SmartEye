package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.samples.vision.ocrreader.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */
public class Barcodeact extends Activity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView contactname, contacttitle, contactorganization;
    DatabaseHelper db;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    TextToSpeech toSpeech;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        statusMessage = (TextView) findViewById(R.id.status_message);
        contactname = (TextView) findViewById(R.id.contact_name);
        contacttitle = (TextView) findViewById(R.id.contact_title);
        contactorganization = (TextView) findViewById(R.id.contact_organization);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        db=new DatabaseHelper(this);

        findViewById(R.id.read_barcode).setOnClickListener(this);
        toSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = toSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        toSpeech.speak("click on bottom of the screen to read barcode", TextToSpeech.QUEUE_FLUSH, null);
                    }
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {



            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    int type = barcode.valueFormat;


                    switch (type) {
                        case Barcode.CONTACT_INFO:
                            Toast.makeText(this, "emailss " + type , Toast.LENGTH_LONG).show();


                            Log.i(TAG, barcode.contactInfo.title);
                            Barcode.Email[] set = barcode.contactInfo.emails;
                            List<String> answers = new ArrayList<String>();
                            for (int i = 0; i < set.length; i++){
                                answers.add(set[i].address);

                                Toast.makeText(this, "emails " + answers, Toast.LENGTH_LONG).show();
                            }
                            contactorganization.setText(barcode.contactInfo.organization);
                            contacttitle.setText(barcode.contactInfo.title);
                            Barcode.PersonName name = barcode.contactInfo.name;
                            String contactName = name.first + " " + name.last;
                            contactname.setText(contactName);
                            break;
                        case Barcode.EMAIL:
                            Toast.makeText(this, "emails " + type , Toast.LENGTH_LONG).show();
                            contacttitle.setText(barcode.email.address);
                            contactname.setText("Barcode type: Email");

                            Log.i(TAG, barcode.email.address);
                            break;
                        case Barcode.ISBN:
                            Toast.makeText(this, "isbn " + type , Toast.LENGTH_LONG).show();
                            contacttitle.setText(barcode.rawValue);
                            contactname.setText("Barcode type: ISBN");
                            Log.i(TAG, barcode.rawValue);

                            break;
                        case Barcode.PHONE:
                            Toast.makeText(this, "phone " + type , Toast.LENGTH_LONG).show();

                            Log.i(TAG, barcode.phone.number);
                            break;
                        case Barcode.PRODUCT:
                            //Toast.makeText(this, "product " + type , Toast.LENGTH_LONG).show();

                            contacttitle.setText("Barcode no: " + barcode.rawValue);
                            contactname.setText("Barcode type: Product");
                            String no = barcode.rawValue;
                          // Toast.makeText(this, no  , Toast.LENGTH_LONG).show();

                            Log.i(TAG, barcode.rawValue);

                            SQLiteDatabase dbo = db.getReadableDatabase();
                            final Cursor res = dbo.rawQuery("SELECT DESCR FROM INFO WHERE BCNUMBER =?", new String[]{no});

                            if(res !=null){
                                res.moveToFirst();
                            }
                            do{
                                final String resul =res.getString(0);

                                contactorganization.setText("Product: " + resul);
                                toSpeech = new TextToSpeech(Barcodeact.this, new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if (status == TextToSpeech.SUCCESS) {
                                            int result = toSpeech.setLanguage(Locale.US);

                                            if (result == TextToSpeech.LANG_MISSING_DATA
                                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                            } else {
                                                toSpeech.speak("Product is "+resul, TextToSpeech.QUEUE_FLUSH, null);
                                            }
                                        }

                                    }
                                });
                            } while (res.moveToNext());









                            break;
                        case Barcode.SMS:
                            Toast.makeText(this, "sms " + type , Toast.LENGTH_LONG).show();

                            Log.i(TAG, barcode.sms.message);
                            break;
                        case Barcode.TEXT:
                            contacttitle.setText(barcode.rawValue);
                            String bnoo = barcode.rawValue;
                            Toast.makeText(this, "text " + type + bnoo, Toast.LENGTH_LONG).show();

                            contactname.setText("Barcode type: Text");

                            Log.i(TAG, barcode.rawValue);

                            SQLiteDatabase database = db.getReadableDatabase();

                            if(bnoo!= null) {

                                Cursor curso = database.rawQuery("SELECT DESCR FROM INFO WHERE BCNUMBER = ?", new String[]{bnoo});

                                if (curso != null) {
                                    Toast.makeText(this, "description " + bnoo, Toast.LENGTH_LONG).show();

                                    curso.moveToFirst();
                                }
                                // cursor.moveToNext();

                                String ds;
                                do ds = curso.getString(0); while (curso.moveToNext());

                                contactorganization.setText(ds);


                            }

                            break;
                        case Barcode.URL:
                            Toast.makeText(this, "url " + type , Toast.LENGTH_LONG).show();
                            contacttitle.setText(barcode.url.url);
                            contactname.setText("Barcode type: URL");

                            Log.i(TAG, "url: " + barcode.url.url);

                            break;
                        case Barcode.WIFI:
                            contacttitle.setText(barcode.wifi.ssid);
                            contactname.setText("Barcode type: WIFI");
                            Log.i(TAG, barcode.wifi.ssid);
                            break;
                        case Barcode.GEO:
                            Log.i(TAG, barcode.geoPoint.lat + ":" + barcode.geoPoint.lng);
                            break;
                        case Barcode.CALENDAR_EVENT:
                            Log.i(TAG, barcode.calendarEvent.description);
                            break;
                        case Barcode.DRIVER_LICENSE:
                            Log.i(TAG, barcode.driverLicense.licenseNumber);
                            break;
                        default:
                            Log.i(TAG, barcode.rawValue);
                            break;
                    }
                    statusMessage.setText(R.string.barcode_success);
                    
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    toSpeech = new TextToSpeech(Barcodeact.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = toSpeech.setLanguage(Locale.US);

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                                } else {
                                    toSpeech.speak("No barcode detected", TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });


                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
