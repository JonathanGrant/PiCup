package games.picup.com.picup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class showGameDetails extends Activity {

    public static int gameID = 0;
    public String gameName = "Unable To Load Game's Name";
    public Game game;
    public String description = "Unable to Load Description";
    public String Location = "Example Location";
    public String date = "Example Date";
    public int time = 1200;
    public int cPlayers = 0;
    public int tPlayers = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_details);
        setAllData();
        setToolbar();
        setTitleText();
    }

    private void setAllData(){
        try {
            Bundle b = getIntent().getExtras();
            ArrayList<String> data = b.getStringArrayList("gamedata");
            gameName = data.get(0);
            date = data.get(1);
            time = Integer.parseInt(data.get(2));
            Location = data.get(3);
            cPlayers = Integer.parseInt(data.get(4));
            tPlayers = Integer.parseInt(data.get(5));
            description = data.get(6);
        } catch(Exception e){
            e.printStackTrace();
        }
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

    public void setTitleText() {
        TextView title = (TextView) findViewById(R.id.gameName);
        title.setText(gameName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_game_details, menu);
        return true;
    }
}
