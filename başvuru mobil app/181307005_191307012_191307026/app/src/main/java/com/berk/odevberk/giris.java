package com.berk.odevberk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class giris extends AppCompatActivity
{
    Button Giris_OgrKayit,Giris_OgrGiris,Giris_AkademisyenGiris,Giris_SifremiUnuttum;
    EditText Giris_Numara,Giris_Sifre;
    CheckBox Giris_SifreGoster;

    FirebaseAuth Auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris);
        //-----------Durum çubuğunu gizlemek için satır-------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Giris_OgrKayit         = (Button)   findViewById(R.id.buton_ogrenci_kaydol);
        Giris_OgrGiris         = (Button)   findViewById(R.id.buton_ogrenci_giris);
        Giris_SifremiUnuttum   = (Button)   findViewById(R.id.buton_ogrenci_sifremi_unuttum);
        Giris_AkademisyenGiris = (Button)   findViewById(R.id.button_giris_ekrani_akademisyen);
        Giris_Numara           = (EditText) findViewById(R.id.et_giris_ogrenci_mail);
        Giris_Sifre            = (EditText) findViewById(R.id.et_giris_ogrenci_sifre);
        Giris_SifreGoster      = (CheckBox) findViewById(R.id.ch_giris_sifregoster);

        //kayıt ekranını açar
        Giris_OgrKayit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(giris.this, ogrenci_kaydol.class));
            }
        });//kayıt ekranı açma işlemi bitiş

        //giriş yapar
        Giris_OgrGiris.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String mail=Giris_Numara.getText().toString();
                String pass=Giris_Sifre.getText().toString();

                Auth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(giris.this, "giriş başarılı", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ogrenci_ana_ekran.class));
                        }
                        else
                        {
                            Toast.makeText(giris.this, "giriş başarısız", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });//giriş işlemi bitiş

        //akademisyen giriş ekranını açar
        Giris_AkademisyenGiris.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(giris.this, akademisyen_giris.class));
            }
        });//akademisyen ekranı açma işlemi bitiş

        //şifreyi açıp kapar
        Giris_SifreGoster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Giris_Sifre.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    Giris_Sifre.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });//şifre gösterme işlemi bitiş

        //şifre sıfırlama işlemi
        //https://www.youtube.com/watch?v=oKMNzd7GVDM
        Giris_SifremiUnuttum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText mail = new EditText(view.getContext());
                AlertDialog.Builder SifreSifirlamaDiyalog = new AlertDialog.Builder(view.getContext());
                SifreSifirlamaDiyalog.setTitle("Şifremi Unuttum");
                SifreSifirlamaDiyalog.setMessage("Mail Adresinizi Giriniz");
                SifreSifirlamaDiyalog.setView(mail);

                SifreSifirlamaDiyalog.setPositiveButton("Gönder", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String email=mail.getText().toString();
                        Auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void unused)
                            {
                                Toast.makeText(giris.this,"şifre sıfırlama linki mail adresinize gönderilmiştir",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(giris.this,"mail gönderilemedi" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                SifreSifirlamaDiyalog.setNegativeButton("İptal", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
                SifreSifirlamaDiyalog.create().show();
            }
        });//şifre sıfırlama biter
    }
}