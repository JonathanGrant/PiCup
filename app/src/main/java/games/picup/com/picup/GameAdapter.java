package games.picup.com.picup;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> implements View.OnLongClickListener, View.OnClickListener {
    public TextView gameName;

    //        public ImageView teaImage;
    private List<Game> games;
    private int rowLayout;
    private Context mContext;
    private static View view;

    public GameAdapter(List<Game> games, int rowLayout, Context context) {

        this.games = games;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public void refresh(){
        games = GameManager.getInstance().getGamesFromParse();
    }

    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        // Set the view to the ViewHolder
        ViewHolder holder = new ViewHolder(v);
        holder.gameName.setOnLongClickListener(GameAdapter.this);
        holder.gameName.setOnClickListener(GameAdapter.this);
        holder.gameName.setTag(holder);
        return holder;
    }
    public void onClick(View v) {
        Log.println(1, "debugz", "2");
    }

    public String getNiceDate(Calendar date){
        String strdate = "";
        String niceDate = "January 1st";

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        if (date != null) {
            strdate = sdf.format(date.getTime());
            if(strdate.startsWith("01")){ //January
                niceDate = "January ";
            } else if(strdate.startsWith("02")) //Feb
                niceDate = "February ";
            else if(strdate.startsWith("03")) //Mar
                niceDate = "March ";
            else if(strdate.startsWith("04")) //Apr
                niceDate = "April ";
            else if(strdate.startsWith("05")) //May
                niceDate = "May ";
            else if(strdate.startsWith("06")) //Jun
                niceDate = "June ";
            else if(strdate.startsWith("07")) //Jul
                niceDate = "July ";
            else if(strdate.startsWith("08")) //Aug
                niceDate = "August ";
            else if(strdate.startsWith("09")) //Sep
                niceDate = "September ";
            else if(strdate.startsWith("10")) //Oct
                niceDate = "October ";
            else if(strdate.startsWith("11")) //Nov
                niceDate = "November ";
            else if(strdate.startsWith("12")) //Dec
                niceDate = "December ";
            //now do the day
            int begin = 3;
            if(strdate.substring(3).startsWith("0"))
                begin = 4;
            if(strdate.substring(3).startsWith("1") || strdate.substring(4).startsWith("4") || strdate.substring(4).startsWith("5") || strdate.substring(4).startsWith("6") || strdate.substring(4).startsWith("7") || strdate.substring(4).startsWith("8") || strdate.substring(4).startsWith("9") || strdate.substring(4).startsWith("0")){
                niceDate += strdate.substring(begin,5) + "th";
            } else if(strdate.substring(4).startsWith("1")){
                niceDate += strdate.substring(begin,5)+"st";
            } else if(strdate.substring(4).startsWith("2")){
                niceDate += strdate.substring(begin,5)+"nd";
            } else if(strdate.substring(4).startsWith("3")){
                niceDate += strdate.substring(begin,5)+"rd";
            }
        }
        return niceDate;
    }

    public String getNiceTime(int time){
        String niceTime = "12:00am";
        String min = time+"";
        String ampm = "am";
        String hour = 0+"";
        if(time/100>12){
            hour = (((int)(time/100))%12)+"";
            ampm="pm";
        } else if(time/100 == 12 || time/100 == 0){
            hour = 12 +"";
        }
        if(time/100==12)
            ampm="pm";
        niceTime = hour + ":"+min.substring(2)+ampm;
        return niceTime;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Game game = games.get(i); //the final may screw it up, but i dont think so
        viewHolder.gameName.setText(game.name);
        viewHolder.gamePlayers.setText(game.committedPlayers+"/"+game.totalPlayers+" Players");
        viewHolder.gameDate.setText(getNiceDate(game.date) + ", "+getNiceTime(game.time));
        viewHolder.gameLocation.setText(game.Location);
        viewHolder.gameName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set id as the selected ID
                showGameDetails.gameID = game.id;
                Intent i = new Intent(GameList.context, showGameDetails.class);
                Bundle b = new Bundle();
                ArrayList<String> l = new ArrayList<String>();
                l.add(game.name);
                l.add(getNiceDate(game.date) + ", "+getNiceTime(game.time));
                l.add(game.Location);
                l.add(game.committedPlayers + "");
                l.add(game.totalPlayers + "");
                l.add(game.description);
                l.add(GameList.uID);
                b.putStringArrayList("gamedata", l);
                b.putStringArrayList("users", game.cPlayers);
                i.putExtras(b);
                mContext.startActivity(i);
            }
        });
        viewHolder.gameDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set id as the selected ID
                showGameDetails.gameID = game.id;
                Intent i = new Intent(GameList.context, showGameDetails.class);
                Bundle b = new Bundle();
                ArrayList<String> l = new ArrayList<String>();
                l.add(game.name);
                l.add(getNiceDate(game.date) + ", "+getNiceTime(game.time));
                l.add(game.Location);
                l.add(game.committedPlayers + "");
                l.add(game.totalPlayers + "");
                l.add(game.description);
                l.add(GameList.uID);
                b.putStringArrayList("gamedata", l);
                b.putStringArrayList("users", game.cPlayers);
                i.putExtras(b);
                mContext.startActivity(i);
            }
        });
        viewHolder.gameLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change highlighted location
                if(game.Location.startsWith("Crom"))
                    GameList.changeField("Crom");
                else if(game.Location.startsWith("Brit"))
                    GameList.changeField("Brit");
                else if(game.Location.startsWith("McCar"))
                    GameList.changeField("MCar");
                else if(game.Location.startsWith("McCal"))
                    GameList.changeField("MCal");
            }
        });
        viewHolder.gamePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set id as the selected ID
                showGameDetails.gameID = game.id;
                Intent i = new Intent(GameList.context, showGameDetails.class);
                Bundle b = new Bundle();
                ArrayList<String> l = new ArrayList<String>();
                l.add(game.name);
                l.add(getNiceDate(game.date) + ", "+getNiceTime(game.time));
                l.add(game.Location);
                l.add(game.committedPlayers+"");
                l.add(game.totalPlayers+"");
                l.add(game.description);
                l.add(GameList.uID);
                b.putStringArrayList("gamedata", l);
                b.putStringArrayList("users", game.cPlayers);
                i.putExtras(b);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games == null ? 0 : games.size();
    }


    // Implement OnLongClick listener. Long Clicked items is removed from list.
    @Override
    public boolean onLongClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.gameName.getId()) {
            String phoneNumber = "6507993840";
            String messageToSend = "Hey, I'm going to your event: ''" + holder.gameName.getText() + "''";

            SmsManager.getDefault().sendTextMessage(phoneNumber, null, messageToSend, null, null);

            games.remove(holder.getPosition());
            // Call this method to refresh the list and display the "updated" list
            notifyDataSetChanged();

            Toast.makeText(mContext, "Item #" + ((int) (holder.getPosition()+1)) + " has been removed from list",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView gameName;
        public TextView gamePlayers;
        public TextView gameDate;
        public TextView gameLocation;
        public ImageView teaImage;

        // Holds all of the cards within the RecyclerView
        public ViewHolder(final View itemView) {
            super(itemView);
            gameName = (TextView) itemView.findViewById(R.id.gameNameCardText);
            gamePlayers = (TextView) itemView.findViewById(R.id.gamePlayersCardText);
            gameDate = (TextView) itemView.findViewById(R.id.gameDateCardText);
            gameLocation = (TextView) itemView.findViewById(R.id.gameLocationCardText);
        }

    }
}