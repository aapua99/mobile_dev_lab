package ua.lviv.iot.andriy_popov.mobiledev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MIN_LENGTH_PASS = 8;
    private EditText passEdit;
    private EditText emailEdit;
    private DialogFragment loadFragment;
    private FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        loadFragment = new Load();
        user = auth.getCurrentUser();
        passEdit = findViewById(R.id.password_wrapper);
        emailEdit = findViewById(R.id.email_wrapper);
        Button signInButton = findViewById(R.id.button_sign_in);
        Button signUpButton = findViewById(R.id.button_sign_up);
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user != null) {
            updateUI();
        }
    }

    private void updateUI() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    private boolean validateEmail() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText().toString()).matches()) {
            emailEdit.setError(getString(R.string.email_wrong));
            return false;
        } else {
            emailEdit.setError(null);
        }
        return true;
    }

    private boolean validatePass() {
        if (passEdit.getText().toString().length() < MIN_LENGTH_PASS) {
            passEdit.setError(getString(R.string.min_length_password));
            return false;
        } else {
            passEdit.setError(null);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                signIn();
                break;
            case R.id.button_sign_up:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            default:
                break;
        }
    }

    private void signIn() {
        if (validateEmail() && validatePass()) {
            loadFragment.show(getFragmentManager(), "Comment");
            auth.signInWithEmailAndPassword(emailEdit.getText().toString(), passEdit.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            loadFragment.dismiss();
                            onSignInSuccess();
                        } else {
                            loadFragment.dismiss();
                            onSignInError();
                        }
                    });
        }
    }

    private void onSignInError() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.sign_in_failed_title));
        alertDialog.setMessage(getString(R.string.sign_in_failed_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_label),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void onSignInSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}