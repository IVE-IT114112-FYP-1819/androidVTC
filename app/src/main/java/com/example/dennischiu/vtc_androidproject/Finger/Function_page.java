package com.example.dennischiu.vtc_androidproject.Finger;


import com.example.dennischiu.vtc_androidproject.Information_setting;
import com.example.dennischiu.vtc_androidproject.R;
import com.example.dennischiu.vtc_androidproject.alert.CalActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Function_page extends AppCompatActivity {

    TextView mTextView_Skip;
    TextView mTextView_Next;

    private TextView mParaLabel;
    private ImageView mFingerprintImage;

    static Handler handler;
    static Vibrator v;

    public static Activity fp;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    MediaPlayer musicPlay;

    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";

    Function_page mFunction_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_page);

        fp = this;

        musicPlay = MediaPlayer.create(this, R.raw.entire);
        musicPlay.start();

        v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.vibrate(60000);

        mFingerprintImage = (ImageView) findViewById(R.id.fingerprintImage);

        mParaLabel = (TextView) findViewById(R.id.paraLabel);

        //Check 1: Android version should be greater or equal to Marshmallow
        //Check 2: Device has Fingerprint scanner
        //Check 3: Have permission to use fingerprint scanner in  the app
        //Check 4: Lock screen is secured with at least 1 type of lock
        // Check 5: At least 1 Fingerprint is registered
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {

                mParaLabel.setText("Fingerprint Scanner not detected in Device");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                    != PackageManager.PERMISSION_GRANTED) {

                mParaLabel.setText("Permission not granted to use Fingerprint Scanner");

            } else if (!keyguardManager.isKeyguardSecure()) {

                mParaLabel.setText("Add Lock To Your Phone in Settings");

            } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                mParaLabel.setText("Your should add at least 1 Fingerprint to use this Feature");

            } else {

                mParaLabel.setText("Lift and rest your finger on the home button");

                generateKey();

                if (cipherInit()) {

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this, this);
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject);

                }

            }

        }

        Intent intent2infor = new Intent(this, Information_setting.class);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                intent2infor.putExtra("running", "sendSMS");
                intent2infor.putExtra("smsOrAlarm", false);
                musicPlay.stop();
                v.cancel();
                Function_page.this.startActivity(intent2infor);
            }
        }, 60000);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                                      KeyGenParameterSpec.Builder(KEY_NAME,
                                                                  KeyProperties.PURPOSE_ENCRYPT |
                                                                          KeyProperties.PURPOSE_DECRYPT)
                                      .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                                      .setUserAuthenticationRequired(true)
                                      .setEncryptionPaddings(
                                              KeyProperties.ENCRYPTION_PADDING_PKCS7)
                                      .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | CertificateException
                | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | NoSuchProviderException e) {

            e.printStackTrace();

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                                         + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                                                        null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {

            return false;

        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

    }

    public void startActivity() {
        Intent intent = new Intent();
        intent.setClass(Function_page.this, CalActivity.class);
        startActivity(intent);
        musicPlay.stop();
        v.cancel();
    }


}


