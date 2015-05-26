package games.picup.com.picup;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Freddie4 on 4/12/2015.
 */
public class Game {
    public String name = "defaultName";
    public String description = "defaultDescription";
    public String Location = "defaultLocation";
    public Calendar date = Calendar.getInstance();
    public int time = 1200; //in the 24hr format, ex: 13:45
    public int committedPlayers = 22;
    public int totalPlayers = 25;
    public String id = "";
    public ArrayList<String> cPlayers = new ArrayList<String>();

    public Game(String id){
        this.id = id;
    }

    public int getImageResourceId(Context context) {
        try {
            return context.getResources().getIdentifier(description, "drawable", context.getPackageName());


        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void addPlayer(String uID){
        cPlayers.add(uID);
        committedPlayers++;
    }

    public boolean hasPlayerRSVPd(String uID){
        for(int i = 0; i < cPlayers.size(); i++){
            if(cPlayers.get(i).equals(uID))
                return true; //then player is RSVP'd - side note: What does RSVP stand for?
        }
        return false;
    }
}