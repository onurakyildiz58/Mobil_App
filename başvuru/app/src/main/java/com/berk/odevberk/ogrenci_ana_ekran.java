package com.berk.odevberk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ogrenci_ana_ekran extends AppCompatActivity
{
    Button ogrenci_yaz_okulu, ogrenci_yatay_gecis, ogrenci_ders_intibaki, ogrenci_cap_basvurusu,
    ogrenci_dgs, ogrenci_cikis, ogrenci_bitenbasvuru, ogrenci_devamedenbasvuru;
    TextView Main_OgrNumara, Main_OgrBolum, Main_OgrAd, Main_OgrSoyad;
    ImageView Main_pp;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    String userID=auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_ana_ekran);
        //-----------Durum cubugunu gizlemek icin satir-------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ogrenci_cikis            = (Button)    findViewById(R.id.buton_main_cikis);
        ogrenci_yaz_okulu        = (Button)    findViewById(R.id.buton_ogrenci_basvuruyap_yazokulu);
        ogrenci_yatay_gecis      = (Button)    findViewById(R.id.buton_ogrenci_basvuruyap_yataygecis);
        ogrenci_ders_intibaki    = (Button)    findViewById(R.id.buton_ogrenci_basvuruyap_dersintibaki);
        ogrenci_cap_basvurusu    = (Button)    findViewById(R.id.buton_ogrenci_basvuruyap_cap);
        ogrenci_dgs              = (Button)    findViewById(R.id.buton_ogrenci_basvuruyap_dikeygecis);
        ogrenci_devamedenbasvuru = (Button)    findViewById(R.id.buton_ogrenci_devam_basvurular);
        ogrenci_bitenbasvuru     = (Button)    findViewById(R.id.buton_ogrenci_biten_basvurular);
        Main_OgrAd               = (TextView)  findViewById(R.id.tv_main_OgrAd);
        Main_OgrSoyad            = (TextView)  findViewById(R.id.tv_main_OgrSoyad);
        Main_OgrBolum            = (TextView)  findViewById(R.id.tv_main_OgrBolum);
        Main_OgrNumara           = (TextView)  findViewById(R.id.tv_main_OgrNo);
        Main_pp                  = (ImageView) findViewById(R.id.iv_main_pp);

        //datayý getliyo
        //https://www.youtube.com/watch?v=-plgl1EQ21Q
        DocumentReference docref=fStore.collection("ogrenciler").document(userID);
        docref.addSnapshotListener(ogrenci_ana_ekran.this, new EventListener<DocumentSnapshot>()
        {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error)
            {
                Main_OgrAd.setText(value.getString("isim"));
                Main_OgrSoyad.setText(value.getString("soy isim"));
                Main_OgrBolum.setText(value.getString("bolum"));
                Main_OgrNumara.setText(value.getString("okul numarasi"));
            }
        });//data getlemesi biter

        //foto getliyo
        //https://www.youtube.com/watch?v=xzCsJF9WtPU
        //https://www.youtube.com/watch?v=S109VilU2J0&list=PL2dkge6Cz_wkS4_5Da9oHNLg4LKA6RWcx&index=5
        //https://www.youtube.com/watch?v=kQ0i91XLUeg&list=PL2dkge6Cz_wkS4_5Da9oHNLg4LKA6RWcx&index=6
        //https://www.youtube.com/watch?v=ATjUWvoe8To&list=PL2dkge6Cz_wkS4_5Da9oHNLg4LKA6RWcx&index=7
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference("ProfilFotosu/"+userID+"/profil.jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        mImageRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>()
                {
                    @Override
                    public void onSuccess(byte[] bytes)
                    {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);

                        Main_pp.setMinimumHeight(dm.heightPixels);
                        Main_pp.setMinimumWidth(dm.widthPixels);
                        Main_pp.setImageBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception exception) { }
        });//foto getleme biter

        //cýkýs yapar
        ogrenci_cikis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                auth.signOut();
                Toast.makeText(ogrenci_ana_ekran.this, "cikis Yapildi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),giris.class));
            }
        });//cýkýs yapma biter

        //yaz okulu basvuru ekranini açar
        ogrenci_yaz_okulu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ogrenci_ana_ekran.this, ogrenci_yaz_okulu_basvuru.class));
            }
        });//yaz okulu basvuru ekranini acma sonu

        //yatay gecis ekranýný açar
        ogrenci_yatay_gecis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ogrenci_ana_ekran.this, ogrenci_yatay_gecis_basvuru.class));
            }
        });//yaz gecis ekraný açma sonu

        //ders intibaki ekrani acar
        ogrenci_ders_intibaki.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ogrenci_ana_ekran.this, ogrenci_ders_intibaki_basvuru.class));
            }
        });//ders intibaki açma sonu

        ////çap baþvuru erkaný açar
        ogrenci_cap_basvurusu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ogrenci_ana_ekran.this, ogrenci_cap_basvurusu.class));
            }
        });//çap baþvuru ekranýný açma sonu

        //dgs baþvuru ekranýný açar
        ogrenci_dgs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ogrenci_ana_ekran.this, ogrenci_dgs_basvurusu.class));
            }
        });//dgs baþvuru ekranýný açma sonu

        //biten baþvurular ekranýný açar
        ogrenci_bitenbasvuru.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ogrenci_ana_ekran.this, ogrenci_biten_basvurular.class));
            }
        });//biten baþvurular ekranýný açma sonu

        //devam eden baþvurular ekranýný açar
        ogrenci_devamedenbasvuru.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ogrenci_ana_ekran.this, ogrenci_devam_eden_basvurular.class));
            }
        });//devam eden baþvurular ekranýný açma sonu

    }//main sonu
}