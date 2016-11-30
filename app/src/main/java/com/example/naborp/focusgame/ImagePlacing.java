package com.example.naborp.focusgame;

/**************************************************************
 *  File: ImagePlacing.java
 *  Author: Nabor Palomera
 *          Ubaldo Jimenez Prieto
 *          Shun Lu
 *          WeiYing Lee
 *
 * Assignment: Focus Game
 * Date Last Modified: 11/29/2016
 *
 * Purpose: ImagePlacing holds all the cards we are going to use
 * in the game. Also its purpose is to display the cards on gridView
 * according what the user picked from Main Menu. We use BaseAdapter
 * class to help us achieve our goal.
 *************************************************************/

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

    //Instance variables
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

    //method: Constrcutor
    //purpose: Set our context and Integer
    /*public ImagePlacing(Context c, Integer[] arr)
    {
        mContext = c;
        userCardsIds =  arr;
    }*/


    //method: Constructor
    //purpose: Set our context and userChoice.
    //In addition, set the size for boolean and cards array.
    //Depending on the user choice, we will randomly pick
    //random cards and set them to our final array to display them later.
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

    //method: getCount
    //purpose: get the length of the cards user picked
    public int getCount() {
        return userCardsIds.length;
    }

    //method: getItem
    //purpose: get the reference/address of the card from a certain position
    public Object getItem(int position) {
        return userCardsIds[position];
    }

    //method: getItemId
    //purpose: to get the Item ID based on the position
    public long getItemId(int position) {
        return 0;
    }

    //method: getSelectedCards
    //purpose: return the boolean array the holds a boolean value that determines
    //whether if a card was clicked during the game
    public boolean[] getSelectedCards() { return selectedCards; }

    //method: isSelecteed
    //purpose: Check if the card has been selected or not
    public boolean isSelected(int position) {return selectedCards[position] == true;}

    //method: setUserCardsID
    //purpose: set a new array of cards into our original userCardsID array into this class
    public void setUserCardsIds(Integer[] userCardsIds)
    {
        this.userCardsIds = userCardsIds;
    }

    //method: setSelectedCardsTrue
    //purpose: Set a certain card position to true
    public void setSelectedCardsTrue(int position) { this.selectedCards[position] = true; }

    //method: setSelectedCardsTrue
    //purpose: Set a certain card position to false
    public void setSelectedCardsFalse(int position) { this.selectedCards[position] = false; }

    //method: setSelectedCards
    //purpose: set a new boolean array into our original selectedCards into this class
    public void setSelectedCards (boolean[] selectedCards) { this.selectedCards = selectedCards; }

    //method: showAll
    //purpose: Set all cards marked as selected. Useful when user clicks End Game
    public void showAll () {
        for (int i = 0; i < selectedCards.length; ++i) {
            selectedCards[i] = true;

        }
    }
    //method: isGamefinished
    //purpose: check whether the user completed the game
    public boolean isGameFinished(){
        for(boolean eachCard: selectedCards) {
            if(!eachCard) {
                return false;
            }
        }

        return true;
    }
    //method: getView
    //purpose: Display all of the cards into gridview
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
            imageView.setImageResource(R.drawable.back_card);
        }
        else {
            imageView.setImageResource(userCardsIds[position]);
        }
        return imageView;
    }
}