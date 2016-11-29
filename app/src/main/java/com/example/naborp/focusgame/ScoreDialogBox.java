package com.example.naborp.focusgame;

/**
 * Created by Eric on 11/28/2016.
 */

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

    private HashMap<Integer, String> data;
   // private TreeMap<Integer, String> showData;
    private Activity c;
    private int userChoice;
    private int score;
    private Dialog d;
    private Button submit;
    private EditText username;
    private TextView scoreText;
    private TextView score1;
    private TextView score2;
    private TextView score3;
    private ImageButton close;

    public ScoreDialogBox(Activity a, int userChoice, int score) {
        super(a);
        this.c = a;
        this.userChoice = userChoice;
        this.score = score;
        data = new HashMap<Integer, String>();
        //showData = new TreeMap<>();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        submit = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.editText);

        scoreText = (TextView) findViewById(R.id.textView);
        scoreText.setText("Your new score is " + score + " points !");

        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        score3 = (TextView) findViewById(R.id.score3);

        //read file
        InputStream is = c.getResources().openRawResource(fileReaderHelper(userChoice));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String s;
        ArrayList<String> scoreList = new ArrayList<>();
        try {
            while((s = reader.readLine()) != null)
            {
                scoreList.add(s);
            }

            score1.setText(scoreList.get(0));
            score2.setText(scoreList.get(1));
            score3.setText(scoreList.get(2));

        } catch (IOException e) {
            e.printStackTrace();
        }

        close = (ImageButton) findViewById(R.id.closeButton);
        submit.setOnClickListener(this);
        close.setOnClickListener(this);
    }

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

    private void fileHandler(int userChoice) throws IOException {

        InputStream is = c.getResources().openRawResource(fileReaderHelper(userChoice));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String s;
        while((s = reader.readLine()) != null)
        {
            char[] arr = s.toCharArray();
            String name = "";
            String score ="";
            boolean b = true;

            for (int i = 0; i < arr.length; ++i) {
                if (arr[i] != ':' && b) {
                    name += arr[i];
                }
                else if (arr[i] == ':') {
                    b = false;
                }
                else {
                    score += arr[i];
                }
            }

            data.put(Integer.valueOf(score), name);

        }

        //Loop through the map
        /*for (Map.Entry<String, Integer> entry : data.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

        }*/

        boolean newHighScore = false;
        TreeMap<Integer, String> treeMap = new TreeMap<>(data);
        Integer scoreFromTxt = treeMap.keySet().iterator().next();

        if(Integer.valueOf(score) > scoreFromTxt)
        {
            treeMap.remove(scoreFromTxt);
            treeMap.put(Integer.valueOf(score), username.getText().toString());
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(c.openFileOutput("card4highscore.txt", c.MODE_PRIVATE));

        String newScore;

        //PrintWriter write = new PrintWriter(new FileWriter(c.getResources().openRawResource(fileReaderHelper(userChoice)));
        for(Integer str: treeMap.keySet())
        {
            String tmpname =  str.toString();
            newScore = treeMap.get(str) + ":" + tmpname;
            outputStreamWriter.write(newScore);
            System.out.println(newScore);
            //System.out.println(str + " " + treeMap.get(str));
        }
        outputStreamWriter.flush();
        outputStreamWriter.close();

    }

    private void showFile() {

    }

    private int fileReaderHelper(int ch) {

        switch (ch) {
            case 2:
                return R.raw.card4highscore;
            case 3:
                return R.raw.card6highscore;
            case 4:
                return R.raw.card8highscore;
            case 5:
                return R.raw.card10highscore;
            case 6:
                return R.raw.card12highscore;
            case 7:
                return R.raw.card14highscore;
            case 8:
                return R.raw.card16highscore;
            case 9:
                return R.raw.card18highscore;
            case 10:
                return R.raw.card20highscore;
        }

        return -1;
    }

}




