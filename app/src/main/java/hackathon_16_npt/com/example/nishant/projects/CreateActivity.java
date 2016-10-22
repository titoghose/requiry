package hackathon_16_npt.com.example.nishant.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateActivity extends AppCompatActivity {

    EditText proj_Name;
    EditText author_Name;
    EditText desc;
    EditText email_Id;
    EditText contact_Num;
    Button submit;

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(my_toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageButton back = (ImageButton) findViewById(R.id.Btn_back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent nextScreen = new Intent(CreateActivity.this, ViewProjectsActivity.class);
                startActivity(nextScreen);
            }

        });
        try {
            mClient = new MobileServiceClient("http://requiry.azurewebsites.net", this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        proj_Name = (EditText) findViewById(R.id.proj_Name);
        author_Name = (EditText) findViewById(R.id.author_Name);
        desc = (EditText) findViewById(R.id.description);
        email_Id = (EditText) findViewById(R.id.email);
        contact_Num = (EditText) findViewById(R.id.contact_Num);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ResearchProject p1 = new ResearchProject();
                p1.setProjectName(proj_Name.getText().toString());
                p1.setAuthorName(author_Name.getText().toString());
                p1.setDescription(desc.getText().toString());
                p1.setEmailId(email_Id.getText().toString());
                p1.setContactNum(contact_Num.getText().toString());

                if(checkEmpty(proj_Name) || checkEmpty(author_Name) || checkEmpty(desc) || checkEmpty(email_Id) || checkEmpty(contact_Num)) {
                    Toast.makeText(CreateActivity.this, "Cannot leave fields empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!emailValidator(p1.getEmailId())) {
                        email_Id.requestFocus();
                        email_Id.setError("Invalid Email");
                    }
                    if (!numberValidater(p1.getContactNum())) {
                        contact_Num.requestFocus();
                        contact_Num.setError("Invalid Contact");
                    }
                    if (emailValidator(p1.getEmailId()) && numberValidater(p1.getContactNum())) {
                        mClient.getTable(ResearchProject.class).insert(p1, new TableOperationCallback<ResearchProject>() {

                            public void onCompleted(ResearchProject entity, Exception exception, ServiceFilterResponse response) {
                                if (exception == null) {
                                    Toast.makeText(CreateActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CreateActivity.this, "Error Happened", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        proj_Name.setText("");
                        author_Name.setText("");
                        desc.setText("");
                        email_Id.setText("");
                        contact_Num.setText("");
                        Intent nextScreen = new Intent(CreateActivity.this, ViewProjectsActivity.class);
                        startActivity(nextScreen);
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent nextScreen = new Intent(CreateActivity.this, ViewProjectsActivity.class);
        startActivity(nextScreen);
    }

    private boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean numberValidater(String contactNum){
        if(contactNum.length()==10)
            return true;
        else
            return false;
    }

    private boolean checkEmpty(EditText txt){
        return txt.getText().toString().trim().length()==0;
    }
}

