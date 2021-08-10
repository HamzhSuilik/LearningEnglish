package einglish.edu.hz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

public class lose_2 extends AppCompatActivity {

    private int level;
    private int lesson;
    private long time;


    // banar ads

    AdView mAdView;
    private int choose2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_2);

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
            mAdView = findViewById(R.id.lose_2_ads_0);

        if(choose2==1)
            mAdView = findViewById(R.id.lose_2_ads_1);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // $$$$$$$$$$$$$$$$$


        Bundle paste = getIntent().getExtras();
        level = paste.getInt("level");
        lesson = paste.getInt("lesson");

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

    public void list(View view) {
        Intent go = new Intent(this, lesson.class);
        go.putExtra("level", level);
        startActivity(go);
        finish();
    }

    public void re_play(View view) {
        Intent go = new Intent(this, test.class);
        go.putExtra("level", level);
        go.putExtra("lesson", lesson);
        startActivity(go);
        finish();
    }
}
