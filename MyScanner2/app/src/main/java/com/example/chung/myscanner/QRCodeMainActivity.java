package com.example.chung.myscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class QRCodeMainActivity extends AppCompatActivity {
    private Button btnScan;
    private Button btnGen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_main_activity_layout);
        btnScan=(Button)findViewById(R.id.btnScan);
        btnGen=(Button)findViewById(R.id.btnGen);
        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gIntent = new Intent(QRCodeMainActivity.this,QRCodeGeneratorActivity.class);
                startActivity(gIntent);
            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rIntent = new Intent(QRCodeMainActivity.this, QRCodeScanActivity.class);
                startActivity(rIntent);
            }
        });

    }
}
