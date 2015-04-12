package games.picup.com.picup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Freddie4 on 4/12/2015.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{
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
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            final View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Game game = games.get(i);
            viewHolder.gameName.setText(game.name);
        }


        @Override
        public int getItemCount() {
            return games == null ? 0 : games.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView gameName;
            public ImageView teaImage;

            // Holds all of the cards within the RecyclerView
            public ViewHolder(final View itemView) {
                super(itemView);
                view = itemView;
                gameName = (TextView) itemView.findViewById(R.id.gameName);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(itemView.getContext(), "on item click", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }