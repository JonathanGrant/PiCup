package games.picup.com.picup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Freddie4 on 4/12/2015.
 */
public class GameManager {

    private static String[] gamesArray = {"Soccer \nLocation:" + " Boston",
            "Soccer \nLocation:" + " Boston",
            "Basketball \nLocation:" + " Cambridge",
            "Street Hockey \nLocation:" + " Malden",
            "Soccer \nLocation:" + " Medford" ,
            "Ultimate Frisbee \nLocation:" + " Somerville"};

    private static GameManager mInstance;
    private List<Game> gamesToPlay;

    public static GameManager getInstance() {
        if (mInstance == null) {
            mInstance = new GameManager();
        }
        return mInstance;
    }


    public List<Game> getGamesToPlay() {

        if (gamesToPlay == null) {
            gamesToPlay = new ArrayList<Game>();

            for (String gamesName : gamesArray) {
                Game game1 = new Game();
                game1.name = gamesName;
                gamesToPlay.add(game1);
            }
        }
        return gamesToPlay;
    }
}
