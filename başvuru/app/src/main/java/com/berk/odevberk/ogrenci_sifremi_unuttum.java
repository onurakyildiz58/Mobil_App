package com.berk.odevberk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ogrenci_sifremi_unuttum extends AppCompatActivity
{
    EditText sifresifirla_isim,sifresifirla_numara,sifresifirla_mail;
    Button sifresifirla;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_sifremi_unuttum);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }
}