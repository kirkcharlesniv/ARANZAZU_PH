package ph.aranzazushrine.aranzazuph;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {
    static int PReqCode = 1;
    static int RequestCode = 1;
    Uri selectedImage;
    private EditText userName, userEmail, userPassword, userPasswordConfirm;
    private Button btnRegister, btnPicture;
    private ImageView profilePicture;
    private TextView loginText;
    private TextInputLayout nameInput, emailInput, passwordInput, confirmPasswordInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginText = findViewById(R.id.loginText);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.nameField);
        userEmail = findViewById(R.id.emailField);
        userPassword = findViewById(R.id.passwordField);
        userPasswordConfirm = findViewById(R.id.confirmPasswordField);
        btnRegister = findViewById(R.id.loginButton);
        btnPicture = findViewById(R.id.adsprofilePictureButton);
        profilePicture = findViewById(R.id.profilePicture);

        nameInput = findViewById(R.id.nameInputLayout);
        emailInput = findViewById(R.id.emailInputLayout);
        passwordInput = findViewById(R.id.passwordInputLayout);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInputLayout);

        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userName.getText().toString();
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String confirmPassword = userPasswordConfirm.getText().toString();

                if (name.isEmpty()) {
                    nameInput.setError("You need to enter a name.");
                } else {
                    nameInput.setError(null);
                }

                if (email.isEmpty()) {
                    emailInput.setError("You need to enter an email address.");
                } else {
                    emailInput.setError(null);
                }

                if (password.isEmpty()) {
                    passwordInput.setError("You need to enter a password with at least 8 characters.");
                } else if (!password.isEmpty() && password.length() <= 8) {
                    passwordInput.setError("Your password must contain at least 8 characters.");
                } else {
                    passwordInput.setError(null);
                }

                if (confirmPassword.isEmpty()) {
                    confirmPasswordInput.setError("You need to confirm your password.");
                } else if (!password.equals(confirmPassword)) {
                    confirmPasswordInput.setError("Your confirmation password doesn't match.");
                } else {
                    confirmPasswordInput.setError(null);
                }
                CreateUserAccount(name, email, password);
            }
        });
    }

    private void CreateUserAccount(final String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showMessage("Your user account was created successfully.");
                    updateUserInfo(name, selectedImage, mAuth.getCurrentUser());
                } else {
                    showMessage(task.getException().getMessage());
                }
            }
        });
    }

    private void updateUserInfo(final String name, Uri selectedImage, final FirebaseUser currentUser) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(selectedImage.getLastPathSegment());
        imageFilePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            updateUI();
                                        }
                                    }
                                });
                    }
                });
            }
        });

    }

    private void updateUI() {
        Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RequestCode);
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(RegisterActivity.this, "Please accept the required permission.", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RequestCode && data != null) {
            selectedImage = data.getData();
            profilePicture.setImageURI(selectedImage);
        }
    }
}
