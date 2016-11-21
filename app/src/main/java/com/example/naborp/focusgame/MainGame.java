package com.example.naborp.focusgame;
/*Main Game class where the cards are diplayed */

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainGame extends AppCompatActivity
{
    private int userChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        Bundle b = getIntent().getExtras();
        this.userChoice = b.getInt("userChoice");
        System.out.println("userChoice" + userChoice);

        GridView cardTable = (GridView) findViewById(R.id.cardTable);

        //Problem call
        cardTable.setAdapter(new ImagePlacing(this, userChoice));

        cardTable.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainGame.this, "" + position,Toast.LENGTH_SHORT);
            }
        });
    }
    public void newGameClicked(View v) {
        Intent intent = new Intent(getApplicationContext(),MainMenu.class);
        startActivity(intent);
    }
}