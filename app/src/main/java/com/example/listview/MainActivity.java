package com.example.listview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    //Khai báo đối tượng lưu trữ các contact
    private ArrayList<Contact> contactList;
    private adt listAdapter;
    private TextView etSearch;
    private ListView listContact;
    private FloatingActionButton btnAdd;
    int selectedid=-1;
    MyDB mysqlitedb;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteItemMenu:
                // Create an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Set the dialog title
                builder.setTitle("Thông báo");
                // Set the message
                builder.setMessage("Bạn có muốn xóa không ?");
                // Set the positive button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle positive button click

                        contactList.remove(selectedid);
                       // listAdapter= new adt(contactList,this);
                        listContact.setAdapter(listAdapter);
                    }
                });

                // Set the negative button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle negative button click

                    }
                });

                // Create the AlertDialog object and show it
                AlertDialog dialog = builder.create();
                dialog.show();


                break;
            case R.id.editItemMenu:
                Intent intent = new Intent(MainActivity.this,
                        MainActivitySub.class);
                Contact c=contactList.get(selectedid);
                Bundle bundle = new Bundle();
                bundle.putInt("id",c.getId());
                bundle.putString("name",c.getName());
                bundle.putInt("tuoi",c.getTuoi());
                bundle.putString("image",c.getImg());

                intent.putExtras(bundle);

                startActivityForResult(intent, 200);
                break;
            case R.id.mnuCall:
                Intent intent1=new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:0122343455"));
                startActivity(intent1);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.mnuSortName:
                Collections.sort(contactList,new LastNameComparator());
                listAdapter= new adt(contactList,this);
                listContact=findViewById(R.id.listContact);
                listContact.setAdapter(listAdapter);
                //sap xếp theo tên
                break;
            case R.id.mnuSortPhone:
                //sắp xếp theo tuoi
                Collections.sort(contactList,new AgeComparator());
                listAdapter= new adt(contactList,this);
                listContact=findViewById(R.id.listContact);
                listContact.setAdapter(listAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class LastNameComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact name1, Contact name2) {
            String[] parts1 = name1.getName().split(" ");
            String[] parts2 = name2.getName().split(" ");

            String lastName1 = parts1[parts1.length - 1];
            String lastName2 = parts2[parts2.length - 1];

            return lastName1.compareTo(lastName2);
        }
    }
    class AgeComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact name1, Contact name2) {
            int parts1 = name1.getTuoi();
            int parts2 = name2.getTuoi();


            return parts1-parts2;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("id");
        String name = bundle.getString("name");
        int tuoi = bundle.getInt("tuoi");
        if(requestCode==100 && resultCode==150)
        {
            //đặt vào listData
            contactList.add(new Contact(id,"" , name,tuoi ));
            listContact.setAdapter(listAdapter);
//            mysqlitedb.addContact(new Contact(id, "", name,tuoi ));
        }

        else if(requestCode==200 && resultCode==150) {
            contactList.set(selectedid, new Contact(id, "", name, tuoi));
//            //cập nhật adapter
            listAdapter.notifyDataSetChanged();
            listContact.setAdapter(listAdapter);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=new ArrayList<>();
        contactList.add(new Contact(1,"img01","nguyen A",19));
        contactList.add(new Contact(2,"img04","nguyen M",30));
        contactList.add(new Contact(3,"img03","nguyen D",10));
        contactList.add(new Contact(4,"img03","nguyen C",17));


        listAdapter= new adt(contactList,this);
        etSearch=findViewById(R.id.textView);
        listContact=findViewById(R.id.listContact);
        btnAdd=findViewById(R.id.btn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivitySub.class);
                startActivityForResult(intent,100);
            }
        });
        listContact.setAdapter(listAdapter);
       registerForContextMenu(listContact);
        listContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                selectedid = position;
                return false;
            }
        });
//
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                listAdapter.getFilter().filter(s.toString());
//                listAdapter.notifyDataSetChanged();
//                //lvContact.setAdapter(listUserAdapter);
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
    }
//    @SuppressLint("RestrictedApi")
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.option, menu);
//
//        // If you want Icon display in Overflow Menu.
//        // https://stackoverflow.com/questions/19750635/icon-in-menu-not-showing-in-android
//        if(menu instanceof MenuBuilder){
//            MenuBuilder m = (MenuBuilder) menu;
//            m.setOptionalIconsVisible(true);
//        }
//        return true;
//    }
}