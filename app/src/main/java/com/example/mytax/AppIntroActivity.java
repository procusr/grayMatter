package com.example.mytax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AppIntroActivity extends AppCompatActivity {

    private LinearLayout myLayout = null;
    private LinearLayout l1,l2;

    private Button welcome = null;
    private Animation uptodown,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        myLayout = (LinearLayout) findViewById(R.id.myLayout);

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AppIntroActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        });

        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);
    }
    public void main(View view)
    {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }
}
















       // setContentView(R.layout.activity_app_intro);
//        addSlide(AppIntroFragment.newInstance("First App Into", "First App Intro Details",
//                R.drawable.one, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
//        addSlide(AppIntroFragment.newInstance("Second App Into", "Second App Intro Details",
//                R.drawable.one, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
////        addSlide(AppIntroFragment.newInstance("Third App Into", "Third App Intro Details",
////                R.drawable.one, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
//    }
//
//    @Override
//    public void onDonePressed(Fragment currentFragment) {
//        super.onDonePressed(currentFragment);
//        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public void onSkipPressed(Fragment currentFragment) {
//        super.onSkipPressed(currentFragment);
//        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);
//        finish();
 //   }
//}