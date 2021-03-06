package com.barwen.daik.flyttguiden.screens.flytt;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
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
    Button mCancelButton;
    TextView mDateShowDate;
    TextView helpTextTop;
    TextView helpTextBottom;
    TextView resetHelpText;
    TextView activeDateShow;

    private NotificationHelper notificationHelper;


    int d;
    int m;
    int y;
    int moveBefore;
    int index;

    long millis;
    long date;
    long moveLong;

    long oneWeekS = 604800; // sekunder
    long twoWeekS = 1209600; // sekunder
    long oneMonthS = 2592000; // sekunder
    long twoMonthS = 5184000; // sekunder
    long threeMonthS = 7776000; // sekunder

    long notificationOneWeek;
    long notificationTwoWeek;
    long notificationOneMonth;
    long notificationTwoMonth;
    long notificationThreeMonth;

    long setNow1 = 1;
    long setNow2 = 2;
    long setNow3 = 3;
    long setNow4 = 4;
    long setNow5 = 5;

    long setNow1000 = 10;
    long setNow2000 = 20;
    long setNow3000 = 30;
    long setNow4000 = 40;
    long setNow5000 = 50;
    long setNow6000 = 60;
    long setNow7000 = 70;

    boolean timeChosen;
    boolean helpClicked;
    boolean isActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flytt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(178, 115, 233));
            window.setNavigationBarColor(Color.rgb(178, 115, 233));
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        mDatePickerButton = findViewById(R.id.date_picker_button);
        mHelpButton = findViewById(R.id.help_button);
        mCancelButton = findViewById(R.id.cancel_button);
        mDateShowDate = findViewById(R.id.date_show_date);
        helpTextTop = findViewById(R.id.textView17);
        helpTextBottom = findViewById(R.id.textView18);

        activeDateShow = findViewById(R.id.date_show_active);
        activeDateShow.setVisibility(View.INVISIBLE);
        resetHelpText = findViewById(R.id.textView52);
        resetHelpText.setVisibility(View.INVISIBLE);

        isActive = sharedPreferences.getBoolean("Active", false);

        if (isActive == true) {
            mHelpButton.setVisibility(View.INVISIBLE);
            mDatePickerButton.setVisibility(View.INVISIBLE);
            helpTextTop.setText("");
            helpTextBottom.setText("");
            resetHelpText.setVisibility(View.VISIBLE);
            helpClicked = true;
            activeDateShow.setVisibility(View.VISIBLE);
            activeDateShow.setText(sharedPreferences.getString("ChoosenDate", ""));
        } else {

        }


        millis = System.currentTimeMillis();

        //createChannels();


        notificationHelper = new NotificationHelper(this);
        dateButtonClicked();

        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timeChosen == true) {

                    final List<NotificationSHIT> list = new ArrayList<>();

                    final Calendar calendar = Calendar.getInstance();

                    if (notificationOneWeek <= 0) {
                        Log.d(TAG, " -- TIDEN, En vecka innan skickas direkt");
                        list.add(new NotificationSHIT(1, "Frosta av kyl och frys", "Även då du kanske har beställt flyttstädning måste grunderna vara fixade, avfrostning är ett måste!", setNow1 + setNow1000));
                        list.add(new NotificationSHIT(2, "Stäng av Spisen och stäng alla fönstren", "Se til att alla elsaker är avstängda och bostaden är stängd och låst", setNow1 + setNow2000));
                        list.add(new NotificationSHIT(3, "Läs av eventuella el och vattenmätare", "Om du har så är det bra att läsa av alla mätare för att veta när du slutade använda sakerna.", setNow1 + setNow3000));
                        list.add(new NotificationSHIT(4, "Packa ihop saker som hör till lägenheten på ett ställe", "t.ex. bredbandsutrustningen om din bostad har det inkluderat", setNow1 + setNow4000));
                        list.add(new NotificationSHIT(5, "Handla allt du kan behöva inför flytten", "Har du ”flytthandskar” och liknande saker som gör att flytten sliter på dig så lite som möjligt?", setNow1 + setNow5000));
                        list.add(new NotificationSHIT(6, "Packa de sista sakerna du klarar dig utan", "Packa allt utom de dagliga sakerna, ta fram kläder du kommer använda och packa resten!", setNow1 + setNow6000));
                    } else {
                        Log.d(TAG, " -- BOKAD EN VECKA");
                        list.add(new NotificationSHIT(1, "Frosta av kyl och frys", "Även då du kanske har beställt flyttstädning måste grunderna vara fixade, avfrostning är ett måste!", notificationOneWeek + 1));
                        list.add(new NotificationSHIT(2, "Stäng av Spisen och stäng alla fönstren", "Se til att alla elsaker är avstängda och bostaden är stängd och låst", notificationOneWeek + 1000));
                        list.add(new NotificationSHIT(3, "Läs av eventuella el och vattenmätare", "Om du har så är det bra att läsa av alla mätare för att veta när du slutade använda sakerna.", notificationOneWeek + 2000));
                        list.add(new NotificationSHIT(4, "Packa ihop saker som hör till lägenheten på ett ställe", "t.ex. bredbandsutrustningen om din bostad har det inkluderat", notificationOneWeek + 3000));
                        list.add(new NotificationSHIT(5, "Handla allt du kan behöva inför flytten", "Har du ”flytthandskar” och liknande saker som gör att flytten sliter på dig så lite som möjligt?", notificationOneWeek + 4000));
                        list.add(new NotificationSHIT(6, "Packa de sista sakerna du klarar dig utan", "Packa allt utom de dagliga sakerna, ta fram kläder du kommer använda och packa resten!", notificationOneWeek + 5000));
                    }
                    if (notificationTwoWeek <= 0) {
                        Log.d(TAG, " -- TIDEN, Två veckor innan skickas direkt");
                        list.add(new NotificationSHIT(7, "Har du meddelat eventuellt jobb om din nya adress?", "Alltid bra ditt jobb vet vart dina lönespecs ska skickas!", setNow2 + setNow1000));
                        list.add(new NotificationSHIT(8, "Dubbelkolla alla avbeställningar och beställningar", "Se till att all information är fixad och allt är beställd och avbeställt. En extra koll skadar inte!", setNow2 + setNow2000));
                        list.add(new NotificationSHIT(9, "Påminn kompisar om flytthjälp!", "I vanlig ordning glöms saker bort, påminn din flyttcrew att det är bara två veckor kvar!", setNow2 + setNow3000));
                    } else {
                        Log.d(TAG, " -- BOKAD TVÅ VECKOR");
                        list.add(new NotificationSHIT(7, "Har du meddelat eventuellt jobb om din nya adress?", "Alltid bra ditt jobb vet vart dina lönespecs ska skickas!", notificationTwoWeek + 1));
                        list.add(new NotificationSHIT(8, "Dubbelkolla alla avbeställningar och beställningar", "Se till att all information är fixad och allt är beställd och avbeställt. En extra koll skadar inte!", notificationTwoWeek + 1000));
                        list.add(new NotificationSHIT(9, "Påminn kompisar om flytthjälp!", "I vanlig ordning glöms saker bort, påminn din flyttcrew att det är bara två veckor kvar!", notificationTwoWeek + 2000));

                    }
                    if (notificationOneMonth <= 0) {
                        Log.d(TAG, " -- TIDEN, En månad innan skickas direkt");
                        list.add(new NotificationSHIT(10, "Fixa gamla bostaden till skicket du fick den i!", "Om du inte redan gjort det så återställ allt i tid, måla, spackla, häng tillbaka dörrar. Detta behöver besiktigas oftast.", setNow3 + setNow1000));
                        list.add(new NotificationSHIT(11, "Börja packa dina saker", "Alla behöver olika lång tid men vänta inte med att börja packa dina saker!", setNow3 + setNow2000));
                        list.add(new NotificationSHIT(12, "Släng saker du inte använt på en säsong!", "Om du inte använt din tröja alls föregående vinter t.ex. då behöver du inte den!", setNow3 + setNow3000));
                        list.add(new NotificationSHIT(13, "Beställ adressändring", "Se till att vara i tid för din adressändring så du inte missar någon viktig post, skaffa gärna eftersändning!", setNow3 + setNow4000));
                        list.add(new NotificationSHIT(14, "Prata med din gamla hyresvärd", "Gå igenom med värden om besiktning m.m. så du har datum att följa framöver.", setNow3 + setNow5000));
                        list.add(new NotificationSHIT(15, "Har du alla möbler?", "Har du säkert kollat på hela tiden men se till att du har dina basmöbler när du flyttar, gärna så att de levereras till nya adressen direkt.", setNow3 + setNow6000));
                        list.add(new NotificationSHIT(16, "Ät upp all mat i skafferiet och frysen!", "Vill du verkligen släpa med gammal mat? Ta och laga mat på allt du har, billigt och mindre slit, mat är tungt!", setNow3 + setNow7000));
                    } else {
                        Log.d(TAG, " -- BOKAD EN MÅNAD INNAN");
                        list.add(new NotificationSHIT(10, "Fixa gamla bostaden till skicket du fick den i!", "Om du inte redan gjort det så återställ allt i tid, måla, spackla, häng tillbaka dörrar. Detta behöver besiktigas oftast.", notificationOneMonth + 1));
                        list.add(new NotificationSHIT(11, "Börja packa dina saker", "Alla behöver olika lång tid men vänta inte med att börja packa dina saker!", notificationOneMonth + 1000));
                        list.add(new NotificationSHIT(12, "Släng saker du inte använt på en säsong!", "Om du inte använt din tröja alls föregående vinter t.ex. då behöver du inte den!", notificationOneMonth + 2000));
                        list.add(new NotificationSHIT(13, "Beställ adressändring", "Se till att vara i tid för din adressändring så du inte missar någon viktig post, skaffa gärna eftersändning!", notificationOneMonth + 3000));
                        list.add(new NotificationSHIT(14, "Prata med din gamla hyresvärd", "Gå igenom med värden om besiktning m.m. så du har datum att följa framöver.", notificationOneMonth + 4000));
                        list.add(new NotificationSHIT(15, "Har du alla möbler?", "Har du säkert kollat på hela tiden men se till att du har dina basmöbler när du flyttar, gärna så att de levereras till nya adressen direkt.", notificationOneMonth + 5000));
                        list.add(new NotificationSHIT(16, "Ät upp all mat i skafferiet och frysen!", "Vill du verkligen släpa med gammal mat? Ta och laga mat på allt du har, billigt och mindre slit, mat är tungt!", notificationOneMonth + 6000));
                    }
                    if (notificationTwoMonth <= 0) {
                        Log.d(TAG, " -- TIDEN, Två månader innan skickas direkt");
                        list.add(new NotificationSHIT(17, "Boka flyttfirma eller hyrbil", "Gör detta i tid, flytthjälp eller bara hyra bil behövs göras i god tid så du inte står utan mot slutet!", setNow4 + setNow1000));
                        list.add(new NotificationSHIT(18, "Skicka inbjudan till flytt-hjälps-fest", "Kompisarna som ska hjälpa behöver veta i tid dina planer, påminn dem gärna senare med ;)", setNow4 + setNow2000));
                        list.add(new NotificationSHIT(19, "Köp/Låna flyttkartonger och återställningsmaterial", "Dags att förbereda för packning. Införskaffa även färg och spackel om du behöver återställa något i bostaden,", setNow4 + setNow3000));
                        list.add(new NotificationSHIT(20, "Beställ ny El", "Då det faktiskt finns en hel del pengar att spara, kolla upp elavtalen i tid, EKO EL!", setNow4 + setNow4000));
                        list.add(new NotificationSHIT(21, "Beställ nytt bredband, TV, telefon", "Se till att kolla möjligheten till olika leverantörer och kolla bästa erbjudande.", setNow4 + setNow5000));
                        list.add(new NotificationSHIT(22, "Beställ ny hemförsäkring", "Se till att du kollar upp olika företag för bästa täckande försäkring.", setNow4 + setNow6000));
                        list.add(new NotificationSHIT(23, "Beställ övriga saker", "Beställ saker som garage, sopämtning, fjärrvärme och annat du kan behöva.", setNow4 + setNow7000));
                    } else {
                        Log.d(TAG, " -- BOKAD TVÅ MÅNADER");
                        list.add(new NotificationSHIT(17, "Boka flyttfirma eller hyrbil", "Gör detta i tid, flytthjälp eller bara hyra bil behövs göras i god tid så du inte står utan mot slutet!", notificationTwoMonth + 1));
                        list.add(new NotificationSHIT(18, "Skicka inbjudan till flytt-hjälps-fest", "Kompisarna som ska hjälpa behöver veta i tid dina planer, påminn dem gärna senare med ;)", notificationTwoMonth + 1000));
                        list.add(new NotificationSHIT(19, "Köp/Låna flyttkartonger och återställningsmaterial", "Dags att förbereda för packning. Införskaffa även färg och spackel om du behöver återställa något i bostaden,", notificationTwoMonth + 2000));
                        list.add(new NotificationSHIT(20, "Beställ ny El", "Då det faktiskt finns en hel del pengar att spara, kolla upp elavtalen i tid, EKO EL!", notificationTwoMonth + 3000));
                        list.add(new NotificationSHIT(21, "Beställ nytt bredband, TV, telefon", "Se till att kolla möjligheten till olika leverantörer och kolla bästa erbjudande.", notificationTwoMonth + 4000));
                        list.add(new NotificationSHIT(22, "Beställ ny hemförsäkring", "Se till att du kollar upp olika företag för bästa täckande försäkring.", notificationTwoMonth + 5000));
                        list.add(new NotificationSHIT(23, "Beställ övriga saker", "Beställ saker som garage, sopämtning, fjärrvärme och annat du kan behöva.", notificationTwoMonth + 6000));
                    }
                    if (notificationThreeMonth <= 0) {
                        Log.d(TAG, " -- TIDEN, Tre månader innan skickas direkt");
                        list.add(new NotificationSHIT(24, "Säg upp bredbandet, TV, Telefon", "Du har alltid en uppsägningstid, det brukar variera mellan 1 - 3 månader, därav viktigt att säga upp detta i tid. Även om du har 1 månad kan du säga upp till ett senare datum.", setNow5 + setNow1000));
                        list.add(new NotificationSHIT(25, "Säg upp elen", "Uppsägningen brukar vara mellan 1 - 3 månader, tänk på att ha kvar elen om du ska ha flyttstädningen eller så även efter du har flyttat ut!", setNow5 + setNow2000));
                        list.add(new NotificationSHIT(26, "Säg upp hemförsäkringen", "Uppsägningstiden brukar vara 1 månad", setNow5 + setNow3000));
                        list.add(new NotificationSHIT(27, "Säg upp eventuella andra saker", "Beroende på om du har sakerna så säg upp fjärrvärme, sophämtning, garage + + +", setNow5 + setNow4000));
                        list.add(new NotificationSHIT(28, "Börja sälja saker du inte vill ha med dig", "Blocket och Tradera t.ex. är bra verktyg att använda i tid. Bli av med saker!", setNow5 + setNow5000));
                    } else {
                        Log.d(TAG, " -- BOKAD TRE MÅNADER");
                        list.add(new NotificationSHIT(24, "Säg upp bredbandet, TV, Telefon", "Du har alltid en uppsägningstid, det brukar variera mellan 1 - 3 månader, därav viktigt att säga upp detta i tid. Även om du har 1 månad kan du säga upp till ett senare datum.", notificationThreeMonth + 1));
                        list.add(new NotificationSHIT(25, "Säg upp elen", "Uppsägningen brukar vara mellan 1 - 3 månader, tänk på att ha kvar elen om du ska ha flyttstädningen eller så även efter du har flyttat ut!", notificationThreeMonth + 1000));
                        list.add(new NotificationSHIT(26, "Säg upp hemförsäkringen", "Uppsägningstiden brukar vara 1 månad", notificationThreeMonth + 2000));
                        list.add(new NotificationSHIT(27, "Säg upp eventuella andra saker", "Beroende på om du har sakerna så säg upp fjärrvärme, sophämtning, garage + + +", notificationThreeMonth + 3000));
                        list.add(new NotificationSHIT(28, "Börja sälja saker du inte vill ha med dig", "Blocket och Tradera t.ex. är bra verktyg att använda i tid. Bli av med saker!", notificationThreeMonth + 4000));
                    }

                    index = 0;

                    for (NotificationSHIT notificationSHIT : list) {

                        index++;

                        long notiTime = notificationSHIT.getTime();
                        long notiTimeTotal = notiTime * 1000;

                        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        alarmIntent.putExtra("id", notificationSHIT.getId());
                        alarmIntent.putExtra("title", notificationSHIT.getTitle());
                        alarmIntent.putExtra("longText", notificationSHIT.getLongText());

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(), index, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        //Log.d(TAG, "calendar.getTimeInMillis: " + calendar.getTimeInMillis());

                        //Log.d(TAG, "calendar.getTimeInMillis + notiTimeTotal + id : " + (calendar.getTimeInMillis() + notiTimeTotal) + " " + notificationSHIT.getId());

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis() + notiTimeTotal,
                                pendingIntent
                        );

                    }

                    mHelpButton.setVisibility(View.INVISIBLE);
                    mDatePickerButton.setVisibility(View.INVISIBLE);
                    helpTextTop.setText("");
                    helpTextBottom.setText("");
                    resetHelpText.setVisibility(View.VISIBLE);
                    helpClicked = true;

                    AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
                    animation1.setDuration(800);
                    animation1.setStartOffset(1);
                    animation1.setFillAfter(true);
                    activeDateShow.startAnimation(animation1);

                    activeDateShow.setText(mDateShowDate.getText().toString());

                    editor.putBoolean("Active", true);
                    editor.putString("ChoosenDate", mDateShowDate.getText().toString());
                    editor.apply();

                    mDateShowDate.setText("");

                } else {
                    Toast.makeText(FlyttActivity.this, R.string.timeChoose,
                            Toast.LENGTH_LONG).show();
                }


            }


        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helpClicked == true) {
                    mHelpButton.setVisibility(View.VISIBLE);
                    mDatePickerButton.setVisibility(View.VISIBLE);
                    helpTextTop.setText(R.string.guiden_h3);
                    helpTextBottom.setText(R.string.guiden_h2_2);
                    helpClicked = false;
                    timeChosen = false;
                    mDateShowDate.setText("");
                    activeDateShow.setText("");
                    resetHelpText.setVisibility(View.INVISIBLE);
                    editor.putBoolean("Active", false);
                    editor.apply();


                    for(int i=1; i<=index; i++){
                        Log.d(TAG, "onClick: deleted " + i);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent myIntent = new Intent(getApplicationContext(),
                                AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                getApplicationContext(), i, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.cancel(pendingIntent);

                    }

                } else {

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

                        i1 += 1;

                        Calendar calle = Calendar.getInstance();
                        calle.set(i, moveBefore, i2); // tiden tills flytt

                        date = calle.getTime().getTime();
                        moveLong = (date - millis) / 1000;

                        if (moveLong < 0) {
                            Toast.makeText(FlyttActivity.this, getString(R.string.guiden_toast_wrong_date), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            mDateShowDate.setText(i + "-" + i1 + "-" + i2);
                        }

                        notificationOneWeek = moveLong - oneWeekS;
                        notificationTwoWeek = moveLong - twoWeekS;
                        notificationOneMonth = moveLong - oneMonthS;
                        notificationTwoMonth = moveLong - twoMonthS;
                        notificationThreeMonth = moveLong - threeMonthS;

                        timeChosen = true;

                    }
                }, y, m, d);
                pickerDialog.show();

            }
        });
    }

    /*public void createChannels() {
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
    }*/


}
