package games.picup.com.picup;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class showGameDetails extends ActionBarActivity {

    public static int gameID = 0;
    private String gameName = "Unable To Load Game's Name";
    public Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_details);
        //setToolbar();
        setTitleText();
        game = GameManager.getGameById(gameID);
        gameName = game.name;
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            //setSupportActionBar(toolbar);
            //toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setTitle(gameName);
        }
    }

    public void setTitleText(){
        TextView title = (TextView) findViewById(R.id.gameName);
        title.setText(gameName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_game_details, menu);
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
