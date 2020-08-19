package com.example.wargame_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    private static final int SUPERMAN_TURN = 1;
    private static final int IRONMAN_TURN = 2;
    private static final int LARGE_ATTACK_POINTS = 50;
    private static final int MEDIUM_ATTACK_POINTS = 30;
    private static final int SMALL_ATTACK_POINTS = 10;

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
                    decreasePB(ironman_PB, SMALL_ATTACK_POINTS);
                    break;
                case R.id.main_BTN_30pt_superman:
                    decreasePB(ironman_PB, MEDIUM_ATTACK_POINTS);
                    break;
                case R.id.main_BTN_50pt_superman:
                    decreasePB(ironman_PB, LARGE_ATTACK_POINTS);
                    break;
            }
        } else if (turn == IRONMAN_TURN) {
            switch (view.getId()) {
                case R.id.main_BTN_10pt_ironman:
                    decreasePB(superman_PB, SMALL_ATTACK_POINTS);
                    break;
                case R.id.main_BTN_30pt_ironman:
                    decreasePB(superman_PB, MEDIUM_ATTACK_POINTS);
                    break;
                case R.id.main_BTN_50pt_ironman:
                    decreasePB(superman_PB, LARGE_ATTACK_POINTS);
                    break;
            }
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
        if(superman_PB.getProgress() == 0 || ironman_PB.getProgress() == 0) {
            disableButtons();
            Toast.makeText(MainActivity.this, "game done",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private void disableButtons() {
        for(Button btn: superman_Buttons)
            btn.setEnabled(false);
        for(Button btn: ironman_Buttons)
            btn.setEnabled(false);
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

        /* set images using glide library*/
        setImages();

        /* initialize value of progress bars to 100 */
        superman_PB.setProgress(100);
        ironman_PB.setProgress(100);
    }

    private void setImages() {
        Glide.with(MainActivity.this)
                .load("")
                .placeholder(R.drawable.superman)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(superman_image);

        Glide.with(MainActivity.this)
                .load("")
                .placeholder(R.drawable.iron)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ironman_image);
    }

    private void switchTurn() {
        ArrayList<Button> switchTo;
        ArrayList<Button> current;
        if (turn == SUPERMAN_TURN) {
            current = superman_Buttons;
            switchTo = ironman_Buttons;
        }
        else {
            current = ironman_Buttons;
            switchTo = superman_Buttons;
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
        if (turn == SUPERMAN_TURN)
            turn = IRONMAN_TURN;
        else
            turn = SUPERMAN_TURN;
    }
}