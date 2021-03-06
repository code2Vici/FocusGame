package com.example.naborp.focusgame;

/**************************************************************
 *  File: MainGame.java
 *  Author: Nabor Palomera
 *          Ubaldo Jimenez Prieto
 *          Shun Lu
 *          WeiYing Lee
 *
 * Assignment: Focus Game
 * Date Last Modified: 11/29/2016
 *
 * Purpose: Its purpose is to display the correct number of cards depending
 * on the user choice on the previous activity. This class also
 * handles the new game button, try again button, end game button, and
 * ON/OFF toggle button. The main purpose of this class is to do the
 * comparison between each card and if two cards match when selected, do
 * nothing just accumulate the points. If two selected cards do not match
 * then we use the try again button to flip cards and try to match again.
 * The new game button takes you to the main menu where the user can
 * reselect the number of cards. The end game displays the whole cards
 * face up. The toggle button let's the user play/stop the music.
 *
 *************************************************************/

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainGame extends AppCompatActivity
{
    private int userChoice;
    private int score;
    //Making it better to hold position
    private int[] savedReference;
    //User should not modify this object
    private GridView cardTable;
    private TextView scoreView;
    private MediaPlayer appSong;
    private ToggleButton play_stop_Button;
    //This field stores the instance of ImagePlacing object
    //User should not modify this object
    private ImagePlacing imagePlacing;

    //method: onCreate(Activity Constructor)
    //purpose: Its purpose is to initialize activities' variables and display when activity is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        //Added position array instance
        savedReference = new int[] {-1, -1};
        //Set score to 0
        score = 0;
        Bundle b = getIntent().getExtras();
        this.userChoice = b.getInt("userChoice");
        System.out.println("userChoice" + userChoice);

        cardTable = (GridView) findViewById(R.id.cardTable);
        //Get score view
        scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Score: " + score);

        cardTable.setAdapter(imagePlacing = new ImagePlacing(this, userChoice));
        cardTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainGame.this, "" + position, Toast.LENGTH_SHORT);

                ImageView img = (ImageView) view;
                //Check if at dead end state && card is unselected
                if (savedReference[1] == -1 && !imagePlacing.isSelected(position)) {
                    //I will use savedReference to count
                    if (savedReference[0] == -1) {
                        //When clicked, show correct picture
                        imagePlacing.setSelectedCardsTrue(position);
                        savedReference[0] = position;
                        img.setImageResource((int) imagePlacing.getItem(position)); //This line might move to below outside block
                    } else {
                        savedReference[1] = position;
                        imagePlacing.setSelectedCardsTrue(position);
                        img.setImageResource((int) imagePlacing.getItem(position));
                        //Continue

                        //Compare if two cards are identical
                        if (imagePlacing.getItem(savedReference[0]).equals(imagePlacing.getItem(savedReference[1]))) {
                            savedReference[0] = -1;
                            savedReference[1] = -1;

                            //Contintue of code, + 2 scores
                            score = score + 2;
                            scoreView.setText("Score: " + score);
                            //Or end game when all cards revealed
                        } else {
                            System.out.println("In -1 score block");
                            if (score > 0) {
                                score = score - 1;
                            }
                            scoreView.setText("Score: " + score);
                            //-1 score, or now allow user to click other cards
                        }
                    }
                }

                //finish game
                if(imagePlacing.isGameFinished()) {
                    ScoreDialogBox dialog = new ScoreDialogBox(MainGame.this, userChoice, score);
                    dialog.show();
                }

            }
        });

        //Music
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

    //method: onSaveInstanceState
    //purpose: To Save Data from Current Activity so it is not lost when rotating
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        int totalsavedCards = userChoice * 2;

        int[] temp = new int[totalsavedCards];

        for(int i = 0; i < totalsavedCards ; i++)
        {
            temp[i] = (int)cardTable.getAdapter().getItem(i);
        }

        savedInstanceState.putIntArray("save", temp);
        savedInstanceState.putBooleanArray("selectedCards", imagePlacing.getSelectedCards());
        savedInstanceState.putIntArray("savedReference", savedReference);
        savedInstanceState.putInt("score", score);

        if(appSong != null) {
            savedInstanceState.putInt("possition", appSong.getCurrentPosition());
            appSong.pause();
        }


    }
    //method: onRestoreInstanceState
    //purpose: To Restore Data from previous destroyed activity and keep the same functionalities
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
        savedReference = savedInstanceState.getIntArray("savedReference");
        score = savedInstanceState.getInt("score");
        scoreView.setText("Score: " + score);

        if (appSong != null) {
            int pos = savedInstanceState.getInt("possition");
            appSong.seekTo(pos);
        }

    }

    //method: newGameClicked
    //purpose: Acts as the onClicked listener. This methods goes to the MainMenu
    //         Activity when newGame button is clicked
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

    //method: endGame
    //purpose: When End Game Button is clicked this method displays all the cards face up
    public void endGameClicked(View v) {
        ImagePlacing imageAdapter = (ImagePlacing) cardTable.getAdapter();
        imageAdapter.showAll();
        cardTable.setAdapter(imageAdapter);
    }

    //method: tryAgainClicked
    //purpose: When Try Again Button is clicked if two cards do not match this buttons
    //         flips both cards to give it a second try
    public void tryAgainClicked(View v) {
        ImagePlacing imageAdapter = (ImagePlacing) cardTable.getAdapter();
        if (savedReference[0] != -1 && savedReference[1] != -1) {
            for (int position : savedReference) {
                imageAdapter.setSelectedCardsFalse(position);
            }
            savedReference[0] = -1;
            savedReference[1] = -1;
            cardTable.setAdapter(imageAdapter);
        }
    }

}