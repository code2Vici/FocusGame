package com.example.naborp.focusgame;
/*Main Game class where the cards are diplayed hi*/

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        GridView cardTable = (GridView) findViewById(R.id.cardTable);
        //Problem call
        cardTable.setAdapter(new ImagePlacing(this, 10));

        cardTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainGame.this, "" + position,Toast.LENGTH_SHORT);
            }
        });
    }
}
