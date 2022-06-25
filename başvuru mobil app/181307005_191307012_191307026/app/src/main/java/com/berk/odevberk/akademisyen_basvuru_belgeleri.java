package com.berk.odevberk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class akademisyen_basvuru_belgeleri extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db1 = FirebaseDatabase.getInstance();
    private DatabaseReference root = db1.getReference().child("akademisyenbelgeler");
    private akademisyen_basvuru_belgeleri_adapter adapter;
    private ArrayList<akademisyen_gelen_basvuru_belgelerini_gormek> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akademisyen_basvuru_belgeleri);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView= findViewById(R.id.recycler_akademisyen_basvuru_belgeleri);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list =new ArrayList<>();
        adapter = new akademisyen_basvuru_belgeleri_adapter(this, list);
        recyclerView.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    akademisyen_gelen_basvuru_belgelerini_gormek model = dataSnapshot.getValue(akademisyen_gelen_basvuru_belgelerini_gormek.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}