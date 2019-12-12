package ua.lviv.iot.andriy_popov.mobiledev;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DialogFragment loadFragment;
    private List<Game> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.listOfGame);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent =new Intent(getContext(), GameActivity.class);
            intent.putExtra("GAME", postList.get(position));
            startActivity(intent);
        });
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        loadFragment =new Load();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadGamesFromDB();
            swipeRefreshLayout.setRefreshing(false);
        });
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
          Intent intent =new Intent(getContext(), AddGameActivity.class);
          startActivity(intent);
        });
        loadGamesFromDB();
        return view;
    }

    private void loadGamesFromDB() {
        loadFragment.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "comment");
        ApiUtil.getServiceClass().getAllGames().enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    loadFragment.dismiss();
                    postList = response.body();
                    GameAdapter adapter = new GameAdapter(Objects.requireNonNull(getActivity())
                            .getApplicationContext(), postList);
                    listView.setAdapter(adapter);
                } else {
                    showError();
                    loadFragment.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                loadFragment.dismiss();
            }
        });
    }
    private void showError() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity())
                .getApplicationContext()).create();
        alertDialog.setTitle(getString(R.string.failed));
        alertDialog.setMessage(getString(R.string.failed));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_label),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


}
