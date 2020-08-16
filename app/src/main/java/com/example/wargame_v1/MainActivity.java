package com.example.wargame_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_VICTORY = "com.example.application.example.EXTRA_VICTORY";
    private static final int SUPERMAN_TURN = 1;
    private static final int IRONMAN_TURN = 2;
    private static final int LARGEATTACKPOINTS = 50;
    private static final int MEDIUMATTACKPOINTS = 30;
    private static final int SMALLATTACKPOINTS = 10;

    private ProgressBar superman_PB;
    private ProgressBar ironman_PB;
    private ImageView superman_image;
    private ImageView ironman_image;
    private Button largeAttack_superman;
    private Button mediumAttack_superman;
    private Button smallAttack_superman;
    private Button largeAttack_ironman;
    private Button mediumAttack_ironman;
    private Button smallAttack_ironman;
    private ArrayList<Button> superman_Buttons;
    private ArrayList<Button> ironman_Buttons;
    private int turn = SUPERMAN_TURN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* initialize values */
        setValues();

        /* add buttons to lists */
        initialize_superman_list();
        initialize_ironman_list();

        /* activate buttons */
        activateButtons(superman_Buttons);
        activateButtons(ironman_Buttons);

        /* disable ironman buttons -> superman start */
        for(Button btn: ironman_Buttons)
            btn.setEnabled(false);
    }

    private void activateButtons(ArrayList<Button> buttons) {

        for(Button btn: buttons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* update progress bar after attack */
                    setProgressBar(view);


                    /* if game over -> switch Activity
                       else -> switch turn */
                    if(!gameOver())
                        switchTurn();
                }
            });
        }
    }

    private void setProgressBar(View view) {
        if (turn == SUPERMAN_TURN) {
            switch (view.getId()) {
                case R.id.main_BTN_10pt_superman:
                    ironman_PB.setProgress(ironman_PB.getProgress() - SMALLATTACKPOINTS);
                    break;
                case R.id.main_BTN_30pt_superman:
                    ironman_PB.setProgress(ironman_PB.getProgress() - MEDIUMATTACKPOINTS);
                    break;
                case R.id.main_BTN_50pt_superman:
                    ironman_PB.setProgress(ironman_PB.getProgress() - LARGEATTACKPOINTS);
                    break;
            }
        } else if (turn == IRONMAN_TURN) {
            switch (view.getId()) {
                case R.id.main_BTN_10pt_ironman:
                    superman_PB.setProgress(superman_PB.getProgress() - SMALLATTACKPOINTS);
                    break;
                case R.id.main_BTN_30pt_ironman:
                    superman_PB.setProgress(superman_PB.getProgress() - MEDIUMATTACKPOINTS);
                    break;
                case R.id.main_BTN_50pt_ironman:
                    superman_PB.setProgress(superman_PB.getProgress() - LARGEATTACKPOINTS);
                    break;
            }
        }
    }

    private void openVictoryActivity(String victory) {
        String extra = victory;
        Intent intent = new Intent(this, VictoryActivity.class);
        intent.putExtra(EXTRA_VICTORY, extra);
        startActivity(intent);
    }

    private boolean gameOver() {
        if(superman_PB.getProgress() == 0) {
            openVictoryActivity("IronMan");
            return true;
        }
        else if (ironman_PB.getProgress() == 0) {
            openVictoryActivity("SUPERMAN");
            return true;
        }
        return false;
    }

    private void initialize_superman_list() {
        superman_Buttons = new ArrayList<>();
        superman_Buttons.add(largeAttack_superman);
        superman_Buttons.add(mediumAttack_superman);
        superman_Buttons.add(smallAttack_superman);
    }

    private void initialize_ironman_list() {
        ironman_Buttons = new ArrayList<>();
        ironman_Buttons.add(largeAttack_ironman);
        ironman_Buttons.add(mediumAttack_ironman);
        ironman_Buttons.add(smallAttack_ironman);
    }

    private void setValues() {
        superman_PB = findViewById(R.id.main_PB_supermanPB);
        ironman_PB = findViewById(R.id.main_PB_ironmanPB);
        largeAttack_superman = findViewById(R.id.main_BTN_50pt_superman);
        mediumAttack_superman = findViewById(R.id.main_BTN_30pt_superman);
        smallAttack_superman = findViewById(R.id.main_BTN_10pt_superman);
        largeAttack_ironman = findViewById(R.id.main_BTN_50pt_ironman);
        mediumAttack_ironman = findViewById(R.id.main_BTN_30pt_ironman);
        smallAttack_ironman = findViewById(R.id.main_BTN_10pt_ironman);
        superman_image = findViewById(R.id.main_IV_superman);
        ironman_image = findViewById(R.id.main_IV_ironman);

        /* initialize value of progress bars to 100 */
        superman_PB.setProgress(100);
        ironman_PB.setProgress(100);
    }

    private void switchTurn() {
        if (turn == SUPERMAN_TURN) {
            for(Button btn: superman_Buttons)
                btn.setEnabled(false);
            for(Button btn: ironman_Buttons)
                    btn.setEnabled(true);
            turn = IRONMAN_TURN;
        }
        else {
            for(Button btn: ironman_Buttons)
                btn.setEnabled(false);
            for(Button btn: superman_Buttons)
                    btn.setEnabled(true);
            turn = SUPERMAN_TURN;
        }
    }
}