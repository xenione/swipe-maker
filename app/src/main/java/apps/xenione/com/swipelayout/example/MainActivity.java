package apps.xenione.com.swipelayout.example;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.fragment.BothSideSwipeFragment;
import apps.xenione.com.swipelayout.example.fragment.HalfRightDragSwipeFragment;
import apps.xenione.com.swipelayout.example.fragment.HalfRightSwipeFragment;
import apps.xenione.com.swipelayout.example.fragment.RightSwipeFragment;
import apps.xenione.com.swipelayout.example.fragment.TwoStepRightSwipeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment(RightSwipeFragment.newInstance(), RightSwipeFragment.TAG);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_rightswipe) {
            showFragment(RightSwipeFragment.newInstance(), RightSwipeFragment.TAG);
        } else if (id == R.id.nav_half_swipe) {
            showFragment(HalfRightSwipeFragment.newInstance(), HalfRightSwipeFragment.TAG);
        } else if (id == R.id.nav_two_step_swipe) {
            showFragment(TwoStepRightSwipeFragment.newInstance(), TwoStepRightSwipeFragment.TAG);
        } else if (id == R.id.nav_both_side_swipe) {
            showFragment(BothSideSwipeFragment.newInstance(), BothSideSwipeFragment.TAG);
        } else if(id==R.id.nav_right_next_swipe){
            showFragment(HalfRightDragSwipeFragment.newInstance(), HalfRightDragSwipeFragment.TAG);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment prevFragment = fm.findFragmentByTag(tag);
        if (prevFragment != null) {
            return;
        }
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.fragment_container, fragment, tag)
                .commit();
    }
}
