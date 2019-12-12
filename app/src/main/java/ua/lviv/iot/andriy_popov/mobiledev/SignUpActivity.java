package ua.lviv.iot.andriy_popov.mobiledev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText passEdit;
    private DialogFragment loadFragment;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        loadFragment = new Load();
        emailEdit = findViewById(R.id.email_wrapper);
        nameEdit = findViewById(R.id.name_wrapper);
        phoneEdit = findViewById(R.id.phone_wrapper);
        passEdit = findViewById(R.id.password_wrapper);
        Button signInButton = findViewById(R.id.button_sign_in);
        Button signUpButton = findViewById(R.id.button_sign_up);
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.button_sign_up:
                signUp();
                break;
            default:
                break;
        }
    }

    private void signUp() {
        if (validateAllFields()) {
            loadFragment.show(getFragmentManager(), "Comment");
            auth.createUserWithEmailAndPassword(emailEdit.getText().toString(), passEdit.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            onSuccessfulSignUp();
                        } else {
                            loadFragment.dismiss();
                            onFailedSignUp();
                        }
                    });
        }
    }

    private boolean validateAllFields() {
        return ValidateFields.validateEmail(emailEdit) && ValidateFields.validateName(nameEdit) &&
                ValidateFields.validatePhone(phoneEdit) && ValidateFields.validatePass(passEdit);

    }

    private void onSuccessfulSignUp() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nameEdit.getText().toString()).build();
            user.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            loadFragment.dismiss();
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            loadFragment.dismiss();
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        }
                    });
        }
    }

    private void onFailedSignUp() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.sign_up_failed_title));
        alertDialog.setMessage(getString(R.string.sign_up_failed_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_label),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
