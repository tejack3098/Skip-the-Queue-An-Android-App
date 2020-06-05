package com.example.pce_canteen;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class OrderPlaced extends AppCompatActivity {
    int orderid,total;
    TextView od;
    TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        tt= (TextView)findViewById(R.id.total);
        od=(TextView)findViewById(R.id.orderid);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;


        orderid= bundle.getInt("orderid");
        total = bundle.getInt("total");



        String s = new Integer(orderid).toString();
        String t = new Integer(total).toString();

        Log.d(TAG, s +" "+t);

        od.setText("Order ID : "+s);
        tt.setText("Total Amount Paid : Rs "+ t);
        //t.setText("Order Total Rs: " + tt);
    }
}
