package com.berk.odevberk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class akademisyen_basvuru_belgeleri_adapter extends RecyclerView.Adapter<akademisyen_basvuru_belgeleri_adapter.MyViewHolder2> {

    ArrayList<akademisyen_gelen_basvuru_belgelerini_gormek> mList;
    Context context;

    public akademisyen_basvuru_belgeleri_adapter(Context context , ArrayList<akademisyen_gelen_basvuru_belgelerini_gormek> mList){

        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.akademisyen_basvuru_belgeleri_tek_bilgi , parent ,false);
        return new MyViewHolder2(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        akademisyen_gelen_basvuru_belgelerini_gormek model = mList.get(position);
        holder.name.setText(model.getName());
        holder.url.setText(model.getUrl());
        holder.button.setOnClickListener((view) -> {
            Uri basvuruindirme = Uri.parse(holder.url.getText().toString());
            Intent istek = new Intent(Intent.ACTION_VIEW, basvuruindirme);
            context.startActivity(istek);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static  class MyViewHolder2 extends RecyclerView.ViewHolder{


        TextView name , url;
        Button button;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tw_akadmeisyen_basvuru_belgesi_tekbilgi_ad);
            url = itemView.findViewById(R.id.akademisyen_url);
            button=itemView.findViewById(R.id.button_akademisyen_belge_indir);
        }
    }
}
