package com.example.chung.myscanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.scanlibrary.ScanConstants;

import java.io.File;
import java.util.ArrayList;

public class ListDocumentActivity extends AppCompatActivity{
    private Button btnShow;
    private Button btnShare;
    private Button btnDelete;
    private ListView list;
    private EditText editText;
    private ArrayList<String> alist;
    private ArrayAdapter<String> adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_document_activity_layout);
        list = (ListView) findViewById(R.id.list);
        list.setEmptyView(findViewById(R.id.empty_list));
        editText=(EditText)findViewById(R.id.txtsearch);
        btnShow=(Button)findViewById(R.id.btn_show);
        btnShare=(Button)findViewById(R.id.btn_share);
        btnDelete=(Button)findViewById(R.id.btn_delete);

        initList();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adap.getFilter().filter(s);
                adap.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnDelete.setOnClickListener(new DeleteOnClickListener());
        btnShare.setOnClickListener(new ShareOnClickListener());
        btnShow.setOnClickListener(new ShowOnClickListener());

    }

    //Event click button Delete
    private class DeleteOnClickListener implements View.OnClickListener{
        File fndel=null;
        @Override
        public void onClick(View v){
            SparseBooleanArray selecttedShow = list.getCheckedItemPositions();
            ArrayList<String> selectedItem = new ArrayList<>();
            for (int i = 0; i < selecttedShow.size(); i++) {
                if (selecttedShow.valueAt(i))
                    selectedItem.add(adap.getItem(selecttedShow.keyAt(i)));

            }
            if(selectedItem.size() > 0) {
                for (int i = 0; i < selectedItem.size(); i++) {
                    File fl = new File(ScanConstants.PDF_PATH + "/" + selectedItem.get(i));
                    fndel = fl;
                    if (fndel != null) {
                        fl.delete();
                        alist.remove(selectedItem.get(i));
                        initList();
                        if (alist.size() <= 0) {
                            list.setAdapter(null);
                        }
                    }
                    fndel = null;
                }
            }
            else{
                Toast.makeText(ListDocumentActivity.this,
                        "Error,No found document need delete",Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    //Event click button Share
    private class ShareOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            SparseBooleanArray selecttedShow = list.getCheckedItemPositions();
            ArrayList<String> selectedItem = new ArrayList<>();
            for (int i = 0; i < selecttedShow.size(); i++) {
                if (selecttedShow.valueAt(i))
                    selectedItem.add(adap.getItem(selecttedShow.keyAt(i)));
            }

            if (selectedItem.size()>0)
            {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.setType("application/pdf");
                ArrayList<Uri> files = new ArrayList<Uri>();
                for (int i = (selectedItem.size() - 1); i >= 0; i--) {
                    File fl = new File(ScanConstants.PDF_PATH + "/" + selectedItem.get(i));
                    Uri uri = Uri.fromFile(fl);
                    files.add(uri);

                }
                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            }
            else{
                Toast.makeText(ListDocumentActivity.this,
                        "Error,No found document need share",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    //Event click button Show
    private class ShowOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            SparseBooleanArray selecttedShow = list.getCheckedItemPositions();
            ArrayList<String> selectedItem = new ArrayList<>();
            for (int i = 0; i < selecttedShow.size(); i++) {
                if (selecttedShow.valueAt(i))
                    selectedItem.add(adap.getItem(selecttedShow.keyAt(i)));
            }

            if (selectedItem.size() == 1) {

                for(int i=0;i<selectedItem.size();i++){
                    File fo = new File(ScanConstants.PDF_PATH + "/" + (String)selectedItem.get(i));
                    if (fo.exists()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(fo), "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                }
            } else {
                if(selectedItem.size()<1) {
                    Toast.makeText(ListDocumentActivity.this,
                            "Error,unknown document need read.", Toast.LENGTH_SHORT).show();
                }

                else if(selectedItem.size()>1){
                    Toast.makeText(ListDocumentActivity.this,
                            "Error,Found more one document,App can only read one document.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //init ListView
    private void initList(){
        File file = new File(ScanConstants.PDF_PATH);
        alist = new ArrayList<>();
        if (file.exists()) {
            File[] cfile = file.listFiles();
            for (File f : cfile) {
                if (f.isFile() && f.getName().endsWith(".pdf")) {
                    alist.add(f.getName());
                }
            }
        }
        if (alist.size() > 0) {
            adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, alist);
            list.setTextFilterEnabled(true);
            list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            list.setAdapter(adap);
        }
        registerForContextMenu(list);
    }

}
