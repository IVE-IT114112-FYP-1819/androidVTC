package com.example.dennischiu.vtc_androidproject;

import com.example.dennischiu.vtc_androidproject.alert.AlarmReceiver;
import com.example.dennischiu.vtc_androidproject.alert.BootUpReceiver;
import com.example.dennischiu.vtc_androidproject.alert.SetAlarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        EditText mEditText_firstname;
        EditText mEditText_lastname;
        EditText mEditText_phone;
        ImageButton mButton_save;

        ImageButton mDialogAlert;

        Button setTime_button;
        NumberPicker hour_numberPicker, mins_numberPicker;
        int hourSet, minSet;

        @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_information_setting);

            mEditText_firstname = findViewById(R.id.et_firstname);
            mEditText_lastname = findViewById(R.id.et_lastname);
            mEditText_phone = findViewById(R.id.et_phone);
            mButton_save = findViewById(R.id.btn_save);
            mDialogAlert = findViewById(R.id.btn_dialog_setAlarm);

            String firstname = getSharedPreferences("Information", MODE_PRIVATE)
                    .getString("firstname", "");
            mEditText_firstname.setText(firstname);

            String lastname = getSharedPreferences("Information", MODE_PRIVATE)
                    .getString("lastname", "");
            mEditText_lastname.setText(lastname);

            String phone = getSharedPreferences("Information", MODE_PRIVATE)
                    .getString("phone", "");
            mEditText_phone.setText(phone);

            mButton_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String firstname = mEditText_firstname.getText().toString().trim();
                    String lastname = mEditText_lastname.getText().toString().trim();
                    String phone = mEditText_phone.getText().toString().trim();
                    SharedPreferences pref = getSharedPreferences("Information", MODE_PRIVATE);
                    pref.edit()
                        .putString("firstname", firstname)
                        .putString("lastname", lastname)
                        .putString("phone", phone)
                        .commit();

                }
            });
            mDialogAlert.setOnClickListener(v -> {
                dialog();
            });

        }

        @SuppressLint("SetTextI18n")
        public void dialog(){
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

                Intent intent = new Intent(this, Information_setting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);   //testing reload the layout. if cannot set alarm  , please delete it //yau

                Toast.makeText(this, R.string.login_error_message, Toast.LENGTH_SHORT)
                     .show();

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
    }

