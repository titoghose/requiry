package hackathon_16_npt.com.example.nishant.projects;

/**
 * Created by DELL on 10/21/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class NewProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newprofile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        Profile p = new Profile();
        p.setName(sharedPreferences.getString("nameKey",""));
        p.setProfilePicURL(sharedPreferences.getString("profilePicURLKey",""));
        p.setUsername(sharedPreferences.getString("usernameKey",""));

        TextView username = (TextView) findViewById(R.id.username);
        username.setText(p.getUsername());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("INFO"));
        tabLayout.addTab(tabLayout.newTab().setText("CREATED PROJECTS"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        getSupportActionBar().setTitle(null);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.Btn_back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                Intent nextScreen = new Intent(NewProfileActivity.this, ViewProjectsActivity.class);
                startActivity(nextScreen);
            }

        });



        /**navigation Drawer Layout starts from here**/

        NavigationView navigationView = (NavigationView) findViewById(R.id.navDrawer);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        TextView name = (TextView) headerLayout.findViewById(R.id.nav_name_textView);
        name.setText(p.getName());

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_menu_profile :
                DrawerLayout mDrawerLayout;
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                mDrawerLayout.closeDrawers();

        }

        switch(item.getItemId()){
            case R.id.nav_menu_viewproj :
                DrawerLayout mDrawerLayout;
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                mDrawerLayout.closeDrawers();

        }

        return false;

    }

}