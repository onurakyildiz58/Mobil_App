package com.berk.odevberk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ogrenci_kaydol extends AppCompatActivity
{
    public static final String TAG = "TAG";
    private Uri dosyaYolu;
    private final int PICK_IMAGE_REQUEST = 22;
    String userID;

    FirebaseStorage storage;
    FirebaseAuth dbAuth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("uploadFile");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    ArrayList<String> fakulte;
    ArrayAdapter<String> fakulte_adapter;

    ArrayList<String> fakulte_muh, fakulte_egitim, fakulte_iktisat, fakulte_iletisim, fakulte_guzelsanat;
    ArrayAdapter<String> bolum_adapter;

    ImageView Kaydol_ppEkle;
    EditText Kaydol_Ad, Kaydol_Soyad, Kaydol_OgrTC, Kaydol_DogumTarih, Kaydol_OgrTElNo, Kaydol_OgrUni, Kaydol_OgrNo, Kaydol_Mail, Kaydol_OgrAdres, Kaydol_OgrIsAdres, Kaydol_OgrSifre, Kaydol_Sinif;
    Spinner Kaydol_Fakulte,Kaydol_Bolum;
    Button Kaydol_Kayit;
    CheckBox Kaydol_SifreGoster;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_kaydol);
        //-----------Durum çubuğunu gizlemek için satır-------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Kaydol_Kayit       = (Button)   findViewById(R.id.button_ogrenci_kaydol);
        Kaydol_Fakulte     = (Spinner)  findViewById(R.id.et_ogrenci_kaydol_fakulte);
        Kaydol_Bolum       = (Spinner)  findViewById(R.id.et_ogrenci_kaydol_bolum);
        Kaydol_SifreGoster = (CheckBox) findViewById(R.id.cb_ogrenci_kaydol_sifregoster);
        Kaydol_Ad          = (EditText) findViewById(R.id.et_ogrenci_kaydol_ad);
        Kaydol_Soyad       = (EditText) findViewById(R.id.et_ogrenci_kaydol_soyad);
        Kaydol_OgrTC       = (EditText) findViewById(R.id.et_ogrenci_kaydol_ogrtc);
        Kaydol_Sinif       = (EditText) findViewById(R.id.et_ogrenci_kaydol_sinif);
        Kaydol_DogumTarih  = (EditText) findViewById(R.id.et_ogrenci_kaydol_dogumtarihi);
        Kaydol_OgrTElNo    = (EditText) findViewById(R.id.et_ogrenci_kaydol_ogrtelno);
        Kaydol_OgrUni      = (EditText) findViewById(R.id.et_ogrenci_kaydol_uni);
        Kaydol_OgrNo       = (EditText) findViewById(R.id.et_ogrenci_kaydol_ogrno);
        Kaydol_Mail        = (EditText) findViewById(R.id.et_ogrenci_kaydol_mail);
        Kaydol_OgrAdres    = (EditText) findViewById(R.id.et_ogrenci_kaydol_ogradres);
        Kaydol_OgrIsAdres  = (EditText) findViewById(R.id.et_ogrenci_kaydol_isadresi);
        Kaydol_OgrSifre    = (EditText) findViewById(R.id.et_ogrenci_kaydol_ogrsifre);
        Kaydol_ppEkle      = (ImageView)findViewById(R.id.ogrenci_kaydol_imageview);

        //şifre gösterme
        Kaydol_SifreGoster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)//tıklanmış olduğunda şifreyi gösterir
                {
                    Kaydol_OgrSifre.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    Kaydol_OgrSifre.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //şifre gösterme bitti

        //fakülteleri seçer
        //https://www.youtube.com/watch?v=NgFBaffNEOY
        //spinner nası yapılır
        CollectionReference subjectsRef = db.collection("fakulteler");
        fakulte = new ArrayList<>();
        fakulte_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fakulte);
        fakulte_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Kaydol_Fakulte.setAdapter(fakulte_adapter);

        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        ArrayList<String> arrayList = (ArrayList<String>) document.get("fakulte");
                        for (String s : arrayList)
                        {
                            fakulte.add(s);
                        }
                    }
                    fakulte_adapter.notifyDataSetChanged();

                }
            }
        });
        //fakülte seçme biter

        // bölüm seçer fakülteye göre
        Kaydol_Fakulte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(i == 0)
                {
                    CollectionReference subjectsRef2 = db.collection("bolumler");
                    fakulte_egitim = new ArrayList<>();
                    bolum_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fakulte_egitim);
                    bolum_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Kaydol_Bolum.setAdapter(bolum_adapter);

                    subjectsRef2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    ArrayList<String> arrayList2 = (ArrayList<String>) document.get("Eğitim Fakültesi");
                                    for (String b : arrayList2)
                                    {
                                        fakulte_egitim.add(b);
                                    }
                                }
                                bolum_adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else if(i == 1)
                {
                    CollectionReference subjectsRef3 = db.collection("bolumler");
                    fakulte_iktisat = new ArrayList<>();
                    bolum_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fakulte_iktisat);
                    bolum_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Kaydol_Bolum.setAdapter(bolum_adapter);

                    subjectsRef3.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    ArrayList<String> arrayList3 = (ArrayList<String>) document.get("İktisadi ve İdari Bilimler Fakültesi");
                                    for (String c : arrayList3)
                                    {
                                        fakulte_iktisat.add(c);
                                    }
                                }
                                bolum_adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else  if(i == 2)
                {
                    CollectionReference subjectsRef1 = db.collection("bolumler");
                    fakulte_muh = new ArrayList<>();
                    bolum_adapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fakulte_muh);
                    bolum_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Kaydol_Bolum.setAdapter(bolum_adapter);

                    subjectsRef1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    ArrayList<String> arrayList1 = (ArrayList<String>) document.get("Mühendislik Fakültesi");
                                    for (String a : arrayList1)
                                    {
                                        fakulte_muh.add(a);
                                    }
                                }
                                bolum_adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else if(i == 3)
                {
                    CollectionReference subjectsRef4 = db.collection("bolumler");
                    fakulte_iletisim= new ArrayList<>();
                    bolum_adapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fakulte_iletisim);
                    bolum_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Kaydol_Bolum.setAdapter(bolum_adapter);

                    subjectsRef4.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    ArrayList<String> arrayList4 = (ArrayList<String>) document.get("İletişim Fakültesi");
                                    for (String d : arrayList4)
                                    {
                                        fakulte_iletisim.add(d);
                                    }
                                }
                                bolum_adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else if(i == 4)
                {
                    CollectionReference subjectsRef5 = db.collection("bolumler");
                    fakulte_guzelsanat = new ArrayList<>();
                    bolum_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fakulte_guzelsanat);
                    bolum_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Kaydol_Bolum.setAdapter(bolum_adapter);

                    subjectsRef5.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    ArrayList<String> arrayList5 = (ArrayList<String>) document.get("Güzel Sanatlar Fakültesi");
                                    for (String e : arrayList5)
                                    {
                                        fakulte_guzelsanat.add(e);
                                    }
                                }
                                bolum_adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {    }
        });
        //bölüm seç biter

        //kayıt başlarrr
        Kaydol_Kayit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Kaydol_ppEkle.setBackground(Drawable.createFromPath("@drawable/ic_baseline_add_a_photo_24"));
                String mail           = Kaydol_Mail.getText().toString();
                String sifre          = Kaydol_OgrSifre.getText().toString();
                String ad             = Kaydol_Ad.getText().toString();
                String soyad          = Kaydol_Soyad.getText().toString();
                String TC             = Kaydol_OgrTC.getText().toString();
                String dogumtarihi    = Kaydol_DogumTarih.getText().toString();
                String telefon        = Kaydol_OgrTElNo.getText().toString();
                String uni            = Kaydol_OgrUni.getText().toString();
                String numara         = Kaydol_OgrNo.getText().toString();
                String adres          = Kaydol_OgrAdres.getText().toString();
                String isadres        = Kaydol_OgrIsAdres.getText().toString();
                String sinif          = Kaydol_Sinif.getText().toString();
                String spinnerFakulte = Kaydol_Fakulte.getSelectedItem().toString();
                String spinnerBolum   = Kaydol_Bolum.getSelectedItem().toString();


                dbAuth.createUserWithEmailAndPassword(mail, sifre)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful() == true)
                                {
                                    Toast.makeText(ogrenci_kaydol.this, "kayıt başarılı", Toast.LENGTH_SHORT).show();
                                    userID=dbAuth.getCurrentUser().getUid();
                                    DocumentReference docRef = db.collection("ogrenciler").document(userID);

                                    Map<String, Object> ogrenciler = new HashMap<>();
                                    ogrenciler.put("isim", ad);
                                    ogrenciler.put("soy isim", soyad);
                                    ogrenciler.put("TC", TC);
                                    ogrenciler.put("dogum tarihi", dogumtarihi);
                                    ogrenciler.put("mail", mail);
                                    ogrenciler.put("sifre", sifre);
                                    ogrenciler.put("telefon numarasi", telefon);
                                    ogrenciler.put("universite", uni);
                                    ogrenciler.put("fakulte", spinnerFakulte);
                                    ogrenciler.put("bolum", spinnerBolum);
                                    ogrenciler.put("sınıf", sinif);
                                    ogrenciler.put("okul numarasi", numara);
                                    ogrenciler.put("adres", adres);
                                    ogrenciler.put("is adres", isadres);
                                    ogrenciler.put("user id",userID);


                                    docRef.set(ogrenciler).addOnSuccessListener(new OnSuccessListener<Void>()
                                    {
                                        @Override
                                        public void onSuccess(Void unused)
                                        {
                                            Log.d(TAG, "kullanıcı oluşturuldu" + userID);
                                            startActivity(new Intent(getApplicationContext(), giris.class));
                                        }
                                    });

                                    if (dosyaYolu != null)
                                    {
                                        ProgressDialog progressDialog = new ProgressDialog(ogrenci_kaydol.this);
                                        progressDialog.setTitle("Yükleniyor");
                                        progressDialog.show();

                                        //StorageReference ref = storageReference.child("images/");
                                        StorageReference ref=FirebaseStorage.getInstance().getReference().child("ProfilFotosu/").child(userID +"/profil.jpg");

                                        ref.putFile(dosyaYolu).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                                        {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(ogrenci_kaydol.this, "Yüklendi", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception e)
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(ogrenci_kaydol.this, "Foto Yükleme Başarısız " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                                        {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                                            {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                                progressDialog.setMessage((int) progress + " %");
                                            }
                                        });
                                    }

                                }
                                else
                                {
                                    Toast.makeText(ogrenci_kaydol.this, "kayıt başarısız", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });
        //kayıt ol biter

        //foto ekleme başlar
        Kaydol_ppEkle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Kaydol_ppEkle.setBackground(null);
                fotosec();
            }
        });
        //foto ekleme biter
    }//main sonu
    //foto ekle metot
    //https://www.geeksforgeeks.org/android-how-to-upload-an-image-on-firebase-storage/
    //https://www.google.com/search?client=opera&q=java+picasso+library&sourceid=opera&ie=UTF-8&oe=UTF-8
    private void fotosec()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Profil Fotosunu Burdan Seçiniz"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            dosyaYolu = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), dosyaYolu);
                Kaydol_ppEkle.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    //foto ekle metot

}