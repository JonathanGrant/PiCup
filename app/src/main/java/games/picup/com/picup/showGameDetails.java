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


public class showGameDetails extends Activity {

    public static int gameID = 0;
    private String gameName = "Unable To Load Game's Name";
    public Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_details);
        setToolbar();
        setTitleText();
        game = GameManager.getGameById(gameID);
        gameName = game.name;
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarshow);
        //setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_action_back);
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
