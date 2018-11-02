package com.example.dennischiu.vtc_androidproject;

import com.example.dennischiu.vtc_androidproject.alert.SetAlarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Information_setting extends AppCompatActivity {

    EditText mEditText_Firstname;
    EditText mEditText_Lastname;
    EditText mEditText_Phone;
    TextView mErrorTest;
    ImageButton mButton_Save;
    ImageButton mSetAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_setting);

        mEditText_Firstname = findViewById(R.id.et_firstname);
        mEditText_Lastname = findViewById(R.id.et_lastname);
        mEditText_Phone = findViewById(R.id.et_phone);
        mButton_Save = findViewById(R.id.btn_save);
        mSetAlert = findViewById(R.id.ib_setAlert);
        mErrorTest = findViewById(R.id.error_message);

        String firstname = getSharedPreferences("Information", MODE_PRIVATE)
                .getString("firstname", "");
        mEditText_Firstname.setText(firstname);

        String lastname = getSharedPreferences("Information", MODE_PRIVATE)
                .getString("lastname", "");
        mEditText_Lastname.setText(lastname);

        String phone = getSharedPreferences("Information", MODE_PRIVATE)
                .getString("phone", "");
        mEditText_Phone.setText(phone);

        mButton_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mEditText_Firstname.getText().length() == 0) {
                    mErrorTest.setVisibility(View.VISIBLE);
                    mErrorTest.setText("Please Enter Your FirstName");
                } else if (mEditText_Lastname.getText().length() == 0) {
                    mErrorTest.setVisibility(View.VISIBLE);
                    mErrorTest.setText("Please Enter Your LastName");
                } else if (mEditText_Phone.getText().length() == 0) {
                    mErrorTest.setVisibility(View.VISIBLE);
                    mErrorTest.setText("Please Enter Your PhoneNumber");
                } else {

                    String firstname = mEditText_Firstname.getText().toString().trim();
                    String lastname = mEditText_Lastname.getText().toString().trim();
                    String phone = mEditText_Phone.getText().toString().trim();
                    SharedPreferences pref = getSharedPreferences("Information", MODE_PRIVATE);
                    pref.edit()
                        .putString("firstname", firstname)
                        .putString("lastname", lastname)
                        .putString("phone", phone)
                        .commit();

                    Toast.makeText(Information_setting.this, R.string.login_error_message, Toast.LENGTH_SHORT)
                         .show();
                }

            }
        });

        mSetAlert.setOnClickListener(v ->

                                     {
                                         Intent intent = new Intent(this, SetAlarm.class);
                                         startActivity(intent);
                                     });


    }
}
