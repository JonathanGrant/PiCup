package games.picup.com.picup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */

public class NewGame extends Activity {

    EditText setSport;
    EditText setLocation;
    EditText setDate;
    private final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategame);
        setToolbar();
        setSport();
        setLocation();
        setDate();
        pushButton();
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
                Intent i = new Intent(NewGame.this, GameList.class);
                Bundle bundle = new Bundle();
                bundle.putString("SPORT", setSport());
                bundle.putString("LOCATION", setLocation());
                bundle.putString("DATE", setDate());
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(i, SECONDARY_ACTIVITY_REQUEST_CODE);

                Toast toast = Toast.makeText(NewGame.this, "New Event Created", Toast.LENGTH_LONG);
                toast.show();
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