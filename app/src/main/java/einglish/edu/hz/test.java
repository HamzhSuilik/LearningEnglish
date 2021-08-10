package einglish.edu.hz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import java.util.Random;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

import java.util.Locale;

public class test extends AppCompatActivity {


    private int []numbers;
    Random roo = new Random();

    private int level;
    private int lesson;
    private int available;
    private int question;
    private int random;
    private ImageView img;
    private TextView ar;
    private TextView k1;
    private TextView k2;
    private TextView k3;
    private TextView k4;
    private int correct;
    private int selected;
    private Button next;
    private int state;
    private boolean answer;
    private int try_num;
    private ImageView hart1;
    private ImageView hart2;
    private ImageView hart3;
    private ImageView hart4;
    private int current;
    private TextView num;

    private long time;

    // ads
    private InterstitialAd mInterstitialAd;
    private int choose;
    private int end_rote;

    // banar ads

    AdView mAdView;
    private int choose2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // ################
        img=findViewById(R.id.test_img);
        ar=findViewById(R.id.text_ar);
        k1=findViewById(R.id.k1);
        k2=findViewById(R.id.k2);
        k3=findViewById(R.id.k3);
        k4=findViewById(R.id.k4);
        next=findViewById(R.id.next);
        hart1=findViewById(R.id.hart1);
        hart2=findViewById(R.id.hart2);
        hart3=findViewById(R.id.hart3);
        hart4=findViewById(R.id.hart4);
        num=findViewById(R.id.num);
        //##################

        // ***********************
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
        // **********************


        Bundle paste = getIntent().getExtras();
        level = paste.getInt("level");
        lesson = paste.getInt("lesson");


        // $$$$$$$$$$$$$$$$$$$$$

        // InterstitialAd

        end_rote=1;

        SharedPreferences gg = getSharedPreferences("file1", Context.MODE_PRIVATE);
        choose=gg.getInt("ad_id",gg.getInt("rand88",1));

        String [] item = getResources().getStringArray(R.array.test_ad);

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


        // $$$$$$$$$$$$$$$$$
        // banar ad
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        SharedPreferences gg5 = getSharedPreferences("file1", Context.MODE_PRIVATE);
        choose2=gg5.getInt("ad_id",gg5.getInt("rand88",1));



        if(choose2==0)
            mAdView = findViewById(R.id.test_ad_0);

        if(choose2==1)
            mAdView = findViewById(R.id.test_ad_1);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // $$$$$$$$$$$$$$$$$


        // $$$$$$$$$$$$$$$$$$$$$



        /*
            Toast sms0= Toast.makeText(this,available+"",Toast.LENGTH_SHORT);
            sms0.show();
         */

