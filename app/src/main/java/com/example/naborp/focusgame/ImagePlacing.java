package com.example.naborp.focusgame;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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
    //This field stores state of is the card selected?.  If it is not selected, show background card
    //If selected, show correct card in userCardsIds
    private boolean[] selectedCards;
    private ArrayList<Integer> tempUserCardsIds = new ArrayList<>();

    private ArrayList<Integer> cardsIds = new ArrayList<>(Arrays.asList(

            R.drawable.animal_0, R.drawable.animal_1,
            R.drawable.animal_2, R.drawable.animal_3,
            R.drawable.animal_4, R.drawable.animal_5,
            R.drawable.animal_6, R.drawable.animal_7,
            R.drawable.animal_8, R.drawable.animal_9));

    public ImagePlacing(Context c, Integer[] arr)
    {
        mContext = c;
        userCardsIds =  arr;
    }
    public ImagePlacing(Context c, int userChoice) {
        mContext = c;
        this.userChoice = userChoice;
        selectedCards = new boolean[userChoice * 2];
        userCardsIds = new Integer[userChoice];
        for (int i = 0; i < userChoice; i++) {
            Collections.shuffle(cardsIds);
            Integer tmp = cardsIds.remove(0);
            tempUserCardsIds.add(tmp);
            tempUserCardsIds.add(tmp);
            Collections.shuffle(tempUserCardsIds);
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

   /* public ImagePlacing(Parcel source)
    {
         for(int i = 0; i < userCardsIds.length; < i++)
    }

    public void writeToParcel(Parcel out, int flags)
        {
        out.writeArray(userCardsIds);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ImagePlacing> CREATOR = new Parcelable.Creator<ImagePlacing>()
    {
        public ImagePlacing createFromParcel(Parcel in)
        {
            return new ImagePlacing(in);
        }
        public ImagePlacing [] newArray(int size)
        {
            return new ImagePlacing[size];
        }

    }; */

    public int getCount() {
        return userCardsIds.length;
    }

    public Object getItem(int position) {
        return userCardsIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public boolean[] getSelectedCards() { return selectedCards; }

    public boolean isSelected(int position) {return selectedCards[position] == true;}
    public void setUserCardsIds(Integer[] userCardsIds)
    {
        this.userCardsIds = userCardsIds;
    }

    public void setSelectedCardsTrue(int position) { this.selectedCards[position] = true; }

    public void setSelectedCardsFalse(int position) { this.selectedCards[position] = false; }

    public void setSelectedCards (boolean[] selectedCards) { this.selectedCards = selectedCards; }

    public void showAll () {
        for (int i = 0; i < selectedCards.length; ++i) {
            selectedCards[i] = true;

        }
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(280,280));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView = (ImageView) convertView;
        }
        //Display background card if false
        if (!selectedCards[position])
        {
            imageView.setImageResource(R.drawable.animal_10);
        }
        else {
            imageView.setImageResource(userCardsIds[position]);
        }
        return imageView;
    }
}