package com.example.listview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adt extends BaseAdapter implements Filterable {
    private ArrayList<Contact> contacts;
    private Activity activity;
    private LayoutInflater inflater;
    //khai báo đối tương LayoutInflater để phân tích giao diện cho một phần tử
    private ArrayList<Contact> databackup;

    public adt(ArrayList<Contact> contacts, Activity activity ) {
        this.contacts = contacts;
        this.activity = activity;
        this.inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contacts.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        if(v==null)
            v=inflater.inflate(R.layout.activity_add_user,null);
        TextView tvID=v.findViewById(R.id.textID);
        tvID.setText(String.valueOf(contacts.get(i).getId()));
        TextView tvName=v.findViewById(R.id.TName);
        tvName.setText(contacts.get(i).getName());
        TextView tvTuoi=v.findViewById(R.id.textAge);
        tvTuoi.setText(String.valueOf(contacts.get(i).getTuoi()));
        return  v;
    }
    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if(databackup==null)
                    databackup = new ArrayList<>(contacts);
                //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                if(charSequence==null || charSequence.length()==0)
                {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                //Còn nếu không rỗng thì thực hiện filter
                else{
                    ArrayList<Contact> newdata = new ArrayList<>();
                    for(Contact u:databackup)
                        if(u.getName().toLowerCase().contains(
                                charSequence.toString().toLowerCase()))
                            newdata.add(u);
                    fr.count=newdata.size();
                    fr.values=newdata;
                }
                return fr;
            }
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                contacts = new ArrayList<Contact>();
                ArrayList<Contact> tmp =(ArrayList<Contact>)filterResults.values;
                for(Contact u: tmp)
                    contacts.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
