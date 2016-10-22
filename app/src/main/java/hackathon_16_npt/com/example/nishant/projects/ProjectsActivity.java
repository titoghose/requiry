package hackathon_16_npt.com.example.nishant.projects;

import android.app.Activity;

import java.net.MalformedURLException;

import hackathon_16_npt.com.example.nishant.projects.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class ProjectsActivity extends AppCompatActivity {

    private MobileServiceClient mClient;
	private MobileServiceTable<ResearchProject> mToDoTable;

    private ResearchProjectAdapter mAdapter;

    private String id;
    private String projectName;
    private String authorName;
    private String description;
    private String emailId;
    private String contactNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        Intent i = getIntent();
        ResearchProject rp = (ResearchProject) i.getSerializableExtra("Project");

        /*Toolbar my_toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(my_toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(rp.getProjectName());
        getSupportActionBar().setIcon(R.drawable.ic_mode_edit_black_24dp);*/

        id=rp.getId();
        projectName=rp.getProjectName();
        authorName=rp.getAuthorName();
        description=rp.getDescription();
        emailId=rp.getEmailId();
        contactNum=rp.getContactNum();

        TextView PN_tv = (TextView) findViewById(R.id.PN_value);
        TextView A_tv = (TextView) findViewById(R.id.A_value);
        TextView D_tv = (TextView) findViewById(R.id.D_value);
        TextView E_tv = (TextView) findViewById(R.id.E_value);
        TextView C_tv = (TextView) findViewById(R.id.C_value);


        PN_tv.setText(projectName);
        A_tv.setText(authorName);
        D_tv.setText(description);
        E_tv.setText(emailId);
        C_tv.setText(contactNum);


        Button apply = (Button) findViewById(R.id.Btn_Apply);
        apply.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                    sendEmail();
                }

        });

        ImageButton back = (ImageButton) findViewById(R.id.Btn_Back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent createIntent = new Intent(ProjectsActivity.this, ViewProjectsActivity.class);
                startActivity(createIntent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent nextScreen = new Intent(ProjectsActivity.this, ViewProjectsActivity.class);
        startActivity(nextScreen);
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {emailId};
        String[] CC = {""};
        String sample="Dear "+authorName+",\n\n(Your email goes here)\n\n(Please upload your CV and Cover Letter as attachments)\n\nsent via Requiry";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application For : "+projectName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, sample);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Email Sent", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ProjectsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
