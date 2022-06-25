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
import android.icu.text.SimpleDateFormat;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ogrenci_yaz_okulu_basvuru extends AppCompatActivity
{
    public static final String TAG = "TAG";
    Button Yaz_belgeIndir,Yaz_belgeYukle,Yaz_dekontYukle;
    EditText Yaz_OgrAd,Yaz_OgrSoyad,Yaz_OgrFakulte,Yaz_OgrBolum,
              Yaz_OgrNumara,Yaz_OgrTC,Yaz_OgrMail,Yaz_OgrTelefon,
              Yaz_OgrAdres,Yaz_BasvuruUni,Yaz_BasvuruTarih,Yaz_BirinciDers,
              Yaz_BirinciAKTS,Yaz_IkinciDers,Yaz_IkinciAKTS,Yaz_UcuncuDers,
            Yaz_UcuncuAKTS,Yaz_DorduncuDers,Yaz_DorduncuAKTS,Yaz_OgrKayitYil,Yaz_dosyaadi;
    CheckBox Yaz_onaylama;
    String durum="";
    String dosyaAdi="";
    int pageHeight = 1120 ;
    int pagewidth = 792;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference dbref1= FirebaseDatabase.getInstance().getReference("ogrencibasvurular");
    DatabaseReference dbref2= FirebaseDatabase.getInstance().getReference("akademisyenbasvurular");
    DatabaseReference dbref3= FirebaseDatabase.getInstance().getReference("akademisyenbelgeler");
    DatabaseReference dbref4= FirebaseDatabase.getInstance().getReference("ogrencibelgeler");
    FirebaseStorage storage;
    String userID=auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_yaz_okulu_basvuru);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Yaz_belgeIndir=(Button) findViewById(R.id.buton_ogrenci_basvuru_yazokulu_belge_indir);
        Yaz_belgeYukle=(Button) findViewById(R.id.buton_ogrenci_basvuru_yazokulu_belge_yukle);
        Yaz_dekontYukle=(Button) findViewById(R.id.buton_ogrenci_basvuru_yazokulu_dekont_yukle);
        Yaz_onaylama=(CheckBox) findViewById(R.id.checkBox_ogrenci_basvuru_yazokulu_onay);

        Yaz_OgrAd=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ad);
        Yaz_OgrSoyad=(EditText) findViewById(R.id.et_ogrenci_yazokulu_soyad);
        Yaz_OgrFakulte=(EditText) findViewById(R.id.et_ogrenci_yazokulu_fakulte);
        Yaz_OgrBolum=(EditText) findViewById(R.id.et_ogrenci_yazokulu_bolum);
        Yaz_OgrNumara=(EditText) findViewById(R.id.et_ogrenci_yazokulu_numara);
        Yaz_OgrTC=(EditText) findViewById(R.id.et_ogrenci_yazokulu_tckimlik);
        Yaz_OgrMail=(EditText) findViewById(R.id.et_ogrenci_yazokulu_eposta);
        Yaz_OgrTelefon=(EditText) findViewById(R.id.et_ogrenci_yazokulu_telno);
        Yaz_OgrAdres=(EditText) findViewById(R.id.et_ogrenci_yazokulu_adres);
        Yaz_BasvuruUni=(EditText) findViewById(R.id.et_ogrenci_yazokulu_basvuru_universite);
        Yaz_BasvuruTarih=(EditText) findViewById(R.id.et_ogrenci_yazokulu_tarih);
        Yaz_BirinciDers=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders1_dersadi);
        Yaz_BirinciAKTS=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders1_AKTS);
        Yaz_IkinciDers=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders1_almakistenilenders);
        Yaz_IkinciAKTS=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders1_almakistenilenakts2);
        Yaz_UcuncuDers=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders2_dersadi);
        Yaz_UcuncuAKTS=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders2_AKTS);
        Yaz_DorduncuDers=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders2_almakistenilenders);
        Yaz_DorduncuAKTS=(EditText) findViewById(R.id.et_ogrenci_yazokulu_ders2_almakistenilenakts2);
        Yaz_OgrKayitYil=(EditText)findViewById(R.id.et_ogrenci_yazokulu_kayityil);
        Yaz_dosyaadi=(EditText) findViewById(R.id.yaz_dosya_adi);

        Yaz_onaylama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Yaz_belgeIndir.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            String ogrenciad=Yaz_OgrAd.getText().toString();
                            String ogrencisoyad=Yaz_OgrSoyad.getText().toString();
                            String ogrencifakulte=Yaz_OgrFakulte.getText().toString();
                            String ogrencibolum=Yaz_OgrBolum.getText().toString();
                            String ogrencinumara=Yaz_OgrNumara.getText().toString();
                            String ogrencitc=Yaz_OgrTC.getText().toString();
                            String ogrencimail=Yaz_OgrMail.getText().toString();
                            String ogrencitelefon=Yaz_OgrTelefon.getText().toString();
                            String ogrenciadres=Yaz_OgrAdres.getText().toString();
                            String ogrencikayit=Yaz_OgrKayitYil.getText().toString();
                            String basvuruuni=Yaz_BasvuruUni.getText().toString();
                            String basvurutarih=Yaz_BasvuruTarih.getText().toString();
                            String birders=Yaz_BirinciDers.getText().toString();
                            String birakts=Yaz_BirinciAKTS.getText().toString();
                            String ikiders=Yaz_IkinciDers.getText().toString();
                            String ikiakts=Yaz_IkinciAKTS.getText().toString();
                            String ucders=Yaz_UcuncuDers.getText().toString();
                            String ucakts=Yaz_UcuncuAKTS.getText().toString();
                            String dortders=Yaz_DorduncuDers.getText().toString();
                            String dortakts=Yaz_DorduncuAKTS.getText().toString();
                            String durum="Bekliyor";

                            DocumentReference docRef = db.collection("Yaz Okulu Belgeleri").document(userID);

                            Map<String, Object> Yaz = new HashMap<>();
                            Yaz.put("isim", ogrenciad);
                            Yaz.put("soy isim", ogrencisoyad);
                            Yaz.put("fakulte", ogrencifakulte);
                            Yaz.put("bolum", ogrencibolum);
                            Yaz.put("numara", ogrencinumara);
                            Yaz.put("TC", ogrencitc);
                            Yaz.put("mail", ogrencimail);
                            Yaz.put("danýþman", ogrencitelefon);
                            Yaz.put("sýnýf", ogrenciadres);
                            Yaz.put("kayit yili",ogrencikayit);
                            Yaz.put("basvuracaðý unü", basvuruuni);
                            Yaz.put("basvuracaðý tarih", basvurutarih);
                            Yaz.put("aldýgý ders", birders);
                            Yaz.put("aldýgý dersin akts", birakts);
                            Yaz.put("istedigi ders", ikiders);
                            Yaz.put("istedigi dersin akts", ikiakts);
                            Yaz.put("aldýgý ikinci ders", ucders);
                            Yaz.put("aldýgý ikinci dersin akts", ucakts);
                            Yaz.put("istedigi ikinci ders", dortders);
                            Yaz.put("istedigi ikinci dersin akts", dortakts);
                            Yaz.put("durum",durum);
                            Yaz.put("user id",userID);

                            docRef.set(Yaz).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void unused)
                                {
                                    Toast.makeText(ogrenci_yaz_okulu_basvuru.this, "basvuru kaydedildi", Toast.LENGTH_SHORT).show();
                                }
                            });
                            //dosya adý için getter
                            DocumentReference docref9=db.collection("ogrenciler").document(userID);
                            docref9.addSnapshotListener(ogrenci_yaz_okulu_basvuru.this, new EventListener<DocumentSnapshot>()
                            {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error)
                                {

                                    java.text.SimpleDateFormat pattern = new java.text.SimpleDateFormat("MMddyyyyHHmm");
                                    String tarih = pattern.format(new Date());

                                    Yaz_dosyaadi.setText(value.getString("okul numarasi"));
                                    String a=Yaz_dosyaadi.getText().toString();//okul num
                                    Yaz_dosyaadi.getText().clear();

                                    Yaz_dosyaadi.setText(value.getString("isim"));
                                    String b=Yaz_dosyaadi.getText().toString();//isim
                                    Yaz_dosyaadi.setText(dosyaAdi);

                                    Yaz_dosyaadi.setText(value.getString("soy isim"));
                                    String c=Yaz_dosyaadi.getText().toString();//soyad
                                    Yaz_dosyaadi.setText(dosyaAdi);

                                    dosyaAdi=a + "_" + b + "_" + c + "_" + tarih;

                                    String ogrenciad=Yaz_OgrAd.getText().toString();
                                    String ogrencisoyad=Yaz_OgrSoyad.getText().toString();
                                    String ogrencifakulte=Yaz_OgrFakulte.getText().toString();
                                    String ogrencibolum=Yaz_OgrBolum.getText().toString();
                                    String ogrencinumara=Yaz_OgrNumara.getText().toString();
                                    String ogrencitc=Yaz_OgrTC.getText().toString();
                                    String ogrencimail=Yaz_OgrMail.getText().toString();
                                    String ogrencitelefon=Yaz_OgrTelefon.getText().toString();
                                    String ogrenciadres=Yaz_OgrAdres.getText().toString();
                                    String ogrencikayit=Yaz_OgrKayitYil.getText().toString();
                                    String basvuruuni=Yaz_BasvuruUni.getText().toString();
                                    String basvurutarih=Yaz_BasvuruTarih.getText().toString();
                                    String birders=Yaz_BirinciDers.getText().toString();
                                    String birakts=Yaz_BirinciAKTS.getText().toString();
                                    String ikiders=Yaz_IkinciDers.getText().toString();
                                    String ikiakts=Yaz_IkinciAKTS.getText().toString();
                                    String ucders=Yaz_UcuncuDers.getText().toString();
                                    String ucakts=Yaz_UcuncuAKTS.getText().toString();
                                    String dortders=Yaz_DorduncuDers.getText().toString();
                                    String dortakts=Yaz_DorduncuAKTS.getText().toString();

                                    PdfDocument pdfDocument = new PdfDocument();
                                    Paint title = new Paint();
                                    PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
                                    PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
                                    Canvas canvas = myPage.getCanvas();
                                    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                    title.setTextSize(18);

                                    title.setColor(ContextCompat.getColor(ogrenci_yaz_okulu_basvuru.this, R.color.black));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    canvas.drawText("Kocaeli Universitesi", 260, 50, title);
                                    canvas.drawText("YAZ OKULU BASVURU FORMU", 240, 25, title);
                                    canvas.drawText("TARIH: "+currentDateandTime+"", 520, 50, title);
                                    title.setTextSize(12);
                                    canvas.drawText("AD: " + ogrenciad, 100, 100, title);
                                    canvas.drawText("SOYAD: " + ogrencisoyad, 100, 120, title);
                                    canvas.drawText("Ogrenci Numarasi: " + ogrencinumara, 100, 140, title);
                                    canvas.drawText("T.C. Numarasi: " + ogrencitc, 400, 100, title);
                                    canvas.drawText("E-Posta Adresi: " + ogrencimail, 400, 120, title);
                                    canvas.drawText("Fakulte: " + ogrencifakulte, 400, 140, title);
                                    canvas.drawText("Bolum: " + ogrencibolum, 100, 160, title);
                                    canvas.drawText("Danisman: " + ogrencitelefon, 100, 180, title);
                                    canvas.drawText("Sinif: " + ogrenciadres , 100, 200, title);
                                    canvas.drawText("Kayit yili: " + ogrencikayit, 100, 220, title);
                                    canvas.drawText("Alinmak istenen dersler", 220, 240, title);
                                    canvas.drawText("Kodu / Kredisi / Adi", 100, 260, title);
                                    canvas.drawText("1. Tercihi" + "        " + birakts  + "        " + birders  , 100, 280, title);
                                    canvas.drawText("2. Tercihi" + "        " + ikiakts  + "        " + ikiders  , 100, 300, title);
                                    canvas.drawText("3. Tercihi" + "        " + ucakts   + "        " + ucders   , 100, 320, title);
                                    canvas.drawText("4. Tercihi" + "        " + dortakts + "        " + dortders , 100, 340, title);
                                    canvas.drawText("5. Tercihi", 100, 360, title);


                                    title.setTextSize(15);
                                    canvas.drawText("DANISMAN IMZA/ Ad Soyad", 100, 380, title);
                                    canvas.drawText("MALI ISLER IMZA/ Ad Soyad", 100, 400, title);
                                    canvas.drawText("OGRENCI ISLERI IMZA/ Ad Soyad", 100, 420, title);
                                    canvas.drawText("OGRENCI ISLERI IMZA/ Ad Soyad", 100, 440, title);
                                    canvas.drawText("YATIRILAN UCRET: TRY", 100, 460, title);


                                    title.setTextSize(15);
                                    canvas.drawText("Ogrenci Ad Soyad", 500, 900, title);
                                    canvas.drawText("Imza", 500, 940, title);
                                    pdfDocument.finishPage(myPage);
                                    File file = new File(Environment.getExternalStorageDirectory(), dosyaAdi+".pdf");
                                    try {
                                        pdfDocument.writeTo(new FileOutputStream(file));
                                        Toast.makeText(ogrenci_yaz_okulu_basvuru.this, "PDF Dosyasi Kaydedildi Sisteme Imzalayip Yukleyiniz.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ogrenci_yaz_okulu_basvuru.this, "lutfen onaylayiniz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Yaz_dekontYukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dekontYukle();
            }
        });
        Yaz_belgeYukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                belgeYukle();
            }
        });
    }//main sonu

    private void belgeYukle()
    {
        Intent intent6 = new Intent();
        intent6.setType("application/pdf");
        intent6.setAction(intent6.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent6, "PDF sec"), 12);
    }
    private void dekontYukle()
    {
        Intent intent7 = new Intent();
        intent7.setType("application/pdf");
        intent7.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent7, "PDF seç"), 13);
    }
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 13 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Yaz_dekontYukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploaddekont(data.getData());
                }
            });
        }
        if(requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Yaz_belgeYukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploadbelge(data.getData());
                }
            });
        }
    }
    private void uploadbelge(Uri data)
    {

        DocumentReference docref11=db.collection("ogrenciler").document(userID);
        docref11.addSnapshotListener(ogrenci_yaz_okulu_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                java.text.SimpleDateFormat pattern = new java.text.SimpleDateFormat("MMddyyyyHHmm");
                String tarih = pattern.format(new Date());

                Yaz_dosyaadi.setText(value.getString("okul numarasi"));
                String a=Yaz_dosyaadi.getText().toString();//okul num
                Yaz_dosyaadi.getText().clear();

                Yaz_dosyaadi.setText(value.getString("isim"));
                String b=Yaz_dosyaadi.getText().toString();//isim
                Yaz_dosyaadi.setText(dosyaAdi);

                Yaz_dosyaadi.setText(value.getString("soy isim"));
                String c=Yaz_dosyaadi.getText().toString();//soyad
                Yaz_dosyaadi.setText(dosyaAdi);

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_yazokulu.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_yaz_okulu_basvuru.this);
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
                                dbref1.child(userID).child("yaz_basvuruformu").setValue(putpdf);
                                dbref2.child(dbref2.push().getKey()).setValue(putpdf);

                                Toast.makeText(ogrenci_yaz_okulu_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
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
    }//uni sýnav sonucu yükeleme bitti


    private void uploaddekont(Uri data)
    {

        DocumentReference docref10=db.collection("ogrenciler").document(userID);
        docref10.addSnapshotListener(ogrenci_yaz_okulu_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                java.text.SimpleDateFormat pattern = new java.text.SimpleDateFormat("MMddyyyyHHmm");
                String tarih = pattern.format(new Date());

                Yaz_dosyaadi.setText(value.getString("okul numarasi"));
                String a=Yaz_dosyaadi.getText().toString();//okul num
                Yaz_dosyaadi.getText().clear();

                Yaz_dosyaadi.setText(value.getString("isim"));
                String b=Yaz_dosyaadi.getText().toString();//isim
                Yaz_dosyaadi.setText(dosyaAdi);

                Yaz_dosyaadi.setText(value.getString("soy isim"));
                String c=Yaz_dosyaadi.getText().toString();//soyad
                Yaz_dosyaadi.setText(dosyaAdi);

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_dekont.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_yaz_okulu_basvuru.this);
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
                                putPDF putpdf = new putPDF(dosyaAdi, uri.toString(),durum);//realtime içerik
                                dbref4.child(userID).child("yaz_dekont").setValue(putpdf);//ogrenci belgeler
                                dbref3.child(dbref3.push().getKey()).setValue(putpdf);//akademisyen belgeler
                                Toast.makeText(ogrenci_yaz_okulu_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
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
    }//uni sýnav sonucu yükeleme bitti
}