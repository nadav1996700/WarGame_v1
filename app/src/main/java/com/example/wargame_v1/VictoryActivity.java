package com.example.wargame_v1;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VictoryActivity extends AppCompatActivity {

    private TextView victory;
    private Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        // initialize variables
        setValues();

        // get victory name and set TextView
        Intent input = getIntent();
        String victor = input.getStringExtra(MainActivity.EXTRA_VICTORY);
        victory.setText(victor + " WON!");

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setValues() {
        newGame = findViewById(R.id.victory_BTN_newGame);
        victory = findViewById(R.id.victory_TV_title);
    }
}