package com.example.pce_canteen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AdapterList extends BaseAdapter  {

    Context context;
    ArrayList<ListPojo> listPojos;


    public AdapterList(Context context, ArrayList<ListPojo> listPojos) {
        this.context = context;
        this.listPojos = listPojos;
    }

    @Override
    public int getCount() {
        return listPojos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        view = LayoutInflater.from(context).inflate(R.layout.custom_list,parent,false);

        final TextView title = (TextView) view.findViewById(R.id.title);
        final TextView price = (TextView) view.findViewById(R.id.price);
        ImageView image = view.findViewById(R.id.image);
        Button button = view.findViewById(R.id.addbtn);

        title.setText(listPojos.get(position).getTitle());
        price.setText(listPojos.get(position).getPrice());
        image.setImageResource(listPojos.get(position).getImages());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;

                String s= (String) price.getText();
                String t =(String) title.getText();
                Button btnadd = (Button)v;
                if(btnadd.getText().toString().equals("ADD") ){
                    button.setText("ADDED");
                    notifyDataSetChanged();
                }
                button.setBackgroundColor(Color.parseColor("#00cc00"));

                if(context instanceof MethodCaller){
                    ((MethodCaller)context).onActionPerformed(t,s,listPojos.get(position).getImages());
                }
                toast =Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();



                Log.d(TAG, t);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public interface MethodCaller{

        void onActionPerformed(String item,String price,int images);
    }
}
