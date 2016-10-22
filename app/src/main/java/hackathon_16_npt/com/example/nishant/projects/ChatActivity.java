package hackathon_16_npt.com.example.nishant.projects;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    private MobileServiceClient mClient;
    private MobileServiceTable<Discussions> mDiscussionsTable;
    private DiscussionsAdapter mAdapter;
    private MobileServiceList<Discussions> result;
    private String name;

    private Timer timer;
    private TimerTask timerTask;

    final Handler handler = new Handler();

    EditText message;
    Button sendBtn;

    private Discussions d;
    private Profile p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        p = new Profile();
        p.setName(sharedPreferences.getString("nameKey",""));

        Intent i = getIntent();
        name = (String) i.getSerializableExtra("Project");

        /**TOOLBAR CODES**/
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setIcon(R.drawable.ic_chrome_reader_mode_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        /*
            Code to create MobileServiceClient and Table objects to read data from server
         */
        try {
            mClient = new MobileServiceClient("http://requiry.azurewebsites.net",this);
            mDiscussionsTable = mClient.getTable(Discussions.class);

        } catch (MalformedURLException e) {
            Toast.makeText(DiscussionsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        /*
            Code to define values to adapter and ListView objects
         */
        mAdapter = new DiscussionsAdapter(this, R.layout.discussions_message);
        ListView listViewToDo = (ListView) findViewById(R.id.msgview);
        listViewToDo.setAdapter(mAdapter);

        message = (EditText) findViewById(R.id.msg);

        startTimer();
        refreshItemsFromTable();

    private void sendMessage(){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                d = new Discussions();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        d.setProject(name);
                        d.setMessage(message.getText().toString());
                        d.setUsername(p.getName());
                        mClient.getTable(Discussions.class).insert(d, new TableOperationCallback<Discussions>() {
                            @Override
                            public void onCompleted(Discussions entity, Exception exception, ServiceFilterResponse response) {

                            }
                        });
                        message.setText("");
                    }
                });
                return null;
            }
        }.execute();
    }
    private void refreshItemsFromTable() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    result = mDiscussionsTable.where().field("project").eq(name).orderBy("updatedAt", QueryOrder.Ascending).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mAdapter.clear();

                            for(Discussions item : result){
                                mAdapter.add(item);
                            }
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

}
