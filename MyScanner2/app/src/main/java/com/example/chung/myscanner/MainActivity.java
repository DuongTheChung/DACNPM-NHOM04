package com.example.chung.myscanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.scanlibrary.ScanConstants;
import  com.scanlibrary.ScanActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private Button btnScanDocument;
    private Button btnScanQRCode;
    private Button btnAbout;
    private Button btnShowDocument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btnScanDocument = (Button) findViewById(R.id.btn_scan_document);
        btnScanQRCode=(Button)findViewById(R.id.btn_scan_qrcode);
        btnShowDocument=(Button)findViewById(R.id.btn_show_document);
        btnAbout=(Button)findViewById(R.id.btn_about);

        btnScanDocument.setOnClickListener(new ScanDocumentClickListener());
        btnScanQRCode.setOnClickListener(new ScanQRCodeClickListener());
        btnShowDocument.setOnClickListener(new ShowDocumentClickListener());
        btnAbout.setOnClickListener(new AboutClickListener());


    }

    private class ScanDocumentClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            doScanDocument();
        }
    }

    private class ScanQRCodeClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            doScanQRCode();
        }
    }

    private class ShowDocumentClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            doShowDocument();
        }
    }

    private class AboutClickListener implements  View.OnClickListener{
        @Override
        public void onClick(View v){
            doAbout();
        }
    }

    private void doScanDocument(){
        Intent intent=new Intent(this,ScanDocumentActivity.class);
        startActivity(intent);
    }

    private void doScanQRCode(){
        Intent intent=new Intent(this,QRCodeMainActivity.class);
        startActivity(intent);
    }

    private void doShowDocument(){
        Intent intent=new Intent(this,ListDocumentActivity.class);
        startActivity(intent);
    }

    private void doAbout(){
        Intent intent=new Intent(this,AboutActivity.class);
        startActivity(intent);
    }

}
