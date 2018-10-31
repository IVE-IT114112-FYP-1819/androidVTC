package com.example.dennischiu.vtc_androidproject;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Information_setting extends AppCompatActivity {

    EditText mEditText_firstname;
    EditText mEditText_lastname;
    EditText mEditText_phone;
    ImageButton mButton_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_setting);


        mEditText_firstname = findViewById(R.id.et_firstname);
        mEditText_lastname = findViewById(R.id.et_lastname);
        mEditText_phone = findViewById(R.id.et_phone);
        mButton_save = findViewById(R.id.btn_save);

        String firstname = getSharedPreferences("Information",MODE_PRIVATE)
                .getString("firstname","");
        mEditText_firstname.setText( firstname );

        String lastname = getSharedPreferences("Information",MODE_PRIVATE)
                .getString("lastname","");
        mEditText_lastname.setText( lastname );

        String phone = getSharedPreferences("Information",MODE_PRIVATE)
                .getString("phone","");
        mEditText_phone.setText( phone );

        mButton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname= mEditText_firstname.getText().toString().trim();
                String lastname= mEditText_lastname.getText().toString().trim();
                String phone= mEditText_phone.getText().toString().trim();
                SharedPreferences pref = getSharedPreferences("Information", MODE_PRIVATE);
                pref.edit()
                        .putString("firstname",firstname)
                        .putString("lastname",lastname)
                        .putString("phone", phone)
                        .commit();

            }
        });



    }
}
