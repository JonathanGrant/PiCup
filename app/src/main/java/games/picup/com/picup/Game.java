package games.picup.com.picup;

import android.content.Context;

/**
 * Created by Freddie4 on 4/12/2015.
 */
public class Game {
    public String name;
    public String description;


    public int getImageResourceId(Context context) {
        try {
            return context.getResources().getIdentifier(name, "drawable", context.getPackageName());


        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}