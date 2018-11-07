package com.example.dennischiu.vtc_androidproject.alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dennischiu.vtc_androidproject.Information_setting;
import com.example.dennischiu.vtc_androidproject.R;

public class CalActivity extends AppCompatActivity {

    TextView numA_TextView, numB_TextView;
    EditText answer_EditText;
    Button submit_button;

    Information_setting mInformation_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        numA_TextView = (TextView) findViewById(R.id.numA);
        numB_TextView = (TextView) findViewById(R.id.numB);
        answer_EditText = (EditText) findViewById(R.id.answer);
        submit_button = (Button) findViewById(R.id.submit_button);

        int numA = (int) Math.floor(Math.random() * 50) + 1;
        int numB = (int) Math.floor(Math.random() * 50) + 1;
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
                intent.putExtra("answer", running);
                startActivity(intent);

            } else {
                Toast.makeText(CalActivity.this, "You answer is Wrong!", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
