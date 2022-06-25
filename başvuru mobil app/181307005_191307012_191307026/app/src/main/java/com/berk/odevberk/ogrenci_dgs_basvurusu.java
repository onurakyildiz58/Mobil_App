package com.berk.odevberk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class ogrenci_dgs_basvurusu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_dgs_basvuru);

        //-----------Durum çubuðunu gizlemek için satýr-------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //-----------ÖSYM'nin sitesine gitmek için link-------------
        findViewById(R.id.ogrenci_dgs_bsvuru_link_butonu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri dgs_osym_basvuru_sayfasi = Uri.parse("https://ais.osym.gov.tr/yetki/giris");
                Intent istek = new Intent(Intent.ACTION_VIEW, dgs_osym_basvuru_sayfasi);
                startActivity(istek);
            }
        });




    }
}