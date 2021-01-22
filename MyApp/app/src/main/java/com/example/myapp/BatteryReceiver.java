package com.example.myapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.Locale;

public class BatteryReceiver extends BroadcastReceiver {

    static  int percentage;

    @Override
    public void onReceive(Context context, Intent intent) {

        TextView statusLabel = ((BatteryMain)context).findViewById(R.id.statusLabel);
        TextView percentageLabel = ((BatteryMain)context).findViewById(R.id.percentageLabel);
        ImageView batteryImage = ((BatteryMain)context).findViewById(R.id.batteryImage);

        String action = intent.getAction();
       String message = "";

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Status
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);


            switch (status) {
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
            statusLabel.setText(message);


            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            percentage = level * 100 / scale;
            percentageLabel.setText(percentage + "%");



            // Image
            Resources res = context.getResources();

            if (percentage >= 90) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b100));

            } else if (90 > percentage && percentage >= 65) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b75));

            } else if (65 > percentage && percentage >= 40) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b50));

            } else if (40 > percentage && percentage >= 15) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b25));

            } else {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b0));

            }

        }
    }


}
