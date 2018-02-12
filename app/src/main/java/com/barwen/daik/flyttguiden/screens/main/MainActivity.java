package com.barwen.daik.flyttguiden.screens.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.barwen.daik.flyttguiden.R;
import com.barwen.daik.flyttguiden.screens.flytt.FlyttActivity;
import com.barwen.daik.flyttguiden.screens.info.InfoActivity;
import com.barwen.daik.flyttguiden.screens.tips.TipsActivity;
import com.barwen.daik.flyttguiden.screens.todo.TodoActivity;

public class MainActivity extends AppCompatActivity {

    private Button flyttBtn;
    private Button todoBtn;
    private Button tipsBtn;
    private FloatingActionButton infoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(178, 115, 233));
            window.setNavigationBarColor(Color.rgb(99, 190, 154));
        }

        flyttBtn = findViewById(R.id.flytt_button);
        todoBtn = findViewById(R.id.todo_button);
        tipsBtn = findViewById(R.id.tips_button);
        infoBtn = findViewById(R.id.info_floating_button);

        flyttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), FlyttActivity.class);
                startActivity(myIntent);
            }
        });

        todoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), TodoActivity.class);
                startActivity(myIntent);
            }
        });

        tipsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), TipsActivity.class);
                startActivity(myIntent);
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), InfoActivity.class);
                startActivity(myIntent);
            }
        });

    }


}
