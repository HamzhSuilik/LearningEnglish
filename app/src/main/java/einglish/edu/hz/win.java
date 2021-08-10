package einglish.edu.hz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

public class win extends AppCompatActivity {


    private int level;
    private int lesson;
    private ImageView img;
    private TextView textView;

    private long time;

    // banar ads

    AdView mAdView;
    private int choose2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        // ***********************
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
        // **********************

        // $$$$$$$$$$$$$$$$$
        // banar ad
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        SharedPreferences gg5 = getSharedPreferences("file1", Context.MODE_PRIVATE);
        choose2=gg5.getInt("ad_id",gg5.getInt("rand88",1));



        if(choose2==0)
            mAdView = findViewById(R.id.win_ads_0);

        if(choose2==1)
            mAdView = findViewById(R.id.win_ads_1);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // $$$$$$$$$$$$$$$$$

        // $$$$$$$$$$$$$$$$$$$
        img=findViewById(R.id.img);

        textView=findViewById(R.id.txt_next);
        // $$$$$$$$$$$$$$$$$$$$

        Bundle paste = getIntent().getExtras();
        level = paste.getInt("level");
        lesson = paste.getInt("lesson");

        save_level();
        save_lesson();

        if(lesson==5){
            textView.setText("الإختبار النهائي");
            img.setImageResource(R.drawable.quiz);
        }

        if(lesson==0){
            textView.setText("المرحلة التالية");
            //img.setImageResource(R.drawable.next_level);
        }

    }

    @Override
    public void onBackPressed() {

        if(time + 2000 > System.currentTimeMillis()){
            Intent go = new Intent(this,level.class);
            startActivity(go);
            finish();
        }else {
            Toast.makeText(this,"اضغط مرة للخروج",Toast.LENGTH_SHORT).show();
        }
        time=System.currentTimeMillis();
    }

    public void save_level(){

        if(lesson==0){
            SharedPreferences gg = getSharedPreferences("save_file", Context.MODE_PRIVATE);
            int saved =gg.getInt("level",1);

            if((level+1)>saved){
                int i=level+1;
                SharedPreferences.Editor copy = gg.edit();
                copy.putInt("level",i);
                copy.apply();
            }
        }

    }


    public void save_lesson(){

            if(lesson!=0) {
                String level_key = "level_" + level;
                String lesson_key = "lesson_" + lesson;

                SharedPreferences gg = getSharedPreferences(level_key, Context.MODE_PRIVATE);
                SharedPreferences.Editor copy = gg.edit();
                copy.putBoolean(lesson_key, true);
                copy.apply();

            }
    }



    public void list(View view) {
        Intent go = new Intent(this,lesson.class);
        go.putExtra("level",level);
        startActivity(go);
        finish();
    }

    public void next(View view) {

        if(lesson<5){
            if(lesson!=0) {
                Intent go = new Intent(this, word.class);
                go.putExtra("level", level);
                go.putExtra("lesson", lesson + 1);
                startActivity(go);
                finish();
            }
        }

        if(lesson==5){
            Intent go = new Intent(this,test.class);
            go.putExtra("level",level);
            go.putExtra("lesson",0);
            startActivity(go);
            finish();
        }

        if(lesson==0){
            if(level!=9) {
                Intent go = new Intent(this, lesson.class);
                go.putExtra("level", level + 1);
                startActivity(go);
                finish();
            }else {
                Intent go = new Intent(this, level.class);
                startActivity(go);
                finish();
            }
        }

    }


    public void main(View view) {
        Intent go = new Intent(this, level.class);
        startActivity(go);
        finish();
    }
}
