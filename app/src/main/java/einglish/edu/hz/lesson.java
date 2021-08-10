package einglish.edu.hz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

public class lesson extends AppCompatActivity {

    private int level;

    private TextView txt;
    private TextView main_txt;

    private ImageView pass1;
    private ImageView pass2;
    private ImageView pass3;
    private ImageView pass4;
    private ImageView pass5;

    private Boolean check1;
    private Boolean check2;
    private Boolean check3;
    private Boolean check4;
    private Boolean check5;

    private long time;

    // banar ads

    AdView mAdView;
    private int choose2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // $$$$$$$$$$$$$$$$$$$$
        pass1=findViewById(R.id.pass1);
        pass2=findViewById(R.id.pass2);
        pass3=findViewById(R.id.pass3);
        pass4=findViewById(R.id.pass4);
        pass5=findViewById(R.id.pass5);
        main_txt=findViewById(R.id.main_txt);
        //$$$$$$$$$$$$$$$$$$$$$

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
            mAdView = findViewById(R.id.lesson_ads_0);

        if(choose2==1)
            mAdView = findViewById(R.id.lesson_ads_1);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // $$$$$$$$$$$$$$$$$

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        Bundle paste = getIntent().getExtras();
        level = paste.getInt("level");

        set_sub();
        set_pass_img();
    }





    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder sms = new AlertDialog.Builder(this);
        sms.setMessage("هل تريد العودة إلى الصفحة الرئيسية")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), level.class);
                        startActivity(intent);
                        finish();

                    }
                })

                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }






    public void goo(View view) {

        Intent go = new Intent(this,test.class);
        go.putExtra("level",level);
        go.putExtra("lesson",0);
        startActivity(go);
        finish();

    }

    public void lee1(View view) {

        Intent go = new Intent(this,word.class);
        go.putExtra("level",level);
        go.putExtra("lesson",1);
        startActivity(go);
        finish();

    }

    public void lee2(View view) {

        Intent go = new Intent(this,word.class);
        go.putExtra("level",level);
        go.putExtra("lesson",2);
        startActivity(go);
        finish();

    }

    public void lee3(View view) {
        finish();
        Intent go = new Intent(this,word.class);
        go.putExtra("level",level);
        go.putExtra("lesson",3);
        startActivity(go);

    }

    public void lee4(View view) {
        finish();
        Intent go = new Intent(this,word.class);
        go.putExtra("level",level);
        go.putExtra("lesson",4);
        startActivity(go);

    }

    public void lee5(View view) {
        finish();
        Intent go = new Intent(this,word.class);
        go.putExtra("level",level);
        go.putExtra("lesson",5);
        startActivity(go);

    }

    public void set_sub(){

        if (level==1){

            main_txt.setText("المستوى الأول");

            txt=findViewById(R.id.sub1);
            txt.setText("الأرقام");

            txt=findViewById(R.id.sub2);
            txt.setText("الألوان");

            txt=findViewById(R.id.sub3);
            txt.setText("الحواس");

            txt=findViewById(R.id.sub4);
            txt.setText("المدرسة");

            txt=findViewById(R.id.sub5);
            txt.setText("الصفات");
        }



        if (level==2){

            main_txt.setText("المستوى الثاني");

            txt=findViewById(R.id.sub1);
            txt.setText("مواصلات");

            txt=findViewById(R.id.sub2);
            txt.setText("أماكن");

            txt=findViewById(R.id.sub3);
            txt.setText("الحيوانات");

            txt=findViewById(R.id.sub4);
            txt.setText("الطيور");

            txt=findViewById(R.id.sub5);
            txt.setText("رموز");
        }


        if (level==3){

            main_txt.setText("المستوى الثالث");

            txt=findViewById(R.id.sub1);
            txt.setText("الوظائف");

            txt=findViewById(R.id.sub2);
            txt.setText("أدوات المطبخ");

            txt=findViewById(R.id.sub3);
            txt.setText("الفاكهة");

            txt=findViewById(R.id.sub4);
            txt.setText(" الخضار و الحبوب");

            txt=findViewById(R.id.sub5);
            txt.setText("أفعال");
        }


        if (level==4){

            main_txt.setText("المستوى الرابع");

            txt=findViewById(R.id.sub1);
            txt.setText("المشاعر");

            txt=findViewById(R.id.sub2);
            txt.setText("المنزل");

            txt=findViewById(R.id.sub3);
            txt.setText("التلفاز");

            txt=findViewById(R.id.sub4);
            txt.setText("الغذاء");

            txt=findViewById(R.id.sub5);
            txt.setText("البلدان");
        }



        if (level==5){

            main_txt.setText("المستوى الخامس");

            txt=findViewById(R.id.sub1);
            txt.setText("الصحة");

            txt=findViewById(R.id.sub2);
            txt.setText("الأثاث");

            txt=findViewById(R.id.sub3);
            txt.setText("الحياة");

            txt=findViewById(R.id.sub4);
            txt.setText("التسوق");

            txt=findViewById(R.id.sub5);
            txt.setText("الطبيعة");
        }



        if (level==6){

            main_txt.setText("المستوى السادس");

            txt=findViewById(R.id.sub1);
            txt.setText("الرياضة");

            txt=findViewById(R.id.sub2);
            txt.setText("الجيش");

            txt=findViewById(R.id.sub3);
            txt.setText("الأدوات");

            txt=findViewById(R.id.sub4);
            txt.setText("الطقس");

            txt=findViewById(R.id.sub5);
            txt.setText(" العمل و الوظيفة");
        }



        if (level==7){

            main_txt.setText("المستوى السابع");

            txt=findViewById(R.id.sub1);
            txt.setText("البنايات، المنظمات");

            txt=findViewById(R.id.sub2);
            txt.setText("المدينة");

            txt=findViewById(R.id.sub3);
            txt.setText("الأشكال");

            txt=findViewById(R.id.sub4);
            txt.setText(" جسم الإنسان");

            txt=findViewById(R.id.sub5);
            txt.setText(" الملابس");
        }


        if (level==8){

            main_txt.setText("المستوى الثامن");

            txt=findViewById(R.id.sub1);
            txt.setText("الفضاء");

            txt=findViewById(R.id.sub2);
            txt.setText("الطاقة");

            txt=findViewById(R.id.sub3);
            txt.setText("الكوارث");

            txt=findViewById(R.id.sub4);
            txt.setText("مواد");

            txt=findViewById(R.id.sub5);
            txt.setText("كرة قدم");
        }


        if (level==9){

            main_txt.setText("المستوى التاسع");

            txt=findViewById(R.id.sub1);
            txt.setText("الحياة البحرية");

            txt=findViewById(R.id.sub2);
            txt.setText("البناء");

            txt=findViewById(R.id.sub3);
            txt.setText("أجهزة");

            txt=findViewById(R.id.sub4);
            txt.setText("العلماء");

            txt=findViewById(R.id.sub5);
            txt.setText("مجالات");
        }



    }

    public void set_pass_img(){

        String level_key = "level_" + level;


        SharedPreferences gg = getSharedPreferences(level_key, Context.MODE_PRIVATE);

        check1=gg.getBoolean("lesson_1",false);
        check2=gg.getBoolean("lesson_2",false);
        check3=gg.getBoolean("lesson_3",false);
        check4=gg.getBoolean("lesson_4",false);
        check5=gg.getBoolean("lesson_5",false);

        if (check1==true)
            pass1.setVisibility(View.VISIBLE);

        if (check2==true)
            pass2.setVisibility(View.VISIBLE);

        if (check3==true)
            pass3.setVisibility(View.VISIBLE);

        if (check4==true)
            pass4.setVisibility(View.VISIBLE);

        if (check5==true)
            pass5.setVisibility(View.VISIBLE);

    }


}
