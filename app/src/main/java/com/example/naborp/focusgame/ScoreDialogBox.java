package com.example.naborp.focusgame;

/**
 * Created by Eric on 11/28/2016..
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

        //submit.setEnabled(true);

        if(username.length() < 3) {
            //submit.setEnabled(false);
        }

        scoreShow.setText("Score: " + score);

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
            //        InputStream is = c.getResources().openRawResource(fileReaderHelper(userChoice));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            FileInputStream is = c.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            for (int i = 0; i < 3; ++i)
            {
                s = reader.readLine();

                userscore.add(new UserScore(s));

//            for (int j = 0; j < arr.length; ++j) {
//                if (arr[j] != ':' && b) {
//                    name += arr[j];
//                }
//                else if (arr[j] == ':') {
//                    b = false;
//                }
//                else {
//                    score += arr[j];
//                }
//            }


                //data.put(Integer.valueOf(score), name);
                ;
                //dataScore[i] = Integer.valueOf(score);
                //dataName[i] = name;
            }
        }


        //Loop through the map
        /*for (Map.Entry<String, Integer> entry : data.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

        }*/

        boolean newHighScore = false;
        //Map<Integer, String> treeMap = new TreeMap<Integer, String>(data);
        //Integer scoreFromTxt = treeMap.keySet().iterator().next();

        /*
        for (int i = 0; i < 2; ++i) {
            if (dataScore[i] > dataScore[i + 1]) {
                Integer tempScore = dataScore[i];
                String tempName = dataName[i];
                dataScore[i] = dataScore[i + 1];
                dataName[i] = dataName[i + 1];
                dataScore[i + 1] = tempScore;
                dataName[i + 1] = tempName;
            }
        }
        */

        Collections.sort(userscore);

        if(Integer.valueOf(score) >= userscore.get(0).getScore())
        {
            /*
            for (int i = 1; i < 3; i++) {
                if (Integer.valueOf(score) <= dataScore[i]) {
                    dataScore[i - 1] = Integer.valueOf(score);
                    dataName[i - 1] = username.getText().toString();
                }
                else if (i == 2) {
                    dataScore[i] = Integer.valueOf(score);
                    dataName[i] = username.getText().toString();
                }
                else;
                */
                userscore.add(new UserScore(username.getText().toString(), score));
            //}
            //treeMap.put(Integer.valueOf(score), username.getText().toString());
        }

        Collections.sort(userscore);

        userscore.remove(userscore.get(0));

        PrintWriter pw = new PrintWriter(c.openFileOutput(filename, c.MODE_PRIVATE));
        //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(c.openFileOutput(c.getFilesDir(), c.MODE_PRIVATE));


        String newScore;

        //PrintWriter write = new PrintWriter(new FileWriter(c.getResources().openRawResource(fileReaderHelper(userChoice)));
        for(int i = 0; i < 3; ++i)
        {
//            String tmpname =  dataName[i];
//            String tmpscore = dataScore[i].toString();
            newScore = userscore.get(i).getStringLine();
            pw.println(userscore.get(i).getStringLine());
            System.out.println(newScore);
        }
        pw.flush();
        pw.close();

    }

}




