package ua.lviv.iot.andriy_popov.mobiledev;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private FirebaseUser user;
    private FirebaseStorage storage;
    private CircleImageView avatarImage;
    private EditText email;
    private EditText name;
    private DialogFragment loadFragment;
    private final static long ONE_MEGABYTE = 1024 * 1024;
    private final static int IMAGE_REQUEST = 1000;
    private final static String JPG = ".jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        loadFragment = new Load();
        storage = FirebaseStorage.getInstance();
        avatarImage = view.findViewById(R.id.avatarImage);
        Button buttonSave = view.findViewById(R.id.save_data);
        email = view.findViewById(R.id.email);
        name = view.findViewById(R.id.name);
        buttonSave.setOnClickListener(v -> uploadUserData());
        avatarImage.setOnClickListener(v -> {
            Intent getImage = new Intent(Intent.ACTION_PICK);
            getImage.setType("image/*");
            startActivityForResult(getImage, IMAGE_REQUEST);
        });
        getUserData();
        return view;
    }

    private void getUserData() {
        loadFragment.show(Objects.requireNonNull(getActivity()).getFragmentManager(), null);
        downloadImageFromDataBase();
        downloadUserInformation();
    }

    private void downloadUserInformation() {
        for (UserInfo profile : user.getProviderData()) {
            name.setText(profile.getDisplayName());
            email.setText(profile.getEmail());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent getImage) {
        super.onActivityResult(requestCode, resultCode, getImage);
        Bitmap logoImage = null;
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = getImage.getData();
            try {
                logoImage = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity())
                                .getContentResolver(),
                        selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            avatarImage.setImageBitmap(logoImage);

        }
    }

    private void uploadUserData() {
        loadFragment.show(Objects.requireNonNull(getActivity()).getFragmentManager(), null);
        uploadImageToDataBase();
        uploadUserInfromation();
    }

    private void uploadUserInfromation() {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name.getText().toString())
                .build();

        OnFailureListener onFailureListener = e -> Toast.makeText(getContext(),
                getString(R.string.error_upload), Toast.LENGTH_LONG).show();
        user.updateEmail(email.getText().toString()).addOnFailureListener(onFailureListener);
        user.updateProfile(profileUpdates).addOnFailureListener(onFailureListener);
    }


    private void uploadImageToDataBase() {
        StorageReference storageRef = storage.getReference();
        StorageReference mountainImagesRef = storageRef.child(email.getText().toString() + JPG);
        avatarImage.setDrawingCacheEnabled(true);
        avatarImage.buildDrawingCache();
        Bitmap bitmap = avatarImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            loadFragment.dismiss();
            Toast.makeText(getContext(), getString(R.string.error_upload), Toast.LENGTH_LONG).show();
        });
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            loadFragment.dismiss();
            Toast.makeText(getContext(), getString(R.string.success_upload), Toast.LENGTH_LONG).show();
        });
    }


    private void downloadImageFromDataBase() {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(email.getText().toString() + JPG);
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp;
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            avatarImage.setImageBitmap(bmp);
            loadFragment.dismiss();
        }).addOnFailureListener(exception -> {
            Toast.makeText(getContext(), getString(R.string.error_download), Toast.LENGTH_LONG).show();
            loadFragment.dismiss();
        });
    }
}