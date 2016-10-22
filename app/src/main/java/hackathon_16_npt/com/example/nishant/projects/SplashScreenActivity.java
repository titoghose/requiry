package hackathon_16_npt.com.example.nishant.projects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by DELL on 10/5/2016.
 */
public class SplashScreenActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final TextView textView_Re = (TextView) findViewById(R.id.Re);
        final TextView textView_search = (TextView) findViewById(R.id.search);
        final TextView textView_En = (TextView) findViewById(R.id.En);
        final TextView textView_quiry = (TextView) findViewById(R.id.quiry);
        final Animation anim_fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        final Animation anim_fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final Animation anim_moveup = AnimationUtils.loadAnimation(this, R.anim.moveup);
        final Animation anim_movedown = AnimationUtils.loadAnimation(this, R.anim.movedown);
        textView_En.setAnimation(anim_fadeout);
        anim_fadeout.start();
        textView_quiry.setAnimation(anim_fadeout);
        anim_fadeout.start();
        textView_Re.setAnimation(anim_fadein);
        anim_fadein.start();
        textView_search.setAnimation(anim_fadein);
        anim_fadein.start();


        //Working Splash Code
        int secondsDelayed = 1;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);

    }
}





