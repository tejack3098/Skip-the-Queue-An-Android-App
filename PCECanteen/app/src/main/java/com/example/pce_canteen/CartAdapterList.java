package com.example.pce_canteen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import static android.content.ContentValues.TAG;

public class CartAdapterList extends BaseAdapter {

    Context context;
    ArrayList<ListPojo> listPojos;
    int count=1;


    public CartAdapterList(Context context, ArrayList<ListPojo> listPojos) {
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

        view = LayoutInflater.from(context).inflate(R.layout.cart_custom_list,parent,false);

        final TextView title = (TextView) view.findViewById(R.id.title);
        final TextView price = (TextView) view.findViewById(R.id.price);
        final TextView c = (TextView) view.findViewById(R.id.count);
        ImageView image = view.findViewById(R.id.image);
        ImageButton plus = (ImageButton) view.findViewById(R.id.plus);
        ImageButton minus = (ImageButton) view.findViewById(R.id.minus);


        title.setText(listPojos.get(position).getTitle());
        price.setText(listPojos.get(position).getPrice());
        image.setImageResource(listPojos.get(position).getImages());

        String pr = (String) price.getText();
        String[] data = pr.split(" ") ;
        int prc = Integer.parseInt(data[1]);
        String t =(String) title.getText();
        if(context instanceof cartMethodCaller){
            ((cartMethodCaller)context).CartonActionPerformed(t,prc,count);
        }

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(String.valueOf(c.getText()))+1;
                String s = new Integer(count).toString();
                c.setText(s);

                String pr = (String) price.getText();
                String[] data = pr.split(" ") ;
                int prc = Integer.parseInt(data[1]);
                String t =(String) title.getText();

                Toast.makeText(context, t +" "+ prc+ " "+ count, Toast.LENGTH_SHORT).show();
                if(context instanceof cartMethodCaller){
                    ((cartMethodCaller)context).CartonActionPerformed(t,prc,count);
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>0) {
                    count = Integer.parseInt(String.valueOf(c.getText())) - 1;
                    String s = new Integer(count).toString();
                    c.setText(s);

                    String pr = (String) price.getText();
                    String[] data = pr.split(" ");
                    int prc = Integer.parseInt(data[1]);
                    String t = (String) title.getText();

             //       Toast.makeText(context, t + " " + prc + " " + count, Toast.LENGTH_SHORT).show();
                    if (context instanceof cartMethodCaller) {
                        ((cartMethodCaller) context).CartonActionPerformed(t, prc, count);
                    }
                }
            }
        });




        return view;
    }

    public interface cartMethodCaller{

        void CartonActionPerformed(String item,int price,int count);
    }
}
