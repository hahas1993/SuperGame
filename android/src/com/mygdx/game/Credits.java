package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Credits extends Activity implements View.OnClickListener {
    private TextView n1;
    private TextView n2;
    private TextView n3;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        n1 = (TextView)findViewById(R.id.textView);
        n2 = (TextView)findViewById(R.id.textView3);
        n3 = (TextView)findViewById(R.id.textView4);
        back = (TextView)findViewById(R.id.textView5);
        back.setOnClickListener(this);
        viewAll();

    }
    @Override
    public void onClick(View view) {
        if(view == back) {
            Intent intent = new Intent(this, com.mygdx.game.Menu.class);
            startActivity(intent);
            finish();
        }
    }

    public void viewAll() {
        n1.setText("Paweł Matwiejuk");
        n2.setText("Piotr Szymkowski");
        n3.setText("Paweł Olszewski");
    }
}
