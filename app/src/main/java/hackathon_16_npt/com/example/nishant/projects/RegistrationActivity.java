package hackathon_16_npt.com.example.nishant.projects;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationActivity extends Activity // definition of the class RegistrationActivity/,n
{
    private MobileServiceClient mClient;

    ImageView profilepic;
    Button upload;
    EditText name;
    EditText email;
    EditText phone;
    EditText username;
    EditText password;
    EditText qualification;
    EditText interest1;
    EditText interest2;
    EditText interest3;
    Button submit;
    String picturePath;
    private Spinner spinner;
    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;
    private String storageConnectionString;

    private static int RESULT_LOAD_IMAGE = 1;
    private URI imgURI;
    private Toolbar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /**BACKGROUND IMAGE CHANGE**/

       /* viewFlipper = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper1);
        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);
//sets auto flipping
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();*/

        /*ImageButton back = (ImageButton) findViewById(R.id.Btn_back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                Intent nextScreen = new Intent(RegistrationActivity.this, ViewProjectsActivity.class);
                startActivity(nextScreen);
            }

        });*/
        //Intent i = getIntent();
        //MobileServiceList<Profile> profiles = (MobileServiceList<Profile>) i.getSerializableExtra("Current Profile");

        ImageButton back = (ImageButton) findViewById(R.id.Btn_back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                Intent nextScreen = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(nextScreen);
            }

        });

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(my_toolbar);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        try {
            mClient = new MobileServiceClient("http://requiry.azurewebsites.net", this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        spinner = (Spinner) findViewById(R.id.prof_stud);
        // Create an ArrayAdapter using the string array and a default spinner layout
        String array[] = {"Professor", "Student"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.designation, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        profilepic = (ImageView) findViewById(R.id.profilepic);
        upload = (Button) findViewById(R.id.uploadBtn);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        qualification = (EditText) findViewById(R.id.qualification);
        phone = (EditText) findViewById(R.id.phone);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        interest1 = (EditText) findViewById(R.id.interest1);
        interest2 = (EditText) findViewById(R.id.interest2);
        interest3 = (EditText) findViewById(R.id.interest3);
        submit = (Button) findViewById(R.id.submit);

        /*
       int flag=0;
        for(Profile p:profiles){
            flag=0;
            if(p.getUsername().equals(username.getText().toString())){
                username.requestFocus();
                username.setError("Username not available");
                flag = 1;
                break;
            }
        }
        */


        ///final int finalFlag = flag;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile p = new Profile();
                p.setName(name.getText().toString());
                p.setDesignation(spinner.getSelectedItem().toString());
                p.setQualification(qualification.getText().toString());
                p.setEmail(email.getText().toString());
                p.setPhone_no(phone.getText().toString());
                p.setUsername(username.getText().toString());
                p.setPassword(password.getText().toString());
                p.setInterest1(interest1.getText().toString());
                p.setInterest2(interest2.getText().toString());
                p.setInterest3(interest3.getText().toString());
                runBlob();
                p.setProfilePicURL(String.valueOf(imgURI));

                if (checkEmpty(name) || checkEmpty(email) || checkEmpty(phone) || checkEmpty(username) || checkEmpty(password) || checkEmpty(interest1))
                    Toast.makeText(RegistrationActivity.this, "Cannot leave fields empty", Toast.LENGTH_SHORT).show();
                else {
                    if (!emailValidator(email.getText().toString())) {
                        email.requestFocus();
                        email.setError("Invalid Email");
                    }
                    if (!numberValidater(phone.getText().toString())) {
                        phone.requestFocus();
                        phone.setError("Invalid Contact");
                    }
                    if (emailValidator(email.getText().toString()) && numberValidater(phone.getText().toString()) /*&& finalFlag == 0*/) {
                        mClient.getTable(Profile.class).insert(p, new TableOperationCallback<Profile>() {
                            @Override
                            public void onCompleted(Profile entity, Exception exception, ServiceFilterResponse response) {
                                if (exception == null) {
                                    Toast.makeText(RegistrationActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
                                    Intent loginPage = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(loginPage);
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Error Happened", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_LOAD_IMAGE&&resultCode==RESULT_OK&&null!=data)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                profilepic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
    }

    @Override
    public void onBackPressed(){
        Intent loginPage = new Intent(RegistrationActivity.this,LoginActivity.class);
        startActivity(loginPage);
    }

    private void runBlob() {
       // new Blob(this,picturePath).execute();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                storageConnectionString = "DefaultEndpointsProtocol=https;"
                        + "AccountName=requirypics;"
                        + "AccountKey=4Ez1VAJr5CvNGxWArHyfs9+kUP978uh+J6ECycTv1K4dnhwYdTjCgio+rmKjc3OPMJmSPn+tufZ6o4HuVl8tfg==";

                try {
                    //Creating a reference to a blob account
                    CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);

                    //Creating a blob service client
                    CloudBlobClient blobClient = account.createCloudBlobClient();

                    //Creating reference to a container in the blob storage account
                    CloudBlobContainer container = blobClient.getContainerReference("profilepics");

                    container.createIfNotExists();

                    // Make the container public
                    // Create a permissions object
                    BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

                    // Include public access in the permissions object
                    containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

                    // Set the permissions on the container
                    container.uploadPermissions(containerPermissions);

                    CloudBlockBlob blob1 = container
                            .getBlockBlobReference("pic1.jpg");

                    File source = new File(picturePath);
                    // Upload text to the blob
                    blob1.upload(new FileInputStream(source), source.length());

                    imgURI = blob1.getUri();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
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




    public Toolbar getSupportActionBar() {
        return supportActionBar;
    }

    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }
} // end of the class definition