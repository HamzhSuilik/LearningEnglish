package einglish.edu.hz;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class word extends Activity implements
        TextToSpeech.OnInitListener,OnItemSelectedListener {

    private TextToSpeech tts;
    private Button buttonSpeak;
    private Spinner speedSpinner,pitchSpinner;
    private static String speed="الافتراضي";

    private String txt;
    private ImageView img;
    private TextView ar_txt;
    private TextView en_txt;
    private TextView lable_txt;
    private TextView num_txt;


    private int position;
    private int level;
    private int lesson;

    private long time;

    // ads
    private InterstitialAd mInterstitialAd;
    private int choose;
    private int end_rote;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // ***********************
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
        // **********************


        // $$$$$$$$$$$$$$$$$$$$$

        // InterstitialAd

        end_rote=1;

        SharedPreferences gg = getSharedPreferences("file1", Context.MODE_PRIVATE);
        choose=gg.getInt("ad_id",gg.getInt("rand88",1));

        String [] item = getResources().getStringArray(R.array.word_ad);

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(item[choose]);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                setEnd_rote();

                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        // $$$$$$$$$$$$$$$$$$$$$


        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


        Bundle paste = getIntent().getExtras();
        level = paste.getInt("level");
        lesson = paste.getInt("lesson");


        img=findViewById(R.id.imageView);
        ar_txt=findViewById(R.id.ar);
        en_txt=findViewById(R.id.en);
        lable_txt=findViewById(R.id.lable);
        num_txt=findViewById(R.id.num);



        ar_txt.setText(AR(level,lesson,0));
        en_txt.setText(EN(level,lesson,0));
        img.setImageResource(IMG(level,lesson,0));
        txt=EN(level,lesson,0);
        subject(level,lesson);


        position=0;

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@




        tts = new TextToSpeech(this, this);
        buttonSpeak =  findViewById(R.id.button1);
        speedSpinner =  findViewById(R.id.spinner1);


//تحميل البيانات من الـ سبينر
        loadSpinnerData();
        speedSpinner.setOnItemSelectedListener(this);

//الضغط على زر تحدث
        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setSpeed();
                speakOut();
            }

        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder sms = new AlertDialog.Builder(this);
        sms.setMessage("هل تريد العودة إلى قائمة الدروس")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mInterstitialAd.isLoaded()) {
                            end_rote=3;
                            mInterstitialAd.show();
                        }else {
                            Intent intent = new Intent(getApplicationContext(), lesson.class);
                            intent.putExtra("level", level);
                            startActivity(intent);
                            finish();
                        }

                    }
                })

                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }



    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
