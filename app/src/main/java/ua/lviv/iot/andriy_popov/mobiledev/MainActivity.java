package ua.lviv.iot.andriy_popov.mobiledev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DialogFragment loadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        listView = findViewById(R.id.listOfGame);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        loadFragment = new Load();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getGamesFromDB();
            swipeRefreshLayout.setRefreshing(false);
        });
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkChangeReceiver receiver = new NetworkChangeReceiver();
        this.registerReceiver(receiver, filter);
        getGamesFromDB();
    }

    private void getGamesFromDB() {
        loadFragment.show(getFragmentManager(), "comment");
        ApiUtil.getServiceClass().getAllGames().enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    loadFragment.dismiss();
                    List<Game> postList = response.body();
                    GameAdapter adapter = new GameAdapter(getApplicationContext(), postList);
                    listView.setAdapter(adapter);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                loadFragment.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = auth.getCurrentUser();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.button_sign_out).setOnMenuItemClickListener(item -> {
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
            return false;
        });
        return true;
    }

    public void showError() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
        alertDialog.setTitle(getString(R.string.failed));
        alertDialog.setMessage(getString(R.string.failed));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_label),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}
