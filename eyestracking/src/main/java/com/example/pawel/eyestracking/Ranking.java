package com.example.pawel.eyestracking;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class Ranking extends Activity implements View.OnClickListener {
    DatabaseHelper mydb;
    private TextView n1;
    private TextView n2;
    private TextView n3;
    private TextView back;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        n1 = (TextView)findViewById(R.id.textView);
        n2 = (TextView)findViewById(R.id.textView3);
        n3 = (TextView)findViewById(R.id.textView4);
        back = (TextView)findViewById(R.id.textView5);

        mydb=new DatabaseHelper(this);
        viewAll();
    }
    @Override
    public void onClick(View view) {
        if(view == back) {
            Intent intent = new Intent(this, com.example.pawel.eyestracking.Menu.class);
            startActivity(intent);

        }
    }

    public void viewAll(){
        list= new ArrayList<String>();
        Cursor res= mydb.getAllData();
        if(res.getCount()==0){//show message
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){


            list.add(res.getString(0));

        }

        //show all
        Collections.sort(list);
        Collections.reverse(list);
        String g = list.get(0);
        n1.setText(g);
        n2.setText(list.get(1));
        n3.setText(list.get(2));
    }
}
