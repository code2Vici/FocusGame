package com.example.naborp.focusgame;
/*Main Game class where the cards are diplayed */

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainGame extends AppCompatActivity
{
    private int userChoice;
    private GridView cardTable;
    private MediaPlayer appSong;
    private ToggleButton play_stop_Button;

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
                Toast.makeText(MainGame.this, "" + position,Toast.LENGTH_SHORT).show();
            }
        });

        play_stop_Button = (ToggleButton) findViewById(R.id.play_stop_Button);
        play_stop_Button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    appSong = MediaPlayer.create(getApplicationContext(),R.raw.black_beatle);
                    appSong.start();
                    appSong.setVolume(100,100);
                    appSong.setLooping(true);
                }
                else {
                    if(appSong != null) {
                        appSong.release();
                        appSong = null;
                    }
                }
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

        if (appSong != null) {
            try {
                appSong.stop();
                appSong.release();
            } finally {
                appSong = null;
            }
        }
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

        if (appSong != null) {
            try {
                appSong.stop();
                appSong.release();
            } finally {
                appSong = null;
            }
        }
    }
}