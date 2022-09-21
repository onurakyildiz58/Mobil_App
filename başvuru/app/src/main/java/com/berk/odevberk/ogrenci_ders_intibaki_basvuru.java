package com.berk.odevberk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ogrenci_ders_intibaki_basvuru extends AppCompatActivity
{
    Button Intibak_basvuruindir,Intibak_basvuruyukle;
    CheckBox Intibak_onayala;
    EditText Intibak_OgrAd,Intibak_OgrSoyad,Intibak_OgrNumara,Intibak_OgrSinif,
            Intibak_OgrKayitTur,Intibak_YariYil,Intibak_UniAnlik,Intibak_BolumAnlik,
            Intibak_UniGelecek,Intibak_BolumGelecek,Intibak_dosyaAdi,

            Intibak_IlkYilBasariliDers1,Intibak_IlkYilBasariliDers2,Intibak_IlkYilBasariliDers3,
            Intibak_IlkYilBasariliDers4, Intibak_IlkYilBasariliDers5,Intibak_IlkYilBasariliDers6,
            Intibak_IlkYilBasariliDers7,Intibak_IlkYilBasariliDers8,

            Intibak_IlkYilGelecekDers1,Intibak_IlkYilGelecekDers2,Intibak_IlkYilGelecekDers3,
            Intibak_IlkYilGelecekDers4,Intibak_IlkYilGelecekDers5,Intibak_IlkYilGelecekDers6,
            Intibak_IlkYilGelecekDers7,Intibak_IlkYilGelecekDers8,

            Intibak_IlkYilBasariliDers1ATKS,Intibak_IlkYilBasariliDers2ATKS,Intibak_IlkYilBasariliDers3ATKS,
            Intibak_IlkYilBasariliDers4ATKS,Intibak_IlkYilBasariliDers5ATKS,Intibak_IlkYilBasariliDers6ATKS,
            Intibak_IlkYilBasariliDers7ATKS,Intibak_IlkYilBasariliDers8ATKS,

            Intibak_IlkYilGelecekDers1ATKS,Intibak_IlkYilGelecekDers2ATKS,Intibak_IlkYilGelecekDers3ATKS,
            Intibak_IlkYilGelecekDers4ATKS,Intibak_IlkYilGelecekDers5ATKS,Intibak_IlkYilGelecekDers6ATKS,
            Intibak_IlkYilGelecekDers7ATKS,Intibak_IlkYilGelecekDers8ATKS,

            Intibak_IkinciYilBasariliDers1,Intibak_IkinciYilBasariliDers2,Intibak_IkinciYilBasariliDers3,
            Intibak_IkinciYilBasariliDers4,Intibak_IkinciYilBasariliDers5,Intibak_IkinciYilBasariliDers6,
            Intibak_IkinciYilBasariliDers7,Intibak_IkinciYilBasariliDers8,

            Intibak_IkinciYilGelecekDers1,Intibak_IkinciYilGelecekDers2,Intibak_IkinciYilGelecekDers3,
            Intibak_IkinciYilGelecekDers4,Intibak_IkinciYilGelecekDers5,Intibak_IkinciYilGelecekDers6,
            Intibak_IkinciYilGelecekDers7,Intibak_IkinciYilGelecekDers8,

            Intibak_IkinciYilBasariliDers1ATKS,Intibak_IkinciYilBasariliDers2ATKS,Intibak_IkinciYilBasariliDers3ATKS,
            Intibak_IkinciYilBasariliDers4ATKS,Intibak_IkinciYilBasariliDers5ATKS,Intibak_IkinciYilBasariliDers6ATKS,
            Intibak_IkinciYilBasariliDers7ATKS,Intibak_IkinciYilBasariliDers8ATKS,

            Intibak_IkinciYilGelecekDers1ATKS,Intibak_IkinciYilGelecekDers2ATKS,Intibak_IkinciYilGelecekDers3ATKS,
            Intibak_IkinciYilGelecekDers4ATKS,Intibak_IkinciYilGelecekDers5ATKS,Intibak_IkinciYilGelecekDers6ATKS,
            Intibak_IkinciYilGelecekDers7ATKS,Intibak_IkinciYilGelecekDers8ATKS,

            Intibak_UcuncuYilBasariliDers1,Intibak_UcuncuYilBasariliDers2,Intibak_UcuncuYilBasariliDers3,
            Intibak_UcuncuYilBasariliDers4, Intibak_UcuncuYilBasariliDers5,Intibak_UcuncuYilBasariliDers6,
            Intibak_UcuncuYilBasariliDers7,Intibak_UcuncuYilBasariliDers8,

            Intibak_UcuncuYilGelecekDers1,Intibak_UcuncuYilGelecekDers2,Intibak_UcuncuYilGelecekDers3,
            Intibak_UcuncuYilGelecekDers4,Intibak_UcuncuYilGelecekDers5,Intibak_UcuncuYilGelecekDers6,
            Intibak_UcuncuYilGelecekDers7,Intibak_UcuncuYilGelecekDers8,

            Intibak_UcuncuYilBasariliDers1ATKS,Intibak_UcuncuYilBasariliDers2ATKS,Intibak_UcuncuYilBasariliDers3ATKS,
            Intibak_UcuncuYilBasariliDers4ATKS,Intibak_UcuncuYilBasariliDers5ATKS,Intibak_UcuncuYilBasariliDers6ATKS,
            Intibak_UcuncuYilBasariliDers7ATKS,Intibak_UcuncuYilBasariliDers8ATKS,

            Intibak_UcuncuYilGelecekDers1ATKS,Intibak_UcuncuYilGelecekDers2ATKS,Intibak_UcuncuYilGelecekDers3ATKS,
            Intibak_UcuncuYilGelecekDers4ATKS,Intibak_UcuncuYilGelecekDers5ATKS,Intibak_UcuncuYilGelecekDers6ATKS,
            Intibak_UcuncuYilGelecekDers7ATKS,Intibak_UcuncuYilGelecekDers8ATKS,

            Intibak_DorduncuYilBasariliDers1,Intibak_DorduncuYilBasariliDers2,Intibak_DorduncuYilBasariliDers3,
            Intibak_DorduncuYilBasariliDers4,Intibak_DorduncuYilBasariliDers5,Intibak_DorduncuYilBasariliDers6,
            Intibak_DorduncuYilBasariliDers7,Intibak_DorduncuYilBasariliDers8,

            Intibak_DorduncuYilGelecekDers1,Intibak_DorduncuYilGelecekDers2,Intibak_DorduncuYilGelecekDers3,
            Intibak_DorduncuYilGelecekDers4,Intibak_DorduncuYilGelecekDers5,Intibak_DorduncuYilGelecekDers6,
            Intibak_DorduncuYilGelecekDers7,Intibak_DorduncuYilGelecekDers8,

            Intibak_DorduncuYilBasariliDers1ATKS,Intibak_DorduncuYilBasariliDers2ATKS,Intibak_DorduncuYilBasariliDers3ATKS,
            Intibak_DorduncuYilBasariliDers4ATKS,Intibak_DorduncuYilBasariliDers5ATKS,Intibak_DorduncuYilBasariliDers6ATKS,
            Intibak_DorduncuYilBasariliDers7ATKS,Intibak_DorduncuYilBasariliDers8ATKS,

            Intibak_DorduncuYilGelecekDers1ATKS,Intibak_DorduncuYilGelecekDers2ATKS,Intibak_DorduncuYilGelecekDers3ATKS,
            Intibak_DorduncuYilGelecekDers4ATKS,Intibak_DorduncuYilGelecekDers5ATKS,Intibak_DorduncuYilGelecekDers6ATKS,
            Intibak_DorduncuYilGelecekDers7ATKS,Intibak_DorduncuYilGelecekDers8ATKS;

    String dosyaAdi="";
    String durum="";
    int pageHeight = 1120 ;
    int pagewidth = 792;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    DatabaseReference dbref1= FirebaseDatabase.getInstance().getReference("ogrencibasvurular");
    DatabaseReference dbref2= FirebaseDatabase.getInstance().getReference("akademisyenbasvurular");
    FirebaseStorage storage;
    String userID=auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_ders_intibaki_basvuru);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intibak_basvuruindir=(Button) findViewById(R.id.button_ders_intibaki_basvuru_belge_indir);
        Intibak_basvuruyukle=(Button) findViewById(R.id.button_ders_intibaki_basvuru_belge_yukle);
        Intibak_onayala=(CheckBox) findViewById(R.id.checkbox_ders_intibaki_basvuru_onay);
        Intibak_OgrAd=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ad);
        Intibak_OgrSoyad=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_soyad);
        Intibak_OgrNumara=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ogrno);
        Intibak_OgrKayitTur=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_kayitturu);
        Intibak_OgrSinif=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ogryili);
        Intibak_YariYil=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_intiedilecekyariyil);
        Intibak_UniAnlik=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_devametyukogrkurum);
        Intibak_BolumAnlik=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_devametyukogrkurumbolum);
        Intibak_UniGelecek=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_intetyukogrkurum);
        Intibak_BolumGelecek=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_intetyukogrkurumbolum);
        Intibak_dosyaAdi=(EditText) findViewById(R.id.intibak_dosyaadi);

        Intibak_IlkYilBasariliDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders1);
        Intibak_IlkYilBasariliDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders2);
        Intibak_IlkYilBasariliDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders3);
        Intibak_IlkYilBasariliDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders4);
        Intibak_IlkYilBasariliDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders5);
        Intibak_IlkYilBasariliDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders6);
        Intibak_IlkYilBasariliDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders7);
        Intibak_IlkYilBasariliDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders8);
        Intibak_IlkYilGelecekDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders9);
        Intibak_IlkYilGelecekDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders10);
        Intibak_IlkYilGelecekDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders11);
        Intibak_IlkYilGelecekDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders12);
        Intibak_IlkYilGelecekDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders13);
        Intibak_IlkYilGelecekDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders14);
        Intibak_IlkYilGelecekDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders15);
        Intibak_IlkYilGelecekDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_ders16);
        Intibak_IlkYilBasariliDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts1);
        Intibak_IlkYilBasariliDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts2);
        Intibak_IlkYilBasariliDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts3);
        Intibak_IlkYilBasariliDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts4);
        Intibak_IlkYilBasariliDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts5);
        Intibak_IlkYilBasariliDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts6);
        Intibak_IlkYilBasariliDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts7);
        Intibak_IlkYilBasariliDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts8);
        Intibak_IlkYilGelecekDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts9);
        Intibak_IlkYilGelecekDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts10);
        Intibak_IlkYilGelecekDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts11);
        Intibak_IlkYilGelecekDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts12);
        Intibak_IlkYilGelecekDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts13);
        Intibak_IlkYilGelecekDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts14);
        Intibak_IlkYilGelecekDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts15);
        Intibak_IlkYilGelecekDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_1yariyil_akts16);

        Intibak_IkinciYilBasariliDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders1);
        Intibak_IkinciYilBasariliDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders2);
        Intibak_IkinciYilBasariliDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders3);
        Intibak_IkinciYilBasariliDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders4);
        Intibak_IkinciYilBasariliDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders5);
        Intibak_IkinciYilBasariliDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders6);
        Intibak_IkinciYilBasariliDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders7);
        Intibak_IkinciYilBasariliDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders8);
        Intibak_IkinciYilGelecekDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders9);
        Intibak_IkinciYilGelecekDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders10);
        Intibak_IkinciYilGelecekDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders11);
        Intibak_IkinciYilGelecekDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders12);
        Intibak_IkinciYilGelecekDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders13);
        Intibak_IkinciYilGelecekDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders14);
        Intibak_IkinciYilGelecekDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders15);
        Intibak_IkinciYilGelecekDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_ders16);
        Intibak_IkinciYilBasariliDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts1);
        Intibak_IkinciYilBasariliDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts2);
        Intibak_IkinciYilBasariliDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts3);
        Intibak_IkinciYilBasariliDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts4);
        Intibak_IkinciYilBasariliDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts5);
        Intibak_IkinciYilBasariliDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts6);
        Intibak_IkinciYilBasariliDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts7);
        Intibak_IkinciYilBasariliDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts8);
        Intibak_IkinciYilGelecekDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts9);
        Intibak_IkinciYilGelecekDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts10);
        Intibak_IkinciYilGelecekDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts11);
        Intibak_IkinciYilGelecekDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts12);
        Intibak_IkinciYilGelecekDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts13);
        Intibak_IkinciYilGelecekDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts14);
        Intibak_IkinciYilGelecekDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts15);
        Intibak_IkinciYilGelecekDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_2yariyil_akts16);

        Intibak_UcuncuYilBasariliDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders1);
        Intibak_UcuncuYilBasariliDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders2);
        Intibak_UcuncuYilBasariliDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders3);
        Intibak_UcuncuYilBasariliDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders4);
        Intibak_UcuncuYilBasariliDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders5);
        Intibak_UcuncuYilBasariliDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders6);
        Intibak_UcuncuYilBasariliDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders7);
        Intibak_UcuncuYilBasariliDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders8);
        Intibak_UcuncuYilGelecekDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders9);
        Intibak_UcuncuYilGelecekDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders10);
        Intibak_UcuncuYilGelecekDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders11);
        Intibak_UcuncuYilGelecekDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders12);
        Intibak_UcuncuYilGelecekDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders13);
        Intibak_UcuncuYilGelecekDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders14);
        Intibak_UcuncuYilGelecekDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders15);
        Intibak_UcuncuYilGelecekDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_ders16);
        Intibak_UcuncuYilBasariliDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts1);
        Intibak_UcuncuYilBasariliDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts2);
        Intibak_UcuncuYilBasariliDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts3);
        Intibak_UcuncuYilBasariliDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts4);
        Intibak_UcuncuYilBasariliDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts5);
        Intibak_UcuncuYilBasariliDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts6);
        Intibak_UcuncuYilBasariliDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts7);
        Intibak_UcuncuYilBasariliDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts8);
        Intibak_UcuncuYilGelecekDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts9);
        Intibak_UcuncuYilGelecekDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts10);
        Intibak_UcuncuYilGelecekDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts11);
        Intibak_UcuncuYilGelecekDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts12);
        Intibak_UcuncuYilGelecekDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts13);
        Intibak_UcuncuYilGelecekDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts14);
        Intibak_UcuncuYilGelecekDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts15);
        Intibak_UcuncuYilGelecekDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_3yariyil_akts16);

        Intibak_DorduncuYilBasariliDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders1);
        Intibak_DorduncuYilBasariliDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders2);
        Intibak_DorduncuYilBasariliDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders3);
        Intibak_DorduncuYilBasariliDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders4);
        Intibak_DorduncuYilBasariliDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders5);
        Intibak_DorduncuYilBasariliDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders6);
        Intibak_DorduncuYilBasariliDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders7);
        Intibak_DorduncuYilBasariliDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders8);
        Intibak_DorduncuYilGelecekDers1=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders9);
        Intibak_DorduncuYilGelecekDers2=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders10);
        Intibak_DorduncuYilGelecekDers3=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders11);
        Intibak_DorduncuYilGelecekDers4=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders12);
        Intibak_DorduncuYilGelecekDers5=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders13);
        Intibak_DorduncuYilGelecekDers6=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders14);
        Intibak_DorduncuYilGelecekDers7=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders15);
        Intibak_DorduncuYilGelecekDers8=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_ders16);
        Intibak_DorduncuYilBasariliDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts1);
        Intibak_DorduncuYilBasariliDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts2);
        Intibak_DorduncuYilBasariliDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts3);
        Intibak_DorduncuYilBasariliDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts4);
        Intibak_DorduncuYilBasariliDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts5);
        Intibak_DorduncuYilBasariliDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts6);
        Intibak_DorduncuYilBasariliDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts7);
        Intibak_DorduncuYilBasariliDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts8);
        Intibak_DorduncuYilGelecekDers1ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts9);
        Intibak_DorduncuYilGelecekDers2ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts10);
        Intibak_DorduncuYilGelecekDers3ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts11);
        Intibak_DorduncuYilGelecekDers4ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts12);
        Intibak_DorduncuYilGelecekDers5ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts13);
        Intibak_DorduncuYilGelecekDers6ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts14);
        Intibak_DorduncuYilGelecekDers7ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts15);
        Intibak_DorduncuYilGelecekDers8ATKS=(EditText) findViewById(R.id.et_ders_intibaki_basvuru_ders_secimi_4yariyil_akts16);

        Intibak_onayala.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Intibak_basvuruindir.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            //pdf creat
                            String ad           = Intibak_OgrAd.getText().toString();
                            String soyad        = Intibak_OgrSoyad.getText().toString();
                            String numara       = Intibak_OgrNumara.getText().toString();
                            String kayittur     = Intibak_OgrKayitTur.getText().toString();
                            String sinif        = Intibak_OgrSinif.getText().toString();
                            String yariyil      = Intibak_YariYil.getText().toString();
                            String unianlik     = Intibak_UniAnlik.getText().toString();
                            String bolumanlik   = Intibak_BolumAnlik.getText().toString();
                            String unigelecek   = Intibak_UniGelecek.getText().toString();
                            String bolumgelecek = Intibak_BolumGelecek.getText().toString();

                            String birders1=Intibak_IlkYilBasariliDers1.getText().toString();
                            String birders2=Intibak_IlkYilBasariliDers2.getText().toString();
                            String birders3=Intibak_IlkYilBasariliDers3.getText().toString();
                            String birders4=Intibak_IlkYilBasariliDers4.getText().toString();
                            String birders5=Intibak_IlkYilBasariliDers5.getText().toString();
                            String birders6=Intibak_IlkYilBasariliDers6.getText().toString();
                            String birders7=Intibak_IlkYilBasariliDers7.getText().toString();
                            String birders8=Intibak_IlkYilBasariliDers8.getText().toString();
                            String birders9=Intibak_IlkYilGelecekDers1.getText().toString();
                            String birders10=Intibak_IlkYilGelecekDers2.getText().toString();
                            String birders11=Intibak_IlkYilGelecekDers3.getText().toString();
                            String birders12=Intibak_IlkYilGelecekDers4.getText().toString();
                            String birders13=Intibak_IlkYilGelecekDers5.getText().toString();
                            String birders14=Intibak_IlkYilGelecekDers6.getText().toString();
                            String birders15=Intibak_IlkYilGelecekDers7.getText().toString();
                            String birders16=Intibak_IlkYilGelecekDers8.getText().toString();
                            String birakts1=Intibak_IlkYilBasariliDers1ATKS.getText().toString();
                            String birakts2=Intibak_IlkYilBasariliDers2ATKS.getText().toString();
                            String birakts3=Intibak_IlkYilBasariliDers3ATKS.getText().toString();
                            String birakts4=Intibak_IlkYilBasariliDers4ATKS.getText().toString();
                            String birakts5=Intibak_IlkYilBasariliDers5ATKS.getText().toString();
                            String birakts6=Intibak_IlkYilBasariliDers6ATKS.getText().toString();
                            String birakts7=Intibak_IlkYilBasariliDers7ATKS.getText().toString();
                            String birakts8=Intibak_IlkYilBasariliDers8ATKS.getText().toString();
                            String birakts9=Intibak_IlkYilGelecekDers1ATKS.getText().toString();
                            String birakts10=Intibak_IlkYilGelecekDers2ATKS.getText().toString();
                            String birakts11=Intibak_IlkYilGelecekDers3ATKS.getText().toString();
                            String birakts12=Intibak_IlkYilGelecekDers4ATKS.getText().toString();
                            String birakts13=Intibak_IlkYilGelecekDers5ATKS.getText().toString();
                            String birakts14=Intibak_IlkYilGelecekDers6ATKS.getText().toString();
                            String birakts15=Intibak_IlkYilGelecekDers7ATKS.getText().toString();
                            String birakts16=Intibak_IlkYilGelecekDers8ATKS.getText().toString();

                            String ikiders1=Intibak_IkinciYilBasariliDers1.getText().toString();
                            String ikiders2=Intibak_IkinciYilBasariliDers2.getText().toString();
                            String ikiders3=Intibak_IkinciYilBasariliDers3.getText().toString();
                            String ikiders4=Intibak_IkinciYilBasariliDers4.getText().toString();
                            String ikiders5=Intibak_IkinciYilBasariliDers5.getText().toString();
                            String ikiders6=Intibak_IkinciYilBasariliDers6.getText().toString();
                            String ikiders7=Intibak_IkinciYilBasariliDers7.getText().toString();
                            String ikiders8=Intibak_IkinciYilBasariliDers8.getText().toString();
                            String ikiders9=Intibak_IkinciYilGelecekDers1.getText().toString();
                            String ikiders10=Intibak_IkinciYilGelecekDers2.getText().toString();
                            String ikiders11=Intibak_IkinciYilGelecekDers3.getText().toString();
                            String ikiders12=Intibak_IkinciYilGelecekDers4.getText().toString();
                            String ikiders13=Intibak_IkinciYilGelecekDers5.getText().toString();
                            String ikiders14=Intibak_IkinciYilGelecekDers6.getText().toString();
                            String ikiders15=Intibak_IkinciYilGelecekDers7.getText().toString();
                            String ikiders16=Intibak_IkinciYilGelecekDers8.getText().toString();
                            String ikiakts1=Intibak_IkinciYilBasariliDers1ATKS.getText().toString();
                            String ikiakts2=Intibak_IkinciYilBasariliDers2ATKS.getText().toString();
                            String ikiakts3=Intibak_IkinciYilBasariliDers3ATKS.getText().toString();
                            String ikiakts4=Intibak_IkinciYilBasariliDers4ATKS.getText().toString();
                            String ikiakts5=Intibak_IkinciYilBasariliDers5ATKS.getText().toString();
                            String ikiakts6=Intibak_IkinciYilBasariliDers6ATKS.getText().toString();
                            String ikiakts7=Intibak_IkinciYilBasariliDers7ATKS.getText().toString();
                            String ikiakts8=Intibak_IkinciYilBasariliDers8ATKS.getText().toString();
                            String ikiakts9=Intibak_IkinciYilGelecekDers1ATKS.getText().toString();
                            String ikiakts10=Intibak_IkinciYilGelecekDers2ATKS.getText().toString();
                            String ikiakts11=Intibak_IkinciYilGelecekDers3ATKS.getText().toString();
                            String ikiakts12=Intibak_IkinciYilGelecekDers4ATKS.getText().toString();
                            String ikiakts13=Intibak_IkinciYilGelecekDers5ATKS.getText().toString();
                            String ikiakts14=Intibak_IkinciYilGelecekDers6ATKS.getText().toString();
                            String ikiakts15=Intibak_IkinciYilGelecekDers7ATKS.getText().toString();
                            String ikiakts16=Intibak_IkinciYilGelecekDers8ATKS.getText().toString();

                            String ucders1=Intibak_UcuncuYilBasariliDers1.getText().toString();
                            String ucders2=Intibak_UcuncuYilBasariliDers2.getText().toString();
                            String ucders3=Intibak_UcuncuYilBasariliDers3.getText().toString();
                            String ucders4=Intibak_UcuncuYilBasariliDers4.getText().toString();
                            String ucders5=Intibak_UcuncuYilBasariliDers5.getText().toString();
                            String ucders6=Intibak_UcuncuYilBasariliDers6.getText().toString();
                            String ucders7=Intibak_UcuncuYilBasariliDers7.getText().toString();
                            String ucders8=Intibak_UcuncuYilBasariliDers8.getText().toString();
                            String ucders9=Intibak_UcuncuYilGelecekDers1.getText().toString();
                            String ucders10=Intibak_UcuncuYilGelecekDers2.getText().toString();
                            String ucders11=Intibak_UcuncuYilGelecekDers3.getText().toString();
                            String ucders12=Intibak_UcuncuYilGelecekDers4.getText().toString();
                            String ucders13=Intibak_UcuncuYilGelecekDers5.getText().toString();
                            String ucders14=Intibak_UcuncuYilGelecekDers6.getText().toString();
                            String ucders15=Intibak_UcuncuYilGelecekDers7.getText().toString();
                            String ucders16=Intibak_UcuncuYilGelecekDers8.getText().toString();
                            String ucakts1=Intibak_UcuncuYilBasariliDers1ATKS.getText().toString();
                            String ucakts2=Intibak_UcuncuYilBasariliDers2ATKS.getText().toString();
                            String ucakts3=Intibak_UcuncuYilBasariliDers3ATKS.getText().toString();
                            String ucakts4=Intibak_UcuncuYilBasariliDers4ATKS.getText().toString();
                            String ucakts5=Intibak_UcuncuYilBasariliDers5ATKS.getText().toString();
                            String ucakts6=Intibak_UcuncuYilBasariliDers6ATKS.getText().toString();
                            String ucakts7=Intibak_UcuncuYilBasariliDers7ATKS.getText().toString();
                            String ucakts8=Intibak_UcuncuYilBasariliDers8ATKS.getText().toString();
                            String ucakts9=Intibak_UcuncuYilGelecekDers1ATKS.getText().toString();
                            String ucakts10=Intibak_UcuncuYilGelecekDers2ATKS.getText().toString();
                            String ucakts11=Intibak_UcuncuYilGelecekDers3ATKS.getText().toString();
                            String ucakts12=Intibak_UcuncuYilGelecekDers4ATKS.getText().toString();
                            String ucakts13=Intibak_UcuncuYilGelecekDers5ATKS.getText().toString();
                            String ucakts14=Intibak_UcuncuYilGelecekDers6ATKS.getText().toString();
                            String ucakts15=Intibak_UcuncuYilGelecekDers7ATKS.getText().toString();
                            String ucakts16=Intibak_UcuncuYilGelecekDers8ATKS.getText().toString();

                            String dortders1=Intibak_DorduncuYilBasariliDers1.getText().toString();
                            String dortders2=Intibak_DorduncuYilBasariliDers2.getText().toString();
                            String dortders3=Intibak_DorduncuYilBasariliDers3.getText().toString();
                            String dortders4=Intibak_DorduncuYilBasariliDers4.getText().toString();
                            String dortders5=Intibak_DorduncuYilBasariliDers5.getText().toString();
                            String dortders6=Intibak_DorduncuYilBasariliDers6.getText().toString();
                            String dortders7=Intibak_DorduncuYilBasariliDers7.getText().toString();
                            String dortders8=Intibak_DorduncuYilBasariliDers8.getText().toString();
                            String dortders9=Intibak_DorduncuYilGelecekDers1.getText().toString();
                            String dortders10=Intibak_DorduncuYilGelecekDers2.getText().toString();
                            String dortders11=Intibak_DorduncuYilGelecekDers3.getText().toString();
                            String dortders12=Intibak_DorduncuYilGelecekDers4.getText().toString();
                            String dortders13=Intibak_DorduncuYilGelecekDers5.getText().toString();
                            String dortders14=Intibak_DorduncuYilGelecekDers6.getText().toString();
                            String dortders15=Intibak_DorduncuYilGelecekDers7.getText().toString();
                            String dortders16=Intibak_DorduncuYilGelecekDers8.getText().toString();
                            String dortakts1=Intibak_DorduncuYilBasariliDers1ATKS.getText().toString();
                            String dortakts2=Intibak_DorduncuYilBasariliDers2ATKS.getText().toString();
                            String dortakts3=Intibak_DorduncuYilBasariliDers3ATKS.getText().toString();
                            String dortakts4=Intibak_DorduncuYilBasariliDers4ATKS.getText().toString();
                            String dortakts5=Intibak_DorduncuYilBasariliDers5ATKS.getText().toString();
                            String dortakts6=Intibak_DorduncuYilBasariliDers6ATKS.getText().toString();
                            String dortakts7=Intibak_DorduncuYilBasariliDers7ATKS.getText().toString();
                            String dortakts8=Intibak_DorduncuYilBasariliDers8ATKS.getText().toString();
                            String dortakts9=Intibak_DorduncuYilGelecekDers1ATKS.getText().toString();
                            String dortakts10=Intibak_DorduncuYilGelecekDers2ATKS.getText().toString();
                            String dortakts11=Intibak_DorduncuYilGelecekDers3ATKS.getText().toString();
                            String dortakts12=Intibak_DorduncuYilGelecekDers4ATKS.getText().toString();
                            String dortakts13=Intibak_DorduncuYilGelecekDers5ATKS.getText().toString();
                            String dortakts14=Intibak_DorduncuYilGelecekDers6ATKS.getText().toString();
                            String dortakts15=Intibak_DorduncuYilGelecekDers7ATKS.getText().toString();
                            String dortakts16=Intibak_DorduncuYilGelecekDers8ATKS.getText().toString();
                            String durum="Bekliyor";

                            DocumentReference docRef = db.collection("Ders Intibaki Belgeleri").document(userID);

                            Map<String, Object> intibak = new HashMap<>();
                            intibak.put("ad", ad);
                            intibak.put("soyad", soyad );
                            intibak.put("numara", numara);
                            intibak.put("kayit turu", kayittur);
                            intibak.put("sinif", sinif);
                            intibak.put("yari yil", yariyil);
                            intibak.put("suanki uni", unianlik);
                            intibak.put("suanki bolum", bolumanlik);
                            intibak.put("intibak edilecek uni", unigelecek);
                            intibak.put("intibak edilecek bolum", bolumgelecek);

                            intibak.put("ilk yari yil suanki ders 1", birders1);
                            intibak.put("ilk yari yil suanki ders 2", birders2);
                            intibak.put("ilk yari yil suanki ders 3", birders3);
                            intibak.put("ilk yari yil suanki ders 4", birders4);
                            intibak.put("ilk yari yil suanki ders 5", birders5);
                            intibak.put("ilk yari yil suanki ders 6", birders6);
                            intibak.put("ilk yari yil suanki ders 7", birders7);
                            intibak.put("ilk yari yil suanki ders 8", birders8);
                            intibak.put("ilk yari yil intibak edilecek ders 9", birders9);
                            intibak.put("ilk yari yil intibak edilecek ders 10", birders10);
                            intibak.put("ilk yari yil intibak edilecek ders 11", birders11);
                            intibak.put("ilk yari yil intibak edilecek ders 12", birders12);
                            intibak.put("ilk yari yil intibak edilecek ders 13", birders13);
                            intibak.put("ilk yari yil intibak edilecek ders 14", birders14);
                            intibak.put("ilk yari yil intibak edilecek ders 15", birders15);
                            intibak.put("ilk yari yil intibak edilecek ders 16", birders16);
                            intibak.put("ilk yari yil suanki ders akts 1", birakts1);
                            intibak.put("ilk yari yil suanki ders akts 2", birakts2);
                            intibak.put("ilk yari yil suanki ders akts 3", birakts3);
                            intibak.put("ilk yari yil suanki ders akts 4", birakts4);
                            intibak.put("ilk yari yil suanki ders akts 5", birakts5);
                            intibak.put("ilk yari yil suanki ders akts 6", birakts6);
                            intibak.put("ilk yari yil suanki ders akts 7", birakts7);
                            intibak.put("ilk yari yil suanki ders akts 8", birakts8);
                            intibak.put("ilk yari yil intibak edilecek ders akts 1", birakts9);
                            intibak.put("ilk yari yil intibak edilecek ders akts 2", birakts10);
                            intibak.put("ilk yari yil intibak edilecek ders akts 3", birakts11);
                            intibak.put("ilk yari yil intibak edilecek ders akts 4", birakts12);
                            intibak.put("ilk yari yil intibak edilecek ders akts 5", birakts13);
                            intibak.put("ilk yari yil intibak edilecek ders akts 6", birakts14);
                            intibak.put("ilk yari yil intibak edilecek ders akts 7", birakts15);
                            intibak.put("ilk yari yil intibak edilecek ders akts 8", birakts16);

                            intibak.put("ikinci yari yil suanki ders 1", ikiders1);
                            intibak.put("ikinci yari yil suanki ders 2", ikiders2);
                            intibak.put("ikinci yari yil suanki ders 3", ikiders3);
                            intibak.put("ikinci yari yil suanki ders 4", ikiders4);
                            intibak.put("ikinci yari yil suanki ders 5", ikiders5);
                            intibak.put("ikinci yari yil suanki ders 6", ikiders6);
                            intibak.put("ikinci yari yil suanki ders 7", ikiders7);
                            intibak.put("ikinci yari yil suanki ders 8", ikiders8);
                            intibak.put("ikinci yari yil intibak edilecek ders 9", ikiders9);
                            intibak.put("ikinci yari yil intibak edilecek ders 10", ikiders10);
                            intibak.put("ikinci yari yil intibak edilecek ders 11", ikiders11);
                            intibak.put("ikinci yari yil intibak edilecek ders 12", ikiders12);
                            intibak.put("ikinci yari yil intibak edilecek ders 13", ikiders13);
                            intibak.put("ikinci yari yil intibak edilecek ders 14", ikiders14);
                            intibak.put("ikinci yari yil intibak edilecek ders 15", ikiders15);
                            intibak.put("ikinci yari yil intibak edilecek ders 16", ikiders16);
                            intibak.put("ikinci yari yil suanki ders akts 1", ikiakts1);
                            intibak.put("ikinci yari yil suanki ders akts 2", ikiakts2);
                            intibak.put("ikinci yari yil suanki ders akts 3", ikiakts3);
                            intibak.put("ikinci yari yil suanki ders akts 4", ikiakts4);
                            intibak.put("ikinci yari yil suanki ders akts 5", ikiakts5);
                            intibak.put("ikinci yari yil suanki ders akts 6", ikiakts6);
                            intibak.put("ikinci yari yil suanki ders akts 7", ikiakts7);
                            intibak.put("ikinci yari yil suanki ders akts 8", ikiakts8);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 1", ikiakts9);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 2", ikiakts10);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 3", ikiakts11);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 4", ikiakts12);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 5", ikiakts13);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 6", ikiakts14);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 7", ikiakts15);
                            intibak.put("ikinci yari yil intibak edilecek ders akts 8", ikiakts16);

                            intibak.put("ucuncu yari yil suanki ders 1", ucders1);
                            intibak.put("ucuncu yari yil suanki ders 2", ucders2);
                            intibak.put("ucuncu yari yil suanki ders 3", ucders3);
                            intibak.put("ucuncu yari yil suanki ders 4", ucders4);
                            intibak.put("ucuncu yari yil suanki ders 5", ucders5);
                            intibak.put("ucuncu yari yil suanki ders 6", ucders6);
                            intibak.put("ucuncu yari yil suanki ders 7", ucders7);
                            intibak.put("ucuncu yari yil suanki ders 8", ucders8);
                            intibak.put("ucuncu yari yil intibak edilecek ders 9", ucders9);
                            intibak.put("ucuncu yari yil intibak edilecek ders 10", ucders10);
                            intibak.put("ucuncu yari yil intibak edilecek ders 11", ucders11);
                            intibak.put("ucuncu yari yil intibak edilecek ders 12", ucders12);
                            intibak.put("ucuncu yari yil intibak edilecek ders 13", ucders13);
                            intibak.put("ucuncu yari yil intibak edilecek ders 14", ucders14);
                            intibak.put("ucuncu yari yil intibak edilecek ders 15", ucders15);
                            intibak.put("ucuncu yari yil intibak edilecek ders 16", ucders16);
                            intibak.put("ucuncu yari yil suanki ders akts 1", ucakts1);
                            intibak.put("ucuncu yari yil suanki ders akts 2", ucakts2);
                            intibak.put("ucuncu yari yil suanki ders akts 3", ucakts3);
                            intibak.put("ucuncu yari yil suanki ders akts 4", ucakts4);
                            intibak.put("ucuncu yari yil suanki ders akts 5", ucakts5);
                            intibak.put("ucuncu yari yil suanki ders akts 6", ucakts6);
                            intibak.put("ucuncu yari yil suanki ders akts 7", ucakts7);
                            intibak.put("ucuncu yari yil suanki ders akts 8", ucakts8);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 1", ucakts9);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 2", ucakts10);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 3", ucakts11);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 4", ucakts12);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 5", ucakts13);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 6", ucakts14);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 7", ucakts15);
                            intibak.put("ucuncu yari yil intibak edilecek ders akts 8", ucakts16);

                            intibak.put("dorduncu yari yil suanki ders 1", dortders1);
                            intibak.put("dorduncu yari yil suanki ders 2", dortders2);
                            intibak.put("dorduncu yari yil suanki ders 3", dortders3);
                            intibak.put("dorduncu yari yil suanki ders 4", dortders4);
                            intibak.put("dorduncu yari yil suanki ders 5", dortders5);
                            intibak.put("dorduncu yari yil suanki ders 6", dortders6);
                            intibak.put("dorduncu yari yil suanki ders 7", dortders7);
                            intibak.put("dorduncu yari yil suanki ders 8", dortders8);
                            intibak.put("dorduncu yari yil intibak edilecek ders 9", dortders9);
                            intibak.put("dorduncu yari yil intibak edilecek ders 10", dortders10);
                            intibak.put("dorduncu yari yil intibak edilecek ders 11", dortders11);
                            intibak.put("dorduncu yari yil intibak edilecek ders 12", dortders12);
                            intibak.put("dorduncu yari yil intibak edilecek ders 13", dortders13);
                            intibak.put("dorduncu yari yil intibak edilecek ders 14", dortders14);
                            intibak.put("dorduncu yari yil intibak edilecek ders 15", dortders15);
                            intibak.put("dorduncu yari yil intibak edilecek ders 16", dortders16);
                            intibak.put("dorduncu yari yil suanki ders akts 1", dortakts1);
                            intibak.put("dorduncu yari yil suanki ders akts 2", dortakts2);
                            intibak.put("dorduncu yari yil suanki ders akts 3", dortakts3);
                            intibak.put("dorduncu yari yil suanki ders akts 4", dortakts4);
                            intibak.put("dorduncu yari yil suanki ders akts 5", dortakts5);
                            intibak.put("dorduncu yari yil suanki ders akts 6", dortakts6);
                            intibak.put("dorduncu yari yil suanki ders akts 7", dortakts7);
                            intibak.put("dorduncu yari yil suanki ders akts 8", dortakts8);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 1", dortakts9);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 2", dortakts10);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 3", dortakts11);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 4", dortakts12);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 5", dortakts13);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 6", dortakts14);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 7", dortakts15);
                            intibak.put("dorduncu yari yil intibak edilecek ders akts 8", dortakts16);
                            intibak.put("durum",durum);
                            intibak.put("user id",userID);

                            docRef.set(intibak).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void unused)
                                {
                                    Toast.makeText(ogrenci_ders_intibaki_basvuru.this, "basvuru kaydedildi", Toast.LENGTH_SHORT).show();
                                }
                            });

                            DocumentReference docref12=db.collection("ogrenciler").document(userID);
                            docref12.addSnapshotListener(ogrenci_ders_intibaki_basvuru.this, new EventListener<DocumentSnapshot>()
                            {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error)
                                {
                                    SimpleDateFormat pattern = new SimpleDateFormat("MMddyyyyHHmm");
                                    String tarih = pattern.format(new Date());

                                    Intibak_dosyaAdi.setText(value.getString("okul numarasi"));
                                    String a=Intibak_dosyaAdi.getText().toString();//okul num
                                    Intibak_dosyaAdi.getText().clear();

                                    Intibak_dosyaAdi.setText(value.getString("isim"));
                                    String b=Intibak_dosyaAdi.getText().toString();//isim
                                    Intibak_dosyaAdi.setText(dosyaAdi);

                                    Intibak_dosyaAdi.setText(value.getString("soy isim"));
                                    String c=Intibak_dosyaAdi.getText().toString();//soyad
                                    Intibak_dosyaAdi.setText(dosyaAdi);

                                    dosyaAdi=a + "_" + b + "_" + c + "_" + tarih;

                                    String ad           = Intibak_OgrAd.getText().toString();
                                    String soyad        = Intibak_OgrSoyad.getText().toString();
                                    String numara       = Intibak_OgrNumara.getText().toString();
                                    String kayittur     = Intibak_OgrKayitTur.getText().toString();
                                    String sinif        = Intibak_OgrSinif.getText().toString();
                                    String yariyil      = Intibak_YariYil.getText().toString();
                                    String unianlik     = Intibak_UniAnlik.getText().toString();
                                    String bolumanlik   = Intibak_BolumAnlik.getText().toString();
                                    String unigelecek   = Intibak_UniGelecek.getText().toString();
                                    String bolumgelecek = Intibak_BolumGelecek.getText().toString();

                                    String birders1=Intibak_IlkYilBasariliDers1.getText().toString();
                                    String birders2=Intibak_IlkYilBasariliDers2.getText().toString();
                                    String birders3=Intibak_IlkYilBasariliDers3.getText().toString();
                                    String birders4=Intibak_IlkYilBasariliDers4.getText().toString();
                                    String birders5=Intibak_IlkYilBasariliDers5.getText().toString();
                                    String birders6=Intibak_IlkYilBasariliDers6.getText().toString();
                                    String birders7=Intibak_IlkYilBasariliDers7.getText().toString();
                                    String birders8=Intibak_IlkYilBasariliDers8.getText().toString();
                                    String birders9=Intibak_IlkYilGelecekDers1.getText().toString();
                                    String birders10=Intibak_IlkYilGelecekDers2.getText().toString();
                                    String birders11=Intibak_IlkYilGelecekDers3.getText().toString();
                                    String birders12=Intibak_IlkYilGelecekDers4.getText().toString();
                                    String birders13=Intibak_IlkYilGelecekDers5.getText().toString();
                                    String birders14=Intibak_IlkYilGelecekDers6.getText().toString();
                                    String birders15=Intibak_IlkYilGelecekDers7.getText().toString();
                                    String birders16=Intibak_IlkYilGelecekDers8.getText().toString();
                                    String birakts1=Intibak_IlkYilBasariliDers1ATKS.getText().toString();
                                    String birakts2=Intibak_IlkYilBasariliDers2ATKS.getText().toString();
                                    String birakts3=Intibak_IlkYilBasariliDers3ATKS.getText().toString();
                                    String birakts4=Intibak_IlkYilBasariliDers4ATKS.getText().toString();
                                    String birakts5=Intibak_IlkYilBasariliDers5ATKS.getText().toString();
                                    String birakts6=Intibak_IlkYilBasariliDers6ATKS.getText().toString();
                                    String birakts7=Intibak_IlkYilBasariliDers7ATKS.getText().toString();
                                    String birakts8=Intibak_IlkYilBasariliDers8ATKS.getText().toString();
                                    String birakts9=Intibak_IlkYilGelecekDers1ATKS.getText().toString();
                                    String birakts10=Intibak_IlkYilGelecekDers2ATKS.getText().toString();
                                    String birakts11=Intibak_IlkYilGelecekDers3ATKS.getText().toString();
                                    String birakts12=Intibak_IlkYilGelecekDers4ATKS.getText().toString();
                                    String birakts13=Intibak_IlkYilGelecekDers5ATKS.getText().toString();
                                    String birakts14=Intibak_IlkYilGelecekDers6ATKS.getText().toString();
                                    String birakts15=Intibak_IlkYilGelecekDers7ATKS.getText().toString();
                                    String birakts16=Intibak_IlkYilGelecekDers8ATKS.getText().toString();

                                    String ikiders1=Intibak_IkinciYilBasariliDers1.getText().toString();
                                    String ikiders2=Intibak_IkinciYilBasariliDers2.getText().toString();
                                    String ikiders3=Intibak_IkinciYilBasariliDers3.getText().toString();
                                    String ikiders4=Intibak_IkinciYilBasariliDers4.getText().toString();
                                    String ikiders5=Intibak_IkinciYilBasariliDers5.getText().toString();
                                    String ikiders6=Intibak_IkinciYilBasariliDers6.getText().toString();
                                    String ikiders7=Intibak_IkinciYilBasariliDers7.getText().toString();
                                    String ikiders8=Intibak_IkinciYilBasariliDers8.getText().toString();
                                    String ikiders9=Intibak_IkinciYilGelecekDers1.getText().toString();
                                    String ikiders10=Intibak_IkinciYilGelecekDers2.getText().toString();
                                    String ikiders11=Intibak_IkinciYilGelecekDers3.getText().toString();
                                    String ikiders12=Intibak_IkinciYilGelecekDers4.getText().toString();
                                    String ikiders13=Intibak_IkinciYilGelecekDers5.getText().toString();
                                    String ikiders14=Intibak_IkinciYilGelecekDers6.getText().toString();
                                    String ikiders15=Intibak_IkinciYilGelecekDers7.getText().toString();
                                    String ikiders16=Intibak_IkinciYilGelecekDers8.getText().toString();
                                    String ikiakts1=Intibak_IkinciYilBasariliDers1ATKS.getText().toString();
                                    String ikiakts2=Intibak_IkinciYilBasariliDers2ATKS.getText().toString();
                                    String ikiakts3=Intibak_IkinciYilBasariliDers3ATKS.getText().toString();
                                    String ikiakts4=Intibak_IkinciYilBasariliDers4ATKS.getText().toString();
                                    String ikiakts5=Intibak_IkinciYilBasariliDers5ATKS.getText().toString();
                                    String ikiakts6=Intibak_IkinciYilBasariliDers6ATKS.getText().toString();
                                    String ikiakts7=Intibak_IkinciYilBasariliDers7ATKS.getText().toString();
                                    String ikiakts8=Intibak_IkinciYilBasariliDers8ATKS.getText().toString();
                                    String ikiakts9=Intibak_IkinciYilGelecekDers1ATKS.getText().toString();
                                    String ikiakts10=Intibak_IkinciYilGelecekDers2ATKS.getText().toString();
                                    String ikiakts11=Intibak_IkinciYilGelecekDers3ATKS.getText().toString();
                                    String ikiakts12=Intibak_IkinciYilGelecekDers4ATKS.getText().toString();
                                    String ikiakts13=Intibak_IkinciYilGelecekDers5ATKS.getText().toString();
                                    String ikiakts14=Intibak_IkinciYilGelecekDers6ATKS.getText().toString();
                                    String ikiakts15=Intibak_IkinciYilGelecekDers7ATKS.getText().toString();
                                    String ikiakts16=Intibak_IkinciYilGelecekDers8ATKS.getText().toString();

                                    String ucders1=Intibak_UcuncuYilBasariliDers1.getText().toString();
                                    String ucders2=Intibak_UcuncuYilBasariliDers2.getText().toString();
                                    String ucders3=Intibak_UcuncuYilBasariliDers3.getText().toString();
                                    String ucders4=Intibak_UcuncuYilBasariliDers4.getText().toString();
                                    String ucders5=Intibak_UcuncuYilBasariliDers5.getText().toString();
                                    String ucders6=Intibak_UcuncuYilBasariliDers6.getText().toString();
                                    String ucders7=Intibak_UcuncuYilBasariliDers7.getText().toString();
                                    String ucders8=Intibak_UcuncuYilBasariliDers8.getText().toString();
                                    String ucders9=Intibak_UcuncuYilGelecekDers1.getText().toString();
                                    String ucders10=Intibak_UcuncuYilGelecekDers2.getText().toString();
                                    String ucders11=Intibak_UcuncuYilGelecekDers3.getText().toString();
                                    String ucders12=Intibak_UcuncuYilGelecekDers4.getText().toString();
                                    String ucders13=Intibak_UcuncuYilGelecekDers5.getText().toString();
                                    String ucders14=Intibak_UcuncuYilGelecekDers6.getText().toString();
                                    String ucders15=Intibak_UcuncuYilGelecekDers7.getText().toString();
                                    String ucders16=Intibak_UcuncuYilGelecekDers8.getText().toString();
                                    String ucakts1=Intibak_UcuncuYilBasariliDers1ATKS.getText().toString();
                                    String ucakts2=Intibak_UcuncuYilBasariliDers2ATKS.getText().toString();
                                    String ucakts3=Intibak_UcuncuYilBasariliDers3ATKS.getText().toString();
                                    String ucakts4=Intibak_UcuncuYilBasariliDers4ATKS.getText().toString();
                                    String ucakts5=Intibak_UcuncuYilBasariliDers5ATKS.getText().toString();
                                    String ucakts6=Intibak_UcuncuYilBasariliDers6ATKS.getText().toString();
                                    String ucakts7=Intibak_UcuncuYilBasariliDers7ATKS.getText().toString();
                                    String ucakts8=Intibak_UcuncuYilBasariliDers8ATKS.getText().toString();
                                    String ucakts9=Intibak_UcuncuYilGelecekDers1ATKS.getText().toString();
                                    String ucakts10=Intibak_UcuncuYilGelecekDers2ATKS.getText().toString();
                                    String ucakts11=Intibak_UcuncuYilGelecekDers3ATKS.getText().toString();
                                    String ucakts12=Intibak_UcuncuYilGelecekDers4ATKS.getText().toString();
                                    String ucakts13=Intibak_UcuncuYilGelecekDers5ATKS.getText().toString();
                                    String ucakts14=Intibak_UcuncuYilGelecekDers6ATKS.getText().toString();
                                    String ucakts15=Intibak_UcuncuYilGelecekDers7ATKS.getText().toString();
                                    String ucakts16=Intibak_UcuncuYilGelecekDers8ATKS.getText().toString();

                                    String dortders1=Intibak_DorduncuYilBasariliDers1.getText().toString();
                                    String dortders2=Intibak_DorduncuYilBasariliDers2.getText().toString();
                                    String dortders3=Intibak_DorduncuYilBasariliDers3.getText().toString();
                                    String dortders4=Intibak_DorduncuYilBasariliDers4.getText().toString();
                                    String dortders5=Intibak_DorduncuYilBasariliDers5.getText().toString();
                                    String dortders6=Intibak_DorduncuYilBasariliDers6.getText().toString();
                                    String dortders7=Intibak_DorduncuYilBasariliDers7.getText().toString();
                                    String dortders8=Intibak_DorduncuYilBasariliDers8.getText().toString();
                                    String dortders9=Intibak_DorduncuYilGelecekDers1.getText().toString();
                                    String dortders10=Intibak_DorduncuYilGelecekDers2.getText().toString();
                                    String dortders11=Intibak_DorduncuYilGelecekDers3.getText().toString();
                                    String dortders12=Intibak_DorduncuYilGelecekDers4.getText().toString();
                                    String dortders13=Intibak_DorduncuYilGelecekDers5.getText().toString();
                                    String dortders14=Intibak_DorduncuYilGelecekDers6.getText().toString();
                                    String dortders15=Intibak_DorduncuYilGelecekDers7.getText().toString();
                                    String dortders16=Intibak_DorduncuYilGelecekDers8.getText().toString();
                                    String dortakts1=Intibak_DorduncuYilBasariliDers1ATKS.getText().toString();
                                    String dortakts2=Intibak_DorduncuYilBasariliDers2ATKS.getText().toString();
                                    String dortakts3=Intibak_DorduncuYilBasariliDers3ATKS.getText().toString();
                                    String dortakts4=Intibak_DorduncuYilBasariliDers4ATKS.getText().toString();
                                    String dortakts5=Intibak_DorduncuYilBasariliDers5ATKS.getText().toString();
                                    String dortakts6=Intibak_DorduncuYilBasariliDers6ATKS.getText().toString();
                                    String dortakts7=Intibak_DorduncuYilBasariliDers7ATKS.getText().toString();
                                    String dortakts8=Intibak_DorduncuYilBasariliDers8ATKS.getText().toString();
                                    String dortakts9=Intibak_DorduncuYilGelecekDers1ATKS.getText().toString();
                                    String dortakts10=Intibak_DorduncuYilGelecekDers2ATKS.getText().toString();
                                    String dortakts11=Intibak_DorduncuYilGelecekDers3ATKS.getText().toString();
                                    String dortakts12=Intibak_DorduncuYilGelecekDers4ATKS.getText().toString();
                                    String dortakts13=Intibak_DorduncuYilGelecekDers5ATKS.getText().toString();
                                    String dortakts14=Intibak_DorduncuYilGelecekDers6ATKS.getText().toString();
                                    String dortakts15=Intibak_DorduncuYilGelecekDers7ATKS.getText().toString();
                                    String dortakts16=Intibak_DorduncuYilGelecekDers8ATKS.getText().toString();

                                    PdfDocument pdfDocument = new PdfDocument();
                                    Paint title = new Paint();
                                    PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
                                    PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
                                    Canvas canvas = myPage.getCanvas();
                                    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                    title.setTextSize(18);
                                    title.setColor(ContextCompat.getColor(ogrenci_ders_intibaki_basvuru.this, R.color.black));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    canvas.drawText("Kocaeli Universitesi", 260, 40, title);
                                    canvas.drawText("INTIBAK BASVURU FORMU", 300, 20, title);
                                    title.setTextSize(12);
                                    canvas.drawText("TARIH:"+currentDateandTime+"", 520, 40, title);

                                    canvas.drawText("KARAR:\n", 100, 50, title);

                                    canvas.drawText(unianlik+" Universitesi "+1+" Fakultesi/YO/MYO "+bolumanlik+" Bolumu/Programinda ogrenim", 100, 80, title);
                                    canvas.drawText("gormekte iken,"+currentDateandTime+". ogretim yili basinda. Yatay Gecis/Dikey Gecis yoluyla", 100, 100, title);
                                    canvas.drawText(bolumgelecek+"bolumune kayit olan "+ad+" nin asagida sunulan derslerden muaf ve sorumlu", 100, 120, title);
                                    canvas.drawText("tutularak "+yariyil+"Yariyila intibakinin yapilmasinin uygun oldugunun Dekanliga arzina karar verilmistir.", 100, 140, title);

                                    canvas.drawText("AD:"+ad, 100, 160, title);
                                    canvas.drawText("SOYAD:"+soyad, 100, 180, title);
                                    canvas.drawText("KAYIT TURU:"+kayittur, 100, 200, title);
                                    canvas.drawText("INTIBAK EDECEGI YARIYIL:"+yariyil, 400, 160, title);
                                    canvas.drawText("OGRENIM YILI:"+sinif, 400, 180, title);
                                    canvas.drawText("INTIBAK EDECEGI DERS PLANI:", 400, 200, title);
                                    canvas.drawText("Devam Etmis Oldugu Yuksek Ogretim Kurumu"+unianlik, 100, 220, title);
                                    canvas.drawText("Bolum/Program:"+bolumanlik, 100, 240, title);
                                    canvas.drawText("Intibak Ettirilecegi Yuksek Ogretim Kurumu"+unigelecek, 400, 220, title);
                                    canvas.drawText("Bolum/Program:"+bolumgelecek, 400, 240, title);

                                    canvas.drawText("1.YARIYIL:", 100, 260, title);
                                    canvas.drawText("Onceki Bolumunde Basarili Oldugu Dersler                                Intibak Edecegi Bolumde Yukumlu Oldugu Dersler:", 100, 280, title);
                                    canvas.drawText("DERSLER " + birders1 + " AKTS " + birakts1 + "                          DERSLER " + birders9 +  " AKTS " + birakts9, 100, 300, title);
                                    canvas.drawText("DERSLER " + birders2 + " AKTS " + birakts2 + "                          DERSLER " + birders10 + " AKTS " + birakts10, 100, 320, title);
                                    canvas.drawText("DERSLER " + birders3 + " AKTS " + birakts3 + "                          DERSLER " + birders11 + " AKTS " + birakts11,  100, 340, title);
                                    canvas.drawText("DERSLER " + birders4 + " AKTS " + birakts4 + "                          DERSLER " + birders12 + " AKTS " + birakts12, 100, 360, title);
                                    canvas.drawText("DERSLER " + birders5 + " AKTS " + birakts5 + "                          DERSLER " + birders13 + " AKTS " + birakts13, 100, 380, title);
                                    canvas.drawText("DERSLER " + birders6 + " AKTS " + birakts6 + "                          DERSLER " + birders14 + " AKTS " + birakts14, 100, 400, title);
                                    canvas.drawText("DERSLER " + birders7 + " AKTS " + birakts7 + "                          DERSLER " + birders15 + " AKTS " + birakts15,  100, 420, title);
                                    canvas.drawText("DERSLER " + birders8 + " AKTS " + birakts8 + "                          DERSLER " + birders16 + " AKTS " + birakts16, 100, 440, title);

                                    canvas.drawText("2.YARIYIL:", 100, 460, title);
                                    canvas.drawText("Onceki Bolumunde Basarili Oldugu Dersler                                Intibak Edecegi Bolumde Yukumlu Oldugu Dersler:", 100, 480, title);
                                    canvas.drawText("DERSLER " + ikiders1 + " AKTS " + ikiakts1 + "                          DERSLER " + ikiders9 +  " AKTS " + ikiakts9, 100, 500, title);
                                    canvas.drawText("DERSLER " + ikiders2 + " AKTS " + ikiakts2 + "                          DERSLER " + ikiders10 + " AKTS " + ikiakts10, 100, 520, title);
                                    canvas.drawText("DERSLER " + ikiders3 + " AKTS " + ikiakts3 + "                          DERSLER " + ikiders11 + " AKTS " + ikiakts11,  100, 540, title);
                                    canvas.drawText("DERSLER " + ikiders4 + " AKTS " + ikiakts4 + "                          DERSLER " + ikiders12 + " AKTS " + ikiakts12, 100, 560, title);
                                    canvas.drawText("DERSLER " + ikiders5 + " AKTS " + ikiakts5 + "                          DERSLER " + ikiders13 + " AKTS " + ikiakts13, 100, 580, title);
                                    canvas.drawText("DERSLER " + ikiders6 + " AKTS " + ikiakts6 + "                          DERSLER " + ikiders14 + " AKTS " + ikiakts14, 100, 600, title);
                                    canvas.drawText("DERSLER " + ikiders7 + " AKTS " + ikiakts7 + "                          DERSLER " + ikiders15 + " AKTS " + ikiakts15,  100, 620, title);
                                    canvas.drawText("DERSLER " + ikiders8 + " AKTS " + ikiakts8 + "                          DERSLER " + ikiders16 + " AKTS " + ikiakts16, 100, 640, title);

                                    canvas.drawText("3.YARIYIL:", 100, 660, title);
                                    canvas.drawText("Onceki Bolumunde Basarili Oldugu Dersler                                Intibak Edecegi Bolumde Yukumlu Oldugu Dersler:", 100, 680, title);
                                    canvas.drawText("DERSLER " + ucders1 + " AKTS " + ucakts1 + "                          DERSLER " + ucders9 +  " AKTS " + ucakts9, 100, 700, title);
                                    canvas.drawText("DERSLER " + ucders2 + " AKTS " + ucakts2 + "                          DERSLER " + ucders10 + " AKTS " + ucakts10, 100, 720, title);
                                    canvas.drawText("DERSLER " + ucders3 + " AKTS " + ucakts3 + "                          DERSLER " + ucders11 + " AKTS " + ucakts11,  100, 740, title);
                                    canvas.drawText("DERSLER " + ucders4 + " AKTS " + ucakts4 + "                          DERSLER " + ucders12 + " AKTS " + ucakts12, 100, 760, title);
                                    canvas.drawText("DERSLER " + ucders5 + " AKTS " + ucakts5 + "                          DERSLER " + ucders13 + " AKTS " + ucakts13, 100, 780, title);
                                    canvas.drawText("DERSLER " + ucders6 + " AKTS " + ucakts6 + "                          DERSLER " + ucders14 + " AKTS " + ucakts14, 100, 800, title);
                                    canvas.drawText("DERSLER " + ucders7 + " AKTS " + ucakts7 + "                          DERSLER " + ucders15 + " AKTS " + ucakts15,  100, 820, title);
                                    canvas.drawText("DERSLER " + ucders8 + " AKTS " + ucakts8 + "                          DERSLER " + ucders16 + " AKTS " + ucakts16, 100, 840, title);

                                    canvas.drawText("4.YARIYIL:", 100, 860, title);
                                    canvas.drawText("Onceki Bolumunde Basarili Oldugu Dersler                                Intibak Edecegi Bolumde Yukumlu Oldugu Dersler:", 100, 880, title);
                                    canvas.drawText("DERSLER " + dortders1 + " AKTS " + dortakts1 + "                          DERSLER " + dortders9 +  " AKTS " + dortakts9, 100, 900, title);
                                    canvas.drawText("DERSLER " + dortders2 + " AKTS " + dortakts2 + "                          DERSLER " + dortders10 + " AKTS " + dortakts10, 100, 920, title);
                                    canvas.drawText("DERSLER " + dortders3 + " AKTS " + dortakts3 + "                          DERSLER " + dortders11 + " AKTS " + dortakts11,  100, 940, title);
                                    canvas.drawText("DERSLER " + dortders4 + " AKTS " + dortakts4 + "                          DERSLER " + dortders12 + " AKTS " + dortakts12, 100, 960, title);
                                    canvas.drawText("DERSLER " + dortders5 + " AKTS " + dortakts5 + "                          DERSLER " + dortders13 + " AKTS " + dortakts13, 100, 980, title);
                                    canvas.drawText("DERSLER " + dortders6 + " AKTS " + dortakts6 + "                          DERSLER " + dortders14 + " AKTS " + dortakts14, 100, 1000, title);
                                    canvas.drawText("DERSLER " + dortders7 + " AKTS " + dortakts7 + "                          DERSLER " + dortders15 + " AKTS " + dortakts15,  100, 1020, title);
                                    canvas.drawText("DERSLER " + dortders8 + " AKTS " + dortakts8 + "                          DERSLER " + dortders16 + " AKTS " + dortakts16, 100, 1040, title);

                                    title.setTextSize(15);
                                    canvas.drawText("Ogrenci Ad Soyad:", 500, 1060, title);
                                    canvas.drawText("Imza", 500, 1080, title);
                                    pdfDocument.finishPage(myPage);
                                    File file = new File(Environment.getExternalStorageDirectory(), dosyaAdi+".pdf");
                                    try {
                                        pdfDocument.writeTo(new FileOutputStream(file));
                                        Toast.makeText(ogrenci_ders_intibaki_basvuru.this, "PDF Dosyasý Kaydedildi Sisteme Imzalayip Yukleyiniz.", Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    pdfDocument.close();
                                }
                            });
                        }
                    });
                }
                else
                {
                    Toast.makeText(ogrenci_ders_intibaki_basvuru.this, "lütfen onaylayýnýz", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intibak_basvuruyukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                belgeYukle();
            }
        });
    }

    private void belgeYukle()
    {
        Intent intent8 = new Intent();
        intent8.setType("application/pdf");
        intent8.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent8, "PDF seç"), 12);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Intibak_basvuruyukle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploaduniPdf(data.getData());
                }
            });
        }
    }

    private void uploaduniPdf(Uri data)
    {

        DocumentReference docref2=db.collection("ogrenciler").document(userID);
        docref2.addSnapshotListener(ogrenci_ders_intibaki_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                SimpleDateFormat pattern = new SimpleDateFormat("MMddyyyyHHmm");
                String tarih = pattern.format(new Date());

                Intibak_dosyaAdi.setText(value.getString("okul numarasi"));
                String a=Intibak_dosyaAdi.getText().toString();//okul num
                Intibak_dosyaAdi.getText().clear();

                Intibak_dosyaAdi.setText(value.getString("isim"));
                String b=Intibak_dosyaAdi.getText().toString();//isim
                Intibak_dosyaAdi.setText(dosyaAdi);

                Intibak_dosyaAdi.setText(value.getString("soy isim"));
                String c=Intibak_dosyaAdi.getText().toString();//soyad
                Intibak_dosyaAdi.setText(dosyaAdi);

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_intibakbasvuruform.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_ders_intibaki_basvuru.this);
                progressDialog.setTitle("dosya yukleniyor");
                progressDialog.show();

                StorageReference reference = storageReference.child("basvurular/").child(userID+"/").child(dosyaAdi);

                reference.putFile(data)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult();

                                durum="bekliyor";
                                putPDF putpdf = new putPDF(dosyaAdi, uri.toString(),durum);
                                dbref1.child(userID).child("intibak_basvuruformu").setValue(putpdf);
                                dbref2.child(dbref2.push().getKey()).setValue(putpdf);
                                Toast.makeText(ogrenci_ders_intibaki_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
                            {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressDialog.setMessage("dosya yukleniyor " + (int) progress + "%");
                            }
                        });
            }
        });
    }
}