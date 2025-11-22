package ph61733.d04072025.pro1122;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText edtUsername;
    private TextInputEditText edtCurrentPassword;
    private TextInputEditText edtNewPassword;
    private TextInputEditText edtConfirmNewPassword;
    private TextInputLayout tilUsername;
    private TextInputLayout tilCurrentPassword;
    private TextInputLayout tilNewPassword;
    private TextInputLayout tilConfirmNewPassword;
    private MaterialButton btnChangePassword;
    private TextView tvBackToLogin;
    
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);
        initViews();
        setupListeners();
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtCurrentPassword = findViewById(R.id.edtCurrentPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        tilUsername = findViewById(R.id.tilUsername);
        tilCurrentPassword = findViewById(R.id.tilCurrentPassword);
        tilNewPassword = findViewById(R.id.tilNewPassword);
        tilConfirmNewPassword = findViewById(R.id.tilConfirmNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
    }

    private void setupListeners() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangePassword();
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleChangePassword() {
        String username = edtUsername.getText().toString().trim();
        String currentPassword = edtCurrentPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();

        // Reset errors
        tilUsername.setError(null);
        tilCurrentPassword.setError(null);
        tilNewPassword.setError(null);
        tilConfirmNewPassword.setError(null);

        // Validate inputs
        boolean isValid = true;

        if (TextUtils.isEmpty(username)) {
            tilUsername.setError(getString(R.string.error_username_required));
            isValid = false;
        }

        if (TextUtils.isEmpty(currentPassword)) {
            tilCurrentPassword.setError(getString(R.string.error_current_password_required));
            isValid = false;
        }

        if (TextUtils.isEmpty(newPassword)) {
            tilNewPassword.setError(getString(R.string.error_new_password_required));
            isValid = false;
        } else if (newPassword.length() < 6) {
            tilNewPassword.setError(getString(R.string.error_password_min_length));
            isValid = false;
        }

        if (TextUtils.isEmpty(confirmNewPassword)) {
            tilConfirmNewPassword.setError(getString(R.string.error_confirm_new_password_required));
            isValid = false;
        } else if (!newPassword.equals(confirmNewPassword)) {
            tilConfirmNewPassword.setError(getString(R.string.error_password_mismatch));
            isValid = false;
        }

        if (isValid) {
            // Check if username exists
            if (!databaseHelper.checkUsernameExists(username)) {
                tilUsername.setError(getString(R.string.error_username_not_found));
                return;
            }

            // Verify current password
            if (!databaseHelper.verifyPassword(username, currentPassword)) {
                tilCurrentPassword.setError(getString(R.string.error_current_password_incorrect));
                return;
            }

            // Check if new password is same as current password
            if (currentPassword.equals(newPassword)) {
                tilNewPassword.setError(getString(R.string.error_new_password_same_as_current));
                return;
            }

            // Update password
            boolean isUpdated = databaseHelper.updatePassword(username, newPassword);

            if (isUpdated) {
                Toast.makeText(this, getString(R.string.change_password_success), Toast.LENGTH_SHORT).show();
                // Navigate to login screen
                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, getString(R.string.error_change_password_failed), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

