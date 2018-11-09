package com.example.dennischiu.vtc_androidproject.alert;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dennischiu.vtc_androidproject.Finger.Function_page;
import com.example.dennischiu.vtc_androidproject.Information_setting;
import com.example.dennischiu.vtc_androidproject.R;

public class CalActivity extends AppCompatActivity {

    TextView numA_TextView, numB_TextView;
    EditText answer_EditText;
    Button submit_button;
    Function_page function_page;
    Information_setting mInformation_setting;

    static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        numA_TextView = (TextView) findViewById(R.id.numA);
        numB_TextView = (TextView) findViewById(R.id.numB);
        answer_EditText = (EditText) findViewById(R.id.answer);
        submit_button = (Button) findViewById(R.id.submit_button);

        int numA = (int) Math.floor(Math.random() * 5) + 1;
        int numB = (int) Math.floor(Math.random() * 5) + 1;
        final int answer = numA + numB;

        numA_TextView.setText(String.valueOf(numA));
        numB_TextView.setText(String.valueOf(numB));

        submit_button.setOnClickListener(v -> {

            String text = answer_EditText.getText().toString();
            if (text.equals("")) {
                Toast.makeText(CalActivity.this, "Please Input you answer", Toast.LENGTH_SHORT).show();

                return;
            }
            int check = Integer.parseInt(text);


            if (check == answer) {
                Toast.makeText(CalActivity.this, "You are correct!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Information_setting.class);
                boolean running = true;
                intent.putExtra("running", "CalCorrect");
                startActivity(intent);
                Function_page.fp.finish();

            } else {
                Toast.makeText(CalActivity.this, "You answer is Wrong!", Toast.LENGTH_SHORT).show();
            }


        });

        Intent intent2infor = new Intent(this, Information_setting.class);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                intent2infor.putExtra("running", "sendSMS");
                intent2infor.putExtra("smsOrAlarm", false);
                CalActivity.this.startActivity(intent2infor);
            }
        }, 60000);

    }
}
