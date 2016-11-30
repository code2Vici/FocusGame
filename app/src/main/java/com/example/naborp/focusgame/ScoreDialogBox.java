package com.example.naborp.focusgame;

/**************************************************************
 *  File: ScoreDialogBox.java
 *  Author: Nabor Palomera
 *          Ubaldo Jimenez Prieto
 *          Shun Lu
 *          WeiYing Lee
 *
 * Assignment: Focus Game
 * Date Last Modified: 11/29/2016
 *
 * Purpose: ScoreDialogBox is to prompt the user to enter their high score
 * when the game is finished. A dialog box pops up displaying the top three
 * scores and the user score at that current moment. In addition, we asked the
 * user to enter their username after every completed game.
 *************************************************************/

import android.app.Activity;
import android.app.Dialog;
import android.app.admin.SystemUpdatePolicy;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.io.*;
import java.util.*;

public class ScoreDialogBox extends Dialog implements android.view.View.OnClickListener {

    //Instance Variables

    //private HashMap<Integer, String> data;
    //private Integer[] dataScore;
    //private String[] dataName;
    private ArrayList<UserScore> userscore;
    private Activity c;
    private int userChoice;
    private int score;
    private Dialog d;
    private Button submit;
    private TextView score1;
    private TextView score2;
    private TextView score3;
    private TextView scoreShow;
    private EditText username;
    private ImageButton close;

    //method: Constructor
    //purpose: Set our activity, user choice and score into our
    //instance variables of this class.
    public ScoreDialogBox(Activity a, int userChoice, int score) {
        super(a);
        this.c = a;
        this.userChoice = userChoice;
        this.score = score;
        userscore = new ArrayList<UserScore>();
        //data = new HashMap<Integer, String>();
        //dataScore = new Integer[3];
        //dataName = new String[3];
    }

    //method: OnCreate
    //purpose: constructor for this activity for Dialog Box
    //Set all our initial labels, buttons, and textview at the start of the pop up.
    // In addition, before displaying the highscore, we read our saved data scores in order
    //to display it to the user.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        submit = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.editText);
        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        score3 = (TextView) findViewById(R.id.score3);
        scoreShow = (TextView) findViewById(R.id.textView);
        close = (ImageButton) findViewById(R.id.closeButton);

        String filename = "card" + (userChoice * 2) + "highscore.txt";

        File file = new File(c.getFilesDir(), filename);
        try{
            //file.delete();
            if (!file.exists()) {
                FileOutputStream fo = c.openFileOutput(filename, c.MODE_PRIVATE);
                PrintWriter pw = new PrintWriter(fo);
                pw.println(new UserScore().getStringLine() + "\n" + new UserScore().getStringLine() + "\n" + new UserScore().getStringLine());
                fo.flush();
                pw.flush();
                fo.close();
                pw.close();
            }

            FileInputStream is = c.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            score1.setText(reader.readLine());
            score2.setText(reader.readLine());
            score3.setText(reader.readLine());

        }catch(IOException e){}

        scoreShow.setText("Score: " + score);

        submit.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    //method: onClick
    //purpose: Set all our event click handlers here.
    //For our case is two buttons: Submit and Close.
    public void onClick(View v) {
        //handle logic
        switch(v.getId()) {
            case R.id.button:
                //save file
                try {
                    fileHandler(userChoice);
                    dismiss();
                    Intent intent = new Intent(c, MainMenu.class);
                    c.startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.closeButton:
                dismiss();
                Intent intent = new Intent(c, MainMenu.class);
                c.startActivity(intent);
                break;
        }



    }

    //method: fileHandler
    //purpose: If the user clicks on submit, If the user score is higher than the top three
    //we save it to the corresponding file according to type(numberOFVards). If it is not higher
    //than the top three scores, then we simply ignore it.
    private void fileHandler(int userChoice) throws IOException {

        String filename = "card" + (userChoice * 2) + "highscore.txt";
        UserScore userScore1 = new UserScore();
        UserScore userScore2 = new UserScore();
        UserScore userScore3 = new UserScore();

        File file = new File(c.getFilesDir(), filename);
        //file.delete();
        if (!file.exists()) {
            FileOutputStream fo = c.openFileOutput(filename, c.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fo);
            pw.println(userScore1.getStringLine() + "\n" + userScore2.getStringLine() + "\n" + userScore3.getStringLine());
            userscore.add(userScore1);
            userscore.add(userScore2);
            userscore.add(userScore3);
            fo.flush();
            pw.flush();
            fo.close();
            pw.close();
        }else{

            FileInputStream is = c.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            for (int i = 0; i < 3; ++i)
            {
                s = reader.readLine();

                userscore.add(new UserScore(s));
            }
        }

        boolean newHighScore = false;

        Collections.sort(userscore);

        if(Integer.valueOf(score) >= userscore.get(0).getScore())
        {
            userscore.add(new UserScore(username.getText().toString(), score));
            userscore.remove(userscore.get(0));
        }

        Collections.sort(userscore);

        PrintWriter pw = new PrintWriter(c.openFileOutput(filename, c.MODE_PRIVATE));

        String newScore;

        for(int i = 0; i < 3; ++i)
        {
            newScore = userscore.get(i).getStringLine();
            pw.println(userscore.get(i).getStringLine());
            System.out.println(newScore);
        }
        pw.flush();
        pw.close();

    }
}




