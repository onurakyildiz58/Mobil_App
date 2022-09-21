package com.berk.odevberk;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class akademisyen_giris extends AppCompatActivity
{
    FirebaseAuth Auth=FirebaseAuth.getInstance();
    CheckBox sifregoster;
    Button giris,akademisyen_sifremi_sifirla;
    EditText aka_mail,aka_sifre;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akademisyen_giris);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        giris = (Button)findViewById(R.id.buton_akademsiyen_ekrani_giris_yap);
        akademisyen_sifremi_sifirla= (Button)findViewById(R.id.buton_akademisyen_sifremi_unuttum2);
        sifregoster=(CheckBox)findViewById(R.id.cb_sifre_goster);
        aka_mail=(EditText)findViewById(R.id.et_akademisyen_ekrani_mail);
        aka_sifre=(EditText)findViewById(R.id.et_akademisyen_ekrani_sifre2);

        akademisyen_sifremi_sifirla.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText mail = new EditText(v.getContext());
                AlertDialog.Builder SifreSifirlamaDiyalog = new AlertDialog.Builder(v.getContext());
                SifreSifirlamaDiyalog.setTitle("Þifremi Unuttum");
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
                                Toast.makeText(akademisyen_giris.this,"þifre sýfýrlama linki mail adresinize gönderilmiþtir",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(akademisyen_giris.this,"mail gönderilemedi" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                SifreSifirlamaDiyalog.setNegativeButton("Ýptal", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
                SifreSifirlamaDiyalog.create().show();
            }
        });

        giris.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String mail=aka_mail.getText().toString();
                String pass=aka_sifre.getText().toString();

                Auth.signInWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(akademisyen_giris.this, "giriþ baþarýlý", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), akademisyen_basvurular.class));
                                }
                                else
                                {
                                    Toast.makeText(akademisyen_giris.this, "giriþ baþarýsýz", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        sifregoster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    aka_sifre.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    aka_sifre.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }
}