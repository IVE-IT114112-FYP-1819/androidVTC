package com.example.dennischiu.vtc_androidproject;

import com.example.dennischiu.vtc_androidproject.alert.AlarmReceiver;
import com.example.dennischiu.vtc_androidproject.alert.BootUpReceiver;
import com.example.dennischiu.vtc_androidproject.alert.CalActivity;
import com.example.dennischiu.vtc_androidproject.alert.SetAlarm;

import org.w3c.dom.Text;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static android.support.constraint.Constraints.TAG;

public class Information_setting extends AppCompatActivity {

    TextView mErrorMessage;
    EditText mEditText_firstname;
    EditText mEditText_lastname;
    EditText mEditText_phone;

    ImageButton mButton_save;
    ImageButton mImageButton_setting;
    ImageButton mDialogAlert;

    Button setTime_button;
    NumberPicker hour_numberPicker, mins_numberPicker;
    int hourSet, minSet;

    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1000;
    private static final int LOCATION_UPDATE_MIN_TIME = 50;
    private LocationManager mLocationManager;
    private String messages;

    private String firstname, lastname, phone;//information



    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                messages =
                        "https://www.google.com/maps/search/" + location.getLatitude() + "," + location.getLongitude();
            } else {
                Log.d("Test", "Location is null");
                messages = "Can't get lcation";
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String p) {
        }


        @Override
        public void onProviderDisabled(String s) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_setting);


        mEditText_firstname = findViewById(R.id.et_firstname);
        mEditText_lastname = findViewById(R.id.et_lastname);
        mEditText_phone = findViewById(R.id.et_phone);
        mButton_save = findViewById(R.id.btn_save);
        mDialogAlert = findViewById(R.id.btn_dialog_setAlarm);
        mErrorMessage = findViewById(R.id.error_message);
        mImageButton_setting = findViewById(R.id.ImageButton_setting);

        SharedPreferences informotion = getSharedPreferences("Information", MODE_PRIVATE);
        firstname = informotion.getString("firstname", "");
        lastname = informotion.getString("lastname", "");
        phone = informotion.getString("phone", "");
        mEditText_firstname.setText(firstname);
        mEditText_lastname.setText(lastname);
        mEditText_phone.setText(phone);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mButton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputInformationCheck();

                if (mEditText_phone.getText().length() == 8 && mEditText_firstname.getText().length() != 0
                        && mEditText_lastname.getText().length() != 0) {

                    Toast.makeText(getApplicationContext(), "Save Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mImageButton_setting.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "TBD", Toast.LENGTH_SHORT).show();
        });

        mDialogAlert.setOnClickListener(v -> {
            SetAlertdialog();
        });

        Intent intentAnswer = getIntent();
        boolean running = intentAnswer.getBooleanExtra("answer",false);
        if(running){
            SetAlertdialog();
        }

    }



    public void inputInformationCheck() {
        if (mEditText_firstname.getText().length() == 0) {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText("Please Enter Your FirstName");
        } else if (mEditText_lastname.getText().length() == 0) {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText("Please Enter Your LastName");
        } else if (mEditText_phone.getText().length() == 0 || mEditText_phone.getText().length() != 8) {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText("Please Enter Your Phone Number");
        } else {

            firstname = mEditText_firstname.getText().toString().trim();
            lastname = mEditText_lastname.getText().toString().trim();
            phone = mEditText_phone.getText().toString().trim();
            SharedPreferences pref = getSharedPreferences("Information", MODE_PRIVATE);
            pref.edit()
                .putString("firstname", firstname)
                .putString("lastname", lastname)
                .putString("phone", phone)
                .commit();

            mErrorMessage.setVisibility(View.INVISIBLE);

        }
    }

    public void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        getCurrentLocation();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED && !phone.isEmpty()) {
            smsManager.sendTextMessage(phone, null, messages + "", null, null);
        }
    }

    @SuppressLint("SetTextI18n")
    public void SetAlertdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                Information_setting.this);
        View myview = getLayoutInflater().inflate(R.layout.dialog_alert, null);

        setTime_button = (Button) myview.findViewById(R.id.setTime_button);
        hour_numberPicker = (NumberPicker) myview.findViewById(R.id.hour_numberPicker);
        mins_numberPicker = (NumberPicker) myview.findViewById(R.id.mins_numberPicker);

        hour_numberPicker.setMinValue(0);
        hour_numberPicker.setMaxValue(24);
        hour_numberPicker.setValue(0);

        mins_numberPicker.setMinValue(0);
        mins_numberPicker.setMaxValue(60);
        mins_numberPicker.setValue(30);

        setTime_button.setOnClickListener(v -> {

            hourSet = hour_numberPicker.getValue();
            minSet = mins_numberPicker.getValue();

            Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00")); //取得時間
            cal.add(Calendar.HOUR, hourSet);
            cal.add(Calendar.MINUTE, minSet);
            cal.set(Calendar.SECOND, 0);
            add_alarm(Information_setting.this, cal); //註冊鬧鐘

            sendSMS();   //hereeeeeee , send SMS function when Alart is work (testing)

            Toast.makeText(this, R.string.Alarm_settings, Toast.LENGTH_SHORT)
                 .show();

            Intent intent = new Intent(this, Information_setting.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

        ComponentName receiver = new ComponentName(this, BootUpReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                                      PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                      PackageManager.DONT_KILL_APP);

        builder.setView(myview);
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public static void add_alarm(Context context, Calendar cal) {
        Log.d(TAG, "alarm add time: " + String.valueOf(cal.get(Calendar.MONTH)) + "." + String
                .valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal
                .get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));

        Intent intent = new Intent(context, AlarmReceiver.class);
        // 以日期字串組出不同的 category 以添加多個鬧鐘
        intent.addCategory(
                "ID." + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + "-"
                        + String.valueOf((cal.get(Calendar.HOUR_OF_DAY))) + "." + String
                        .valueOf(cal.get(Calendar.MINUTE)) + "." + String.valueOf(cal.get(Calendar.SECOND)));
        String AlarmTimeTag = "Alarmtime " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String
                .valueOf(cal.get(Calendar.MINUTE)) + ":" + String.valueOf(cal.get(Calendar.SECOND));

        intent.putExtra("title", "activity_app");
        intent.putExtra("time", AlarmTimeTag);

        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);       //註冊鬧鐘
    }

    /***    取消(與系統註冊的)鬧鐘    ***/
    private static void cancel_alarm(Context context, Calendar cal) {
        Log.d(TAG, "alarm cancel time: " + String.valueOf(cal.get(Calendar.MONTH)) + "." + String
                .valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal
                .get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));

        Intent intent = new Intent(context, AlarmReceiver.class);
        // 以日期字串組出不同的 category 以添加多個鬧鐘
        intent.addCategory(
                "ID." + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + "-"
                        + String.valueOf((cal.get(Calendar.HOUR_OF_DAY))) + "." + String
                        .valueOf(cal.get(Calendar.MINUTE)) + "." + String.valueOf(cal.get(Calendar.SECOND)));
        String AlarmTimeTag = "Alarmtime " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String
                .valueOf(cal.get(Calendar.MINUTE)) + ":" + String.valueOf(cal.get(Calendar.SECOND));

        intent.putExtra("title", "activity_app");
        intent.putExtra("time", AlarmTimeTag);

        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pi);    //取消鬧鐘，只差在這裡
    }


    private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Location location = null;
        if (!isGPSEnabled) {
            Log.d("Test", "Location do not open");
        } else if (isGPSEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_MIN_TIME,
                                                    LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            messages = "https://www.google.com/maps/search/" + location.getLatitude() + "," + location.getLongitude();
        } else {
            Log.d("Test", "Location is null");
            messages = "Can't get lcation";
        }
    }


}

