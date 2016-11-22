package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Menu extends Activity implements View.OnClickListener{
    private TextView start;
    private TextView ranking;
    private TextView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        start=(TextView) findViewById(R.id.textView);
        ranking = (TextView) findViewById(R.id.textView3);
        exit = (TextView) findViewById(R.id.textView4);
        exit.setOnClickListener(this);
        ranking.setOnClickListener(this);
        start.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view == start){
            Intent intent = new Intent(Menu.this, com.mygdx.game.AndroidLauncher.class);
            startActivity(intent);

        }
        else if (view==ranking){

            Intent intent = new Intent(Menu.this, com.mygdx.game.Ranking.class);
            startActivity(intent);

        }

        else{

            finish();
        }
    }

}
