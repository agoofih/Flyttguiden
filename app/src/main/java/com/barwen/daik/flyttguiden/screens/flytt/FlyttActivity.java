package com.barwen.daik.flyttguiden.screens.flytt;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FlyttActivity extends AppCompatActivity {
    private static final String TAG = "FlyttActivity";

    Button mDatePickerButton;
    Button mHelpButton;
    TextView mDateShowDate;

    private NotificationHelper notificationHelper;


    private static final int notification_one = 101;
    private static final int notification_two = 102;

    int d;
    int m;
    int y;
    int moveBefore;

    long millis;
    long date;
    long moveLong;

    double oneWeekSec = 604800.0; // sekunder
    double twoWeekSex = 1209600.0; // sekunder
    double oneMonthSec = 2629744.0; // sekunder
    double twoMonthSec = 5259488.0; // sekunder
    double threeMonthSec = 7802791.0; // sekunder

    double notificationOneWeek;
    double notificationTwoWeek;
    double notificationOneMonth;
    double notificationTwoMonth;
    double notificationThreeMonth;


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



        millis = System.currentTimeMillis();

        //notificationFactory = new NotificationFactory(this );

        createChannels();


        notificationHelper = new NotificationHelper(this);
        dateButtonClicked();

        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: awdawdwad");

                final List<NotificationSHIT> list = new ArrayList<>();

                final Calendar calendar = Calendar.getInstance();

                list.add(new NotificationSHIT(1, "HEJ", "HEJARE", 1000 * 5));

                list.add(new NotificationSHIT(2, "HEJ2", "HEJARE2", 1000 * 10));


                int index = 0;

                for (NotificationSHIT notificationSHIT : list) {

                    index ++;

                    Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    alarmIntent.putExtra("id", notificationSHIT.getId());
                    alarmIntent.putExtra("title", notificationSHIT.getTitle());
                    alarmIntent.putExtra("longText", notificationSHIT.getLongText());

                    Log.d(TAG, "onClick: " + alarmIntent);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(), index, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis() + notificationSHIT.getTime(),
                            pendingIntent
                    );
                    Log.d(TAG, "onClick: DONE");
                }
            }
        });



    }

    public void dateButtonClicked() {
        mDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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

   /* public void helpButtonClicked() {
        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.add(Calendar.SECOND, 1);
                setAlarm(view.getContext(), gregorianCalendar);


            }
        });
    }*/

  public void createChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        "CHANNEL_ONE_ID",
                        "CHANNEL_ONE_NAME",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setShowBadge(true);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationManager.createNotificationChannel(notificationChannel);

                NotificationChannel notificationChannel2 = new NotificationChannel(
                        "CHANNEL_TWO_ID",
                        "CHANNEL_TWO_NAME",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                notificationChannel2.enableLights(false);
                notificationChannel2.enableVibration(true);
                notificationChannel2.setLightColor(Color.RED);
                notificationChannel2.setShowBadge(false);
                notificationManager.createNotificationChannel(notificationChannel2);
            }
        }
    }

    private void sendNotification(int id, Notification.Builder notification) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(id, notification.build());
        }
    }

    private Notification.Builder showExpandableNotification() {
        // Implement a expandable notification
        String longText = "As he crossed toward the pharmacy at the corner he involuntarily turned his head because of a burst of light that had ricocheted from his temple, and saw, with that quick smile with which we greet a rainbow or a rose, a blindingly white parallelogram of sky being unloaded from the van—a dresser with mirrors across which, as across a cinema screen, passed a flawlessly clear reflection of boughs sliding and swaying not arboreally, but with a human vacillation, produced by the nature of those who were carrying this sky, these boughs, this gliding façade.";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return new Notification.Builder(this, "CHANNEL_TWO_ID")
                    .setSmallIcon(R.drawable.ic_stat_notificon)
                    .setContentTitle("Wow! I got a title!")
                    .setContentText("Subject")
                    .setStyle(new Notification.BigTextStyle().bigText(longText));
        } else {
            return new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat_notificon)
                    .setContentTitle("Wow! I got a title!")
                    .setContentText("Subject")
                    .setStyle(new Notification.BigTextStyle().bigText(longText));
        }
    }


}
