package com.example.naborp.focusgame;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.VideoView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class ImagePlacing extends BaseAdapter {

    private Context mContext;
    private int userChoice;
    private Integer[] userCardsIds;
    private ArrayList<Integer> tempUserCardsIds = new ArrayList<>();

    private ArrayList<Integer> cardsIds = new ArrayList<>(Arrays.asList(

            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,

            R.drawable.sample_0, R.drawable.sample_1))

            /*
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3))
            */
    ;

    public ImagePlacing(Context c, int userChoice){
        mContext = c;
        this.userChoice = userChoice;
        userCardsIds = new Integer[userChoice];
        for(int i = 0; i < userChoice; i++)
        {
            Collections.shuffle(cardsIds);
            Integer tmp = cardsIds.remove(0);
            tempUserCardsIds.add(tmp);
            tempUserCardsIds.add(tmp);
        }

        Collections.shuffle(tempUserCardsIds);
        userCardsIds = tempUserCardsIds.toArray(userCardsIds);

    }

    /*
        public void randomizeCards() {

        for(int i = 0; i < userChoice; i++) {
            Collections.shuffle(cardsIds);
            Integer tmp = cardsIds.remove(0);
            tempUserCardsIds.add(tmp);
            tempUserCardsIds.add(tmp);
        }

        Collections.shuffle(tempUserCardsIds);
        userCardsIds = tempUserCardsIds.toArray(userCardsIds);

        //tempUserCardsIds.toArray(new Integer[0]);

    }  */

    public int getCount() {
        return userCardsIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(120,120));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(userCardsIds[position]);
        return imageView;
    }
}