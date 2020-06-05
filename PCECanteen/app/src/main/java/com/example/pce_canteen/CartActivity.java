package com.example.pce_canteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import static android.content.ContentValues.TAG;

public class CartActivity extends AppCompatActivity implements CartAdapterList.cartMethodCaller  {

    ListView cartList;
    ArrayList<ListPojo> listarray;
    CartAdapterList cartadapter;
    Button placeOrder;
    TextView t;

    int OrderId=100;

    Dictionary orderfinal = new Hashtable();

    OrderTotal o = new OrderTotal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartList = findViewById(R.id.list_view);

        placeOrder = (Button)findViewById(R.id.btn_placeorder);
        t =(TextView)findViewById(R.id.ordertotal);

        Intent intent = getIntent();

        listarray =  intent.getParcelableArrayListExtra("key");

        Log.d(TAG, String.valueOf(listarray));

        cartadapter = new CartAdapterList(this,listarray);
        cartList.setAdapter(cartadapter);



        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Order Placed Successfully!!!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(CartActivity.this,OrderPlaced.class);
                i.putExtra("total",o.getTotal());
                i.putExtra("orderid",OrderId);
                startActivity(i);
                finish();
            }
        });

    }


    @Override
    public void CartonActionPerformed(String item, int price, int count) {

        int ti = price * count;
        int total = 0;
        orderfinal.put(item,ti);

        for (Enumeration i = orderfinal.elements(); i.hasMoreElements();)
        {
            total = total + Integer.valueOf((Integer) i.nextElement());
        }
        String tt = new Integer(total).toString();
        t.setText("Order Total Rs: " + tt);
        o.setTotal(total);


    }

}
