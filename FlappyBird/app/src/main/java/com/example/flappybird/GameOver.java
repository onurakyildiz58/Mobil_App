package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    ImageView tekrarDene;
    TextView Puan, maxPuan;
    Button btn_tekrarDene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        tekrarDene = findViewById(R.id.tekrarDene);
        Puan = findViewById(R.id.puan);
        maxPuan = findViewById(R.id.maxPuan);
        btn_tekrarDene = findViewById(R.id.btn_tekrarDene);

        int puan = getIntent().getExtras().getInt("puan");
        SharedPreferences sharedPreferences = getSharedPreferences("Preference", 0);
        int puanSP = sharedPreferences.getInt("puanSP", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(puan > puanSP){
            puanSP = puan;
            editor.putInt("puanMax", puanSP);
            editor.commit();
        }
        Puan.setText(puan);
        maxPuan.setText(puanSP);

        tekrarDene.setOnClickListener(view -> {
            Intent intent = new Intent(GameOver.this, MainActivity.class);
            startActivity(intent);
        });

        btn_tekrarDene.setOnClickListener(view -> finishAffinity());

    }
}