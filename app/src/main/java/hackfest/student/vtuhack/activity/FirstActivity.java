package hackfest.student.vtuhack.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import hackfest.student.vtuhack.R;

/**
 * Created by Srinidhi on 2015-10-17.
 */
public class FirstActivity extends AppCompatActivity implements FragmentDrawerStudent.FragmentDrawerListener{

    private Toolbar mToolbar;
    private FragmentDrawerStudent drawerFragment;
    String studentid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //studentid= (String) getIntent().getExtras().get("studentid");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawerStudent)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new ProfileFragment();
                title = getString(R.string.title_profile);
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                fragment = new CreditsFragment();
                title = getString(R.string.title_credits);
                Toast.makeText(getApplicationContext(),"Credits",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                fragment = new PerformanceFragment();
                title = getString(R.string.title_performance);
                Toast.makeText(getApplicationContext(),"Performance",Toast.LENGTH_SHORT).show();
                break;
            /*case 3:
                fragment = new SchoolsFragment();
                title = getString(R.string.title_school);
                Toast.makeText(getApplicationContext(),"school",Toast.LENGTH_SHORT).show();
                break;*/
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
