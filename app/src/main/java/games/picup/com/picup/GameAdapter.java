package games.picup.com.picup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> implements  View.OnLongClickListener, View.OnClickListener {
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

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Game game = games.get(i);
        viewHolder.gameName.setText(game.name);
        viewHolder.gameName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Event #" + ((int) (viewHolder.getPosition()+1)), Toast.LENGTH_SHORT).show();
                //set id as the selected ID
                showGameDetails.gameID = ((int) (viewHolder.getPosition()));
                Intent i = new Intent(GameList.context, showGameDetails.class);
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
        public ImageView teaImage;

        // Holds all of the cards within the RecyclerView
        public ViewHolder(final View itemView) {
            super(itemView);
            gameName = (TextView) itemView.findViewById(R.id.gameNameCardText);
            gamePlayers = (TextView) itemView.findViewById(R.id.gamePlayersCardText);
        }

    }
}