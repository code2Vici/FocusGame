package com.example.naborp.focusgame;

/**************************************************************
 *  File: MainMenu.java
 *  Author: Nabor Palomera
 *          Ubaldo Jimenez Prieto
 *          Shun Lu
 *          WeiYing Lee
 *
 * Assignment: Focus Game
 * Date Last Modified: 11/29/2016
 *
 * Purpose: This class' purpose is to get the number of cards to
 * display on the game depending on what the user selects. For
 * instance, if the user selects 4 cards, only 4 cards will be
 * displayed to play with. If he/she selects 20, the maximum
 * allowed cards to choose, all 20 will display.
 *
 *************************************************************/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //method: onCreate(Activity Constructor)
    //purpose: Its purpose is to initialize activities' variables and display when activity is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Spinner cardChoice = (Spinner)findViewById(R.id.spinner);
        //cardChoice.setPrompt("Select the number of cards");
        String[] numOfCards = new String[]{"Select the # of Cards to play with","4","6","8","10","12","14","16","18","20"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,numOfCards);
        cardChoice.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardChoice.setAdapter(adapter);
        cardChoice.setOnItemSelectedListener(this);
    }

    //method: onItemSelected
    /*purpose: When user selects an option from dropdown menu this method acts
               as an onSelectedListener so that when you select x number of cards
               it goes to the correct activity with the correct number of cards
               depending on the choice. To do this we implemented a simple switch
               case to get the user choice and depending on the choice we start
               a new intent with the choice selected.
     */
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        MainGame startGame;
        Intent intent;
        Bundle b;
        int userChoice = 0;

        switch (position)
        {
            case 0 :
                break;
            case 1 :
                //Display MainGame Activity with 4 Cards
                userChoice = 2;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 2 :
                //Display MainGame Activity with 6 Cards
                userChoice = 3;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 3 :
                //Display MainGame Activity with 8 Cards
                userChoice = 4;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 4 :
                //Display MainGame Activity with 10 Cards
                userChoice = 5;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 5 :
                //Display MainGame Activity with 12 Cards
                userChoice = 6;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 6 :
                //Display MainGame Activity with 14 Cards
                userChoice = 7;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 7 :
                //Display MainGame Activity with 16 Cards
                userChoice = 8;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 8 :
                //Display MainGame Activity with 18 Cards
                userChoice = 9;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 9 :
                //Display MainGame Activity with 20 Cards
                userChoice = 10;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}