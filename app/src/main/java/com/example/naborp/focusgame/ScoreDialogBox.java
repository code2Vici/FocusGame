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
import android.widget.ImageButton;

import java.io.*;
import java.util.*;

public class ScoreDialogBox extends Dialog implements android.view.View.OnClickListener {

    private HashMap<Integer, String> data;
    private Activity c;
    private int userChoice;
    private int score;
    private Dialog d;
    private Button submit;
    private ImageButton close;

    public ScoreDialogBox(Activity a, int userChoice, int score) {
        super(a);
        this.c = a;
        this.userChoice = userChoice;
        this.score = score;
        data = new HashMap<Integer, String>();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        submit = (Button) findViewById(R.id.button);
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

            //String s = reader.readLine();
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

        /*for (Map.Entry<String, Integer> entry : data.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

        }*/

        Map<Integer, String> treeMap = new TreeMap<Integer, String>(data);
        for (Integer str : treeMap.keySet()) {

            if()
        }




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




