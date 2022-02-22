package com.usu.minesweeperstarter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.usu.minesweeperstarter.GameView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String gameMode = intent.getStringExtra("difficulty");
        Log.d("Game Mode is:", gameMode);
        GameView gameView = new GameView(this, gameMode);
        setContentView(gameView);
    }
}
