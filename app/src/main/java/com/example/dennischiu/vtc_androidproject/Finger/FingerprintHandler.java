package com.example.dennischiu.vtc_androidproject.Finger;


import com.example.dennischiu.vtc_androidproject.R;
import com.example.dennischiu.vtc_androidproject.Second_page;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context mContext;
    private Function_page mFunction_page;

    public FingerprintHandler(Context context, Function_page function_page) {
        this.mContext = context;
        this.mFunction_page = function_page;
    }


    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal , 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Auth Error " + errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed. " , false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: " + helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now access the app. ",  true);
    }

    private void update(String s, boolean b) {
        TextView paraLabel = (TextView)((Activity)mContext).findViewById(R.id.paraLabel);
        ImageView imageView = (ImageView) ((Activity)mContext).findViewById(R.id.fingerprintImage);

        paraLabel.setText(s);
        if(b==false){

            paraLabel.setTextColor(ContextCompat.getColor(mContext,R.color.flower_blue));

        } else{
            paraLabel.setTextColor(ContextCompat.getColor(mContext, R.color.black_alpha));
            imageView.setImageResource(R.mipmap.action_done);
            mFunction_page.startActivity(); //here

        }
    }
}
