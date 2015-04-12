package games.picup.com.picup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        public GameAdapter(List<Game> games, int rowLayout, Context context) {

            this.games = games;
            this.rowLayout = rowLayout;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Game game = games.get(i);
            viewHolder.gameName.setText(game.name);
//        viewHolder.teaImage.setImageDrawable(mContext.getDrawable(teaImage.getImageResourceId(mContext)));
        }


        @Override
        public int getItemCount() {
            return games == null ? 0 : games.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView gameName;
            public ImageView teaImage;


            public ViewHolder(View itemView) {
                super(itemView);
                gameName = (TextView) itemView.findViewById(R.id.gameName);
            }

        }
    }