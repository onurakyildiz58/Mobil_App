package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2, batteryPercentage, mTextViewPercentage;

    private double batteryCapacity;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        android.content.Context context = getApplicationContext();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(infoDeliver, intentFilter);

        batteryPercentage = findViewById(R.id.battery_percentage);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        mTextViewPercentage = findViewById(R.id.tv_percentage);
        mProgressBar = findViewById(R.id.pb);

        Object mPowerProfile = null;
        String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(this);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            batteryCapacity = (double)Class.forName(POWER_PROFILE_CLASS).getMethod("getAveragePower", java.lang.String.class).invoke(mPowerProfile, "battery.capacity");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private BroadcastReceiver infoDeliver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String charging_status = "", battery_condition = "", power_source="şarj olmuyor";

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int healt = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            if(healt == BatteryManager.BATTERY_HEALTH_COLD)
            {
                battery_condition = "soğuk";
            }
            if(healt == BatteryManager.BATTERY_HEALTH_DEAD)
            {
                battery_condition = "kötü";
            }
            if(healt == BatteryManager.BATTERY_HEALTH_OVERHEAT)
            {
                battery_condition = "aşırı ısınmış";
            }
            if(healt == BatteryManager.BATTERY_HEALTH_GOOD)
            {
                battery_condition = "iyi";
            }
            if(healt == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE)
            {
                battery_condition = "yüksek voltaj";
            }
            if(healt == BatteryManager.BATTERY_HEALTH_UNKNOWN)
            {
                battery_condition = "bilinmiyor";
            }
            if(healt == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE)
            {
                battery_condition = "ölçüm başarısız";
            }

            int temp_C = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)/10;
            int temp_F = (int)(temp_C * 1.8 + 32);

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_AC)
            {
                power_source = "prizden şarj oluyor";
            }
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
            {
                power_source = "usbden şarj oluyor";
            }
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS)
            {
                power_source = "kablosuz şarj oluyor";
            }

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            if(status == BatteryManager.BATTERY_STATUS_CHARGING)
            {
                charging_status = "şarj oluyor";
            }
            if(status == BatteryManager.BATTERY_STATUS_DISCHARGING)
            {
                charging_status = "şarj olmuyor";
            }
            if(status == BatteryManager.BATTERY_STATUS_FULL)
            {
                charging_status = "batarya ful";
            }
            if(status == BatteryManager.BATTERY_STATUS_UNKNOWN)
            {
                charging_status = "bilinmiyor";
            }
            if(status == BatteryManager.BATTERY_STATUS_NOT_CHARGING)
            {
                charging_status = "şarj olmuyor";
            }

            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            batteryPercentage.setText("pil yüzdesi: " + level + "%");
            textView1.setText("durum: \n" +
                              "sıcaklık: \n" +
                              "güc kaynağı: \n" +
                              "şarj durumu: \n" +
                              "tip: \n" +
                              "voltaj: \n" +
                              "kapasite: ");
            textView2.setText(battery_condition + "\n" + "" +
                              temp_C + "" + (char)0x00B0 + "C/" + temp_F + "" + (char)0X00B0 + "F\n" + "" +
                              power_source + "\n" + "" +
                              charging_status + "\n" + "" +
                              technology + "\n" + "" +
                              voltage + "mV\n" + "" +
                              batteryCapacity + "mAh");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            float percentage = levels/(float)scale;
            mProgressStatus = (int) ((percentage) * 100);
            mTextViewPercentage.setText("" + mProgressStatus + "%");
            mProgressBar.setProgress(mProgressStatus);

        }
    };
}