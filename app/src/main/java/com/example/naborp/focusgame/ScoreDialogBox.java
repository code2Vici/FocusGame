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

    private HashMap<String, Integer> score;
    private Activity c;
    private int userChoice;
    private Dialog d;
    private Button submit;
    private ImageButton close;

    public ScoreDialogBox(Activity a, int userChoice) {
        super(a);
        this.c = a;
        this.userChoice = userChoice;
        score = new HashMap<String, Integer>();
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
        String filename = "R.raw." + (userChoice * 2) + "cardHighScore";
        File f = new File(filename);
        if (!f.exists())
        {
            PrintWriter pw = new PrintWriter(new FileWriter(filename));
            //for (int i = 0; i < scores.size(); ++i) {
            //    pw.println(scores.get(i));
            //}

            pw.flush();
            pw.close();
        }

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line = reader.readLine();
        //scores.add(0, new UserScore(line));
        //scores.remove(scores.size() - 1);

        for (int i = 1; i < 5; ++i) {
            line = reader.readLine();
         //   scores.add(i, new UserScore(line));
         //   scores.remove(scores.size() - 1);
        }
    }
}
