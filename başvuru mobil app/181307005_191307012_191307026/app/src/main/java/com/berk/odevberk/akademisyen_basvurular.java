package com.berk.odevberk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.protobuf.Value;

import java.util.ArrayList;


public class akademisyen_basvurular extends AppCompatActivity {

    private RecyclerView recyclerView1;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root1 = db.getReference().child("akademisyenbasvurular");
    private akademisyen_basvurular_adapter adapter1;
    private ArrayList<akademisyen_gelen_basvurulari_gormek> list1;
    Button belgeleriac;
    TextView Main_AkaAd,Main_AkaBolum;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    String userID=auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akademisyen_basvurular);

        //-----------Öðrenci kaydol activitysini açmak için kodlar------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        belgeleriac=(Button)findViewById(R.id.btn_belge_ac);
        Main_AkaAd=(TextView)findViewById(R.id.editTextTextPersonName);
        Main_AkaBolum=(TextView)findViewById(R.id.editTextTextPersonName2);

        belgeleriac.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(akademisyen_basvurular.this,akademisyen_basvuru_belgeleri.class));
            }
        });

        DocumentReference docref=fStore.collection("akademisyen").document(userID);
        docref.addSnapshotListener(akademisyen_basvurular.this, new EventListener<DocumentSnapshot>()
        {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error)
            {
                Main_AkaAd.setText(value.getString("isim")+" "+value.getString("soyad"));
                Main_AkaBolum.setText(value.getString("bolum"));
            }
        });

        recyclerView1= findViewById(R.id.firestore_list_akademisyen_basvurular);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        list1 =new ArrayList<>();
        adapter1 = new akademisyen_basvurular_adapter(this, list1);
        recyclerView1.setAdapter(adapter1);
        root1.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    akademisyen_gelen_basvurulari_gormek model1 = dataSnapshot.getValue(akademisyen_gelen_basvurulari_gormek.class);
                    list1.add(model1);
                }
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }
}



