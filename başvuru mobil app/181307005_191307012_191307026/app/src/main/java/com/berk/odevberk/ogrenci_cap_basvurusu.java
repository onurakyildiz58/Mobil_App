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
import android.util.Log;
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

public class ogrenci_cap_basvurusu extends AppCompatActivity
{
    public static final String TAG = "TAG";
    Button Cap_FormYukle,Cap_FormIndir;
    CheckBox Cap_onaylama,Cap_ikinciTercih, Cap_birinciTercih;
    EditText Cap_BirinciTercihDers,Cap_BirincitercihFak, Cap_BirinciTercihYandal,
            Cap_IkinciTercihFak,Cap_IkinciTercihDers,Cap_IkinciTercihYandal,
            Cap_anadalFak,Cap_anadalProg,Cap_anadalOrtalama,Cap_anadalOsys,
            Cap_anadalSinif, Cap_OgrAdres,Cap_OgrMail,Cap_OgrTelNo,Cap_OgrtcNo,
            Cap_OgrSoyad,Cap_OgrAd,dosya_adi;
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
        setContentView(R.layout.ogrenci_cap_basvurusu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Cap_FormYukle           = (Button) findViewById(R.id.et_ogrenci_cap_basvurusu_basvuruyukle);//inidridði belgeyi yüklücek storage
        Cap_FormIndir           = (Button) findViewById(R.id.buton_ogrenci_cap_basvurusu_basvuruindir);//pdf oluþturucak indirecek
        Cap_onaylama            = (CheckBox) findViewById(R.id.checkbox_cap_basvuru_onay);
        Cap_ikinciTercih        = (CheckBox) findViewById(R.id.checkBox_ogrenci_cap_basvurusu_ikincitercih);
        Cap_birinciTercih       = (CheckBox) findViewById(R.id.checkBox_ogrenci_cap_basvurusu_birincitercih);
        Cap_BirinciTercihDers   = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_birincitercih_cap);
        Cap_BirincitercihFak    = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_birincitercih_fak);
        Cap_BirinciTercihYandal = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_birincitercih_yandal);
        Cap_IkinciTercihFak     = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ikincitercih_fak);
        Cap_IkinciTercihDers    = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ikincitercih_cap);
        Cap_IkinciTercihYandal  = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ikincitercih_yandal);
        Cap_anadalFak           = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_anadalfak);
        Cap_anadalProg          = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_anadalprog);
        Cap_anadalOrtalama      = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_anadalgno);
        Cap_anadalOsys          = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_yerlestigiosyspuani);
        Cap_anadalSinif         = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_anadalsinif);
        Cap_OgrAdres            = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_adres);
        Cap_OgrMail             = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_eposta);
        Cap_OgrTelNo            = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_telno);
        Cap_OgrtcNo             = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_tcno);
        Cap_OgrSoyad            = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_soyad);
        Cap_OgrAd               = (EditText) findViewById(R.id.et_ogrenci_cap_basvurusu_ogrbilgiler_ad);
        dosya_adi               = (EditText)findViewById(R.id.et_cap_dosyaadi);

        //check box onaylanmadan verileri db ye kaydetmez
        Cap_onaylama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Cap_FormIndir.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            //dbye kaydedicek
                            String ad=Cap_OgrAd.getText().toString();
                            String soyad=Cap_OgrSoyad.getText().toString();
                            String TC=Cap_OgrtcNo.getText().toString();
                            String telefon=Cap_OgrTelNo.getText().toString();
                            String mail=Cap_OgrMail.getText().toString();
                            String adres=Cap_OgrAdres.getText().toString();
                            String anadalfak=Cap_anadalFak.getText().toString();
                            String anadalprog=Cap_anadalProg.getText().toString();
                            String ortalama=Cap_anadalOrtalama.getText().toString();
                            String osys=Cap_anadalOsys.getText().toString();
                            String sinif=Cap_anadalSinif.getText().toString();
                            String birinciders=Cap_BirinciTercihDers.getText().toString();
                            String birincifak=Cap_BirincitercihFak.getText().toString();
                            String birinciyandal=Cap_BirinciTercihYandal.getText().toString();
                            String ikinciders=Cap_IkinciTercihDers.getText().toString();
                            String ikincifak=Cap_IkinciTercihFak.getText().toString();
                            String ikinciyandal=Cap_IkinciTercihYandal.getText().toString();
                            String durum="Bekliyor";

                            DocumentReference docRef = db.collection("Cap Belgeleri").document(userID);

                            Map<String, Object> Cap = new HashMap<>();
                            Cap.put("isim", ad);
                            Cap.put("soy isim", soyad);
                            Cap.put("TC", TC);
                            Cap.put("telefon numarasi", telefon);
                            Cap.put("mail", mail);
                            Cap.put("sinif", sinif);
                            Cap.put("adres", adres);
                            Cap.put("uni ortalamasi", ortalama);
                            Cap.put("fakulte", anadalfak);
                            Cap.put("anadal programi", anadalprog);
                            Cap.put("osys puani", osys);
                            Cap.put("1.tercih cap yapýlmak istenen fakulte", birinciders);
                            Cap.put("1.tercih cap yapmak istenen bolum", birincifak);
                            Cap.put("1.tercih yandal yapmak istenen bolum", birinciyandal);
                            Cap.put("2.tercih cap yapýlmak istenen fakulte", ikinciders);
                            Cap.put("2.tercih cap yapmak istenen bolum", ikincifak);
                            Cap.put("2.tercih yandal yapmak istenen bolum", ikinciyandal);
                            Cap.put("durum",durum);
                            Cap.put("user id",userID);

                            docRef.set(Cap).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void unused)
                                {
                                    Toast.makeText(ogrenci_cap_basvurusu.this, "basvuru kaydedildi", Toast.LENGTH_SHORT).show();
                                }
                            });

                            //dosya adý için getter
                            DocumentReference docref7=db.collection("ogrenciler").document(userID);
                            docref7.addSnapshotListener(ogrenci_cap_basvurusu.this, new EventListener<DocumentSnapshot>()
                            {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error)
                                {

                                    SimpleDateFormat pattern = new SimpleDateFormat("MMddyyyyHHmm");
                                    String tarih = pattern.format(new Date());

                                    dosya_adi.setText(value.getString("okul numarasi"));
                                    String a=dosya_adi.getText().toString();//okul num
                                    dosya_adi.getText().clear();

                                    dosya_adi.setText(value.getString("isim"));
                                    String b=dosya_adi.getText().toString();//isim
                                    dosya_adi.setText(dosyaAdi);

                                    dosya_adi.setText(value.getString("soy isim"));
                                    String c=dosya_adi.getText().toString();//soyad
                                    dosya_adi.setText(dosyaAdi);

                                    dosyaAdi=a + "_" + b + "_" + c + "_" + tarih;

                                    String ad=Cap_OgrAd.getText().toString();
                                    String soyad=Cap_OgrSoyad.getText().toString();
                                    String TC=Cap_OgrtcNo.getText().toString();
                                    String telefon=Cap_OgrTelNo.getText().toString();
                                    String mail=Cap_OgrMail.getText().toString();
                                    String adres=Cap_OgrAdres.getText().toString();
                                    String anadalfak=Cap_anadalFak.getText().toString();
                                    String anadalprog=Cap_anadalProg.getText().toString();
                                    String ortalama=Cap_anadalOrtalama.getText().toString();
                                    String osys=Cap_anadalOsys.getText().toString();
                                    String sinif=Cap_anadalSinif.getText().toString();
                                    String birinciders=Cap_BirinciTercihDers.getText().toString();
                                    String birincifak=Cap_BirincitercihFak.getText().toString();
                                    String birinciyandal=Cap_BirinciTercihYandal.getText().toString();
                                    String ikinciders=Cap_IkinciTercihDers.getText().toString();
                                    String ikincifak=Cap_IkinciTercihFak.getText().toString();
                                    String ikinciyandal=Cap_IkinciTercihYandal.getText().toString();

                                    //pdf oluþturacak
                                    PdfDocument pdfDocument = new PdfDocument();
                                    Paint title = new Paint();
                                    PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
                                    PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
                                    Canvas canvas = myPage.getCanvas();
                                    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                    title.setTextSize(18);
                                    title.setColor(ContextCompat.getColor(ogrenci_cap_basvurusu.this, R.color.black));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    canvas.drawText("Kocaeli Universitesi", 260, 50, title);
                                    canvas.drawText("CIFT ANADAL / YANDAL BASVURU FORMU", 240, 25, title);
                                    canvas.drawText("TARIH:  "+currentDateandTime+"", 520, 50, title);
                                    title.setTextSize(12);
                                    canvas.drawText("AD: " + ad, 100, 100, title);
                                    canvas.drawText("SOYAD: " + soyad, 100, 120, title);
                                    canvas.drawText("Telefon Numarasi: " + telefon, 100, 140, title);
                                    canvas.drawText("T.C. Numarasi: " + TC, 400, 100, title);
                                    canvas.drawText("E-Posta Adresi: " + mail, 400, 120, title);
                                    canvas.drawText("Tebligat Adresi: " + adres, 400, 140, title);
                                    canvas.drawText("Anadal Fakultesi: " + anadalfak, 100, 160, title);
                                    canvas.drawText("Anadal Programi: " + anadalprog, 100, 180, title);
                                    canvas.drawText("Sinifi/Yariyili: " + sinif, 100, 200, title);
                                    canvas.drawText("AGNO: " + ortalama, 100, 220, title);
                                    canvas.drawText("Bolum Program Tercihi", 220, 240, title);
                                    canvas.drawText("Fakulte/Yuksekokul/Program            Cift Anadal      Yandal        Cift/Yan", 100, 260, title);
                                    canvas.drawText("1. Tercihi" + "                         " + birincifak + "                   " + birinciders + "             " + birinciyandal, 100, 280, title);
                                    canvas.drawText("2. Tercihi" + "                         " + ikincifak + "                   " + ikinciders + "             " + ikinciyandal, 100, 300, title);
                                    canvas.drawText("3. Tercihi", 100, 320, title);
                                    title.setTextSize(15);
                                    canvas.drawText("Ogrenci Ad Soyad", 500, 900, title);
                                    canvas.drawText("Imza", 500, 940, title);
                                    pdfDocument.finishPage(myPage);
                                    try
                                    {
                                        File file = new File(Environment.getExternalStorageDirectory(), dosyaAdi+".pdf");
                                        pdfDocument.writeTo(new FileOutputStream(file));
                                        Toast.makeText(ogrenci_cap_basvurusu.this, "PDF Dosyasi Indirildi Imzalayýp Yükleyiniz.", Toast.LENGTH_SHORT).show();
                                    }
                                    catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    pdfDocument.close();
                                    //pdf oluþturma biter

                                }
                            });
                        }
                    });
                }
                else
                {
                    Toast.makeText(ogrenci_cap_basvurusu.this, "Lütfen Onaylayýnýz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Cap_FormYukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pdfYukle();
            }
        });

    }//main sonu

    private void pdfYukle()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF sec"), 12);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Cap_FormYukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploadPdf(data.getData());
                }
            });
        }
    }

    private void uploadPdf(Uri data)
    {

        DocumentReference docref8=db.collection("ogrenciler").document(userID);
        docref8.addSnapshotListener(ogrenci_cap_basvurusu.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                SimpleDateFormat pattern = new SimpleDateFormat("MMddyyyyHHmm");
                String tarih = pattern.format(new Date());

                dosya_adi.setText(value.getString("okul numarasi"));
                String a=dosya_adi.getText().toString();//okul num
                dosya_adi.getText().clear();

                dosya_adi.setText(value.getString("isim"));
                String b=dosya_adi.getText().toString();//isim
                dosya_adi.getText().clear();

                dosya_adi.setText(value.getString("soy isim"));
                String c=dosya_adi.getText().toString();//soyad
                dosya_adi.getText().clear();

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_capbasvuruform.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_cap_basvurusu.this);
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
                                dbref1.child(userID).child("cap_basvuruformu").setValue(putpdf);
                                dbref2.child(dbref2.push().getKey()).setValue(putpdf);
                                Toast.makeText(ogrenci_cap_basvurusu.this, "dosya yüklendi", Toast.LENGTH_SHORT).show();
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