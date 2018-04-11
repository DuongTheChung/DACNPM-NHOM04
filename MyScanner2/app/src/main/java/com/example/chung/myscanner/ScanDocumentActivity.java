package com.example.chung.myscanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

public class ScanDocumentActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 99;
    private Button btnCamera;
    private Button btnGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_scan_activity_layout);
        init();
    }

    private void init(){
        btnCamera=(Button)findViewById(R.id.btn_camera);
        btnGallery=(Button)findViewById(R.id.btn_gallery);

        btnCamera.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA));
        btnGallery.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA));
    }

    private class ScanButtonClickListener implements View.OnClickListener {
        private int preference;
        public ScanButtonClickListener(int preference) {
            this.preference = preference;
        }
        public ScanButtonClickListener() {
        }
        @Override
        public void onClick(View v) {
            startScan(preference);
        }
    }

    protected void startScan(int preference) {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String fstr = data.getStringExtra(ScanConstants.SCANNED_RESULT);
            doListDocument();
        }
    }

    private void doListDocument(){
        Intent intent=new Intent(this,ListDocumentActivity.class);
        startActivity(intent);
    }

    private Bitmap convertByteArrayToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
