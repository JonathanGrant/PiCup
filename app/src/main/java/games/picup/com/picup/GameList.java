package games.picup.com.picup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.views.Button;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by Freddie4 on 4/11/2015.
 */
public class GameList extends Activity {

    Toolbar toolbar;
    Button FAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Available Games");
//        setSupportActionBar(toolbar);
        setUpButton();
    }

    private void setUpButton() {
        FAB = (Button) findViewById(R.id.buttonFloat);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameList.this, CreateGameActivity.class);
                startActivity(i);
            }
        });
    }

    public void getGameData(String sport, String location, String date, double distance) {

        final Card card = new Card(getApplicationContext());
        CardHeader header = new CardHeader(getApplicationContext());
        header.setTitle("Sport: " + sport + "\nDate: " + date
                +  "\nDistance: " + Double.toString(distance) + " mi");
        //Add Header to card
        card.addCardHeader(header);

        card.setTitle("Location: " + location);

        //Set card in the cardView
        CardViewNative cardView = (CardViewNative) findViewById(R.id.carddemo);

        cardView.setCard(card);
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
