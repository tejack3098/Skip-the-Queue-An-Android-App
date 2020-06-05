package com.example.pce_canteen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;

public class MainActivity extends AppCompatActivity implements AdapterList.MethodCaller {

    ListView listView;
    ArrayList<ListPojo> list;
    ArrayList<ListPojo> cartlist;
    AdapterList adapterList;
    String mSelectedItem;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.list_view);
        listShow();

        btn = (Button)findViewById(R.id.addbtn);
        cartlist = new ArrayList<ListPojo>();

        adapterList = new AdapterList(this,list);
        listView.setAdapter(adapterList);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CartActivity.class);
                i.putParcelableArrayListExtra("key",cartlist);
                startActivity(i);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listShow(){
        list = new ArrayList<ListPojo>();

       list.add(new ListPojo("Biryani","Rs 110",R.drawable.biryani));
       list.add(new ListPojo("Veg Thali","Rs 70",R.drawable.thalli));
        list.add(new ListPojo("Misal Pav","Rs 40",R.drawable.misal));
        list.add(new ListPojo("Dosa","Rs 50",R.drawable.dosa));
        list.add(new ListPojo("Sandwich","Rs 55",R.drawable.sandwich));
        list.add(new ListPojo("Franky","Rs 50",R.drawable.frankie));
        list.add(new ListPojo("Vada Pav","Rs 15",R.drawable.vada));




    }

    @Override
    public void onActionPerformed(String item,String price, int images) {
        Toast.makeText(this, "Received: "+item, Toast.LENGTH_SHORT).show();


        cartlist.add(new ListPojo(item,price,images));
    }
}
