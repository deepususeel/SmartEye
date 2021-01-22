package com.example.myapp;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.os.SystemClock.sleep;


public class message extends Activity {
    private static message inst;
    private final int CHECK_CODE = 0x1;
    private final int LONG_DURATION = 5000;
    private final int SHORT_DURATION = 1100;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;

    private Speaker speaker;

    //private ToggleButton toggle;
    private CompoundButton.OnCheckedChangeListener toggleListener;

    private TextView smsText;
    private TextView smsSender;
    TextToSpeech tts;

    private BroadcastReceiver smsReceiver;
    public static message instance() {
        return inst;
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
        initializeSMSReceiver();
        registerSMSReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_act);

        smsListView = (ListView) findViewById(R.id.SMSList);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);

        smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selectedFromList = (String) (smsListView.getItemAtPosition(position));
                tts = new TextToSpeech(message.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(Locale.US);

                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
                            } else {
                                tts.speak(selectedFromList,
                                        TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                    }
                });
               // speaker.speak(selectedFromList);


            }
        });

        // Add SMS Read Permision At Runtime
        // Todo : If Permission Is Not GRANTED
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            // Todo : If Permission Granted Then Show SMS
            refreshSmsInbox();

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(message.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        //toggle = (ToggleButton)findViewById(R.id.speechToggle);
        smsText = (TextView) findViewById(R.id.sms_text);
        smsSender = (TextView) findViewById(R.id.sms_sender);

        toggleListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    speaker.allow(true);
                    speaker.speak(getString(R.string.start_speaking));
                } else {
                    speaker.speak(getString(R.string.stop_speaking));
                    speaker.allow(false);
                }
            }
        };
        //toggle.setOnCheckedChangeListener(toggleListener);

        checkTTS();
       // initializeSMSReceiver();
        //registerSMSReceiver();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
    @Override
    protected void onStop()
    {
        unregisterReceiver(smsReceiver);
        super.onStop();
    }

    public void refreshSmsInbox () {
            ContentResolver contentResolver = getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");
//            String io= smsInboxCursor.getString(smsInboxCursor.getColumnIndexOrThrow("address"));
  //          Log.d(TAG ,io);
        Log.d(TAG, String.valueOf(indexAddress));
        //    String indexAddres = getApplicationContext(),smsInboxCursor.getString(smsInboxCursor.getColumnIndexOrThrow("address"));
        //Log.d(TAG, getContactName(getApplicationContext(),smsInboxCursor.getString(smsInboxCursor.getColumnIndexOrThrow("address"))));

            if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
            arrayAdapter.clear();
            do {
                String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                        "\n" + smsInboxCursor.getString(indexBody) + "\n";
                arrayAdapter.add(str);
            } while (smsInboxCursor.moveToNext());
        }

        public void updateList ( final String smsMessage){
            arrayAdapter.insert(smsMessage, 0);
            arrayAdapter.notifyDataSetChanged();
        }

        public void onItemClick (AdapterView< ? > parent, View view, int pos, long id) {
            try {
                String[] smsMessages = smsMessagesList.get(pos).split("\n");
                String address = smsMessages[0];
                String smsMessage = "";
                for (int i = 1; i < smsMessages.length; ++i) {
                    smsMessage += smsMessages[i];
                }

                String smsMessageStr = address + "\n";
                smsMessageStr += smsMessage;
                Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
        Log.d(TAG, "checkTTS: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
             //   speaker = new Speaker(this);
            }else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }
    }

    private void initializeSMSReceiver(){
        smsReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: method");

                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    Object[] pdus = (Object[])bundle.get("pdus");
                    for(int i=0;i<pdus.length;i++){
                        byte[] pdu = (byte[])pdus[i];
                        SmsMessage message = SmsMessage.createFromPdu(pdu);
                        final String text = message.getDisplayMessageBody();
                        final String sender = getContactName(message.getOriginatingAddress());
                        Log.d(TAG, sender);
                        //speaker.pause(LONG_DURATION);
                      //  sleep(100);


                        //speaker.speak("You have a new message from" + sender + "!");
                        sleep(100);
//                        speaker.pause(SHORT_DURATION);
                        tts = new TextToSpeech(message.this, new TextToSpeech.OnInitListener() {

                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                    int result = tts.setLanguage(Locale.US);
 {
                                        tts.speak("You hav a new message from "+ sender + "and the message is                  " +  text, TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }

                            }
                        });



                       // tts.speak(text,TextToSpeech.QUEUE_FLUSH, null);
                        //speaker.speak(text);
                        smsSender.setText("Message from " + sender);
                        smsText.setText(text);
                        Log.d(TAG, "initialize ");
                    }
                }

            }
        };
    }

    private String getContactName(String phone){
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        String projection[] = new String[]{ContactsContract.Data.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }else {
            return "unknown number";
        }
    }

    private void registerSMSReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
        Log.d(TAG, "registerSMSReceiver: ");
    }
}
