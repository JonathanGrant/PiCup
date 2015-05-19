package games.picup.com.picup;

import java.util.ArrayList;
import java.util.List;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */
public class GameManager {

    public static String[] gamesArray = {"Post-HackUMass Soccer! " +
            "18/22 Players\nRudd Field, UMass Amherst, MA 01002\n April 12th, 9:30pm",
            "Make Zlatan Jealous (Soccer) 10/48 Players\nHampshire College Soccer Field, Amherst, MA 01002\nApril 15th, 4:00am",
            "Sweet Hoops Basketball... Totally Chill  11/15 Players\n Boyden Gym, UMass Amherst, MA 01003\n April 16th, 2015 7:25pm",
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

    public Game getGameById(int id){
        List<Game> gameList = getGamesToPlay();
        return gameList.get(id);
    }
}
