package games.picup.com.picup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */
public class GameManager {
    boolean[] ids = new boolean[1000];

    public static String[] gamesArray = {"Post-HackUMass Soccer!"+"\n April 12th, 9:30pm",
            "Make Zlatan Jealous (Soccer) 10/48 Players\nHampshire College Soccer Field, Amherst, MA 01002\nApril 15th, 4:00am",
            "Sweet Hoops Basketball... Totally Chill  11/15 Players\n Boyden Gym, UMass Amherst, MA 01003\n April 16th, 2015 7:25pm",
            "Street Hockey \nLocation:" + " Malden",
            "Soccer \nLocation:" + " Medford" ,
            "Ultimate Frisbee \nLocation:" + " Somerville"};

    private static GameManager mInstance;
    private static List<Game> gamesToPlay;

    public static GameManager getInstance() {
        if (mInstance == null) {
            mInstance = new GameManager();
        }
        return mInstance;
    }

    public void setBoolean(){
        for(boolean b : ids)
            b = false;
    }

    public List<Game> getGamesToPlay() {

        if (gamesToPlay == null) {
            gamesToPlay = new ArrayList<Game>();
            setBoolean();

            for (String gamesName : gamesArray) {
                int randID = 0;
                while(ids[randID]) //while the ID is taken, try again... This will not work when the app gets bigger
                    randID = (int)(Math.random()*1000);
                Game game1 = new Game(randID); //get random ID... but shit now I need to check to ensure it isn't taken already
                game1.name = gamesName;
                game1.date = Calendar.getInstance();
                game1.committedPlayers = 14;
                game1.totalPlayers = 22; //should I allow subs?
                game1.description = "Alright faggots. We're going to play a world-class 11v11. No noobs. Bring both a white and dark shirt, and a ball. Messi, if you go, bring your fucking pump this time!";
                game1.Location = "Heritage Oaks Park, Los Altos, CA";
                game1.time = 1730;
                gamesToPlay.add(game1);
            }
        }
        return gamesToPlay;
    }

    public static Game getGameById(int id){
        List<Game> gameList = gamesToPlay;
        return gameList.get(id);
    }
}
