package com.example.pce_canteen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.pce_canteen.SQLiteHelper.TABLE_NAME;

public class Login extends AppCompatActivity {
    TextView mytv1,regtxt;
    EditText mytv2,mytv3;
    Button mybtn;
    Typeface myfont;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    SharedPreferences sp;
    public static final String Username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mytv1 = (TextView) findViewById(R.id.textView);
        mytv2 = (EditText) findViewById(R.id.EditText);
        mytv3 = (EditText) findViewById(R.id.txtPassword);
        regtxt = (TextView) findViewById(R.id.textView1);
        mybtn = (Button) findViewById(R.id.simpleButton);
        myfont = Typeface.createFromAsset(getAssets(), "font/Roboto-Medium.ttf");
        mytv1.setTypeface(myfont);
        mytv2.setTypeface(myfont);
        mytv3.setTypeface(myfont);
        regtxt.setTypeface(myfont);
        mybtn.setTypeface(myfont);
        regtxt.setText(Html.fromHtml("Not Registered? <u>Register</u>"));
        sqLiteHelper = new SQLiteHelper(this);
        regtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, Register.class);
                startActivity(myIntent);
            }
        });

        sp = getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }

        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calling EditText is empty or no method.
                CheckEditTextStatus();

                // Calling login method.
                LoginFunction();



            }
        });
    }

    private void goToMainActivity() {
        Intent i = new Intent(Login.this,MainActivity.class);
        startActivity(i);
    }

    public void LoginFunction(){

        if(EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check final result ..
            CheckFinalResult();

        }
        else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(Login.this,"All fields are required",Toast.LENGTH_LONG).show();

        }

    }

    // Checking EditText is empty or not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = mytv2.getText().toString();
        PasswordHolder = mytv3.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult() {

        if (TempPassword.equalsIgnoreCase(PasswordHolder)) {
            SharedPreferences.Editor editor = sp.edit();

            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();

            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(Login.this, MainActivity.class);

            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE username = '" + EmailHolder + "'";
            sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();
            Cursor mcursor = sqLiteDatabaseObj.rawQuery(selectQuery, null);
            if (mcursor != null) {
                mcursor.moveToFirst();
            }
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(mcursor));
            editor.putString("uname", EmailHolder);
            if (mcursor != null) {

                editor.putString("gno", mcursor.getString(4));

            }
            editor.apply();
            sp.edit().putBoolean("logged", true).apply();
            //intent.putExtras(extras);

            startActivity(intent);


        } else {

            Toast.makeText(Login.this, "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND";

    }

}

