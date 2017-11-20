package com.niranjana.connect3;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    //0 = x ,1 = o
    int activePlayer = 0;
    int winner = 2;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[] connect = {2,2,2};
    boolean connected = false;
    int xCount=0,oCount=0;
   public TextView turnView = (TextView)findViewById(R.id.turnTextView);
    public boolean isConnectedHorizontally()
    {
        int i=0;
        do {
            for (int j = 0; j < 3; j++) {
                connect[j] = gameState[i + j];
            }
            i=i+3;
            connected = isConnected(connect);
        }while(i<7 && !connected) ;
            return connected;
    }
    public boolean isConnectedVertically()
    {
        int i=0;
        do{
            for (int j = 0, k = i; j < 3; j++) {
                connect[j] = gameState[k];
                k=k+3;
            }
            i=i+1;
            connected = isConnected(connect);
        }while(i<3 && !connected);
        return  connected;
    }
    public boolean isConnectedDiagonally()
    {
        //check primary diagonal
        for(int i=0,j=0;i<9;i=i+4)
        {
            connect[j++] = gameState[i];
        }
        connected = isConnected(connect);
        if(connected){
            return true;
        }else{
            //check secondary diagonal
            for(int i=2,j=0;i<7;i=i+2)
            {
                connect[j++] = gameState[i];
            }
            connected = isConnected(connect);

        }

        return connected;
    }
    public boolean isConnected(int[] connect)
    {
        {
            if((connect[0] == 0) && ( connect[1] == 0) && (connect[2] == 0))
            {
                winner = 0;
                return true;
            }
            if((connect[0] == 1) && ( connect[1] == 1) && (connect[2] == 1))
            {
                winner = 1;
                return true;
            }
        }
        return false;
    }

    public boolean connectCheck()
    {
        if(isConnectedHorizontally()){
            return true;
        }else
        {
            if(isConnectedVertically())
            {
                return true;
            }
            else
            {

                if(isConnectedDiagonally())
                {
                    return true;
                }
                else
                    return false;
            }
        }

    }
    public void dropIn(View view)
    {

        ImageView counter = (ImageView) view;
        int tapped = Integer.parseInt(counter.getTag().toString());
        boolean finishedGame = false;
        System.out.println("Tag: "+counter.getTag().toString());
        if(gameState[tapped] == 2 ) {
            if (winner == 2) {


                counter.setTranslationY(-1000f);
                gameState[tapped] = activePlayer;
                if (activePlayer == 0) {
                    counter.setImageResource(R.drawable.x_tic);
                    activePlayer = 1;
                    turnView.setText("Turn 'O'");
                } else if (activePlayer == 1) {
                    counter.setImageResource(R.drawable.o_tic);
                    activePlayer = 0;
                    turnView.setText("Turn 'X'");
                }
                counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
                finishedGame = connectCheck();
                if (finishedGame) {
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerTextView);
                    if (winner == 0) {
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                        winnerMessage.setText("'X' has Won!");
                        TextView scoreMessageX = (TextView) findViewById(R.id.xScore);
                        xCount++;
                        scoreMessageX.setText("X= "+String.valueOf(xCount));

                    } else if (winner == 1) {
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                        winnerMessage.setText("'O' has Won!");
                        TextView scoreMessageX = (TextView) findViewById(R.id.oScore);
                        oCount++;
                        scoreMessageX.setText("O= "+String.valueOf(oCount));

                    } else
                        winner = 2;
                }


            }
        }
        for(int i=0;i<9;i++) {
            System.out.println(gameState[i]+"  ");
        }


    }
    public void playAgain(View view)
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        winner = 2;
        for(int i=0;i<9;i++) {
            gameState[i] = 2;
        }
        for(int i=0;i<3;i++){
            connect[i]=2;
        }

        connected = false;
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridBoard);
        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
