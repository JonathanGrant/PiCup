package games.picup.com.picup;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Freddie4 on 4/12/2015.
 */
public class GameManager extends Activity {

    private static String[] gamesArray = {"", "", "", "", "", ""};

    private static GameManager mInstance;
    private List<Game> gamesToPlay;

    public static GameManager getInstance() {
        if (mInstance == null) {
            mInstance = new GameManager();
        }
        return mInstance;
    }

    public List<Game> getGamesToPlay() {
//        NewGame g1 = new NewGame();
//        Bundle e1 = getIntent().getExtras();
//        if(e1 != null) {
//            String spo = e1.getString("SPORT");
//            String loc = e1.getString("LOCATION");
//            String date = e1.getString("DATE");

        if (gamesToPlay == null) {
            gamesToPlay = new ArrayList<Game>();

            for (String gamesName : gamesArray) {
                Game game = new Game();
                game.name = gamesName;
                gamesToPlay.add(game);
            }
        }
        return gamesToPlay;
    }
//        }

//        else {
//        }
//        if (gamesToPlay == null) {
//            gamesToPlay = new ArrayList<Game>();
//
//            for (String countryName : gamesArray) {
//                Game game = new Game();
//                game.name = countryName;
//                gamesToPlay.add(game);
//            }
//        }
//        return gamesToPlay;
//    }
}
