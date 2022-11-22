package com.example.foodshare;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText email,password;

    Button button1;
    TextView txtview;
    DBHelper db;
    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        button1 = (Button) findViewById(R.id.button1);
        txtview=(TextView)findViewById(R.id.txtview);
        db=new DBHelper(this);

        button1.setOnClickListener(v -> {
            String pass=password.getText().toString();
            String mail=email.getText().toString();


            if( mail.equals("") || pass.equals("") )
                Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            else
            {
                Boolean checkuserpass=db.checkpassword(mail,pass);
                if(checkuserpass){
                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    String str = email.getText().toString();
                    Intent intent = new Intent(MainActivity.this,Activity5.class);
                    intent.putExtra("message_key", str);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                }

            }});

        txtview.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, Register.class);
            startActivity(intent2);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
    }

}

