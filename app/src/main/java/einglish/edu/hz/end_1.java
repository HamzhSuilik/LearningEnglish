package einglish.edu.hz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class end_1 extends AppCompatActivity {

    private int level;
    private int lesson;
    private ImageView img;
    private TextView txt;

    private long time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_1);


        // ***********************
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
        // **********************

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$
        img=findViewById(R.id.final_img);
        txt=findViewById(R.id.final_txt);
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$


        Bundle paste = getIntent().getExtras();
        level = paste.getInt("level");
        lesson = paste.getInt("lesson");

        if(lesson==5){
            img.setImageResource(R.drawable.quiz);
            txt.setText("الإختبار النهائي");
        }


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

    public void test(View view) {

        Intent go = new Intent(this,test.class);
        go.putExtra("level",level);
        go.putExtra("lesson",lesson);
        finish();
        startActivity(go);

    }

    public void re_play(View view) {

        Intent go = new Intent(this,word.class);
        go.putExtra("level",level);
        go.putExtra("lesson",lesson);
        startActivity(go);
        finish();


    }

    public void list(View view) {
        Intent go = new Intent(this,lesson.class);
        go.putExtra("level",level);
        startActivity(go);
        finish();
    }

    public void next(View view) {

        if(lesson!=5){
            Intent go = new Intent(this,word.class);
            go.putExtra("level",level);
            go.putExtra("lesson",lesson+1);
            startActivity(go);
            finish();
        }else {
            Intent go = new Intent(this,test.class);
            go.putExtra("level",level);
            go.putExtra("lesson",0);
            startActivity(go);
            finish();
        }

    }
}
