package ua.lviv.iot.andriy_popov.mobiledev;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//File for review


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView username;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username_label);
        auth = FirebaseAuth.getInstance();
        Button buttonSignOut = findViewById(R.id.button_sign_out);
        buttonSignOut.setOnClickListener(this);

        updateUI();

    }

    private void updateUI() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            username.setText(user.getDisplayName());
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_out:
                auth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
