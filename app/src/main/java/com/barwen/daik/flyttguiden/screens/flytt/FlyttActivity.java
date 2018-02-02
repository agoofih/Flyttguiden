package com.barwen.daik.flyttguiden.screens.flytt;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.barwen.daik.flyttguiden.R;

import java.util.Calendar;
import java.util.Date;


public class FlyttActivity extends AppCompatActivity {
    private static final String TAG = "FlyttActivity";

    Button mDatePickerButton;
    Button mHelpButton;
    TextView mDateShowDate;

    int d;
    int m;
    int y;
    int moveBefore;

    long millis;
    long date;
    long moveLong;

    String poop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flytt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(178,115,233));
            window.setNavigationBarColor(Color.rgb(178,115,233));
        }

        mDatePickerButton = findViewById(R.id.date_picker_button);
        mHelpButton = findViewById(R.id.help_button);
        mDateShowDate = findViewById(R.id.date_show_date);

        dateButtonClicked();
        helpButtonClicked();

        millis = System.currentTimeMillis();

    }

    public void dateButtonClicked() {
        mDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                d = calendar.get((Calendar.DAY_OF_MONTH));
                m = calendar.get(Calendar.MONTH);
                y = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(FlyttActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        moveBefore = i1;

                        i1+=1;
                        Log.d(TAG, " - Flytten sker " + i + " " + i1 + " " + i2); // info att skriva ut till användaren
                        Calendar calle = Calendar.getInstance();
                        calle.set(i,moveBefore,i2); // tiden tills flytt

                        date = calle.getTime().getTime();
                        moveLong = date - millis;
                        Log.d(TAG, "onDateSet: MOVELONG" + moveLong);
                        if (moveLong < 0){
                            Toast.makeText(FlyttActivity.this, getString(R.string.guiden_toast_wrong_date), Toast.LENGTH_SHORT).show();
                        }else{
                            mDateShowDate.setText(i + "-" + i1 + "-" + i2);
                        }



                    }
                }, y, m, d);
                pickerDialog.show();
            }
        });



    }

    public void helpButtonClicked() {
        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, " Help klickad");
            }
        });
    }


}
