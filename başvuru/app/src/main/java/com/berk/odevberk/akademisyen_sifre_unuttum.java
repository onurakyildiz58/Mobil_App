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

        //-----------Durum çubuðunu gizlemek için satýr-------------

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //-----------Akademisyen Þifre Sýfýrlama activitysini açmak için kodlar------------



    }
}