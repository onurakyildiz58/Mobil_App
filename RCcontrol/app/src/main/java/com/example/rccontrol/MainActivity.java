package com.example.rccontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.FocusFinder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
{
    TextView slider_textspeed, slider_textdir;
    Slider slider_speed, slider_direction;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        slider_speed = findViewById(R.id.slider_speed);
        slider_textspeed  = findViewById(R.id.slider_textspeed);
        slider_direction = findViewById(R.id.slider_direction);
        slider_textdir = findViewById(R.id.slider_textdir);

        slider_speed.addOnChangeListener(new Slider.OnChangeListener()
        {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser)
            {
                slider_textspeed.setText(Float.toString(value));
                root.child("speed").setValue(Float.toString(value));

            }
        });

        slider_direction.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                slider_textdir.setText(Float.toString(value));
                root.child("direction").setValue(Float.toString(value));
            }
        });
    }
}