//package hackathon_16_npt.com.example.nishant.projects;


        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.support.design.widget.CollapsingToolbarLayout;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.NavigationView;
        import android.support.design.widget.Snackbar;
        import android.support.design.widget.TabLayout;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        //import android.support.v7.graphics.Palette;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import java.util.ArrayList;
        import java.util.List;
/*public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //ui control
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    CardAdapter adapter;
    List<Flower> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);

        collapsingToolbarLayout.setTitle("Name");
        setSupportActionBar(toolbar);



        NavigationView navigationView = (NavigationView) findViewById(R.id.navDrawer);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        initializeData();

        adapter = new CardAdapter(flowers);

        //set adapter
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "You clicked on the fab", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private void initializeData() {
        flowers = new ArrayList<>();
        flowers.add(new Flower("Your CoordinatorLayout is wrapping your DrawerLayout and NavigationView, which means the Coordinator is in control of how everything is laid out. You need to nest the Coordinator inside the drawer, like so:", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 2", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 3", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 4", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 5", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 6", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 7", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 8", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 9", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 10", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 11", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 12", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 13", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 14", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 15", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 16", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 17", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 18", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 19", R.drawable.ic_grade_black_24dp));
        flowers.add(new Flower("Info 20s", R.drawable.ic_grade_black_24dp));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_menu_viewproj :
                Intent createScreen = new Intent(ProfileActivity.this, ViewProjectsActivity.class);
                startActivity(createScreen);
        }

        switch(item.getItemId()){
            case R.id.nav_menu_profile :
                Intent createScreen = new Intent(ProfileActivity.this, ProjectsActivity.class);
                startActivity(createScreen);
        }
        return false;
    }
}*/