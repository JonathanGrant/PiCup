package games.picup.com.picup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */

public class NewGame extends Activity {

    EditText setSport;
    EditText setLocation;
    EditText setDate;
    private final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;
    public int numQuotes = 28;
    String[] str = new String[numQuotes];
    String uID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategame);
        setToolbar();
        setSport();
        setLocation();
        setDate();
        pushButton();
        addQuote();
        addLogOutButton();
        //now get uID
        Bundle e1 = getIntent().getExtras();
        if (e1 != null) {
            uID = e1.getString("uID");
        }
        //Now Parse
//        Parse.enableLocalDatastore(this); //what does this do? What if I didn't have this?
        //start Parse
        Parse.initialize(this, "B4rIuWBWbeVaHrdtdnUZcC5ziI2cqAm1ZneexOXy", "mcGiMCshfXbCH29AXXiiK7lU9KBxrCRb0r00psWB");
    }

    public void addLogOutButton(){
        Button b1 = (Button) findViewById(R.id.logout);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewGame.this, LoginActivity.class);

                //now log out
                logOut();

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(i, SECONDARY_ACTIVITY_REQUEST_CODE);

                Toast toast = Toast.makeText(NewGame.this, "Successfully Logged Out Bro", Toast.LENGTH_LONG);
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

    private void addQuote(){
        try {
            InputStream json=getAssets().open("FootballQuotes.json");
            Scanner inz = new Scanner(json);
            for(int i = 0; i<numQuotes; i++){
                str[i] = inz.nextLine();
            }
            inz.close();
            //end of file i/o and reading
            //now change text view
            TextView t1 = (TextView)findViewById(R.id.quoter);
            int num = (int)(Math.random()*numQuotes);
            t1.setText(str[num]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            //setSupportActionBar(toolbar);
            //toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setTitle("Create New Game");
        }
    }

    private void pushButton() {
        Button b1 = (Button) findViewById(R.id.enter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First send to Parse, then send to local database
                ParseObject game1 = new ParseObject("Game");
                //need to create a JSON Array of the id's that are RSVP'd
                JSONArray rsvpd = new JSONArray();
                rsvpd.put(uID);
                game1.put("NAME", setSport());
                game1.put("DESCRIPTION", "DER KLASIK. BVB Dortmund v Bayern München\nWelchen ist der Beste? Ich weiß dass Bayern ist besser als Dortmund, aber seine penalty kickers sind schwer. SEHR schwer...");
                String locat = "Cromwell Field";
                int randID = (int)(Math.random()*1000);
                if((1+randID)%4==0) { //don't make the first IM...
                    locat = "Brittingham Field"; //Sometimes Krommie is taken
                }
                else if((1+randID)%8== 0) {
                    locat = "McCalister Field";
                }
                else if((1+randID)%16==0) { //Once a blue moon
                    locat = "McCarthy Quad";
                }
                game1.put("LOCATION", locat);
                game1.put("DATE", setDate());
                game1.put("TIME", 1200);
                game1.put("CPLAYERS", 12);
                game1.put("TPLAYERS", 25);
                game1.put("RPLAYERS", rsvpd);
                game1.saveInBackground();
                final String gameID = game1.getObjectId();

                //add Game ID to Parse Object of Game ID's
                ParseQuery<ParseObject> query = ParseQuery.getQuery("gIDs");
                query.getInBackground("r7lHWJwsoa", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (e == null) {
                            try {
                                JSONArray jar = object.getJSONArray("gIDsArray");
                                jar.put(gameID);
                                object.put("gIDsArray", jar);
                                object.saveInBackground();
                                Intent i = new Intent(NewGame.this, GameList.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("gID", gameID);
                                i.putExtras(bundle);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivityForResult(i, SECONDARY_ACTIVITY_REQUEST_CODE);
                                Toast toast = Toast.makeText(NewGame.this, "New Event Created", Toast.LENGTH_SHORT);
                                toast.show();
                            } catch (java.lang.NullPointerException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            // something went wrong
                        }
                    }
                });
            }
        });
    }

    public String setSport() {
        setSport = (EditText) findViewById(R.id.sport_set);
        String sport = String.valueOf(setSport.getText());

        return sport;
    }

    public String setLocation() {
        setLocation = (EditText) findViewById(R.id.location_set);
        String location = String.valueOf(setLocation.getText());

        return location;
    }

    public String setDate() {
        setDate = (EditText) findViewById(R.id.date_set);
        String date = String.valueOf(setDate.getText());

        return date;
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
}