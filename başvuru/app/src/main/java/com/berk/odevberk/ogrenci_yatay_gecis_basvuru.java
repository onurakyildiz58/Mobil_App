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
import android.widget.TextView;
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

public class ogrenci_yatay_gecis_basvuru extends AppCompatActivity
{
    public static final String TAG = "TAG";
    Button Yatay_unisinavsonucyukle,Yatay_notbelgesiyukle,Yatay_transkripyukle,Yatay_disiplinyukle,Yatay_basvurubelyukle,Yatay_basvurubelindir;
    CheckBox Yatay_onaylama;
    EditText Yatay_Universite,Yatay_Fakulte,Yatay_Bolum,Yatay_Sinif,
            Yatay_OgrenciUni,Yatay_OgrenciFak,Yatay_OgrenciBol,
            Yatay_OgrenciSinif,Yatay_OgrenciAd,Yatay_OgrenciSoyad,
            Yatay_OgrenciNumara,Yatay_OgrenciTelefon,Yatay_OgrenciAdres,dosya_adi;
    String durum="";
    String dosyaAdi="";
    int pageHeight = 1120 ;
    int pagewidth = 792;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference dbref1= FirebaseDatabase.getInstance().getReference("ogrencibasvurular");
    DatabaseReference dbref4= FirebaseDatabase.getInstance().getReference("ogrencibelgeler");
    DatabaseReference dbref2= FirebaseDatabase.getInstance().getReference("akademisyenbasvurular");
    DatabaseReference dbref3= FirebaseDatabase.getInstance().getReference("akademisyenbelgeler");
    FirebaseStorage storage;
    String userID=auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_yatay_gecis_basvuru);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Yatay_unisinavsonucyukle=(Button) findViewById(R.id.buton_ogrenci_yatay_gecis_sinavsonucyukle);
        Yatay_notbelgesiyukle=(Button) findViewById(R.id.buton_ogrenci_yatay_gecis_notbelgesiyukle);
        Yatay_transkripyukle=(Button) findViewById(R.id.buton_ogrenci_yatay_gecis_transkripyukle);
        Yatay_disiplinyukle=(Button) findViewById(R.id.buton_ogrenci_yatay_gecis_disiplinyukle);
        Yatay_basvurubelyukle=(Button) findViewById(R.id.buton_ogrenci_yatay_gecis_basvurubelyukle);
        Yatay_basvurubelindir=(Button) findViewById(R.id.buton_ogrenci_yatay_gecis_basvurubelindir);
        Yatay_onaylama=(CheckBox) findViewById(R.id.checkBox_ogrenci_yatay_gecis_basvuru);
        dosya_adi=(EditText)findViewById(R.id.yatay_dosya_adi);
        Yatay_Universite=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_basvurubilgileri_universite);
        Yatay_Fakulte=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_basvurubilgileri_fakulte);
        Yatay_Bolum=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_basvurubilgileri_bolum);
        Yatay_Sinif=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_basvurubilgileri_sinif);
        Yatay_OgrenciUni=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_universite);
        Yatay_OgrenciFak=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_fakulte);
        Yatay_OgrenciBol=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_bolum);
        Yatay_OgrenciSinif=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_ogrsinif);
        Yatay_OgrenciAd=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_ad);
        Yatay_OgrenciSoyad=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_soyad);
        Yatay_OgrenciNumara=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_ogrno);
        Yatay_OgrenciTelefon=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_telno);
        Yatay_OgrenciAdres=(EditText) findViewById(R.id.et_ogrenci_yatay_gecis_ogrencibilgileri_adres);

        Yatay_onaylama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Yatay_basvurubelindir.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            String belgeuni=Yatay_Universite.getText().toString();
                            String belgefak=Yatay_Fakulte.getText().toString();
                            String belgebol=Yatay_Bolum.getText().toString();
                            String belgesinif=Yatay_Sinif.getText().toString();
                            String ogruni=Yatay_OgrenciUni.getText().toString();
                            String ogrfak=Yatay_OgrenciFak.getText().toString();
                            String ogrbol=Yatay_OgrenciBol.getText().toString();
                            String ogrsinif=Yatay_OgrenciSinif.getText().toString();
                            String ograd=Yatay_OgrenciAd.getText().toString();
                            String ogrsoyad=Yatay_OgrenciSoyad.getText().toString();
                            String ogenumara=Yatay_OgrenciNumara.getText().toString();
                            String ogrtel=Yatay_OgrenciTelefon.getText().toString();
                            String ogradres=Yatay_OgrenciAdres.getText().toString();
                            String durum="Bekliyor";

                            DocumentReference docRef = db.collection("Yatay Gecis Belgeleri").document(userID);

                            Map<String, Object> Yatay = new HashMap<>();
                            Yatay.put("basvuracagý uni", belgeuni);
                            Yatay.put("basvuracagý fakulte", belgefak);
                            Yatay.put("basvuracagý bolum", belgebol);
                            Yatay.put("basvuracagý sýnýf", belgesinif);
                            Yatay.put("ogrenci universite", ogruni);
                            Yatay.put("ogrenci fakulte", ogrfak);
                            Yatay.put("ogrenci bolum", ogrbol);
                            Yatay.put("ogrenci sinif", ogrsinif);
                            Yatay.put("ogrenci ad", ograd);
                            Yatay.put("ogrenci soyad", ogrsoyad);
                            Yatay.put("ogrenci numara", ogenumara);
                            Yatay.put("ogrenci telefon", ogrtel);
                            Yatay.put("ogrenci adres", ogradres);
                            Yatay.put("durum",durum);
                            Yatay.put("user id",userID);

                            docRef.set(Yatay).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void unused)
                                {
                                    Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "basvuru kaydedildi", Toast.LENGTH_SHORT).show();
                                }
                            });

                            //dosya adý için getter
                            DocumentReference docref1=db.collection("ogrenciler").document(userID);
                            docref1.addSnapshotListener(ogrenci_yatay_gecis_basvuru.this, new EventListener<DocumentSnapshot>()
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

                                    //https://www.geeksforgeeks.org/how-to-generate-a-pdf-file-in-android-app/
                                    //https://www.tutorialspoint.com/itext/itext_setting_position_of_image.htm
                                    //https://stackoverflow.com/questions/29679966/add-a-bitmap-image-in-a-pdf-document-in-android
                                    //https://stackoverflow.com/questions/4847093/how-do-i-write-a-java-pattern-in-this-format-filepath-int-int-int-int
                                    //https://stackoverflow.com/questions/40171085/android-firebase-storage-innerclass-download-to-bitmap
                                    //pdf oluþturma
                                    String belgeuni=Yatay_Universite.getText().toString();
                                    String belgefak=Yatay_Fakulte.getText().toString();
                                    String belgebol=Yatay_Bolum.getText().toString();
                                    String belgesinif=Yatay_Sinif.getText().toString();
                                    String ogruni=Yatay_OgrenciUni.getText().toString();
                                    String ogrfak=Yatay_OgrenciFak.getText().toString();
                                    String ogrbol=Yatay_OgrenciBol.getText().toString();
                                    String ogrsinif=Yatay_OgrenciSinif.getText().toString();
                                    String ograd=Yatay_OgrenciAd.getText().toString();
                                    String ogrsoyad=Yatay_OgrenciSoyad.getText().toString();
                                    String ogrnumara=Yatay_OgrenciNumara.getText().toString();
                                    String ogrtel=Yatay_OgrenciTelefon.getText().toString();
                                    String ogradres=Yatay_OgrenciAdres.getText().toString();

                                    PdfDocument pdfDocument = new PdfDocument();
                                    Paint title = new Paint();
                                    PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
                                    PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
                                    Canvas canvas = myPage.getCanvas();
                                    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                    title.setTextSize(18);
                                    title.setColor(ContextCompat.getColor(ogrenci_yatay_gecis_basvuru.this, R.color.black));
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    canvas.drawText("Kocaeli Universitesi", 260, 50, title);
                                    canvas.drawText("Yatay Gecis Basvuru Formu", 300, 25, title);
                                    title.setTextSize(12);
                                    canvas.drawText("TARIH: "+currentDateandTime+"", 520, 50, title);

                                    canvas.drawText(ogruni+" universitesi "+ogrfak+" Fakultesi "+ogrbol+" bolumunden "+ogrsinif+". sinif "+ogrnumara+" numarali ogrencisiyim", 100, 100, title);
                                    canvas.drawText("Fakultenizin "+belgebol+" bolumu "+belgesinif+". sinifina yatay gecis yapmak istemekteyim.", 100, 120, title);
                                    canvas.drawText("Tarafinizdan talep edilen belgeler ilisikte sunulmus olup", 100, 140, title);
                                    canvas.drawText("bilgilerinizi ve geregini arz ederim", 100, 160, title);

                                    canvas.drawText("Adres: "+ogradres, 100, 180, title);
                                    canvas.drawText("Irtibat Telefonu: "+ogrtel, 100, 200, title);

                                    canvas.drawText("Ekler", 100, 280, title);
                                    canvas.drawText("Not cizelgesi transkript: ", 100, 310, title);
                                    canvas.drawText("Not degerlendirme sistemi onayli: ", 100, 340, title);
                                    canvas.drawText("Disiplin belgesi: ", 100, 370, title);
                                    canvas.drawText("Universite sinav sonuc belgesi: ", 100, 400, title);
                                    title.setTextSize(15);
                                    canvas.drawText("Ogrenci Ad Soyad", 500, 900, title);
                                    canvas.drawText("Imza", 500, 940, title);
                                    pdfDocument.finishPage(myPage);
                                    File file = new File(Environment.getExternalStorageDirectory(), dosyaAdi+".pdf");
                                    try {
                                        pdfDocument.writeTo(new FileOutputStream(file));
                                        Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "PDF Dosyasi Indirildi Imzalayip Yukleyiniz.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "lütfen onaylayýnýz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //pdf yükeleme
        //https://www.youtube.com/watch?v=lY9zSr6cxko
        //https://stackoverflow.com/questions/54988533/how-to-populate-a-spinner-with-the-result-of-a-firestore-query
        //https://stackoverflow.com/questions/53747054/firebase-get-an-arraylist-field-from-all-documents
        //https://www.youtube.com/watch?v=mkWZNjDCSdg
        //https://www.youtube.com/watch?v=D06hA5PW-TU
        Yatay_unisinavsonucyukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                unisonucuyukle();
            }
        });

        Yatay_notbelgesiyukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notbelgeslyukle();
            }
        });

        Yatay_transkripyukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                transkriptyukle();
            }
        });

        Yatay_disiplinyukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                disiplinyukle();
            }
        });

        Yatay_basvurubelyukle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                basvuruyukle();
            }
        });

    }//main sonu

    private void unisonucuyukle()
    {
        Intent intent1 = new Intent();
        intent1.setType("application/pdf");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent1, "PDF seç"), 12);
    }
    private void transkriptyukle()
    {
        Intent intent4 = new Intent();
        intent4.setType("application/pdf");
        intent4.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent4, "PDF seç"), 13);
    }
    private void disiplinyukle()
    {
        Intent intent5 = new Intent();
        intent5.setType("application/pdf");
        intent5.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent5, "PDF seç"), 14);
    }
    private void basvuruyukle()
    {
        Intent intent3 = new Intent();
        intent3.setType("application/pdf");
        intent3.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent3, "PDF seç"), 15);
    }
    private void notbelgeslyukle()
    {
        Intent intent2 = new Intent();
        intent2.setType("application/pdf");
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent2, "PDF seç"), 16);
    }
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 16 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Yatay_notbelgesiyukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploadnotPdf(data.getData());
                }
            });
        }
        else if(requestCode == 15 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Yatay_basvurubelyukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploadbasvuruPdf(data.getData());
                }
            });
        }
        else if(requestCode == 14 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Yatay_disiplinyukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploaddisiplinPdf(data.getData());
                }
            });
        }
        else if(requestCode == 13 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Yatay_transkripyukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploadtransPdf(data.getData());
                }
            });
        }
        else if(requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Yatay_unisinavsonucyukle.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    uploaduniPdf(data.getData());
                }
            });

        }
    }

    private void uploaduniPdf(Uri data)
    {

        DocumentReference docref2=db.collection("ogrenciler").document(userID);
        docref2.addSnapshotListener(ogrenci_yatay_gecis_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

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

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_unisonucu.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_yatay_gecis_basvuru.this);
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
                                dbref4.child(userID).child("uni_sonucu").setValue(putpdf);
                                dbref3.child(dbref3.push().getKey()).setValue(putpdf);
                                Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
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
    private void uploadnotPdf(Uri data)
    {

        DocumentReference docref3=db.collection("ogrenciler").document(userID);
        docref3.addSnapshotListener(ogrenci_yatay_gecis_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

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

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_notbelgesi.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_yatay_gecis_basvuru.this);
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
                                dbref4.child(userID).child("not_belgesi").setValue(putpdf);
                                dbref3.child(dbref3.push().getKey()).setValue(putpdf);
                                Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
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
    private void uploadbasvuruPdf(Uri data)
    {

        DocumentReference docref6=db.collection("ogrenciler").document(userID);
        docref6.addSnapshotListener(ogrenci_yatay_gecis_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                java.text.SimpleDateFormat pattern = new java.text.SimpleDateFormat("MMddyyyyHHmm");
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

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_yataygecis.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_yatay_gecis_basvuru.this);
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
                                dbref1.child(userID).child("yataygecis_basvuruformu").setValue(putpdf);
                                dbref2.child(dbref2.push().getKey()).setValue(putpdf);
                                Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
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
    private void uploadtransPdf(Uri data)
    {

        DocumentReference docref4=db.collection("ogrenciler").document(userID);
        docref4.addSnapshotListener(ogrenci_yatay_gecis_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

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

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_transktript.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_yatay_gecis_basvuru.this);
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
                                dbref4.child(userID).child("transkript").setValue(putpdf);
                                dbref3.child(dbref3.push().getKey()).setValue(putpdf);
                                Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
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
    private void uploaddisiplinPdf(Uri data)
    {

        DocumentReference docref5=db.collection("ogrenciler").document(userID);
        docref5.addSnapshotListener(ogrenci_yatay_gecis_basvuru.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

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

                dosyaAdi=a + "_" + b + "_" + c + "_" + tarih+"_disiplin.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(ogrenci_yatay_gecis_basvuru.this);
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
                                dbref4.child(userID).child("disiplin_belgesi").setValue(putpdf);
                                dbref3.child(dbref3.push().getKey()).setValue(putpdf);
                                Toast.makeText(ogrenci_yatay_gecis_basvuru.this, "dosya yuklendi", Toast.LENGTH_SHORT).show();
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