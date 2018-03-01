package com.alexandravalkova.hangmangame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newGameButton;
    Button dictionaryButton;
    View.OnClickListener onNewGameButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(gameIntent);
        }
    };
    View.OnClickListener onDictionaryButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent dictionaryIntent = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(dictionaryIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameButton = (Button) findViewById(R.id.newGameButton);
        dictionaryButton = (Button) findViewById(R.id.dictionaryButton);

        newGameButton.setOnClickListener(onNewGameButtonClicked);
        dictionaryButton.setOnClickListener(onDictionaryButtonClicked);
    }
}
