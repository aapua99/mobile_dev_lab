package ua.lviv.iot.andriy_popov.mobiledev;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddGameActivity extends AppCompatActivity {

    private final static String[] isOnline = {"Online", "Offline"};
    private EditText editNameGame;
    private EditText editBrowserGame;
    private EditText editCreatorGame;
    private TextView editDateRegisterGame;
    private TextView editDateStartPlayGame;
    private Spinner isOnlineSpinner;
    private Button addGame;
    private DialogFragment loadFragment;
    private DatePickerDialog.OnDateSetListener dateRegister;
    private DatePickerDialog.OnDateSetListener dateStartPlayGame;
    private final Calendar myCalendar = Calendar.getInstance();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private final static String URL_IMAGE = "https://images.unsplash.com/photo-1499084732479-de2c02d45fcc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        initView();
        createOnDateSetListener();
        loadFragment = new Load();
        editDateStartPlayGame.setText(simpleDateFormat.format(myCalendar.getTime()));
        editDateRegisterGame.setText(simpleDateFormat.format(myCalendar.getTime()));
        editDateRegisterGame.setOnClickListener(v -> new DatePickerDialog(AddGameActivity.this,
                dateRegister, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        editDateStartPlayGame.setOnClickListener(v -> new DatePickerDialog(AddGameActivity.this,
                dateStartPlayGame, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, isOnline);
        isOnlineSpinner.setAdapter(adapter);
        addGame.setOnClickListener(v -> addGameToDB());
    }

    private void addGameToDB() {
        if(ValidateFields.validateName(editNameGame) && ValidateFields.validateName(editBrowserGame) &&
        ValidateFields.validateName(editCreatorGame)) {
            sendDataToDatabase();
        }
    }

    private void sendDataToDatabase() {
        loadFragment.show(getFragmentManager(), "Comment");
        Game game =new Game(editNameGame.getText().toString(),
                            editBrowserGame.getText().toString(),
                            editDateRegisterGame.getText().toString(),
                            editDateStartPlayGame.getText().toString(),
                            isOnlineSpinner.getSelectedItem().toString(),
                            editCreatorGame.getText().toString(),
                            URL_IMAGE);
        ApiUtil.getServiceClass().addGame(game).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
               loadFragment.dismiss();
               exitToMain();
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                loadFragment.dismiss();
                exitToMain();
            }
        });
    }

    void createOnDateSetListener(){
        dateRegister = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editDateRegisterGame.setText(simpleDateFormat.format(myCalendar.getTime()));
        };
        dateStartPlayGame = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editDateStartPlayGame.setText(simpleDateFormat.format(myCalendar.getTime()));
        };
    }

    void exitToMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    void initView(){
        editBrowserGame = findViewById(R.id.edit_browser_game);
        editCreatorGame = findViewById(R.id.edit_creator_game);
        editNameGame = findViewById(R.id.edit_name_game);
        editDateRegisterGame = findViewById(R.id.edit_date_register);
        editDateStartPlayGame = findViewById(R.id.edit_date_start_play);
        addGame = findViewById(R.id.add_game);
        isOnlineSpinner = findViewById(R.id.is_online);
    }
}
