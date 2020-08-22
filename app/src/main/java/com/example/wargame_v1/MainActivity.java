package com.example.wargame_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

//glide
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PLAYER1_TURN = 1;
    private static final int PLAYER2_TURN = 2;
    private static final int LARGE_ATTACK_POINTS = 50;
    private static final int MEDIUM_ATTACK_POINTS = 30;
    private static final int SMALL_ATTACK_POINTS = 10;

    private ProgressBar player1_PB;
    private ProgressBar player2_PB;
    private Button largeAttack_player1;
    private Button mediumAttack_player1;
    private Button smallAttack_player1;
    private Button largeAttack_player2;
    private Button mediumAttack_player2;
    private Button smallAttack_player2;
    private ArrayList<Button> player1_Buttons;
    private ArrayList<Button> player2_Buttons;
    private int turn = PLAYER1_TURN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize values */
        setValues();

        /* add buttons to lists */
        initialize_player1_list();
        initialize_player2_list();

        /* activate buttons */
        activateButtons(player1_Buttons);
        activateButtons(player2_Buttons);

        /* disable ironman buttons -> superman start */
        for(Button btn: player2_Buttons)
            btn.setEnabled(false);
    }

    private void activateButtons(ArrayList<Button> buttons) {

        for(Button btn: buttons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* update progress bar after attack */
                    setProgressBar(view);
                    /* if game over -> switch Activity, else -> switch turn */
                    if(!gameOver())
                        switchTurn();
                }
            });
        }
    }

    /* update life bar */
    private void setProgressBar(View view) {
        switch (view.getId()) {
            case R.id.main_BTN_10pt_player1:
                decreasePB(player2_PB, SMALL_ATTACK_POINTS);
                break;
            case R.id.main_BTN_10pt_player2:
                decreasePB(player1_PB, SMALL_ATTACK_POINTS);
                break;
            case R.id.main_BTN_30pt_player1:
                decreasePB(player2_PB, MEDIUM_ATTACK_POINTS);
                break;
            case R.id.main_BTN_30pt_player2:
                decreasePB(player1_PB, MEDIUM_ATTACK_POINTS);
                break;
            case R.id.main_BTN_50pt_player1:
                decreasePB(player2_PB, LARGE_ATTACK_POINTS);
                break;
            case R.id.main_BTN_50pt_player2:
                decreasePB(player1_PB, LARGE_ATTACK_POINTS);
                break;
        }

    }

    private void decreasePB(ProgressBar pb, int points) {
        pb.setProgress(pb.getProgress() - points);
        if(pb.getProgress() < 40) {
            pb.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.red_progress_bar));
        }
    }

    /* if game over -> send victory name to Victory Activity and switch activity */
    private boolean gameOver() {
        if(player1_PB.getProgress() == 0 || player2_PB.getProgress() == 0) {
            disableButtons();
            Toast.makeText(MainActivity.this, "game done",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private void disableButtons() {
        for(Button btn: player1_Buttons)
            btn.setEnabled(false);
        for(Button btn: player2_Buttons)
            btn.setEnabled(false);
    }

    /* add buttons of player_1 to list */
    private void initialize_player1_list() {
        player1_Buttons = new ArrayList<>();
        player1_Buttons.add(smallAttack_player1);
        player1_Buttons.add(mediumAttack_player1);
        player1_Buttons.add(largeAttack_player1);
    }

    /* add buttons of player_2 to list */
    private void initialize_player2_list() {
        player2_Buttons = new ArrayList<>();
        player2_Buttons.add(smallAttack_player2);
        player2_Buttons.add(mediumAttack_player2);
        player2_Buttons.add(largeAttack_player2);
    }

    private void setValues() {
        player1_PB = findViewById(R.id.main_PB_player1);
        player2_PB = findViewById(R.id.main_PB_player2);
        largeAttack_player1 = findViewById(R.id.main_BTN_50pt_player1);
        mediumAttack_player1 = findViewById(R.id.main_BTN_30pt_player1);
        smallAttack_player1 = findViewById(R.id.main_BTN_10pt_player1);
        largeAttack_player2 = findViewById(R.id.main_BTN_50pt_player2);
        mediumAttack_player2 = findViewById(R.id.main_BTN_30pt_player2);
        smallAttack_player2 = findViewById(R.id.main_BTN_10pt_player2);
        ImageView player1_image = findViewById(R.id.main_IV_player1);
        ImageView player2_image = findViewById(R.id.main_IV_player2);

        /* set images using glide library*/
        setImage(player1_image, ContextCompat.getDrawable(this, R.drawable.superman));
        setImage(player2_image, ContextCompat.getDrawable(this, R.drawable.iron));

        /* initialize value of progress bars to 100 */
        player1_PB.setProgress(100);
        player2_PB.setProgress(100);
    }

    /* set images using glide library*/
    private void setImage(ImageView iv, Drawable photo) {
        Glide.with(MainActivity.this)
                .load(photo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);
    }

    private void switchTurn() {
        ArrayList<Button> switchTo;
        ArrayList<Button> current;
        if (turn == PLAYER1_TURN) {
            current = player1_Buttons;
            switchTo = player2_Buttons;
        }
        else {
            current = player2_Buttons;
            switchTo = player1_Buttons;
        }
        makeSwitch(current, switchTo);
    }

    private void makeSwitch(ArrayList<Button> current, ArrayList<Button> switchTo) {
        for(Button btn: current)
            btn.setEnabled(false);
        for(Button btn: switchTo)
            btn.setEnabled(true);
        changeTurn();
    }

    private void changeTurn() {
        if (turn == PLAYER1_TURN)
            turn = PLAYER2_TURN;
        else
            turn = PLAYER1_TURN;
    }
}