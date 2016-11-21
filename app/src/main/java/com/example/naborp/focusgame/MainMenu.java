package com.example.naborp.focusgame;

//Ubaldo

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
                //TODO: Display MainGame Activity with 4 Cards
                userChoice = 2;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 2 :
                //TODO: Display MainGame Activity with 6 Cards
                userChoice = 3;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 3 :
                //TODO: Display MainGame Activity with 8 Cards
                userChoice = 4;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 4 :
                //TODO: Display MainGame Activity with 10 Cards
                userChoice = 5;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 5 :
                //TODO: Display MainGame Activity with 12 Cards
                userChoice = 6;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 6 :
                //TODO: Display MainGame Activity with 14 Cards
                userChoice = 7;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 7 :
                //TODO: Display MainGame Activity with 16 Cards
                userChoice = 8;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 8 :
                //TODO: Display MainGame Activity with 18 Cards
                userChoice = 9;
                startGame = new MainGame();
                intent = new Intent(this, startGame.getClass());
                b = new Bundle();
                b.putInt("userChoice", userChoice);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 9 :
                //TODO: Display MainGame Activity with 20 Cards
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
