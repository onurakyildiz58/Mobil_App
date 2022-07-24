package com.example.faceid_fingerprintid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Button btn_auth;
    TextView durum;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_auth = findViewById(R.id.btn_auth);
        durum =  findViewById(R.id.status);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                durum.setText("Hata : " + errString);
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);
                durum.setText("Durum : Başarılı");
            }
            @Override
            public void onAuthenticationFailed()
            {
                super.onAuthenticationFailed();
                durum.setText("Durum : Başarısız");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biyometrik Giriş")
                .setSubtitle("FaceID ya da Parmak İzi Girişi Seçiniz")
                .setNegativeButtonText("İptal")
                .build();

        btn_auth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}