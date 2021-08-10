package einglish.edu.hz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Random;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class level extends AppCompatActivity {
    private ImageView img;
    private int level;
    long time;

    // ad adrese

    private int number;
    private int id;
    private boolean okk =true;
    private boolean check;
    private int value;

    // pre_get_adres

    private int rand;
    private boolean choose;
    Random roo = new Random();

    // banar ads

    AdView mAdView;
    private int choose2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        // ***********************
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
        // **********************

        // ##############################
        // ##############################

        if ("einglish.edu.hz"!=("einglish.ed"+"u.hz")){
            finish();
        }

        // ad adrese

        number=27;
        id=0;
        okk=true;
        value=99;



        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        SharedPreferences gg = getSharedPreferences("file1", Context.MODE_PRIVATE);
        check=gg.getBoolean("check",false);
        // ###############################


        boolean kt=gg.getBoolean("start_key",false);

        if(kt==false) {
            read();
            read2();
        }


        // $$$$$$$$$$$$$$$$$$$

        // pre_get_adres

        ads_random();

        // $$$$$$$$$$$$$$$$$$$


        // $$$$$$$$$$$$$$$$$
        // banar ad
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        SharedPreferences gg5 = getSharedPreferences("file1", Context.MODE_PRIVATE);
        choose2=gg5.getInt("ad_id",gg.getInt("rand88",1));



        if(choose2==0)
            mAdView = findViewById(R.id.addd0);

        if(choose2==1)
            mAdView = findViewById(R.id.addd1);


        AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);


        // $$$$$$$$$$$$$$$$$



        // ##############################
        // ##############################


/*
        SharedPreferences gg = getSharedPreferences("save_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor copy = gg.edit();
        copy.putInt("level",1);
        copy.apply();
*/


        SharedPreferences gg2 = getSharedPreferences("save_file", Context.MODE_PRIVATE);
        level=gg2.getInt("level",1);

        int[] res = {0,R.id.lock1,R.id.lock2,R.id.lock3,R.id.lock4,R.id.lock5,R.id.lock6,R.id.lock7
                ,R.id.lock8,R.id.lock9};


        for (int i=level;i>0;i--){

            if(i!=10) {
                img = findViewById(res[i]);
                img.setImageResource(R.drawable.tt);
            }
        }

        if(level!=10) {
            img = findViewById(res[level]);
            img.setImageResource(R.drawable.red);
        }


    }

    @Override
    public void onBackPressed() {

        check();

        if(time + 2000 > System.currentTimeMillis()){
            finish();
        }else {
            Toast.makeText(this,"اضغط مرة أخرى للخروج",Toast.LENGTH_SHORT).show();
        }
        time=System.currentTimeMillis();
    }


    public void goo1(View view) {
        check();

        if(level<1) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",1);
            startActivity(go);
            finish();
        }
    }

    public void goo2(View view) {
        check();

        if(level<2) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",2);
            startActivity(go);
            finish();
        }
    }

    public void goo3(View view) {
        check();

        if(level<3) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",3);
            startActivity(go);
            finish();
        }
    }


    public void goo4(View view) {
        check();

        if(level<4) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",4);
            startActivity(go);
            finish();
        }
    }

    public void goo5(View view) {
        check();

        if(level<5) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",5);
            startActivity(go);
            finish();
        }
    }

    public void goo6(View view) {
        check();

        if(level<6) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",6);
            startActivity(go);
            finish();
        }
    }


    public void goo7(View view) {
        check();

        if(level<7) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",7);
            startActivity(go);
            finish();
        }
    }

    public void goo8(View view) {
        check();

        if(level<8) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",8);
            startActivity(go);
            finish();
        }
    }

    public void goo9(View view) {
        check();

        if(level<9) {
            Toast sms = Toast.makeText(this, "عليك تخط المراحل السابقة اولاً", Toast.LENGTH_SHORT);
            sms.show();
        }else{
            Intent go = new Intent(this,lesson.class);
            go.putExtra("level",9);
            startActivity(go);
            finish();
        }
    }


    public void rate(View view) {
        check();

        Intent star5 =new Intent(Intent.ACTION_VIEW);
        star5.setData(Uri.parse("https://play.google.com/store/apps/details?id=einglish.edu.hz"));
        startActivity(star5);

    }

    public void share(View view) {
        check();

        Intent shear =new Intent(Intent.ACTION_SEND);
        shear.setType("text/plain");
        shear.putExtra(Intent.EXTRA_TEXT,
                "تطبيق تعلم الإنجليزية"
                        +"\n"+
                        "يحتوي على 500 كلمة مهمة لتعلم اللغة الإنجليزية مع صور و نطق للكلمات"
                        +"\n"+

                        "رابط التحميل من جوجل بلاي"

                        +"\n"+

                        "https://play.google.com/store/apps/details?id=einglish.edu.hz"

                );
        startActivity(shear);
    }

    // ads adres

    public void read(){



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("id");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SharedPreferences gg = getSharedPreferences("file1", Context.MODE_PRIVATE);
                SharedPreferences.Editor copy = gg.edit();
                copy.putInt("ad_id", dataSnapshot.getValue(int.class));
                copy.apply();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                //value = -9;

            }
        });



    }

    public void read2(){



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("number");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SharedPreferences gg = getSharedPreferences("file1", Context.MODE_PRIVATE);
                SharedPreferences.Editor copy = gg.edit();
                copy.putInt("number", dataSnapshot.getValue(int.class));
                copy.apply();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                //value = -9;

            }
        });



    }

    public void next(){

        int ad,num;

        SharedPreferences gg = getSharedPreferences("file1", Context.MODE_PRIVATE);

        int s1=gg.getInt("ad_id",1009);
        int s2=gg.getInt("number",1009);

        ad=s1;
        num=s2;

        if (ad==0){
            ad=1;
        }else{
            ad=0;
        }

        num++;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("id");
        myRef.setValue(ad);

        DatabaseReference myRef2 = database.getReference("number");
        myRef2.setValue(num);

        SharedPreferences gg2 = getSharedPreferences("file1", Context.MODE_PRIVATE);
        SharedPreferences.Editor copy = gg2.edit();
        copy.putBoolean("start_key",true);
        copy.apply();

    }

    public void check(){
        SharedPreferences gg = getSharedPreferences("file1", Context.MODE_PRIVATE);
        int s1=gg.getInt("ad_id",1009);
        boolean k=gg.getBoolean("start_key",false);

        if(s1!=1009&&k==false)
            next();
    }


    // pre_get_adres

    public void ads_random(){
        SharedPreferences fil = getSharedPreferences("save_file", Context.MODE_PRIVATE);
        choose=fil.getBoolean("choose88",false);

        if(choose==false){
            rand=roo.nextInt(2);

            // *********

            SharedPreferences gg = getSharedPreferences("save_file", Context.MODE_PRIVATE);
            SharedPreferences.Editor copy = gg.edit();
            copy.putInt("rand88",rand);
            copy.putBoolean("choose88",true);
            copy.apply();


        }

        //Toast.makeText(this,fil.getInt("rand88",99)+"",Toast.LENGTH_LONG).show();

    }
}
