package games.picup.com.picup;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
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
    static MapFragment map;
    private static double[] latlon = {0.0,0.0};
    public static Context context;
    private final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;
    public static String uID = "";
    public static String Location = "Crom";
    boolean first = true;
    static double tLX = 0, tLR = 0, bLX = 0, bLR = 0, tRX = 0, tRR = 0, bRX = 0, bRR = 0, cX = 0, cY = 0, zoomSize = 17.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        context = getApplicationContext();
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
        uID = ""; //Clear the uID
        SharedPreferences sets = getSharedPreferences(LoginActivity.PREFFS, 0); // 0 - for private mode
        SharedPreferences.Editor editor = sets.edit();
        sets.edit().clear().commit();
        //Set "hasLoggedIn" to false
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

    public void toGame(int gameID){
        Log.println(1,"debugz","0");
        //set id as the selected ID
        showGameDetails.gameID = gameID;
        Intent i = new Intent(GameList.this, showGameDetails.class);
        startActivity(i);
    }

    private void bundles() {
        Bundle e1 = getIntent().getExtras();
        if (e1 != null) {
            //String spo = e1.getString("SPORT");
            //String loc = e1.getString("LOCATION");
            //String date = e1.getString("DATE");
            uID = e1.getString("uID");
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
        if(first)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(27.6200, 75.1500), 2)); //Sikar, India
        // Polylines are useful for marking paths and routes on the map.
        first = false;
        setMapValues();
        map.addPolygon(new PolygonOptions().geodesic(true)
                        .add(new LatLng(tLX, tLR))  // top left
                        .add(new LatLng(bLX, bLR))  // bottom left
                        .add(new LatLng(bRX, bRR))  // bottom right
                        .add(new LatLng(tRX, tRR)) //top right
                        .add(new LatLng(tLX, tLR)) // top left
                        .fillColor(Color.RED)
        );
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cX, cY), (float)zoomSize)); //where Cromwell is

        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
    }

    public void setMapValues(){
        if(Location.equals("Crom")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.Crom_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        } else if(Location.equals("Brit")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.Brit_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        } else if(Location.equals("MCar")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.MCar_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        } else if(Location.equals("MCal")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.MCal_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        }
    }

    public static void changeField(String field){
        Location = field;
        LatLng latlng = new LatLng(cX,cY);
        if(field.equals("Crom"))
            latlng = new LatLng(34.022007,-118.287832);
        else if(field.equals("Brit"))
            latlng = new LatLng(34.023129, -118.287639);
        else if(field.equals("MCal"))
            latlng = new LatLng(34.026535,-118.282747);
        else if(field.equals("MCar"))
            latlng = new LatLng(34.020913,-118.283122);
        map.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17.0f));
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
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latLng.latitude+","+latLng.longitude+"&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}