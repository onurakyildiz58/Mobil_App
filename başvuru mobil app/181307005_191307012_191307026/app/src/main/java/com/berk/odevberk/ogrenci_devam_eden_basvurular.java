package com.berk.odevberk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ogrenci_devam_eden_basvurular extends AppCompatActivity {

    FirebaseAuth auth=FirebaseAuth.getInstance();
    String userID=auth.getCurrentUser().getUid();
    private RecyclerView nFirestoreList;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("ogrencibasvurular").child(userID);
    private ogrenci_basvurular_adapter adapter;
    private ArrayList<ogrenci_devam_eden_basvurulari_gormek> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogrenci_devam_eden_basvurular);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        nFirestoreList = findViewById(R.id.recyclerview_ogrenci_devam_eden_basvurularim);
        nFirestoreList.setHasFixedSize(true);
        nFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        list1=new ArrayList<>();
        adapter =new ogrenci_basvurular_adapter(this,list1);
        nFirestoreList.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ogrenci_devam_eden_basvurulari_gormek model1 = dataSnapshot.getValue(ogrenci_devam_eden_basvurulari_gormek.class);
                    list1.add(model1);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

}