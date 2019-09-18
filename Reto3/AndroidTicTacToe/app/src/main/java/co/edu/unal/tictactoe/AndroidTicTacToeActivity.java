package co.edu.unal.tictactoe;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;

import edu.harding.tictactoe.*;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("New Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startNewGame();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mInfoTextView = (TextView) findViewById(R.id.information);

        mCptWins = (TextView) findViewById(R.id.wins_counter);
        mCptTies = (TextView) findViewById(R.id.ties_counter);
        mCptLoses = (TextView) findViewById(R.id.android_counter);

        mGame = new TicTacToeGame();

        // Initialize win, lose and tie game's counters
        mWin = 0;
        mLose = 0;
        mTie = 0;

        mRandomPlayer = new Random();

        startNewGame();


    }

    // To know when the game finishes
    private Boolean mGameOver;

    // How many wins, loses and ties
    private int mWin, mLose, mTie;

    // Random strating player
    private Random mRandomPlayer;

    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // Buttons making up the board
    private Button mBoardButtons[];

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mCptWins;
    private TextView mCptTies;
    private TextView mCptLoses;

    // Set up the game board.
    private void startNewGame() {

        this.mGameOver = false;
        mCptWins.setText(""+mWin);
        mCptTies.setText(""+mTie);
        mCptLoses.setText(""+mLose);

        mGame.clearBoard();
        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }


        if (mRandomPlayer.nextInt(2) == 1){
            // Human goes first
            mInfoTextView.setText(R.string.first_human);
        }
        else{
            // Computer goes first
            mInfoTextView.setText(R.string.turn_computer);
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            mInfoTextView.setText(R.string.turn_human);

        }
        // End of startNewGame
    }

    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled() && !mGameOver) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                // If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);

                    winner = mGame.checkForWinner();
                }
                if (winner == 0)
                    mInfoTextView.setText(R.string.turn_human);
                else if (winner == 1) {
                    mInfoTextView.setText(R.string.result_tie);
                    mTie ++;
                    mGameOver = true;
                }

                else if (winner == 2) {
                    mInfoTextView.setText(R.string.result_human_wins);
                    mWin ++;
                    mGameOver = true;
                }

                else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mLose ++;
                    mGameOver = true;
                }


            }
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }


}


