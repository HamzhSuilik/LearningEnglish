package einglish.edu.hz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;





public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // InterstitialAd
/*
           mInterstitialAd = new InterstitialAd(this);
         mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
          mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/



        Intent go = new Intent(this,level.class);
        //go.putExtra("level",7);
        //go.putExtra("lesson",0);
        startActivity(go);
        finish();




    }

    public void goo(View view) {

    }
}
