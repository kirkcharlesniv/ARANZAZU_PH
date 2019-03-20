package ph.aranzazushrine.aranzazuph.Auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import ph.aranzazushrine.aranzazuph.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText userEmail;
    private TextInputLayout emailInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        userEmail = findViewById(R.id.emailField);
        Button forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        emailInput = findViewById(R.id.emailInputLayout);
        mAuth = FirebaseAuth.getInstance();

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = userEmail.getText().toString();
                int errorStatus = 0;

                if (email.isEmpty()) {
                    emailInput.setError("You need to enter your email address.");
                    errorStatus = 1;
                } else {
                    emailInput.setError(null);
                }

                if (errorStatus == 0) {
                    sendPasswordResetEmail(email);
                }
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Password Reset Email has been sent. Please check your inbox.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
