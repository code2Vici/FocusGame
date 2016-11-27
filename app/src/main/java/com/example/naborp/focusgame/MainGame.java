package com.example.naborp.focusgame;
/*Main Game class where the cards are diplayed */

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainGame extends AppCompatActivity
{
    private int userChoice;
    private int timesClicked = 0;
    private Object savedReference;
    private int savedReferencePosition;
    private GridView cardTable;
    private MediaPlayer appSong;
    private ToggleButton play_stop_Button;
    //This field stores the instance of ImagePlacing object
    private ImagePlacing imagePlacing;

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

        cardTable.setAdapter(imagePlacing = new ImagePlacing(this, userChoice));
        cardTable.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainGame.this, "" + position,Toast.LENGTH_SHORT).show();

                ImageView img = (ImageView) view;

                //First Card being faced up when clicked
                if(timesClicked < 1)
                {
                    //Save image reference and its position
                    savedReference = imagePlacing.getItem(position);
                    savedReferencePosition = position;

                    //Show correct image and disable clickable on image
                    img.setImageResource((int)savedReference);
                    img.setOnClickListener(null);
                    timesClicked++;
                }
                //Second Card being clicked after one is faced up
                else
                {
                    //Compare if two cards are identical
                    if(imagePlacing.getItem(position).equals(savedReference))
                    {
                        //If both cards are identical, set their position to true
                        imagePlacing.setSelectedCardsTrue(position);
                        imagePlacing.setSelectedCardsTrue(savedReferencePosition);

                        //Show correct image and disable clickable on image
                        img.setImageResource((int)imagePlacing.getItem(position));
                        img.setOnClickListener(null);
                    }
                    else
                    {
                        /*Save Cards and reset them to Adapter. Adapter will handle
                        whether to leave facedown or switch back to faceup card based
                        on selectedCards boolean array*/
                        ImagePlacing imageAdapter = (ImagePlacing) cardTable.getAdapter();
                        cardTable.setAdapter(imageAdapter);

                        //We need a delay to show second card being display
                        //Possible Code below to delay for a few seconds

                        /*
                        final Handler handler = new Handler();

                        Runnable runnable = new Runnable()
                        {
                            int i=0;
                            public void run()
                            {
                                img.setImageResource(R.drawable.animal_10);
                                i++;

                                if(i > imagePlacing.getCount() - 1)
                                {
                                    i=0;
                                }
                                handler.postDelayed(this, 50);  //for interval...
                            }
                        };
                        handler.postDelayed(runnable, 3000); //for initial delay..
                        */
                    }

                    //Reset important variables after two clicks
                    timesClicked = 0;
                    savedReference = null;
                    savedReferencePosition = 0;
                }

            }
        });

        play_stop_Button = (ToggleButton) findViewById(R.id.play_stop_Button);
        play_stop_Button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    appSong = MediaPlayer.create(getApplicationContext(),R.raw.jeopardy_song);
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
        savedInstanceState.putBooleanArray("selectedCards", imagePlacing.getSelectedCards());

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

        //cardTable.setAdapter(new ImagePlacing(this, storing));
        //Improved version that does not need new constructor anymore
        ImagePlacing imageAdapter = (ImagePlacing) cardTable.getAdapter();
        imageAdapter.setUserCardsIds(storing);
        imageAdapter.setSelectedCards(savedInstanceState.getBooleanArray("selectedCards"));
    }
    //When New Game is clicked
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

    //When End Game Button is clicked
    public void endGameClicked(View v) {
        ImagePlacing imageAdapter = (ImagePlacing) cardTable.getAdapter();
        imageAdapter.showAll();
        cardTable.setAdapter(imageAdapter);
    }
}