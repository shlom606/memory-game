package com.example.memory;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    TextView showTurn,count1,count2;
    ImageView curView = null;
    private int countPair = 0;
    final int[] drawable = new int[] {
            R.drawable.apple,
            R.drawable.cherry,
            R.drawable.bloodorange,
            R.drawable.coconut,
            R.drawable.dragonfruit,
            R.drawable.blackcurrant,
            R.drawable.acerola,
            R.drawable.apricot,
            R.drawable.avocado,
            R.drawable.blueberry,
            R.drawable.arbutus,
            R.drawable.banana,
            R.drawable.blackberry,
            R.drawable.melon,
            R.drawable.clementine,
            R.drawable.crambola,

    };
    int[] pos = {0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7,8,10,9,10,8,9,11,11,12,13,14,15,15,13,14,12};

    int currentPos = -1;
    boolean player1,player2;
    int score1=0,score2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        randomize(pos);//randomize all positions
        player1=true;player2=false;
        ImageAdapter imageAdapter = new ImageAdapter(this);
        GridView gridView = (GridView) findViewById(R.id.simpleGridView);
        showTurn=(TextView) findViewById(R.id.theTurn);
        count1=(TextView) findViewById(R.id.score1);
        count2=(TextView) findViewById(R.id.score2);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentPos < 0 ) {
                    currentPos = position;
                    curView = (ImageView) view;
                    ((ImageView) view).setImageResource(drawable[pos[position]]);
                }
                else {
                    if (pos[currentPos] == pos[position]) {

                        ((ImageView) view).setImageResource(drawable[pos[position]]);
                        Toast.makeText(GameActivity.this, "its a Match!", Toast.LENGTH_LONG).show();
                        if(player1){
                            score1++;
                            count1.setText("     "+Integer.toString(score1));
                        }
                        else {
                            score2++;
                            count2.setText("     "+Integer.toString(score2));
                        }
                        countPair++;
                        if (countPair == 16) {
                            Toast.makeText(GameActivity.this, "the game ended!", Toast.LENGTH_LONG).show();
                            checkWinner(score1,score2);
                        }
                    } else if (pos[currentPos] != pos[position]) {
                        Handler handler = new Handler();
                        ((ImageView) view).setImageResource(drawable[pos[position]]);
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                curView.setImageResource(R.drawable.card);
                                ((ImageView) view).setImageResource(R.drawable.card);
                                changeTurns();
                            }
                        }, 500);


                        Toast.makeText(GameActivity.this, "Not Match!", Toast.LENGTH_LONG).show();
                    }
                    currentPos = -1;
                }
            }
        });
    }
    public void changeTurns()
    {
        if(player1){
            player1=false;
            player2=true;
            showTurn.setText("              ITS PLAYER #2 TURN!!!");//the turn is changing
        }
        else {
            player2=false;
            player1=true;
            showTurn.setText("              ITS PLAYER #1 TURN!!!");//the turn is changing
        }
    }
    public void checkWinner(int sc1,int sc2)
    {
        if(sc1>sc2)
        {
            Intent intent = new Intent(GameActivity.this,player1winscreen.class);
            startActivity(intent);
        }
        if(sc1<sc2){
            Intent intent = new Intent(GameActivity.this,player2winscreen.class);
            startActivity(intent);
        }
        if(sc1==sc2){
            Intent intent = new Intent(GameActivity.this,tiegame.class);
            startActivity(intent);
        }
    }
    public int[] randomize(int [] array){

        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
        return array;
    }

}

