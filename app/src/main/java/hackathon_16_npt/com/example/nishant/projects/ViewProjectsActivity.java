package hackathon_16_npt.com.example.nishant.projects;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.net.MalformedURLException;

public class ViewProjectsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private MobileServiceClient mClient;
    private MobileServiceTable<ResearchProject> mResearchProjectTable;
    private ResearchProjectAdapter mAdapter;
    private MobileServiceList<ResearchProject> result;
    private static final String TAG = ViewProjectsActivity.class.getSimpleName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ActionBarDrawerToggle toggle;

    TextView nav_name_textView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_projects);

        /*
            Code for custom toolbar
        */

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        Profile p = new Profile();
        p.setName(sharedPreferences.getString("nameKey",""));
        p.setUsername(sharedPreferences.getString("usernameKey",""));
        p.setPassword(sharedPreferences.getString("passwordKey",""));
        p.setEmail(sharedPreferences.getString("emailKey",""));
        p.setPhone_no(sharedPreferences.getString("phoneKey",""));
        p.setDesignation(sharedPreferences.getString("designationKey",""));
        p.setQualification(sharedPreferences.getString("qualificationsKey",""));
        p.setInterest1(sharedPreferences.getString("interest1Key",""));
        p.setInterest2(sharedPreferences.getString("interest2Key",""));
        p.setInterest3(sharedPreferences.getString("interest3Key",""));
        p.setProfilePicURL(sharedPreferences.getString("profilePicURLKey",""));

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setIcon(R.drawable.ic_chrome_reader_mode_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        /*
            Code to check for active internet connection
        */
        isInternetAvailable(ViewProjectsActivity.this);

        /*
            Code to create MobileServiceClient and Table objects to read data from server
         */
        try {
            mClient = new MobileServiceClient("http://requiry.azurewebsites.net",this);
            mResearchProjectTable = mClient.getTable(ResearchProject.class);

        } catch (MalformedURLException e) {
            Toast.makeText(ViewProjectsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        /*
            Code to define values to adapter and ListView objects
         */
        mAdapter = new ResearchProjectAdapter(this, R.layout.project_list);
        ListView listViewToDo = (ListView) findViewById(R.id.listViewResearchProjects);
        listViewToDo.setAdapter(mAdapter);

        /*
            Code for function call to refresh list from database table
         */
        refreshItemsFromTable();

        /*
            Defining SwipeRefreshLayout object and adding functionality to refresh list from database
         */
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_viewprojects_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue,R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                public void onRefresh() {
                    isInternetAvailable(ViewProjectsActivity.this);
                    refreshItemsFromTable();
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, 3000);
                    }

                }
        });


        /*
            Code for redirection to create new project
         */

        /*THIS BUTTON HAS BEEN MOVED TO PROFILE.*/
        Button plus = (Button) findViewById(R.id.Bt_Create_Proj);
        plus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent createScreen = new Intent(ViewProjectsActivity.this, CreateActivity.class);
                startActivity(createScreen);
            }
        });

        /*
            Code for redirection to particular project page
         */
        listViewToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Intent createScreen = new Intent(ViewProjectsActivity.this, ProjectsActivity.class);
                createScreen.putExtra("Project",result.get(position));
                startActivity(createScreen);

            }

        });



        /*
            navigation Drawer Layout starts from here
        */

        NavigationView navigationView = (NavigationView) findViewById(R.id.navDrawer);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        TextView name = (TextView) headerLayout.findViewById(R.id.nav_name_textView);
        name.setText(p.getName());

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
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
                    result = mResearchProjectTable.orderBy("updatedAt", QueryOrder.Descending).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mAdapter.clear();

                            for(ResearchProject item : result){
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

    public void isInternetAvailable(Context context)
    {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            Log.d(TAG,"no internet");
            Toast.makeText(ViewProjectsActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        int textlength = newText.length();
        //MobileServiceList<ResearchProject> tempList = new MobileServiceList<ResearchProject>();
        mAdapter = new ResearchProjectAdapter(this, R.layout.project_list);
        for(ResearchProject item: result){
            if (textlength <= item.getProjectName().length()) {
                if (item.getProjectName().toLowerCase().contains(newText.toString().toLowerCase()) || item.getDescription().toLowerCase().contains(newText.toString().toLowerCase()) || item.getAuthorName().toLowerCase().contains(newText.toString().toLowerCase())) {
                    mAdapter.add(item);
                }
            }
        }
        ListView listViewToDo = (ListView) findViewById(R.id.listViewResearchProjects);
        listViewToDo.setAdapter(mAdapter);
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_menu_profile :
                Intent createScreen = new Intent(ViewProjectsActivity.this, NewProfileActivity.class);
                startActivity(createScreen);
        }

        switch(item.getItemId()){
            case R.id.nav_menu_viewproj :
                Intent createScreen = new Intent(ViewProjectsActivity.this, ViewProjectsActivity.class);
                startActivity(createScreen);
        }

        return false;

    }

    @Override
    public void onClick(View v) {

    }
}

