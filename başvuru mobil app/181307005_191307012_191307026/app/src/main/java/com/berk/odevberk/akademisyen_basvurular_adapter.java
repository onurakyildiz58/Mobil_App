package com.berk.odevberk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.ArrayList;

public class akademisyen_basvurular_adapter extends RecyclerView.Adapter<akademisyen_basvurular_adapter.MyViewHolder> {

    ArrayList<akademisyen_gelen_basvurulari_gormek> mList;
    Context context;

    public akademisyen_basvurular_adapter(Context context , ArrayList<akademisyen_gelen_basvurulari_gormek> mList){

        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.akademisyen_basvuru_tek_bilgi , parent ,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        akademisyen_gelen_basvurulari_gormek model = mList.get(position);
        holder.name.setText(model.getName());
        holder.url.setText(model.getUrl());
        holder.situation.setText(model.getSituation());
        holder.button.setOnClickListener((view) ->
        {
            Uri basvuruindirme = Uri.parse(holder.url.getText().toString());
            Intent istek = new Intent(Intent.ACTION_VIEW, basvuruindirme);
            context.startActivity(istek);
        });
        /*
        holder.btnreddet.setOnClickListener((view) ->
        {
           onayla();
        });
        holder.btnonyala.setOnClickListener((view) ->
        {
            reddet();
        });*/
    }
    /*
    private void reddet()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();
        myref.child("akademisyenbasvurular").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("situation").setValue("reddedildi");
                Dialog dialog = null;
                dialog.dismiss();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }
    private void onayla()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();
        myref.child("akademisyenbasvurular").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("situation").setValue("onaylandı");
                Dialog dialog = null;
                dialog.dismiss();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name , url,situation;
        Button button,btnonyala,btnreddet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tw_akadmeisyen_tekbilgi_ad);
            url = itemView.findViewById(R.id.tw_akadmeisyen_tekbilgi_url);
            button=  itemView.findViewById(R.id.button_akademisyen_bavuru_belgesini_indir);
            situation = itemView.findViewById(R.id.tw_akademisyen_basvuruekrani_basvuru_durumu);
            btnonyala=itemView.findViewById(R.id.button_akademisyen_basvuru_onay);
            btnreddet=itemView.findViewById(R.id.button_akademisyen_basvuru_ret);
        }
    }
}