// تستطيع من هنا تغيير اللغة UK
            int result = tts.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "للأسف لم يتم اعتماده");
            } else {
                buttonSpeak.setEnabled(true);
                speakOut();
            }

        } else { Log.e("TTS", "Initilization فشل!");}

    }

    @Override
    public void onDestroy() {
// ايقاف تحويل الصوت
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    // قائمة السرعة مع سرعة كلاً منها
    private void setSpeed(){
        if(speed.equals("بطيء جداً")){
            tts.setSpeechRate(0.1f);
        }
        if(speed.equals("بطيء")){
            tts.setSpeechRate(0.5f);
        }
        if(speed.equals("الافتراضي")){
            tts.setSpeechRate(1.0f); // السرعة الافتراضية 1.0f
        }
        if(speed.equals("سريع")){
            tts.setSpeechRate(1.5f);
        }
        if(speed.equals("سريع جداً")){
            tts.setSpeechRate(2.0f);
        }

    }

    private void speakOut() {
        String text = txt;
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void loadSpinnerData() {
        //البيانات داخل عنصر سرعة سبينر
        List<String> lables = new ArrayList<String>();
        lables.add("الافتراضي");
        lables.add("بطيء جداً");
        lables.add("بطيء");
        lables.add("سريع");
        lables.add("سريع جداً");

        // إنشاء محول لـ سبينر
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lables);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // ربط محول  adapter بـ سبينر
        speedSpinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // بعد اختيار عنصر من قائمة سبينر.. توست
        speed = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "القد اخترت:" + speed,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void back(View view) {

        if(position!=0) {

            position--;

            int position2 = position + 1;
            num_txt.setText(position2 + "/" + number_of_words(level, lesson));

            ar_txt.setText(AR(level, lesson, position));
            en_txt.setText(EN(level, lesson, position));
            img.setImageResource(IMG(level, lesson, position));
            txt = EN(level, lesson, position);

            setSpeed();
            speakOut();

        }

    }

    public void next(View view) {

        if ((position+1) == available_num(level, lesson)) {

            if (mInterstitialAd.isLoaded()) {
                end_rote=1;
                mInterstitialAd.show();
            }else {

                Intent go = new Intent(this, end_1.class);
                go.putExtra("level", level);
                go.putExtra("lesson", lesson);
                startActivity(go);
                finish();
            }

        } else {


        position++;

        int position2 = position + 1;
        num_txt.setText(position2 + "/" + number_of_words(level, lesson));

        ar_txt.setText(AR(level, lesson, position));
        en_txt.setText(EN(level, lesson, position));
        img.setImageResource(IMG(level, lesson, position));
        txt = EN(level, lesson, position);


        setSpeed();
        speakOut();
    }

    }




    public int IMG(int level_num,int lesson_num,int word_num){

        lesson_num--;

        int re;
        re=0;

        int[][] level_1 ={
                {R.drawable.a_1_1_1,R.drawable.a_1_1_2,R.drawable.a_1_1_3,R.drawable.a_1_1_4,R.drawable.a_1_1_5,R.drawable.a_1_1_6,R.drawable.a_1_1_7,R.drawable.a_1_1_8,R.drawable.a_1_1_9,R.drawable.a_1_1_10,R.drawable.a_1_1_11}
                ,
                {R.drawable.a_1_2_1,R.drawable.a_1_2_2,R.drawable.a_1_2_3,R.drawable.a_1_2_4,R.drawable.a_1_2_5,R.drawable.a_1_2_6,R.drawable.a_1_2_7,R.drawable.a_1_2_8,R.drawable.a_1_2_9,R.drawable.a_1_2_10}
                ,
                {R.drawable.a_1_3_1,R.drawable.a_1_3_2,R.drawable.a_1_3_3,R.drawable.a_1_3_4,R.drawable.a_1_3_5,R.drawable.a_1_3_6,R.drawable.a_1_3_7,R.drawable.a_1_3_8,R.drawable.a_1_3_9,R.drawable.a_1_3_10}
                ,
                {R.drawable.a_1_4_1,R.drawable.a_1_4_2,R.drawable.a_1_4_3,R.drawable.a_1_4_4,R.drawable.a_1_4_5,R.drawable.a_1_4_6,R.drawable.a_1_4_7,R.drawable.a_1_4_8,R.drawable.a_1_4_9,R.drawable.a_1_4_10,R.drawable.a_1_4_11}
                ,
                {R.drawable.a_1_5_1,R.drawable.a_1_5_2,R.drawable.a_1_5_3,R.drawable.a_1_5_4,R.drawable.a_1_5_5,R.drawable.a_1_5_6,R.drawable.a_1_5_7,R.drawable.a_1_5_8,R.drawable.a_1_5_9,R.drawable.a_1_5_10,R.drawable.a_1_5_11,R.drawable.a_1_5_12,R.drawable.a_1_5_13,R.drawable.a_1_5_14}
        };

        int[][] level_2 ={
                {R.drawable.a_2_1_1,R.drawable.a_2_1_2,R.drawable.a_2_1_3,R.drawable.a_2_1_4,R.drawable.a_2_1_5}
                ,
                {R.drawable.a_2_2_1,R.drawable.a_2_2_2,R.drawable.a_2_2_3,R.drawable.a_2_2_4,R.drawable.a_2_2_5,R.drawable.a_2_2_6,R.drawable.a_2_2_7,R.drawable.a_2_2_8,R.drawable.a_2_2_9,R.drawable.a_2_2_10,R.drawable.a_2_2_11}
                ,
                {R.drawable.a_2_3_1,R.drawable.a_2_3_2,R.drawable.a_2_3_3,R.drawable.a_2_3_4,R.drawable.a_2_3_5,R.drawable.a_2_3_6,R.drawable.a_2_3_7,R.drawable.a_2_3_8,R.drawable.a_2_3_9,R.drawable.a_2_3_10,R.drawable.a_2_3_11,R.drawable.a_2_3_12,R.drawable.a_2_3_13,R.drawable.a_2_3_14,R.drawable.a_2_3_15,R.drawable.a_2_3_16,R.drawable.a_2_3_17,R.drawable.a_2_3_18}
                ,
                {R.drawable.a_2_4_1,R.drawable.a_2_4_2,R.drawable.a_2_4_3,R.drawable.a_2_4_4,R.drawable.a_2_4_5,R.drawable.a_2_4_6,R.drawable.a_2_4_7,R.drawable.a_2_4_8,R.drawable.a_2_4_9}
                ,
                {R.drawable.a_2_5_1,R.drawable.a_2_5_2,R.drawable.a_2_5_3,R.drawable.a_2_5_4,R.drawable.a_2_5_5,R.drawable.a_2_5_6,R.drawable.a_2_5_7,R.drawable.a_2_5_8}
        };

        int[][] level_3 ={
                {R.drawable.a_3_1_1,R.drawable.a_3_1_2,R.drawable.a_3_1_3,R.drawable.a_3_1_4,R.drawable.a_3_1_5,R.drawable.a_3_1_6,R.drawable.a_3_1_7,R.drawable.a_3_1_8,R.drawable.a_3_1_9,R.drawable.a_3_1_10,R.drawable.a_3_1_11,R.drawable.a_3_1_12,R.drawable.a_3_1_13,R.drawable.a_3_1_14}
                ,
                {R.drawable.a_3_2_1,R.drawable.a_3_2_2,R.drawable.a_3_2_3,R.drawable.a_3_2_4,R.drawable.a_3_2_5,R.drawable.a_3_2_6,R.drawable.a_3_2_7,R.drawable.a_3_2_8,R.drawable.a_3_2_9}
                ,
                {R.drawable.a_3_3_1,R.drawable.a_3_3_2,R.drawable.a_3_3_3,R.drawable.a_3_3_4,R.drawable.a_3_3_5,R.drawable.a_3_3_6,R.drawable.a_3_3_7,R.drawable.a_3_3_8,R.drawable.a_3_3_9,R.drawable.a_3_3_10}
                ,
                {R.drawable.a_3_4_1,R.drawable.a_3_4_2,R.drawable.a_3_4_3,R.drawable.a_3_4_4,R.drawable.a_3_4_5,R.drawable.a_3_4_6,R.drawable.a_3_4_7,R.drawable.a_3_4_8}
                ,
                {R.drawable.a_3_5_1,R.drawable.a_3_5_2,R.drawable.a_3_5_3,R.drawable.a_3_5_4,R.drawable.a_3_5_5,R.drawable.a_3_5_6,R.drawable.a_3_5_7,R.drawable.a_3_5_8,R.drawable.a_3_5_9,R.drawable.a_3_5_10,R.drawable.a_3_5_11,R.drawable.a_3_5_12,R.drawable.a_3_5_13,R.drawable.a_3_5_14,R.drawable.a_3_5_15,R.drawable.a_3_5_16,R.drawable.a_3_5_17,R.drawable.a_3_5_18,R.drawable.a_3_5_19}
        };

        int[][] level_4 ={
                {R.drawable.a_4_1_1,R.drawable.a_4_1_2,R.drawable.a_4_1_3,R.drawable.a_4_1_4,R.drawable.a_4_1_5,R.drawable.a_4_1_6,R.drawable.a_4_1_7,R.drawable.a_4_1_8}
                ,
                {R.drawable.a_4_2_1,R.drawable.a_4_2_2,R.drawable.a_4_2_3,R.drawable.a_4_2_4,R.drawable.a_4_2_5,R.drawable.a_4_2_6,R.drawable.a_4_2_7,R.drawable.a_4_2_8,R.drawable.a_4_2_9,R.drawable.a_4_2_10,R.drawable.a_4_2_11,R.drawable.a_4_2_12,R.drawable.a_4_2_13}
                ,
                {R.drawable.a_4_3_1,R.drawable.a_4_3_2,R.drawable.a_4_3_3,R.drawable.a_4_3_4,R.drawable.a_4_3_5,R.drawable.a_4_3_6,R.drawable.a_4_3_7,R.drawable.a_4_3_8,R.drawable.a_4_3_9,R.drawable.a_4_3_10,R.drawable.a_4_3_11}
                ,
                {R.drawable.a_4_4_1,R.drawable.a_4_4_2,R.drawable.a_4_4_3,R.drawable.a_4_4_4,R.drawable.a_4_4_5,R.drawable.a_4_4_6,R.drawable.a_4_4_7,R.drawable.a_4_4_8,R.drawable.a_4_4_9,R.drawable.a_4_4_10,R.drawable.a_4_4_11,R.drawable.a_4_4_12,R.drawable.a_4_4_13,R.drawable.a_4_4_14}
                ,
                {R.drawable.a_4_5_1,R.drawable.a_4_5_2,R.drawable.a_4_5_3,R.drawable.a_4_5_4,R.drawable.a_4_5_5,R.drawable.a_4_5_6,R.drawable.a_4_5_7,R.drawable.a_4_5_8,R.drawable.a_4_5_9,R.drawable.a_4_5_10,R.drawable.a_4_5_11,R.drawable.a_4_5_12,R.drawable.a_4_5_13}
        };

        int[][] level_5 ={
                {R.drawable.a_5_1_1,R.drawable.a_5_1_2,R.drawable.a_5_1_3,R.drawable.a_5_1_4,R.drawable.a_5_1_5,R.drawable.a_5_1_6,R.drawable.a_5_1_7,R.drawable.a_5_1_8,R.drawable.a_5_1_9,R.drawable.a_5_1_10}
                ,
                {R.drawable.a_5_2_1,R.drawable.a_5_2_2,R.drawable.a_5_2_3,R.drawable.a_5_2_4,R.drawable.a_5_2_5,R.drawable.a_5_2_6,R.drawable.a_5_2_7,R.drawable.a_5_2_8,R.drawable.a_5_2_9,R.drawable.a_5_2_10,R.drawable.a_5_2_11,R.drawable.a_5_2_12}
                ,
                {R.drawable.a_5_3_1,R.drawable.a_5_3_2,R.drawable.a_5_3_3,R.drawable.a_5_3_4,R.drawable.a_5_3_5,R.drawable.a_5_3_6,R.drawable.a_5_3_7,R.drawable.a_5_3_8,R.drawable.a_5_3_9}
                ,
                {R.drawable.a_5_4_1,R.drawable.a_5_4_2,R.drawable.a_5_4_3,R.drawable.a_5_4_4,R.drawable.a_5_4_5,R.drawable.a_5_4_6,R.drawable.a_5_4_7,R.drawable.a_5_4_8,R.drawable.a_5_4_9,R.drawable.a_5_4_10,R.drawable.a_5_4_11,R.drawable.a_5_4_12,R.drawable.a_5_4_13}
                ,
                {R.drawable.a_5_5_1,R.drawable.a_5_5_2,R.drawable.a_5_5_3,R.drawable.a_5_5_4,R.drawable.a_5_5_5,R.drawable.a_5_5_6,R.drawable.a_5_5_7,R.drawable.a_5_5_8,R.drawable.a_5_5_9,R.drawable.a_5_5_10,R.drawable.a_5_5_11,R.drawable.a_5_5_12,R.drawable.a_5_5_13,R.drawable.a_5_5_14,R.drawable.a_5_5_15,R.drawable.a_5_5_16,R.drawable.a_5_5_17,R.drawable.a_5_5_18}
        };

        int[][] level_6 ={
                {R.drawable.a_6_1_1,R.drawable.a_6_1_2,R.drawable.a_6_1_3,R.drawable.a_6_1_4,R.drawable.a_6_1_5,R.drawable.a_6_1_6,R.drawable.a_6_1_7,R.drawable.a_6_1_8}
                ,
                {R.drawable.a_6_2_1,R.drawable.a_6_2_2,R.drawable.a_6_2_3,R.drawable.a_6_2_4,R.drawable.a_6_2_5,R.drawable.a_6_2_6,R.drawable.a_6_2_7,R.drawable.a_6_2_8,R.drawable.a_6_2_9,R.drawable.a_6_2_10,R.drawable.a_6_2_11,R.drawable.a_6_2_12}
                ,
                {R.drawable.a_6_3_1,R.drawable.a_6_3_2,R.drawable.a_6_3_3,R.drawable.a_6_3_4,R.drawable.a_6_3_5,R.drawable.a_6_3_6,R.drawable.a_6_3_7,R.drawable.a_6_3_8}
                ,
                {R.drawable.a_6_4_1,R.drawable.a_6_4_2,R.drawable.a_6_4_3,R.drawable.a_6_4_4,R.drawable.a_6_4_5,R.drawable.a_6_4_6,R.drawable.a_6_4_7,R.drawable.a_6_4_8}
                ,
                {R.drawable.a_6_5_1,R.drawable.a_6_5_2,R.drawable.a_6_5_3,R.drawable.a_6_5_4,R.drawable.a_6_5_5,R.drawable.a_6_5_6,R.drawable.a_6_5_7,R.drawable.a_6_5_8,R.drawable.a_6_5_9}
        };

        int[][] level_7 ={
                {R.drawable.a_7_1_1,R.drawable.a_7_1_2,R.drawable.a_7_1_3,R.drawable.a_7_1_4,R.drawable.a_7_1_5,R.drawable.a_7_1_6,R.drawable.a_7_1_7,R.drawable.a_7_1_8,R.drawable.a_7_1_9}
                ,
                {R.drawable.a_7_2_1,R.drawable.a_7_2_2,R.drawable.a_7_2_3,R.drawable.a_7_2_4,R.drawable.a_7_2_5,R.drawable.a_7_2_6,R.drawable.a_7_2_7,R.drawable.a_7_2_8,R.drawable.a_7_2_9,R.drawable.a_7_2_10,R.drawable.a_7_2_11,R.drawable.a_7_2_12,R.drawable.a_7_2_13}
                ,
                {R.drawable.a_7_3_1,R.drawable.a_7_3_2,R.drawable.a_7_3_3,R.drawable.a_7_3_4,R.drawable.a_7_3_5,R.drawable.a_7_3_6,R.drawable.a_7_3_7,R.drawable.a_7_3_8,R.drawable.a_7_3_9,R.drawable.a_7_3_10,R.drawable.a_7_3_11}
                ,
                {R.drawable.a_7_4_1,R.drawable.a_7_4_2,R.drawable.a_7_4_3,R.drawable.a_7_4_4,R.drawable.a_7_4_5,R.drawable.a_7_4_6,R.drawable.a_7_4_7,R.drawable.a_7_4_8,R.drawable.a_7_4_9,R.drawable.a_7_4_10,R.drawable.a_7_4_11,R.drawable.a_7_4_12,R.drawable.a_7_4_13,R.drawable.a_7_4_14}
                ,
                {R.drawable.a_7_5_1,R.drawable.a_7_5_2,R.drawable.a_7_5_3,R.drawable.a_7_5_4,R.drawable.a_7_5_5,R.drawable.a_7_5_6,R.drawable.a_7_5_7}
        };

        int[][] level_8 ={
                {R.drawable.a_8_1_1,R.drawable.a_8_1_2,R.drawable.a_8_1_3,R.drawable.a_8_1_4,R.drawable.a_8_1_5,R.drawable.a_8_1_6,R.drawable.a_8_1_7,R.drawable.a_8_1_8,R.drawable.a_8_1_9,R.drawable.a_8_1_10,R.drawable.a_8_1_11,R.drawable.a_8_1_12}
                ,
                {R.drawable.a_8_2_1,R.drawable.a_8_2_2,R.drawable.a_8_2_3,R.drawable.a_8_2_4,R.drawable.a_8_2_5,R.drawable.a_8_2_6,R.drawable.a_8_2_7,R.drawable.a_8_2_8,R.drawable.a_8_2_9,R.drawable.a_8_2_10,R.drawable.a_8_2_11,R.drawable.a_8_2_12}
                ,
                {R.drawable.a_8_3_1,R.drawable.a_8_3_2,R.drawable.a_8_3_3,R.drawable.a_8_3_4,R.drawable.a_8_3_5,R.drawable.a_8_3_6,R.drawable.a_8_3_7,R.drawable.a_8_3_8,R.drawable.a_8_3_9,R.drawable.a_8_3_10}
                ,
                {R.drawable.a_8_4_1,R.drawable.a_8_4_2,R.drawable.a_8_4_3,R.drawable.a_8_4_4,R.drawable.a_8_4_5,R.drawable.a_8_4_6,R.drawable.a_8_4_7,R.drawable.a_8_4_8,R.drawable.a_8_4_9,R.drawable.a_8_4_10,R.drawable.a_8_4_11}
                ,
                {R.drawable.a_8_5_1,R.drawable.a_8_5_2,R.drawable.a_8_5_3,R.drawable.a_8_5_4,R.drawable.a_8_5_5,R.drawable.a_8_5_6,R.drawable.a_8_5_7,R.drawable.a_8_5_8}
        };

        int[][] level_9 ={
                {R.drawable.a_9_1_1,R.drawable.a_9_1_2,R.drawable.a_9_1_3,R.drawable.a_9_1_4,R.drawable.a_9_1_5,R.drawable.a_9_1_6,R.drawable.a_9_1_7,R.drawable.a_9_1_8,R.drawable.a_9_1_9,R.drawable.a_9_1_10,R.drawable.a_9_1_11}
                ,
                {R.drawable.a_9_2_1,R.drawable.a_9_2_2,R.drawable.a_9_2_3,R.drawable.a_9_2_4,R.drawable.a_9_2_5,R.drawable.a_9_2_6,R.drawable.a_9_2_7,R.drawable.a_9_2_8,R.drawable.a_9_2_9,R.drawable.a_9_2_10}
                ,
                {R.drawable.a_9_3_1,R.drawable.a_9_3_2,R.drawable.a_9_3_3,R.drawable.a_9_3_4,R.drawable.a_9_3_5,R.drawable.a_9_3_6,R.drawable.a_9_3_7,R.drawable.a_9_3_8,R.drawable.a_9_3_9,R.drawable.a_9_3_10}
                ,
                {R.drawable.a_9_4_1,R.drawable.a_9_4_2,R.drawable.a_9_4_3,R.drawable.a_9_4_4,R.drawable.a_9_4_5,R.drawable.a_9_4_6,R.drawable.a_9_4_7,R.drawable.a_9_4_8}
                ,
                {R.drawable.a_9_5_1,R.drawable.a_9_5_2,R.drawable.a_9_5_3,R.drawable.a_9_5_4,R.drawable.a_9_5_5,R.drawable.a_9_5_6}
        };


        if (level_num==1)
            re=level_1[lesson_num][word_num];

        if (level_num==2)
            re=level_2[lesson_num][word_num];

        if (level_num==3)
            re=level_3[lesson_num][word_num];

        if (level_num==4)
            re=level_4[lesson_num][word_num];

        if (level_num==5)
            re=level_5[lesson_num][word_num];

        if (level_num==6)
            re=level_6[lesson_num][word_num];

        if (level_num==7)
            re=level_7[lesson_num][word_num];

        if (level_num==8)
            re=level_8[lesson_num][word_num];

        if (level_num==9)
            re=level_9[lesson_num][word_num];

        return re;

    }

    public String AR(int level_num,int lesson_num,int word_num){

        lesson_num--;

        String re;
        re="0";

        String[][] level_1 ={
                {"واحد","إثنان","ثلاثة","أربعة","خمسة","ستة","سبعة","ثمانية","تسعة","عشرة","مئة"}
                ,
                {"احمر","اصفر","اخضر","ازرق","اسود","ابيض","بني ","برتقالي","رمادي","بنفسجي"}
                ,
                {"الحواس","السمع","التذوق","البصر","الشم","اللمس","صاخب","هادئ","ناعم","خشن"}
                ,
                {"معلم","مدرسة","طالب","فصل","مكتبة","حساب","انجليزية","يدرس","واجب","القراءة","الكتابة"}
                ,
                {"طويل","قصير","سمين","رفيع","جيد","سيء","سعيد","حزين","كبير","صغير","سريع","بطئ","نظيف","وسخ"}

        };

        String[][] level_2 ={
                {"سيارة","شاحنة","طائرة","قطار","حافلة"}
                ,
                {"حديقة حيوان","مزرعة","بحر","مستشفى","فندق","مطار","بنك","شاطئ","حديقة","شارع","قرية"}
                ,
                {"أسد","نمر","ذئب","جمل","ثعلب","دب","زرافة","قرد","خروف","أرنب","سمكة","ثعبان","فأر","سلحفاة","ضفدع","كلب","بقرة","حصان"}
                ,
                {"بط","إوزه","طاووس","نسر","خفاش","طائر ","حمامة","دجاجة","غراب"}
                ,
                {"علامة الدولار","علامة يساوي","علامة تعجب","أكبر من","أقل من","علامة النسبة المئوية","علامة استفهام","علامة الاقتباس"}
        };

        String[][] level_3 ={
                {"طبیب","نجار","سائق","مھندس","جزار","طيار","رجل شرطة","رجل بريد","ربة منزل","ممرضة","مدرس","مزارع","جندي","صياد"}
                ,
                {"شوكة","معلقة","سكينة","كوب","ملح","سكر","شاي","قهوة","صابون"}
                ,
                {"فاكهة","موز","تفاح","برتقال","عنب","تين","بطيخ","خوخ","فراولة","ليمون"}
                ,
                {"طماطم","بطاطس","خيار","بصل","ذرة","أرز","جزر","قمح"}
                ,
                {"يذهب","يقرأ","يكتب","يرسم","يجري","ينام","يري","يحب","يسطيع","يلعب","يسبح","يقطع","يزرع","ينظف","يفتح","يغلق","يطير","يساعد","يغسل"}
        };

        String[][] level_4 ={
                {"غاضب","متحمّس","خائف","جائع","وحيد","سعيد","مستاء","منهك"}
                ,
                {"منزل","غرفة","غرفة معيشة","غرفة نوم","مطبخ","حمام","الحديقة","غرفة المكتب","باب","جرس","نافذة","حائط","مفتاح"}
                ,
                {"الصورة المتحركة","السينما","الكوميديا","الرواية البوليسية","فلم","فلم الرعب","المسرحيّة","جهاز التحكّم عن بعد"," البرنامج التلفزيوني","الصوت","الموسيقى"}
                ,
                {"الفطور","العشاء","الشراب","الغداء","البازلاء","المطعم","بيضة","الجبن","المربّى","اللحم","السلطة","الشوربة","الكأس","السكين"}
                ,
                {"البلاد","العَلَم","الجنسية","المحافظة","فرنسا","إنجلترا","الصين","ألمانيا","اليابان","فلسطين","مصر","الأردن","العالم"}
        };

        String[][] level_5 ={
                {"الدَمّ","المرض","الصيدلية","العيّنة","الحقنة","فرشاة الأسنان","الفيروس","الإنفلونزا","الطبّ","مريض"}
                ,
                {"السجادة","الكرسي","الستارة","المصباح","القفل","المرآة","الأثاثِ","الوسادة","الثلاجة","الهاتف","التلفزيون","فرن"}
                ,
                {"الطفل ","العُمر","حيّ","الولادة","عيد الميلاد","كبير السن","يولدَ","يموت","شاب"}
                ,
                {"المخبز","المصرف","العملة المعدنية","القائمة","مركز التسوّق","السوق","الإيصال","النقد","المال","السعر","تكلفة","يدفع","يبيع"}
                ,
                {"الغيمة","الصحراء","الغابة","التَلّ","البحيرة","الجبل","الكوكب","النهر","الصخرة","النجم","البركان","جزيرة","محيط","العشب","الفضاء","الشمس","الثلج","النار"}
        };

        String[][] level_6 ={
                {"المشي","كرة السلة","الملاكمة","الشطرنج","ركوب الدراجات","كرة القدم","التزلج","البيسبول"}
                ,
                {"المعركة","العَلَم","الجنرال","المروحية","الملك","الفارس","المسدّس","الصاروخ","الجندي","الحرب","جيش","إمبراطور"}
                ,
                {"الأدوات","المكنسة","المطرقة","الإبرة","المضخّة","المنشار","المجرفة","المقصّ"}
                ,
                {"الطقس","غائم","الضباب","بارد","دافئ","تمطر","تثلج","عاصفُ"}
                ,
                {"الرئيس","الحاسبة","الحاسوب","المصنع","الملف","التقرير","مكتب","العمل","الإدارة"}
        };

        String[][] level_7 ={
                {"الجامعة","البرج","ناطحة السحاب","السجن","المتحف","المسجد","المستشفى","القلعة","المخبز","البرج","ناطحة السحاب","السجن","المتحف","المسجد","المستشفى","القلعة","المخبز"}
                ,
                {"المدينة ","السائق"," الطريق السريع","المسافر","الميناء","سكة الحديد","الملعب","سيارة الأجرة","التذكرة","القطار","حادث","تقاطع","إشارة المرور"}
                ,
                {"شكل","دائرة","مربع","مثلث","مكعب","كروي","مخروط","هرم","أسطوانة","بيضوي","المستطيل"}
                ,
                {"جسم","رأس","وجه","شعر","عين","أذن","أنف","فم","أسنان","لسان","ذراع","رجل","قدم","قلب"}
                ,
                {"ملابس","فستان","قمیص","بنطلون","قبعة","حذاء","جوارب"}
        };

        String[][] level_8 ={
                {"فضاء","شمس","قمر","نجم","كوكب","مريخ","مشتري","زحل","مكوك","صاروخ","رائد فضاء","مجرة"}
                ,
                {"طاقة","كهرباء","الطاقة الشمسية","نفط","وقود","تلوث","رياح","بطارية","فحم","برق","يولد","إحتباس حراري"}
                ,
                {"كارثة","بركان","زلزال","يدمر","خطر","نيزك","حريق","مجاعة","سم","تصحر"}
                ,
                {"خشب","بلاستيك","حديد","نحاس","قماش","قطن","ألمينيوم","ورق","جلد","ذهب","فضة"}
                ,
                {"لاعب","مدرب","حارس مرمى","جمهور","هدف","كأس","مباراة","يفوز"}
        };

        String[][] level_9 ={
                {"سمكة","سمك القرش","صيد السمك","صياد سمك","طحالب","محيط","بحر","شاطئ","أخطبوط","دلفين","فقمة"}
                ,
                {"مبنى","مهندس","معماري","عامل بناء","أرضية","يحفر","حجر","دهان","سقف","بلاط"}
                ,
                {"ثلاجة","مصباح","تلفاز","حاسوب","راديو","مكوى","هاتف","آلة حاسبة","مكيف","جرس"}
                ,
                {"يخترع","يكتشف","مختبر","كيمياء","فيزياء","أحياء","رياضيات","علماء"}
                ,
                {"سياسي","إقتصادي","إجتماعي","عسكري","ثقافي","تربوي"}
        };

        if (level_num==1)
            re=level_1[lesson_num][word_num];

        if (level_num==2)
            re=level_2[lesson_num][word_num];

        if (level_num==3)
            re=level_3[lesson_num][word_num];

        if (level_num==4)
            re=level_4[lesson_num][word_num];

        if (level_num==5)
            re=level_5[lesson_num][word_num];

        if (level_num==6)
            re=level_6[lesson_num][word_num];

        if (level_num==7)
            re=level_7[lesson_num][word_num];

        if (level_num==8)
            re=level_8[lesson_num][word_num];

        if (level_num==9)
            re=level_9[lesson_num][word_num];

        return re;

    }

    public String EN(int level_num,int lesson_num,int word_num){

        lesson_num--;

        String re;
        re="0";

        String[][] level_1 ={
                {"one","two","three","four","five","six","seven","eight","nine","ten","hundred"}
                ,
                {"red","yellow","green","blue","black","white","brown","orange","grey","purple"}
                ,
                {"Senses","Hearing","Tasting","Sight","smelling","Touch","raucous","Quiet","Soft ","rough"}
                ,
                {"teacher","school","Student","class","library","Math","English","study","Homework","reading","writing"}
                ,
                {"tall","Short","fat","thin","good","Bad","happy","Sad","big","small","Fast","slow","Clean","dirty"}
        };

        String[][] level_2 ={
                {"Car","Van","Plane","train","bus"}
                ,
                {"Zoo","Farm","Sea","hospital","hotel","airport","bank","beach","park","street","village"}
                ,
                {"Lion","Tiger","Wolf","camel","Fox","bear","giraffe","Monkey","sheep","Rabbit","fish","Snake","mouse","Turtle","Frog","Dog","cow","horse"}
                ,
                {"duck","goose","peacock","Eagle","bat","bird","dove","Hen","crow"}
                ,
                {"Dollar sign","Equal sign","Exclamation point","Greater than","less than","percent sign","Question mark","quotation mark"}
        };

        String[][] level_3 ={
                {"Doctor","Carpenter","Driver","Engineer","butcher","pilot","Policeman","postman","Housewife","nurse","Teacher","farmer","soldier","fisherman"}
                ,
                {"Fork","Spoon","Knife","cup","Salt","Sugar","tea","coffee","soap"}
                ,
                {"Fruits ","Banana","Apple","orange","grapes","fig","watermelon","peach","Strawberry","Lemon"}
                ,
                {"Tomato","Potato","cucumber","onions","corn","rice","Carrot","wheat"}
                ,
                {"go","read","write","draw","run","sleep","see","like","can","play","swim","cut","Plant","clean","Open","Close","Fly","help","Wash"}
        };

        String[][] level_4 ={
                {"angry","excited","fear","hunger","lonely","happy","upset","exhousted"}
                ,
                {"house","room","Living- room","Bed- room","kitchen","Bath- room","park","Office- room","door","bell","window","wall","key"}
                ,
                {"cartoon","cinema","comedy","detective story","film","horror film","play","remote control","TV program","voice","music"}
                ,
                {"breakfast","dinner","drink","lunch","pea","restaurant","egg","cheese","jam","meat","salad","soup","cup","knife"}
                ,
                {"country","flag","nationality","province","France","England","China","Germany","Japan","Palestine","Egypt","Jordan","world"}
        };

        String[][] level_5 ={
                {"blood ","disease","pharmacy","sample","syringe","toothbrush","virus","flu","medicine","sick"}
                ,
                {"carpet","chair","curtain","lamp","lock","mirror","furniture","pillow","refrigerator","telephone","television","oven"}
                ,
                {"baby","age","alive","birth","birthday","old","born","die","young"}
                ,
                {"bakery","bank","coin","list","mall","market","receipt","cash","money","price","cost","pay","sell"}
                ,
                {"cloud","desert","forest","hill","lake","mountain","planet","river","rock","star","volcano","island","ocean","grass","space","sun","ice","fire"}
        };

        String[][] level_6 ={
                {"walk","basketball","boxing","chess","cycling","football","ski","baseball"}
                ,
                {"battle","flag","general","helicopter","king","knight","pistol","rocket","soldier","war","army","emperor"}
                ,
                {"Tools ","broom","hammer","needle","pump","saw","shovel","scissors"}
                ,
                {"Weather ","cloudy","fog","cold","warm","raining","snowing","windy"}
                ,
                {"boss","calculator","computer","factory","file","report","office","business","management"}
        };

        String[][] level_7 ={
                {"university","tower","skyscraper","prison","museum","mosque","hospital","castle","bakery"}
                ,
                {"city ","driver","highway","passenger","port","railroad","stadium","taxi","ticket","train","accident","intersection","traffic light"}
                ,
                {"shape","Circle","Square","triangle","cubic","spherical","cone","pyramid","cylinder","oval","rectangle"}
                ,
                {"body","Head","Face","hair","eye","ear","nose","mouth","teeth","tongue","arm","leg","foot","heart"}
                ,
                {"Clothes","dress","Shirt","Trousers","Hat","Shoes","Socks"}
        };

        String[][] level_8 ={
                {"Space","Sun","Moon","Star","planet","Mars","Jupiter","Saturn","shuttle","Rocket","astronaut","Galaxy"}
                ,
                {"energy","Electricity","solar power","oil","fuel"," pollution","wind","battery","coal","Lightning","Generates","global warming"}
                ,
                {"Disaster","volcano","earthquake","Destroyed","risk","meteor","fire","famine","poison","Desertification"}
                ,
                {"Wood","Plastic ","iron","copper","cloth","cotton","Aluminum","paper","skin","gold","Silver"}
                ,
                {"Player","Coach","A goal keeper","audience","Goal","cup","match","Win"}
        };

        String[][] level_9 ={
                {"fish","Shark","fishing","Fisher man","Algae","Ocean","Sea","Beach","Octopus","dolphin","seal"}
                ,
                {"Building","Engineer","architect","A builder","floor","Dig","Stone","paint","roof","Tiles"}
                ,
                {"Refrigerator","lamp","television","Computer","radio","iron","Telephone","calculator","condition","Bell"}
                ,
                {"invent","discover","Laboratory","chemistry","physics","Biology","Maths","Scientists"}
                ,
                {"Political","economic","social","Military","cultural","educational"}
        };


        if (level_num==1)
            re=level_1[lesson_num][word_num];

        if (level_num==2)
            re=level_2[lesson_num][word_num];

        if (level_num==3)
            re=level_3[lesson_num][word_num];

        if (level_num==4)
            re=level_4[lesson_num][word_num];

        if (level_num==5)
            re=level_5[lesson_num][word_num];

        if (level_num==6)
            re=level_6[lesson_num][word_num];

        if (level_num==7)
            re=level_7[lesson_num][word_num];

        if (level_num==8)
            re=level_8[lesson_num][word_num];

        if (level_num==9)
            re=level_9[lesson_num][word_num];

        return re;

    }

    public int number_of_words(int level_num,int lesson_num){

        int[][] lessons ={
                {56,11,10,10,11,14}
                ,
                {51,5,11,18,9,8}
                ,
                {60,14,9,10,8,19}
                ,
                {59,8,13,11,14,13}
                ,
                {62,10,12,9,13,18}
                ,
                {45,8,12,8,8,9}
                ,
                {54,9,13,11,14,7}
                ,
                {53,12,12,10,11,8}
                ,
                {45,11,10,10,8,6}
        };

        return lessons[level_num-1][lesson_num];


    }

    public void subject(int level,int lesson){

        num_txt.setText("1/"+number_of_words(level,lesson));

        if (level==1){

            if(lesson==1)
                lable_txt.setText("الدرس الأول : الأرقام");

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : الألوان");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : الحواس");

            if(lesson==4)
                lable_txt.setText("الدرس الرابع : المدرسة");

            if(lesson==5)
                lable_txt.setText("الدرس الخامس : الصفات");
        }


        if (level==2){

            if(lesson==1)
                lable_txt.setText("الدرس الأول : مواصلات");

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : أماكن");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : الحيوانات");

            if(lesson==4)
                lable_txt.setText("الدرس الرابع : الطيور");

            if(lesson==5)
                lable_txt.setText("الدرس الخامس : رموز");
        }


        if (level==3){

            if(lesson==1)
                lable_txt.setText("الدرس الأول : الوظائف");

            if(lesson==2) {
                lable_txt.setText("الدرس الثاني : أدوات المطبخ");
                lable_txt.setTextSize(18f);
            }

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : الفاكهة");

            if(lesson==4){
                lable_txt.setText("الدرس الرابع : الخضار و الحبوب");
                lable_txt.setTextSize(18f);
            }

            if(lesson==5)
                lable_txt.setText("الدرس الخامس : أفعال");
        }


        if (level==4){

            if(lesson==1)
                lable_txt.setText("الدرس الأول : المشاعر");

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : المنزل");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : التلفاز");

            if(lesson==4)
                lable_txt.setText("الدرس الرابع : الغذاء");

            if(lesson==5)
                lable_txt.setText("الدرس الخامس : البلدان");
        }


        if (level==5){

            if(lesson==1)
                lable_txt.setText("الدرس الأول : الصحة");

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : الأثاث");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : الحياة");

            if(lesson==4)
                lable_txt.setText("الدرس الرابع : التسوق");

            if(lesson==5)
                lable_txt.setText("الدرس الخامس : الطبيعة");
        }


        if (level==6){

            if(lesson==1)
                lable_txt.setText("الدرس الأول : الرياضة");

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : الجيش");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : الأدوات");

            if(lesson==4)
                lable_txt.setText("الدرس الرابع : الطقس");

            if(lesson==5){
                lable_txt.setText("الدرس الخامس : العمل و الوظيفة");
                lable_txt.setTextSize(18f);
            }
        }


        if (level==7){

            if(lesson==1){
                lable_txt.setText("الدرس الأول : البنايات، المنظمات");
                lable_txt.setTextSize(18f);
            }

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : المدينة");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : الأشكال");

            if(lesson==4){
                lable_txt.setText("الدرس الرابع : جسم الإنسان");
                lable_txt.setTextSize(18f);
            }

            if(lesson==5)
                lable_txt.setText("الدرس الخامس : الملابس");
        }


        if (level==8){

            if(lesson==1)
                lable_txt.setText("الدرس الأول : الفضاء");

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : الطاقة");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : الكوارث");

            if(lesson==4)
                lable_txt.setText("الدرس الرابع : مواد");

            if(lesson==5){
                lable_txt.setText("الدرس الخامس : كرة قدم");
                lable_txt.setTextSize(20f);
            }
        }


        if (level==9){

            if(lesson==1){
                lable_txt.setText("الدرس الأول : الحياة البحرية");
                lable_txt.setTextSize(18f);
            }

            if(lesson==2)
                lable_txt.setText("الدرس الثاني : البناء");

            if(lesson==3)
                lable_txt.setText("الدرس الثالث : أجهزة");

            if(lesson==4)
                lable_txt.setText("الدرس الرابع : العلماء");

            if(lesson==5)
                lable_txt.setText("الدرس الخامس : مجالات");
        }


    }

    public int available_num(int level , int lesson){

        int[][] lessons ={
                {56,11,10,10,11,14}
                ,
                {51,5,11,18,9,8}
                ,
                {60,14,9,10,8,19}
                ,
                {59,8,13,11,14,13}
                ,
                {62,10,12,9,13,18}
                ,
                {45,8,12,8,8,9}
                ,
                {54,9,13,11,14,7}
                ,
                {53,12,12,10,11,8}
                ,
                {45,11,10,10,8,6}
        };

        return lessons[level-1][lesson];
    }

    public void voice(View view) {
        setSpeed();
        speakOut();
    }

    public void main_minu(View view) {
        AlertDialog.Builder sms = new AlertDialog.Builder(this);
        sms.setMessage("هل تريد العودة إلى الصفحة الرئيسية")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mInterstitialAd.isLoaded()) {
                            end_rote=2;
                            mInterstitialAd.show();
                        }else {

                            Intent intent = new Intent(getApplicationContext(), level.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                })

                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void setEnd_rote(){

        if(end_rote==1){
            Intent go = new Intent(this,end_1.class);
            go.putExtra("level",level);
            go.putExtra("lesson",lesson);
            startActivity(go);
            finish();
        }

        if(end_rote==2){
            Intent intent = new Intent(getApplicationContext(), level.class);
            startActivity(intent);
            finish();
        }

        if(end_rote==3){
            Intent intent = new Intent(getApplicationContext(), lesson.class);
            intent.putExtra("level",level);
            startActivity(intent);
            finish();
        }

    }
}