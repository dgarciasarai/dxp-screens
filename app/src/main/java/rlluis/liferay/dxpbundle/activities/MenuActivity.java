package rlluis.liferay.dxpbundle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

import rlluis.liferay.dxpbundle.R;


public class MenuActivity extends PushScreensActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

	private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    public void onClick(View v) {
        //startActivity(new Intent(this, ProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            //startActivity(new Intent(this, EventsActivity.class));
            startActivity(new Intent(this, ListaEventos.class));
        } else if (id == R.id.nav_form) {
            //startActivity(new Intent(this, FormActivity.class));
            startActivity(new Intent(this, ListaFormularios.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_webat) {
            startActivity(new Intent(this, ATWebContent.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadPortrait() {
        User user = SessionContext.getCurrentUser();

        View headerView = navigationView.getHeaderView(0);
        String text = "EOEOE";
        if (SessionContext.isLoggedIn()) {
            text = user.getFirstName() + " " + user.getLastName();
            ((TextView) headerView.findViewById(R.id.logged_user)).setText(text);
            headerView.findViewById(R.id.liferay_portrait).setOnClickListener(this);
        }
    }

    @Override
    protected Session getDefaultSession() {
        return null;
    }

    @Override
    protected void onPushNotificationReceived(final JSONObject jsonObject) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LiferayLogger.d("Push notification received! " + jsonObject);
                Snackbar.make(findViewById(android.R.id.content), "Reloading list...", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onErrorRegisteringPush(String message, Exception e) {
        e.printStackTrace();
    }

    @Override
    protected String getSenderId() {
        return getString(R.string.sender_id);
    }
}
