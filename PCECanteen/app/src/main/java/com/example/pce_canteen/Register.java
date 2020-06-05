package com.example.pce_canteen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    TextView mytv1;
    EditText name,pass,uname,gno;
    Button mybtn;
    Typeface myfont;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    boolean EditTextEmptyHolder;
    String SQLiteDataBaseQueryHolder ;
    String NameHolder, UnameHolder, PasswordHolder, GnoHolder;
    Cursor cursor;
    String F_Result = "Not_Found";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mytv1 = (TextView) findViewById(R.id.textView);
        name = (EditText) findViewById(R.id.EditText);
        pass = (EditText) findViewById(R.id.txtPassword);
        uname = (EditText) findViewById(R.id.EditText1);
        gno = (EditText) findViewById(R.id.EditText2);
        mybtn = (Button) findViewById(R.id.simpleButton);
        myfont = Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf");
        mytv1.setTypeface(myfont);
        name.setTypeface(myfont);
        uname.setTypeface(myfont);
        gno.setTypeface(myfont);
        pass.setTypeface(myfont);
        mybtn.setTypeface(myfont);

        sqLiteHelper = new SQLiteHelper(this);

        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();

                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();

                // Checking EditText is empty or Not.
                CheckEditTextStatus();

                // Method to check Email is already exists or not.
                CheckingEmailAlreadyExistsOrNot();

                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();


            }
        });
    }
    // SQLite database build method.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_Email + " VARCHAR, " + SQLiteHelper.Table_Column_3_Password + " VARCHAR, "+ SQLiteHelper.Table_Column_4_Gno +" VARCHAR);");

    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){

        // If editText is not empty then this block will executed.

        if(EditTextEmptyHolder == true)
        {

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,username,password,guardno) VALUES('"+NameHolder+"', '"+UnameHolder+"', '"+PasswordHolder+"', '"+GnoHolder+"');";

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(Register.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(Register.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        name.getText().clear();

        uname.getText().clear();

        pass.getText().clear();

        gno.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = name.getText().toString() ;
        UnameHolder = uname.getText().toString();
        PasswordHolder = pass.getText().toString();
        GnoHolder = gno.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(GnoHolder)|| TextUtils.isEmpty(UnameHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{UnameHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(Register.this,"Username Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

}
