package com.example.foodshare;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    Button button1;
    EditText food, time, serves;
    TextView display;
    Button  viewdata;
    DBHelper DB;
    private String mailid;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        button1 =view.findViewById(R.id.button);
        food =view.findViewById(R.id.food);
        time =view.findViewById(R.id.time);
        serves =view.findViewById(R.id.serves);
        viewdata =view.findViewById(R.id.button1);
        display=view.findViewById(R.id.display);


        DB = new DBHelper(getActivity());

        Bundle data=getArguments();
        if(data!=null)
        {
            mailid= data.getString("mydata");
        }
        Cursor res1 = DB.totalserves();
        if(res1.getCount()!=0){
            if (res1.moveToFirst()) {
                String total=res1.getString(0);
                display.setText(total) ;

            }}

        button1.setOnClickListener(v -> {
            Intent intent1 = new Intent(getActivity(),Donate.class);
            intent1.putExtra("message", mailid);
            startActivity(intent1);

        });

        viewdata.setOnClickListener(view1 -> {
            Cursor res = DB.getdata(mailid);
            if(res.getCount()==0){
                Toast.makeText(getActivity(), "No Donations Yet", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                buffer.append("Food Items : ").append(res.getString(1)).append("\n");
                buffer.append("Date : ").append(res.getString(2)).append("\n");
                buffer.append("Time : ").append(res.getString(3)).append("\n");
                buffer.append("Serves Around: ").append(res.getString(4)).append("\n\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle("My Donation Details");
            builder.setMessage(buffer.toString());
            builder.show();
        });

        return view;
    }



}

