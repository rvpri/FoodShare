package com.example.foodshare;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText email,username,password,confirmpassword,address,phone;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DBHelper db;

        button1 = findViewById(R.id.button1);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        username= findViewById(R.id.username);
        confirmpassword= findViewById(R.id.confirmpassword);
        address= findViewById(R.id.Pickup_Address);
        phone= findViewById(R.id.phone);
        db=new DBHelper(this);

        button1.setOnClickListener(v -> {
           String user=username.getText().toString();
           String pass=password.getText().toString();
           String mail=email.getText().toString();
           String confirmpass=confirmpassword.getText().toString();
           String address1=address.getText().toString();
           String phone1=phone.getText().toString();

           if(user.equals("") || mail.equals("") ||phone1.equals("") ||pass.equals("") || confirmpass.equals("") || address1.equals("") )
               Toast.makeText(Register.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
           else
           {
               if(pass.equals(confirmpass)){
                   Boolean checkuser= db.checkemail(mail);
                   if(checkuser==false){
                       Boolean insert=db.insertuserdata1(mail,user,phone1,pass,address1) ;
                   if(insert){
                       Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                       Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(intent1);
                       finish();
                   }
                   }
                   else{
                       Toast.makeText(Register.this, "Account Exists", Toast.LENGTH_SHORT).show();
                   }

               }
               else{
                   Toast.makeText(Register.this, "Passwords not Matching", Toast.LENGTH_SHORT).show();
               }
           }

        });

    }
}