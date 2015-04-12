package games.picup.com.picup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.views.Button;

/**
 * Created by Freddie4 on 4/11/2015.
 */
public class GameList extends Activity {

    Toolbar toolbar;
    Button FAB;
    RecyclerView mRecyclerView;
    GameAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Available Games");
//        setSupportActionBar(toolbar);
        setUpButton();
//        NewGame g1 = new NewGame();
//        Bundle e1 = getIntent().getExtras();
//        if(e1 != null) {
//            String spo = e1.getString("SPORT");
//            String loc = e1.getString("LOCATION");
//            String date = e1.getString("DATE");
//            getGameData(spo, loc, date, 4.6);
//        }
//
//        else {
//            getGameData("", "", "", 0.0);
//        }
        // gets recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // manager for recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // animations for recyclerViews
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new GameAdapter(GameManager.getInstance().getGamesToPlay(), R.layout.card_view, this);
        mRecyclerView.setAdapter(mAdapter);
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

    public void getGameData(String sport, String location, String date, double distance) {
//        Card card = new Card(getApplicationContext());
//        CardHeader header = new CardHeader(getApplicationContext());
//
//        header.setTitle("Sport: " + sport + "\nDate: " + date
//                + "\nDistance: " + Double.toString(distance) + " mi");
//        //Add Header to card
//        card.addCardHeader(header);
//
//        card.setTitle("Location: " + location);
//        //Set card in the cardView
//        CardViewNative cardView = (CardViewNative) findViewById(R.id.carddemo);
//        cardView.setCard(card);
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
