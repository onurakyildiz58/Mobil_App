package com.berk.odevberk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class akademisyen_sifre_unuttum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akademisyen_sifre_unuttum);

        //-----------Durum �ubu�unu gizlemek i�in sat�r-------------

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //-----------Akademisyen �ifre S�f�rlama activitysini a�mak i�in kodlar------------



    }
}