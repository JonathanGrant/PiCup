package games.picup.com.picup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */

public class NewGame extends Activity implements View.OnKeyListener {

    EditText setSport;
    EditText setLocation;
    EditText setDate;
    EditText setTPlayers;
    EditText setName;
    EditText setTime;
    EditText setDesc;
    private final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;
    public int numQuotes = 28;
    String[] str = new String[numQuotes];
    String uID = "";
    ParseObject game1 = new ParseObject("Game");
    private SimpleDateFormat dateFormatter;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategame);
        setToolbar();
        setSport();
        setLocation();
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        setTime();
        setDate();
        setTPlayers();
        setDesc();
        setName();
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

    private void addGame(){
        //First send to Parse, then send to local database
        //need to create a JSON Array of the id's that are RSVP'd
        JSONArray rsvpd = new JSONArray();
        rsvpd.put(uID);
        game1.put("NAME", getName());
        game1.put("DESCRIPTION", getDesc());
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
        game1.put("DATE", getDate());
        game1.put("TIME", Integer.parseInt(getTime()));
        game1.put("CPLAYERS", 1);
        game1.put("TPLAYERS", Integer.parseInt(getTPlayers()));
        game1.put("RPLAYERS", rsvpd);
        game1.saveInBackground(new SaveCallback() { //this way, we dont ask for the object's id until after it is saved
            public void done(ParseException e) { //and we dont enter the id until it is saved
                if (e == null) {
                    //game saved, now get game's id
                    final String gameID = getObjectID(game1);
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
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //What does this do?
                                    startActivity(i);
                                    Toast toast = Toast.makeText(NewGame.this, "New Event Created", Toast.LENGTH_SHORT);
                                    toast.show();
                                } catch (java.lang.NullPointerException e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                // something went wrong
                                Toast.makeText(NewGame.this,"Could not retrieve game id list", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    //failed
                    Toast.makeText(NewGame.this,"Error saving game, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getObjectID(ParseObject o){
        return o.getObjectId();
    }

    private void pushButton() {
        Button b1 = (Button) findViewById(R.id.enter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame();
            }
        });
    }

    public void setSport(){
        setSport = (EditText) findViewById(R.id.sport_set);
        //setSport.setOnKeyListener(this);
    }

    public String getSport() {
        String sport = String.valueOf(setSport.getText());
        return sport;
    }

    public void setLocation(){
        setLocation = (EditText) findViewById(R.id.location_set);
        //setLocation.setOnKeyListener(this);
    }

    public String getLocation() {
        String location = setLocation.getText().toString();
        return location;
    }

    private void setDateTimeField() {
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                setDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setDate(){
        setDate = (EditText) findViewById(R.id.date_set);
        setDate.setInputType(InputType.TYPE_NULL);
        setDateTimeField();
        setDate.setOnKeyListener(this);

    }

    public String getDate() {
        String date = String.valueOf(setDate.getText());
        return date;
    }

    public void setName(){
        setName = (EditText) findViewById(R.id.name_set);
        //setName.setOnKeyListener(this);
    }

    public String getName() {
        return setName.getText().toString();
    }

    public void setDesc(){
        setDesc = (EditText) findViewById(R.id.name_set);
        //setDesc.setOnKeyListener(this);
    }

    public String getDesc() {
        return setDesc.getText().toString();
    }

    public void setTPlayers(){
        setTPlayers = (EditText) findViewById(R.id.tplayers_set);
        setTPlayers.setOnKeyListener(this);
    }

    public String getTPlayers() {
        return setTPlayers.getText().toString();
    }

    public void setTime(){
        setTime = (EditText) findViewById(R.id.time_set);
        setTime.setInputType(InputType.TYPE_NULL);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(NewGame.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        setTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    public String getTime() {
        String twofour = setTime.getText().toString().replaceAll(":", "" ); //remove symbols
        return twofour;
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
    public boolean onKey(View v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            //if user presses enter, switch the edittext to the next one
            if(v.equals(setName)){
                setTPlayers.requestFocus();
            } else if(v.equals(setSport)){
                setLocation.requestFocus();
            } else if(v.equals(setLocation)){
                setLocation.clearFocus();
            }
        }
        return false;
    }

    public boolean testInfo(){ //this method looks at all the inputs and makes sure it is okay to send it to parse
        //First, ensure there is a valid name
        if(getName().isEmpty()){
            //Highlight setName in red
            setName.requestFocus();
        }


        return true;
    }
}