        state=1;
        answer=true;
        try_num=4;
        current=1;

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        if (lesson!=0){
            available=available_num(level,lesson);
            question=available;

            numbers=new int[question];
            for (int i=question-1;i>-1;i--){
                numbers[i]=0;
            }


           new_question();

        }
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        if (lesson==0){
            available=available_num(level,lesson);
            question=25;

            numbers=new int[question];
            for (int i=question-1;i>-1;i--){
                numbers[i]=0;
            }

            new_question_0();

        }
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        num.setText(current+"/"+question);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder sms = new AlertDialog.Builder(this);
        sms.setMessage("هل تريد العودة إلى الصفحة الرئيسية")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mInterstitialAd.isLoaded()) {
                            end_rote=5;
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

    public boolean check(int num){

        boolean re;
        re=true;

        for (int i=0;i<available;i++){

            if(numbers[i]==num){
                i=99;
                re=false;
            }else {


                if (numbers[i] == 0) {
                    numbers[i] = num;
                    i = 99;
                    re = true;
                }

            }
        }

        return re;
    }

    public int get_rand(){
        int randome=roo.nextInt(available);
        boolean key;
        key=false;

        while (key ==false){
            randome++;
            if(randome>available)
                randome=1;
            key=check(randome);
        }
        return randome;
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

    public int lesson_0(int level , int rand){
        int re=0;
         int level_1[]={1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5};
         int level_2[]={1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5};
         int level_3[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};
         int level_4[]={1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5,5};
         int level_5[]={1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};
         int level_6[]={1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5};
         int level_7[]={1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5};
         int level_8[]={1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5};
         int level_9[]={1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,5,5,5,5,5,5};

         if(level==1)
             re=level_1[rand];

        if(level==2)
            re=level_2[rand];

        if(level==3)
            re=level_3[rand];

        if(level==4)
            re=level_4[rand];

        if(level==5)
            re=level_5[rand];

        if(level==6)
            re=level_6[rand];


        if(level==7)
            re=level_7[rand];

        if(level==8)
            re=level_8[rand];

        if(level==9)
            re=level_9[rand];


        return re;
    }

    public int word_0(int level , int rand){
        int re=0;


        int level_1[]={1,2,3,4,5,6,7,8,9,10,11,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,11,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        int level_2[]={1,2,3,4,5,1,2,3,4,5,6,7,8,9,10,11,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8};
        int level_3[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
        int level_4[]={1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,1,2,3,4,5,6,7,8,9,10,11,12,13,14,1,2,3,4,5,6,7,8,9,10,11,12,13};
        int level_5[]={1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        int level_6[]={1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,9};
        int level_7[]={1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,1,2,3,4,5,6,7,8,9,10,11,12,13,14,1,2,3,4,5,6,7};
        int level_8[]={1,2,3,4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,11,1,2,3,4,5,6,7,8};
        int level_9[]={1,2,3,4,5,6,7,8,9,10,11,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,1,2,3,4,5,6};

        if(level==1)
            re=level_1[rand];

        if(level==2)
            re=level_2[rand];

        if(level==3)
            re=level_3[rand];

        if(level==4)
            re=level_4[rand];

        if(level==5)
            re=level_5[rand];

        if(level==6)
            re=level_6[rand];


        if(level==7)
            re=level_7[rand];

        if(level==8)
            re=level_8[rand];

        if(level==9)
            re=level_9[rand];

        return re-1;
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

    public void new_question(){

        random=get_rand()-1;


        ar.setText(AR(level,lesson,random));

        img.setImageResource(IMG(level,lesson,random));

        if((random+1)<available){
            k1.setText(EN(level,lesson,random+1));

        }else {
            k1.setText(EN(level,lesson,0));
        }


        if((random+2)<available){
            k2.setText(EN(level,lesson,random+2));
        }else {
            k2.setText(EN(level,lesson,(random+2)-available));
        }


        if((random+3)<available){
            k3.setText(EN(level,lesson,random+3));
        }else {
            k3.setText(EN(level,lesson,(random+3)-available));
        }

        if((random+4)<available){
            k4.setText(EN(level,lesson,random+4));
        }else {
            k4.setText(EN(level,lesson,(random+4)-available));
        }




        Random r1 = new Random();
        int num1=r1.nextInt(38);
        num1++;

        correct=(num1%4)+1;


        if(correct==1){
            k1.setText(EN(level,lesson,random));
        }

        if(correct==2){
            k2.setText(EN(level,lesson,random));
        }

        if(correct==3){
            k3.setText(EN(level,lesson,random));
        }

        if(correct==4){
            k4.setText(EN(level,lesson,random));
        }

        txt_size();

    }

    public void new_question_0(){

        random=get_rand()-1;


        ar.setText(AR(level,lesson_0(level,random),word_0(level,random)));

        img.setImageResource(IMG(level,lesson_0(level,random),word_0(level,random)));




        if((random+1)<available){
            k1.setText(EN(level,lesson_0(level,random+1),word_0(level,random+1)));
        }else {
            k1.setText(EN(level,1,0));
        }


        if((random+2)<available){
            k2.setText(EN(level,lesson_0(level,random+2),word_0(level,random+2)));
        }else {
            k2.setText(EN(level,1,1));
        }


        if((random+3)<available){
            k3.setText(EN(level,lesson_0(level,random+3),word_0(level,random+3)));
        }else {
            k3.setText(EN(level,1,2));
        }

        if((random+4)<available){
            k4.setText(EN(level,lesson_0(level,random+4),word_0(level,random+4)));
        }else {
            k4.setText(EN(level,1,3));
        }

        Random r1 = new Random();
        int num1=r1.nextInt(38);
        num1++;

        correct=(num1%4)+1;


        if(correct==1){
            k1.setText(EN(level,lesson_0(level,random),word_0(level,random)));
        }

        if(correct==2){
            k2.setText(EN(level,lesson_0(level,random),word_0(level,random)));
        }

        if(correct==3){
            k3.setText(EN(level,lesson_0(level,random),word_0(level,random)));
        }

        if(correct==4){
            k4.setText(EN(level,lesson_0(level,random),word_0(level,random)));
        }

        txt_size();

    }

    public void k1(View view) {
        click(1);
    }

    public void k2(View view) {
        click(2);
    }

    public void k3(View view) {
        click(3);
    }

    public void k4(View view) {
        click(4);
    }

    public void next(View view) {

        if(state==1){
            correction();
        }else {
            next_question();
        }

    }


    public void correction(){
        next.setText("التالي");

        k1.setClickable(false);
        k2.setClickable(false);
        k3.setClickable(false);
        k4.setClickable(false);



        if(correct==1)
            k1.setBackgroundResource(R.drawable.bt_g);

        if(correct==2)
            k2.setBackgroundResource(R.drawable.bt_g);

        if(correct==3)
            k3.setBackgroundResource(R.drawable.bt_g);

        if(correct==4)
            k4.setBackgroundResource(R.drawable.bt_g);

        if(selected==correct){
            answer=true;
        }else {
            answer=false;
        }

        if(answer==true){
            img.setImageResource(R.drawable.a_4_1_6);
        }

        if (answer==false){
            img.setImageResource(R.drawable.a_4_1_1);

            if (selected==1)
                k1.setBackgroundResource(R.drawable.bt_r);

            if (selected==2)
                k2.setBackgroundResource(R.drawable.bt_r);

            if (selected==3)
                k3.setBackgroundResource(R.drawable.bt_r);

            if (selected==4)
                k4.setBackgroundResource(R.drawable.bt_r);

            try_num--;

            if(try_num==3)
                hart4.setImageResource(R.drawable.falseee);

            if(try_num==2)
                hart3.setImageResource(R.drawable.falseee);

            if(try_num==1)
                hart2.setImageResource(R.drawable.falseee);

            if(try_num==0)
                hart1.setImageResource(R.drawable.falseee);

        }




        state=2;
    }

    public void next_question(){

        next.setVisibility(View.INVISIBLE);

        current++;

        next.setText("تأكيد");
        k1.setClickable(true);
        k2.setClickable(true);
        k3.setClickable(true);
        k4.setClickable(true);

        k1.setBackgroundResource(R.drawable.bt2);
        k2.setBackgroundResource(R.drawable.bt2);
        k3.setBackgroundResource(R.drawable.bt2);
        k4.setBackgroundResource(R.drawable.bt2);


        if ((current)==(question+1)){
            if (mInterstitialAd.isLoaded()) {
                end_rote=1;
                mInterstitialAd.show();
            }else {
                Intent go = new Intent(this, win.class);
                go.putExtra("level", level);
                go.putExtra("lesson", lesson);
                startActivity(go);
                finish();
            }
        }else{

            if(try_num!=0) {
                num.setText(current + " / " + question);

                if (lesson == 0) {
                    new_question_0();
                } else {
                    new_question();
                }
            }
        }

        if(try_num==0){

            if(lesson!=0) {
                if (mInterstitialAd.isLoaded()) {
                    end_rote=2;
                    mInterstitialAd.show();
                }else {
                    Intent go = new Intent(this, lose.class);
                    go.putExtra("level", level);
                    go.putExtra("lesson", lesson);
                    startActivity(go);
                    finish();
                }
            }

            if(lesson==0) {

                if (mInterstitialAd.isLoaded()) {
                    end_rote=3;
                    mInterstitialAd.show();
                }else {
                    Intent go = new Intent(this, lose_2.class);
                    go.putExtra("level", level);
                    go.putExtra("lesson", lesson);
                    startActivity(go);
                    finish();
                }
            }

        }


        state=1;
    }


    public void click(int i){
        k1.setBackgroundResource(R.drawable.bt2);
        k2.setBackgroundResource(R.drawable.bt2);
        k3.setBackgroundResource(R.drawable.bt2);
        k4.setBackgroundResource(R.drawable.bt2);

        selected=i;

        if (i==1)
            k1.setBackgroundResource(R.drawable.bt_y);

        if (i==2)
            k2.setBackgroundResource(R.drawable.bt_y);

        if (i==3)
            k3.setBackgroundResource(R.drawable.bt_y);

        if (i==4)
            k4.setBackgroundResource(R.drawable.bt_y);

        next.setVisibility(View.VISIBLE);
        next.setText("تأكيد");
        state=1;


    }

    public void home(View view) {

        AlertDialog.Builder sms = new AlertDialog.Builder(this);
        sms.setMessage("هل تريد العودة إلى الصفحة الرئيسية")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mInterstitialAd.isLoaded()) {
                            end_rote=4;
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

    public void txt_size(){

        k1.setTextSize(28f);
        k2.setTextSize(28f);
        k3.setTextSize(28f);
        k4.setTextSize(28f);

        // ****************

        if(k1.getText().toString().length()>9){
            k1.setTextSize(24f);
        }

        if(k2.getText().toString().length()>9){
            k2.setTextSize(24f);
        }

        if(k3.getText().toString().length()>9){
            k3.setTextSize(24f);
        }

        if(k4.getText().toString().length()>9){
            k4.setTextSize(24f);
        }

        // ****************

        if(k1.getText().toString().length()>14){
            k1.setTextSize(20f);
        }

        if(k2.getText().toString().length()>14){
            k2.setTextSize(20f);
        }

        if(k3.getText().toString().length()>14){
            k3.setTextSize(20f);
        }

        if(k4.getText().toString().length()>14){
            k4.setTextSize(20f);
        }
    }

    public void setEnd_rote(){

        if(end_rote==1){
            Intent go = new Intent(this, win.class);
            go.putExtra("level", level);
            go.putExtra("lesson", lesson);
            startActivity(go);
            finish();
        }

        if(end_rote==2){
            Intent go = new Intent(this, lose.class);
            go.putExtra("level", level);
            go.putExtra("lesson", lesson);
            startActivity(go);
            finish();
        }

        if(end_rote==3){
            Intent go = new Intent(this, lose_2.class);
            go.putExtra("level", level);
            go.putExtra("lesson", lesson);
            startActivity(go);
            finish();
        }

        if(end_rote==4){
            Intent intent = new Intent(getApplicationContext(), level.class);
            startActivity(intent);
            finish();
        }


        if(end_rote==5){
            Intent intent = new Intent(getApplicationContext(), level.class);
            startActivity(intent);
            finish();
        }
    }


}
