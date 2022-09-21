package com.berk.odevberk;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;

public class ogrenci_basvurular_adapter extends RecyclerView.Adapter<ogrenci_basvurular_adapter.MyViewHolder>
{
    ArrayList<ogrenci_devam_eden_basvurulari_gormek> mList1;
    Context context1;

    public ogrenci_basvurular_adapter(Context context , ArrayList<ogrenci_devam_eden_basvurulari_gormek> mList){

        this.mList1 = mList;
        this.context1 = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1).inflate(R.layout.ogrenci_devam_eden_basvurular_tek_bilgi , parent ,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ogrenci_devam_eden_basvurulari_gormek model1 = mList1.get(position);
        holder.name.setText(model1.getName());
        holder.url.setText(model1.getUrl());
        holder.situation.setText(model1.getSituation());
        holder.button.setOnClickListener((view) ->
        {
            Uri basvuruindirme = Uri.parse(holder.url.getText().toString());
            Intent istek = new Intent(Intent.ACTION_VIEW, basvuruindirme);
            context1.startActivity(istek);
        });
    }

    @Override
    public int getItemCount() {
        return mList1.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name , url,situation;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tw_ogrenci_tek_bilgi_basvuru_ismi);
            url = itemView.findViewById(R.id.tw_ogrenci_tek_bilgi_basvuru_tarihi);
            situation=itemView.findViewById(R.id.tw_ogrenci_tek_bilgi_basvuru_durumu);
            button=itemView.findViewById(R.id.button_ogrenci_bavuru_belgesini_indir);
        }
    }
}
