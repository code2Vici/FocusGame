package com.example.naborp.focusgame;
/*Main Game class where the cards are diplayed */

import android.content.Intent;
import android.content.res.Configuration;
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
    private GridView cardTable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        Bundle b = getIntent().getExtras();
        this.userChoice = b.getInt("userChoice");
        System.out.println("userChoice" + userChoice);

        cardTable = (GridView) findViewById(R.id.cardTable);

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

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        int totalsavedCards = userChoice * 2;

        int[] temp = new int[totalsavedCards ];

        for(int i = 0; i < totalsavedCards ; i++)
        {
            temp[i] = (int)cardTable.getAdapter().getItem(i);
        }

        savedInstanceState.putIntArray("save", temp);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        int[] temp = savedInstanceState.getIntArray("save");

        Integer[] storing = new Integer[temp.length];
        int i = 0;

        for(int address: temp)
        {
          storing[i++] = Integer.valueOf(address);
        }

        cardTable.setAdapter(new ImagePlacing(this, storing));
    }
    public void newGameClicked(View v) {

        Intent intent = new Intent(getApplicationContext(),MainMenu.class);
        startActivity(intent);
    }
}