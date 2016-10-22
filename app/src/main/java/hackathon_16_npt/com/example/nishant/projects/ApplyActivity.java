package hackathon_16_npt.com.example.nishant.projects;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ApplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        Button CV = (Button) findViewById(R.id.Btn_CV);
        CV.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                Intent nextScreen = new Intent(ApplyActivity.this, ViewProjectsActivity.class);
                startActivity(nextScreen);
            }

        });

        Button back = (Button) findViewById(R.id.Btn_Back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent nextScreen = new Intent(ApplyActivity.this, ProjectsActivity.class);
                startActivity(nextScreen);
            }

        });
    }
}
