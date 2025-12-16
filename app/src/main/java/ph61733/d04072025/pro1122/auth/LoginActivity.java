package ph61733.d04072025.pro1122.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity {
    private EditText etEmailOrPhone;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;
    private ProgressBar progressBar;
    private SharedPreferencesHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo ApiClient với context
        ApiClient.init(this);

        prefs = new SharedPreferencesHelper(this);

        // Nếu đã đăng nhập, kiểm tra role và chuyển đến màn hình phù hợp
        if (prefs.isLoggedIn()) {
            String role = prefs.getUserRole();
            if ("ADMIN".equalsIgnoreCase(role)) {
                navigateToAdminDashboard();
            } else {
                navigateToHome();
            }
            return;
        }

        initViews();
        setupListeners();
    }

    private void initViews() {
        etEmailOrPhone = findViewById(R.id.etEmailOrPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
        progressBar = findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> {
            String emailOrPhone = etEmailOrPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInput(emailOrPhone, password)) {
                performLogin(emailOrPhone, password);
            }
        });

        tvForgotPassword.setOnClickListener(v -> {
            // TODO: Navigate to forgot password screen
            Toast.makeText(this, "Quên mật khẩu", Toast.LENGTH_SHORT).show();
        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInput(String emailOrPhone, String password) {
        if (TextUtils.isEmpty(emailOrPhone)) {
            etEmailOrPhone.setError("Vui lòng nhập email");
            etEmailOrPhone.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Vui lòng nhập mật khẩu");
            etPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            etPassword.requestFocus();
            return false;
        }
}
