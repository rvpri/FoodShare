package com.example.foodshare;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {

    DBHelper db;
    EditText username,password,address,phone;
    Button update;
    private String mailid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        password =view.findViewById(R.id.password);
        username =view.findViewById(R.id.username);
        address =view.findViewById(R.id.Pickup_Address);
        update =view.findViewById(R.id.updatedetails);
        phone =view.findViewById(R.id.phoneno);
        db = new DBHelper(getActivity());

        Bundle data=getArguments();
        if(data!=null)
        {
            mailid= data.getString("mydata");
        }

        Cursor res = db.retreivedata(mailid);
        if(res.getCount()!=0){
            if (res.moveToFirst()) {
                String name=res.getString(1);
                username.setText(name) ;
                String phone1=res.getString(2);
                phone.setText(phone1) ;
                String pass=res.getString(3);
                password.setText(pass) ;
                String add=res.getString(4);
                address.setText(add);
            }}

        update.setOnClickListener(v -> {
            String  name= username.getText().toString();
            String pass = password.getText().toString();
            String add = address.getText().toString();
            String mobile = phone.getText().toString();
            Boolean updatedata = db.updateuserdata(mailid,name,mobile,pass,add);
            if(updatedata)
                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Could Not Update", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}