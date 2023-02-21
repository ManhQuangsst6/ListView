package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivitySub extends AppCompatActivity {
    private TextView etId;
    private TextView etFullName;
    private TextView etTuoi;
    private ImageView ivImage;
    private Button btnOk;
    private Button btnCancel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub);
        etId=findViewById(R.id.userCode);
        etFullName=findViewById(R.id.textName);
        etTuoi=findViewById(R.id.etTuoi);
        ivImage=findViewById(R.id.imageView2);
        btnOk=findViewById(R.id.btnOk);
        btnCancel=findViewById(R.id.btnCancel);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            int id=bundle.getInt("id");
            String img=bundle.getString("image");
            String name=bundle.getString("name");
            int tuoi=bundle.getInt("tuoi");
            etId.setText(String.valueOf(id));
            etTuoi.setText(String.valueOf(tuoi));
            etFullName.setText(name);
            btnOk.setText("Edit");
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id=Integer.parseInt(etId.getText().toString());
                    int tuoi=Integer.parseInt(etTuoi.getText().toString());
                    String name=etFullName.getText().toString();
                    Intent intent= new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("name",name);
                    bundle.putInt("tuoi", tuoi);
                    intent.putExtras(bundle);
                    setResult(150, intent);
                    finish();
                }
            });
        }
//        else{
//            btnOk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int id=Integer.parseInt(etId.getText().toString());
//                    int tuoi=Integer.parseInt(etTuoi.getText().toString());
//                    String name=etFullName.getText().toString();
//                    Intent intent= new Intent();
//                    Bundle bundle=new Bundle();
//                    bundle.putInt("id", id);
//                    bundle.putString("name",name);
//                    bundle.putInt("tuoi", tuoi);
//                    intent.putExtras(bundle);
//                    setResult(200, intent);
//                    finish();
//                }
//            });
//        }


    }
}