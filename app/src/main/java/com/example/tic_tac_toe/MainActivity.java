package com.example.tic_tac_toe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int count;

    private int player1;
    private int player2;

    private TextView textPlayer1;
    private TextView textPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPlayer1 = findViewById(R.id.text_player1);
        textPlayer2 = findViewById(R.id.text_player2);
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String button = "button_" + i + j;
                int res = getResources().getIdentifier(button,"id",getPackageName());
                buttons[i][j] = findViewById(res);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetGame();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals(""))
            return;
        if(player1Turn)
        {
            ((Button) v).setText("X");
        }
        else
        {
            ((Button) v).setText("O");
        }
        count++;
        if(checkForWin())
        {
            if(player1Turn)
            {
                player1Wins();
            }
            else {
                player2Wins();
            }
        }
        else if(count == 9)
        {
            draw();
        }
        else
        {
            player1Turn = !player1Turn;
        }
    }
    private boolean checkForWin()
    {
        String[][] field = new String[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++)
        {
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
            {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
            {
                return true;
            }
        }
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
        {
            return true;
        }
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
        {
            return true;
        }
        return false;
    }
    private void player1Wins()
    {
        player1++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void player2Wins()
    {
        player2++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }
    private void draw()
    {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePoints()
    {
        textPlayer1.setText("Player 1: " + player1);
        textPlayer2.setText("Player 2: " + player2);
    }
    private void resetBoard()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                buttons[i][j].setText("");
            }
        }
        count = 0;
        player1Turn = true;
    }
    private void resetGame()
    {
        player1 = 0;
        player2 = 0;
        updatePoints();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count",count);
        outState.putInt("Player1",player1);
        outState.putInt("player2",player2);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt("count");
        player1 = savedInstanceState.getInt("player1");
        player2 = savedInstanceState.getInt("player2");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
