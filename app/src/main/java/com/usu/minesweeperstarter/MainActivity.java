package com.usu.minesweeperstarter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(2,10,2,10);

        mainLayout.setBackgroundColor(getResources().getColor(R.color.secondary));

        TextView title = new TextView(this);
        title.setTextSize(50);
        title.setText("Mine Sweeper");
        title.setGravity(1);

        TextView difficultyQ = new TextView(this);
        difficultyQ.setTextSize(20);
        difficultyQ.setText("Select a difficulty");
        difficultyQ.setGravity(1);


        // Easy button
        AppCompatButton easy = new AppCompatButton(this);
        easy.setLayoutParams(params);
        easy.setText("Easy");
        easy.setPadding(24, 30, 24, 30);
        easy.setBackgroundColor(getResources().getColor(R.color.primary));
        easy.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("difficulty", "easy");
            startActivity(intent);
        });

        // Intermediate button
        AppCompatButton inter = new AppCompatButton(this);
        inter.setLayoutParams(params);
        inter.setText("Intermediate");
        inter.setPadding(24, 30, 24, 30);
        inter.setBackgroundColor(getResources().getColor(R.color.primary));
        inter.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("difficulty", "inter");
            startActivity(intent);
        });

        // Hard button
        AppCompatButton hard = new AppCompatButton(this);
        hard.setLayoutParams(params);
        hard.setText("Expert");
        hard.setPadding(24, 30, 24, 30);
        hard.setBackgroundColor(getResources().getColor(R.color.primary));
        hard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("difficulty", "exp");
            startActivity(intent);
        });

        // Place buttons onto the screen
        mainLayout.addView(title);
        mainLayout.addView(difficultyQ);
        mainLayout.addView(easy);
        mainLayout.getPaddingTop();
        mainLayout.addView(inter);
        mainLayout.getPaddingTop();
        mainLayout.addView(hard);

        setContentView(mainLayout);

    }
}