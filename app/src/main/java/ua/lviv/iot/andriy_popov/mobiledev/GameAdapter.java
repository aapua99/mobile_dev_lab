package ua.lviv.iot.andriy_popov.mobiledev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends ArrayAdapter<Game> {

    public GameAdapter(Context context, List<Game> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Game game = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_games, parent, false);
        }


        TextView nameGame = convertView.findViewById(R.id.nameGame);
        TextView creatorGame = convertView.findViewById(R.id.creatorGame);
        TextView browserGame = convertView.findViewById(R.id.browserGame);
        TextView dateRegister = convertView.findViewById(R.id.dateRegister);
        TextView dateStartPlay = convertView.findViewById(R.id.dateStartPlay);
        TextView status = convertView.findViewById(R.id.status);
        ImageView image =convertView.findViewById(R.id.imageView);
        Picasso.get()
                .load(game.getImage())
                .resize(320, 120)
                .centerCrop()
                .into(image);
        nameGame.setText(game.getName());
        creatorGame.setText(game.getCreator());
        browserGame.setText(game.getBrowser());
        dateRegister.setText(game.getDateRegister());
        dateStartPlay.setText(game.getDateStartPlay());
        status.setText(game.getStatus());

        return convertView;

    }

}
