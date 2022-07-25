package com.example.qrscanner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.scan);

        btnScan.setOnClickListener(v->
        {
            ScanCode();
        });
    }
    private void ScanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Ses Açma Tuşu İle Flash Açabilirsiniz");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() != null){
            AlertDialog.Builder  builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Sonuç");
            builder.setMessage(result.getContents());
            String msg = result.getContents();
            builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.dismiss();
                }
            }).show();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(msg));
            startActivity(intent);
        }
    });
}