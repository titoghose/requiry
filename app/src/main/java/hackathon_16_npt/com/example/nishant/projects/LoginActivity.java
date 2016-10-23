package hackathon_16_npt.com.example.nishant.projects;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

public class LoginActivity extends AppCompatActivity {

    EditText User_Name;
    EditText User_Pass;
    Button SignIn;
    Button SignUp;

    private MobileServiceClient mClient;
    private MobileServiceTable<Profile> mProfileTable;
    private MobileServiceList<Profile> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        /*ImageButton back = (ImageButton) findViewById(R.id.Btn_back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                Intent nextScreen = new Intent(LoginActivity.this, ViewProjectsActivity.class);
                startActivity(nextScreen);
            }

        });*/
        try {
            mClient = new MobileServiceClient("http://requiry.azurewebsites.net", this);
            mProfileTable = mClient.getTable(Profile.class);
        } catch (MalformedURLException e) {
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        User_Name = (EditText) findViewById(R.id.TxtUserName);
        User_Pass = (EditText) findViewById(R.id.TxtPass);
        SignIn = (Button) findViewById(R.id.BtnSignIn);
        SignUp = (Button) findViewById(R.id.BtnSignUp);

        refreshItemsFromTable();

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile p = new Profile();
                p.setUsername(User_Name.getText().toString());
                p.setPassword(User_Pass.getText().toString());

                if (checkEmpty(User_Name) || checkEmpty(User_Pass)) {
                    Toast.makeText(LoginActivity.this, "Cannot leave fields empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    /* Check if the username and password match with each other or not*/
                    int flag = 0,i=0;
                    for (; i < result.size() ; i++) {
                        if (User_Name.getText().toString().equals(result.get(i).getUsername()) && User_Pass.getText().toString().equals(result.get(i).getPassword())) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        Toast.makeText(LoginActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SharedPreferences sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("nameKey", result.get(i).getName());
                        editor.putString("usernameKey", result.get(i).getUsername());
                        editor.putString("passwordKey", result.get(i).getPassword());
                        editor.putString("emailKey", result.get(i).getEmail());
                        editor.putString("phoneKey", result.get(i).getPhone_no());
                        editor.putString("interest1Key", result.get(i).getInterest1());
                        editor.putString("interest2Key", result.get(i).getInterest2());
                        editor.putString("interest3Key", result.get(i).getInterest3());
                        editor.putString("designationKey", result.get(i).getDesignation());
                        editor.putString("qualificationsKey", result.get(i).getQualification());
                        editor.putString("profilePicURLKey", result.get(i).getProfilePicURL());
                        editor.apply();
                        User_Name.setText("");
                        User_Pass.setText("");
                        Intent nextScreen = new Intent(LoginActivity.this, ViewProjectsActivity.class);
                        //nextScreen.putExtra("Current Profile",result.get(i));
                        startActivity(nextScreen);
                    }
                }
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regScreen = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(regScreen);
            }
        });



        /**EDIT TEXT ENTER PRESSED CODE**/
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void refreshItemsFromTable() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    result = mProfileTable.execute().get();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private boolean checkEmpty(EditText txt){
        return txt.getText().toString().trim().length()==0;
    }




}
