package games.picup.com.picup;

import android.telephony.SmsManager;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
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
    public static ArrayList<String> gIDs = new ArrayList<String>();

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

    public List<Game> getGamesFromParse(){
        gIDs.clear();
        setBoolean();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("gIDs");
        query.getInBackground("r7lHWJwsoa", new GetCallback<ParseObject>() {
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    try {
                        gamesToPlay = new ArrayList<Game>();
                        JSONArray jar = object.getJSONArray("gIDsArray");
                        //get game ids from jar
                        for (int i = 0; i < jar.length(); i++) {
                            gIDs.add(jar.getString(i));
                        }
                        gamesToPlay = getGamesFromGList();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    } catch (java.lang.NullPointerException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    // something went wrong
                }
            }
        });

        return gamesToPlay;
    }

    public List<Game> getGamesFromGList(){
        if (gamesToPlay == null)
            gamesToPlay = new ArrayList<Game>();
        //if(gIDs.size() == 0) gIDs.add("P5kDFiziY5");
        for (int i = 0; i < gIDs.size(); i++) {
            ParseQuery<ParseObject>query = ParseQuery.getQuery("Game");
            query.getInBackground(gIDs.get(i), new GetCallback<ParseObject>() {
                public void done(ParseObject g, com.parse.ParseException e) {
                    if (e == null) {
                        Game game1 = new Game(g.getObjectId()); //get random ID... but shit now I need to check to ensure it isn't taken already
                        game1.name = g.getString("NAME");
                        Date d = g.getCreatedAt();
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);
                        game1.date = c;
                        game1.committedPlayers = g.getInt("CPLAYERS");
                        game1.totalPlayers = g.getInt("TPLAYERS");
                        game1.description = g.getString("DESCRIPTION");
                        game1.Location = g.getString("LOCATION");
                        int i = 0;
                        switch (game1.Location) {
                            case "Brittingham Field":
                                i = 1;
                                break;
                            case "McCalister Field":
                                i = 2;
                                break;
                            case "McCarthy Quad":
                                i = 3;
                                break;
                            default:
                                i = 1;
                                break;
                        } //WOW a switch case :)
                        game1.time = g.getInt("TIME");
                        gamesToPlay.add(game1);
                        GameList.usedFields[i] = true;
                    } else {
                        // something went wrong
                    }
                }
            });
        }
        return gamesToPlay;
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
                Game game1 = new Game(randID+""); //get random ID... but shit now I need to check to ensure it isn't taken already
                game1.name = gamesName;
                game1.date = Calendar.getInstance();
                game1.committedPlayers = 14;
                game1.totalPlayers = 22; //should I allow subs?
                game1.description = "Alright faggots. We're going to play a world-class 11v11. No noobs. Bring both a white and dark shirt, and a ball. Messi, if you go, bring your fucking pump this time!";
                game1.Location = "Cromwell Field";
                int i = 0;
                if((1+randID)%4==0) { //don't make the first IM...
                    game1.Location = "Brittingham Field"; //Sometimes Krommie is taken
                    i = 1;
                }
                else if((1+randID)%8==0) {
                    game1.Location = "McCalister Field";
                    i = 2;
                }
                else if((1+randID)%16==0) { //Once a blue moon
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
            if(gamesToPlay.get(i).id.equals(id))
                return gamesToPlay.get(i);
        }
        return null;
    }
}
