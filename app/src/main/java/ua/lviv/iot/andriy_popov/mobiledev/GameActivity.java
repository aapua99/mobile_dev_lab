package ua.lviv.iot.andriy_popov.mobiledev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        Game game = (Game) Objects.requireNonNull(intent.getExtras()).getSerializable("GAME");
        TextView nameGame = findViewById(R.id.nameGame);
        TextView creatorGame = findViewById(R.id.creatorGame);
        TextView browserGame = findViewById(R.id.browserGame);
        TextView dateRegister = findViewById(R.id.dateRegister);
        TextView dateStartPlay = findViewById(R.id.dateStartPlay);
        TextView status = findViewById(R.id.status);
        ImageView image = findViewById(R.id.imageView);
        Picasso.get()
                .load(game.getImage())
                .resize(360, 320)
                .centerCrop()
                .into(image);
        nameGame.setText(game.getName());
        creatorGame.setText(game.getCreator());
        browserGame.setText(game.getBrowser());
        dateRegister.setText(game.getDateRegister());
        dateStartPlay.setText(game.getDateStartPlay());
        status.setText(game.getStatus());
    }
}
