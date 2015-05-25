package games.picup.com.picup;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;


public class showGameDetails extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    public static int gameID = 0;
    public String gameName = "Unable To Load Game's Name";
    public Game game;
    public String description = "Unable to Load Description";
    public String Location = "Example Location";
    public String datetime = "Example Date and Time";
    public int cPlayers = 0;
    public int tPlayers = 1;
    public String uID="";
    public ArrayList<String> uList = new ArrayList<String>();
    MapFragment map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_details);
        setAllData();
        setToolbar();
        setText();
        setButton();

        map = new MapFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.add(R.id.container, map, "Map Fragment");
        trans.commit();

        map.getMapAsync(this);
    }

    private void setAllData(){
        try {
            Bundle b = getIntent().getExtras();
            ArrayList<String> data = b.getStringArrayList("gamedata");
            gameName = data.get(0);
            datetime = data.get(1);
            Location = data.get(2);
            cPlayers = Integer.parseInt(data.get(3));
            tPlayers = Integer.parseInt(data.get(4));
            description = data.get(5);
            uID = data.get(6);
            uList = b.getStringArrayList("users");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addPlayer(String uID){
        uList.add(uID);
        cPlayers++;
    }

    public boolean hasPlayerRSVPd(String uID){
        for(int i = 0; i < uList.size(); i++){
            if(uList.get(i).equals(uID))
                return true; //then player is RSVP'd - side note: What does RSVP stand for?
        }
        return false;
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarshow);
        //Now remove the log out button
        Button logOut = (Button) findViewById(R.id.logout);
        logOut.setVisibility(View.INVISIBLE);
        logOut.setEnabled(false);
        toolbar.setTitle(gameName);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_undobar_undo));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button logOut = (Button) findViewById(R.id.logout);
                logOut.setVisibility(View.INVISIBLE);
                logOut.setEnabled(false);
                onBackPressed();
            }
        });
    }

    public void setText() {
        TextView title = (TextView) findViewById(R.id.gameName);
        title.setText(gameName);
        TextView desc = (TextView) findViewById(R.id.gameDesc);
        desc.setText(description);
        TextView dati = (TextView) findViewById(R.id.gameDateTime);
        dati.setText("\n\n" + datetime);
        TextView play = (TextView) findViewById(R.id.gamePlayers);
        play.setText(cPlayers + "/" + tPlayers + " Players\n");
    }

    public void setButton() {
        Button play = (Button) findViewById((R.id.playButton));
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Adding you to the game...", Toast.LENGTH_SHORT).show();
                if (hasPlayerRSVPd(uID)) {
                    Toast.makeText(getApplicationContext(), "You have already joined the game", Toast.LENGTH_SHORT).show();
                } else if (cPlayers >= tPlayers) {
                    Toast.makeText(getApplicationContext(), "Cannot join; this game is full", Toast.LENGTH_SHORT).show();
                } else {
                    addPlayer(uID);
                    Toast.makeText(getApplicationContext(), "Successfully joined!", Toast.LENGTH_SHORT).show();
                }
                setText();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_game_details, menu);
        return true;
    }

    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latLng.latitude+","+latLng.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.022007, -118.287832), 17.0f)); //where Cromwell is
        // Polylines are useful for marking paths and routes on the map.
        map.addPolygon(new PolygonOptions().geodesic(true)
                        .add(new LatLng(34.022551, -118.288223))  // top left
                        .add(new LatLng(34.021879, -118.288565))  // bottom left
                        .add(new LatLng(34.021418, -118.287526))  // bottom right
                        .add(new LatLng(34.022018, -118.287010)) //top right
                        .add(new LatLng(34.022551, -118.288223)) // top left
                        .fillColor(Color.RED)
        );
        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
    }
}
