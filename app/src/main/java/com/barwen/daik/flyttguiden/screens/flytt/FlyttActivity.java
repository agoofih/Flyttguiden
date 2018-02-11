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

    private NotificationHelper notificationHelper;


    private static final int notification_one = 101;
    private static final int notification_two = 102;

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

    long setNow1 = 600000;
    long setNow2 = 610000;
    long setNow3 = 620000;
    long setNow4 = 630000;
    long setNow5 = 640000;

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



        //editor.putString("Name","Harneet");
        //editor.apply();

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //String name = preferences.getString("Name", "");

        resetHelpText = findViewById(R.id.textView52);
        resetHelpText.setVisibility(View.INVISIBLE);


        mDatePickerButton = findViewById(R.id.date_picker_button);
        mHelpButton = findViewById(R.id.help_button);
        mCancelButton = findViewById(R.id.cancel_button);
        mDateShowDate = findViewById(R.id.date_show_date);
        helpTextTop = findViewById(R.id.textView17);
        helpTextBottom = findViewById(R.id.textView18);

        isActive = sharedPreferences.getBoolean("Active", false);

        if (isActive == true){
            mHelpButton.setVisibility(View.INVISIBLE);
            mDatePickerButton.setVisibility(View.INVISIBLE);
            helpTextTop.setText("");
            helpTextBottom.setText("");
            resetHelpText.setVisibility(View.VISIBLE);
            helpClicked = true;
            mDateShowDate.setText(sharedPreferences.getString("ChoosenDate", ""));
        }else{

        }


        millis = System.currentTimeMillis();
        //Log.d(TAG, "TIDEN NU: MILLIS: " + millis);

        //notificationFactory = new NotificationFactory(this );

        createChannels();


        notificationHelper = new NotificationHelper(this);
        dateButtonClicked();

        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timeChosen == true){

                    final List<NotificationSHIT> list = new ArrayList<>();

                    final Calendar calendar = Calendar.getInstance();

                    if (notificationOneWeek <= 0) {
                        Log.d(TAG, " -- TIDEN, En vecka innan skickas direkt");
                        list.add(new NotificationSHIT(1, "Frosta av kyl och frys", "Även då du kanske har beställt flyttstädning måste grunderna vara fixade, avfrostning är ett måste!", setNow1 + 1));
                        list.add(new NotificationSHIT(2, "Stäng av Spisen och stäng alla fönstren", "Se til att alla elsaker är avstängda och bostaden är stängd och låst", setNow1 + 1000));
                        list.add(new NotificationSHIT(3, "Läs av eventuella el och vattenmätare", "Om du har så är det bra att läsa av alla mätare för att veta när du slutade använda sakerna.", setNow1 + 2000));
                        list.add(new NotificationSHIT(4, "Packa ihop saker som hör till lägenheten på ett ställe", "t.ex. bredbandsutrustningen om din bostad har det inkluderat", setNow1 + 3000));
                        list.add(new NotificationSHIT(5, "Handla allt du kan behöva inför flytten", "Har du ”flytthandskar” och liknande saker som gör att flytten sliter på dig så lite som möjligt?", setNow1 + 4000));
                        list.add(new NotificationSHIT(6, "Packa de sista sakerna du klarar dig utan", "Packa allt utom de dagliga sakerna, ta fram kläder du kommer använda och packa resten!", setNow1 + 5000));
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
                        list.add(new NotificationSHIT(7, "Har du meddelat eventuellt jobb om din nya adress?", "Alltid bra ditt jobb vet vart dina lönespecs ska skickas!", setNow2 + 1));
                        list.add(new NotificationSHIT(8, "Dubbelkolla alla avbeställningar och beställningar", "Se till att all information är fixad och allt är beställd och avbeställt. En extra koll skadar inte!", setNow2 + 1000));
                        list.add(new NotificationSHIT(9, "Påminn kompisar om flytthjälp!", "I vanlig ordning glöms saker bort, påminn din flyttcrew att det är bara två veckor kvar!", setNow2 + 2000));
                    } else {
                        Log.d(TAG, " -- BOKAD TVÅ VECKOR");
                        list.add(new NotificationSHIT(7, "Har du meddelat eventuellt jobb om din nya adress?", "Alltid bra ditt jobb vet vart dina lönespecs ska skickas!", notificationTwoWeek + 1));
                        list.add(new NotificationSHIT(8, "Dubbelkolla alla avbeställningar och beställningar", "Se till att all information är fixad och allt är beställd och avbeställt. En extra koll skadar inte!", notificationTwoWeek + 1000));
                        list.add(new NotificationSHIT(9, "Påminn kompisar om flytthjälp!", "I vanlig ordning glöms saker bort, påminn din flyttcrew att det är bara två veckor kvar!", notificationTwoWeek + 2000));

                    }
                    if (notificationOneMonth <= 0) {
                        Log.d(TAG, " -- TIDEN, En månad innan skickas direkt");
                        list.add(new NotificationSHIT(10, "Fixa gamla bostaden till skicket du fick den i!", "Om du inte redan gjort det så återställ allt i tid, måla, spackla, häng tillbaka dörrar. Detta behöver besiktigas oftast.", setNow3 + 1));
                        list.add(new NotificationSHIT(11, "Börja packa dina saker", "Alla behöver olika lång tid men vänta inte med att börja packa dina saker!", setNow3 + 1000));
                        list.add(new NotificationSHIT(12, "Släng saker du inte använt på en säsong!", "Om du inte använt din tröja alls föregående vinter t.ex. då behöver du inte den!", setNow3 + 2000));
                        list.add(new NotificationSHIT(13, "Beställ adressändring", "Se till att vara i tid för din adressändring så du inte missar någon viktig post, skaffa gärna eftersändning!", setNow3 + 3000));
                        list.add(new NotificationSHIT(14, "Prata med din gamla hyresvärd", "Gå igenom med värden om besiktning m.m. så du har datum att följa framöver.", setNow3 + 4000));
                        list.add(new NotificationSHIT(15, "Har du alla möbler?", "Har du säkert kollat på hela tiden men se till att du har dina basmöbler när du flyttar, gärna så att de levereras till nya adressen direkt.", setNow3 + 5000));
                        list.add(new NotificationSHIT(16, "Ät upp all mat i skafferiet och frysen!", "Vill du verkligen släpa med gammal mat? Ta och laga mat på allt du har, billigt och mindre slit, mat är tungt!", setNow3 + 6000));
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
                        list.add(new NotificationSHIT(17, "Boka flyttfirma eller hyrbil", "Gör detta i tid, flytthjälp eller bara hyra bil behövs göras i god tid så du inte står utan mot slutet!", setNow4 + 1));
                        list.add(new NotificationSHIT(18, "Skicka inbjudan till flytt-hjälps-fest", "Kompisarna som ska hjälpa behöver veta i tid dina planer, påminn dem gärna senare med ;)", setNow4 + 1000));
                        list.add(new NotificationSHIT(19, "Köp/Låna flyttkartonger och återställningsmaterial", "Dags att förbereda för packning. Införskaffa även färg och spackel om du behöver återställa något i bostaden,", setNow4 + 2000));
                        list.add(new NotificationSHIT(20, "Beställ ny El", "Då det faktiskt finns en hel del pengar att spara, kolla upp elavtalen i tid, EKO EL!", setNow4 + 3000));
                        list.add(new NotificationSHIT(21, "Beställ nytt bredband, TV, telefon", "Se till att kolla möjligheten till olika leverantörer och kolla bästa erbjudande.", setNow4 + 4000));
                        list.add(new NotificationSHIT(22, "Beställ ny hemförsäkring", "Se till att du kollar upp olika företag för bästa täckande försäkring.", setNow4 + 5000));
                        list.add(new NotificationSHIT(23, "Beställ övriga saker", "Beställ saker som garage, sopämtning, fjärrvärme och annat du kan behöva.", setNow4 + 6000));
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
                        list.add(new NotificationSHIT(24, "Säg upp bredbandet, TV, Telefon", "Du har alltid en uppsägningstid, det brukar variera mellan 1 - 3 månader, därav viktigt att säga upp detta i tid. Även om du har 1 månad kan du säga upp till ett senare datum.", setNow5 + 1));
                        list.add(new NotificationSHIT(25, "Säg upp elen", "Uppsägningen brukar vara mellan 1 - 3 månader, tänk på att ha kvar elen om du ska ha flyttstädningen eller så även efter du har flyttat ut!", setNow5 + 1000));
                        list.add(new NotificationSHIT(26, "Säg upp hemförsäkringen", "Uppsägningstiden brukar vara 1 månad", setNow5 + 2000));
                        list.add(new NotificationSHIT(27, "Säg upp eventuella andra saker", "Beroende på om du har sakerna så säg upp fjärrvärme, sophämtning, garage + + +", setNow5 + 3000));
                        list.add(new NotificationSHIT(28, "Börja sälja saker du inte vill ha med dig", "Blocket och Tradera t.ex. är bra verktyg att använda i tid. Bli av med saker!", setNow5 + 4000));
                    } else {
                        Log.d(TAG, " -- BOKAD TRE MÅNADER");
                        list.add(new NotificationSHIT(24, "Säg upp bredbandet, TV, Telefon", "Du har alltid en uppsägningstid, det brukar variera mellan 1 - 3 månader, därav viktigt att säga upp detta i tid. Även om du har 1 månad kan du säga upp till ett senare datum.", notificationThreeMonth + 1));
                        list.add(new NotificationSHIT(25, "Säg upp elen", "Uppsägningen brukar vara mellan 1 - 3 månader, tänk på att ha kvar elen om du ska ha flyttstädningen eller så även efter du har flyttat ut!", notificationThreeMonth + 1000));
                        list.add(new NotificationSHIT(26, "Säg upp hemförsäkringen", "Uppsägningstiden brukar vara 1 månad", notificationThreeMonth + 2000));
                        list.add(new NotificationSHIT(27, "Säg upp eventuella andra saker", "Beroende på om du har sakerna så säg upp fjärrvärme, sophämtning, garage + + +", notificationThreeMonth + 3000));
                        list.add(new NotificationSHIT(28, "Börja sälja saker du inte vill ha med dig", "Blocket och Tradera t.ex. är bra verktyg att använda i tid. Bli av med saker!", notificationThreeMonth + 4000));
                    }


                /*list.add(new NotificationSHIT(3, "HEJ", "HEJARE", 1000 * 5));

                list.add(new NotificationSHIT(2, "HEJ2", "HEJARE2", 1000 * 10));*/

                        index = 0;

                    for (NotificationSHIT notificationSHIT : list) {

                        index++;

                        long notiTime = notificationSHIT.getTime();
                        long notiTimeTotal = notiTime * 1000;

                        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        alarmIntent.putExtra("id", notificationSHIT.getId());
                        alarmIntent.putExtra("title", notificationSHIT.getTitle());
                        alarmIntent.putExtra("longText", notificationSHIT.getLongText());

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(),1 , alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis() + notiTimeTotal,
                                pendingIntent
                        );

                       // Log.d(TAG, "notificationShit calendar: " + calendar.getTimeInMillis() + " get time " + notiTimeTotal);

                    }


                    mHelpButton.setVisibility(View.INVISIBLE);
                    mDatePickerButton.setVisibility(View.INVISIBLE);
                    helpTextTop.setText("");
                    helpTextBottom.setText("");
                    resetHelpText.setVisibility(View.VISIBLE);
                    helpClicked = true;

                    editor.putBoolean("Active", true);
                    editor.putString("ChoosenDate", mDateShowDate.getText().toString());
                    editor.apply();

                }else{
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
                    resetHelpText.setVisibility(View.INVISIBLE);
                    editor.putBoolean("Active", false);
                    editor.apply();

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(getApplicationContext(),
                            AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), 1, myIntent, 0);

                    alarmManager.cancel(pendingIntent);

                }else{

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
                        //Log.d(TAG, " - Flytten sker " + i + " " + i1 + " " + i2); // info att skriva ut till användaren
                        Calendar calle = Calendar.getInstance();
                        calle.set(i, moveBefore, i2); // tiden tills flytt

                        date = calle.getTime().getTime();
                        moveLong = (date - millis) / 1000;
                        //Log.d(TAG, "onDateSet: MOVELONG: " + moveLong);
                        if (moveLong < 0) {
                            Toast.makeText(FlyttActivity.this, getString(R.string.guiden_toast_wrong_date), Toast.LENGTH_SHORT).show();
                        } else {
                            mDateShowDate.setText(i + "-" + i1 + "-" + i2);
                        }

                        notificationOneWeek = moveLong - oneWeekS;
                        notificationTwoWeek = moveLong - twoWeekS;
                        notificationOneMonth = moveLong - oneMonthS;
                        notificationTwoMonth = moveLong - twoMonthS;
                        notificationThreeMonth = moveLong - threeMonthS;

                        timeChosen = true;

                        /*Log.d(TAG, "moveLong - oneWeekMS = BOKA EN VECKA INNAN FLYTT  = " + (notificationOneWeek)); // notificationOneWeek
                        Log.d(TAG, "moveLong - twoWeekS = BOKA TVÅ VECKOR INNAN FLYTT  = " + (notificationTwoWeek)); // notificationTwoWeek
                        Log.d(TAG, "moveLong - oneMonthS = BOKA EN MÅNAD INNAN FLYTT  = " + (notificationOneMonth)); // notificationOneMonth
                        Log.d(TAG, "moveLong - twoMonthS = BOKA TVÅ MÅNADER INNAN FLYTT  = " + (notificationTwoMonth)); // notificationTwoMonth
                        Log.d(TAG, "moveLong - threeMonthS = BOKA TRE MÅNADER INNAN FLYTT  = " + (notificationThreeMonth)); // notificationThreeMonth*/
                    }
                }, y, m, d);
                pickerDialog.show();

            }
        });
    }

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

   /* private void sendNotification(int id, Notification.Builder notification) {
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
    }*/


}
