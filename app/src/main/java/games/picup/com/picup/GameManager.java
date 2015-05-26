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

    public static String[] gamesArray = {"Post-HackUMass Soccer!",
            "Make Zlatan Jealous (Soccer)",
            "Sweet Hoops Basketball... Totally Chill",
            "Street Hockey",
            "Soccer" ,
            "Ultimate Frisbee"};

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
                ids[randID] = true; //set random id to be taken
                Game game1 = new Game(randID); //get random ID... but shit now I need to check to ensure it isn't taken already
                game1.name = gamesName;
                game1.date = Calendar.getInstance();
                game1.committedPlayers = 14;
                game1.totalPlayers = 22; //should I allow subs?
                game1.description = "Alright faggots. We're going to play a world-class 11v11. No noobs. Bring both a white and dark shirt, and a ball. Messi, if you go, bring your fucking pump this time!";
                game1.Location = "Cromwell Field";
                int i = 0;
                if((1+randID)%7==0) { //don't make the first IM...
                    game1.Location = "Brittingham Field"; //Sometimes Krommie is taken
                    i = 1;
                }
                else if((1+randID)%13==0) {
                    game1.Location = "McCalister Field";
                    i = 2;
                }
                else if((1+randID)%17==0) { //Once a blue moon
                    game1.Location = "McCarthy Quad";
                    i = 3;
                }
                game1.time = 1730;
                gamesToPlay.add(game1);
                GameList.usedFields[i] = true;
            }
        }
        return gamesToPlay;
    }

    public static Game getGameById(int id){
        for(int i = 0; i < gamesToPlay.size(); i++){
            if(gamesToPlay.get(i).id == id)
                return gamesToPlay.get(i);
        }
        return null;
    }
}
