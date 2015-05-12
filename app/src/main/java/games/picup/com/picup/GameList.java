package games.picup.com.picup;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */
public class GameList extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    Toolbar toolbar;
    Button FAB;
    RecyclerView mRecyclerView;
    GameAdapter mAdapter;
    MapFragment map;
    private static double[] latlon = {0.0,0.0};
    private final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Available Games");
//        setSupportActionBar(toolbar);
        setUpButton();

        map = new MapFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.add(R.id.container, map, "Map Fragment");
        trans.commit();

        // gets recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // manager for recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // animations for recyclerViews
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new GameAdapter(GameManager.getInstance().getGamesToPlay(), R.layout.card_view, this);
        mRecyclerView.setAdapter(mAdapter);

//        bundles();
        map.getMapAsync(this);
        addLogOutButton();
    }

    public void addLogOutButton(){
        android.widget.Button b1 = (android.widget.Button) findViewById(R.id.logout);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameList.this, LoginActivity.class);

                //now log out
                logOut();

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(i, SECONDARY_ACTIVITY_REQUEST_CODE);

                Toast toast = Toast.makeText(GameList.this, "Successfully Logged Out Bro", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void logOut(){
        SharedPreferences sets = getSharedPreferences(LoginActivity.PREFFS, 0); // 0 - for private mode
        SharedPreferences.Editor editor = sets.edit();
        sets.edit().clear().commit();
        //Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", false);
        // Commit the edits!
        editor.commit();
    }

    private void setUpButton() {
        FAB = (Button) findViewById(R.id.buttonFloat);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameList.this, NewGame.class);
                startActivity(i);
            }
        });
    }

    private void bundles() {
        Bundle e1 = getIntent().getExtras();
        if (e1 != null) {
            String spo = e1.getString("SPORT");
            String loc = e1.getString("LOCATION");
            String date = e1.getString("DATE");
        }
    }

//    private String[] addGames(String[] A){
//        Bundle e1 = getIntent().getExtras();
//        if(e1 != null) {
//            String spo = e1.getString("SPORT");
//            String loc = e1.getString("LOCATION");
//            String date = e1.getString("DATE");
//
//            for(int i = 0; i < A.length-1; i++){
//                A[i] = spo;
//            }
//        }
//        return A;
//    }

    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-18.142, 178.431), 2));
        // Polylines are useful for marking paths and routes on the map.
        map.addPolygon(new PolygonOptions().geodesic(true)
                        .add(new LatLng(42.375660, -72.536554))  // top left
                        .add(new LatLng(42.375192, -72.536619))  // bottom left
                        .add(new LatLng(42.375089, -72.535407))  // bottom right
                        .add(new LatLng(42.375573, -72.535345))  // top right
                        .add(new LatLng(42.375660, -72.536554)) // top left
                        .fillColor(Color.RED)
        );
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.375660, -72.536554), 15.0f));

        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
    }

    public void onBostonSoccerOne(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latLng.latitude+","+latLng.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}