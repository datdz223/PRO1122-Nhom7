package ph61733.d04072025.pro1122;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvFullName;
    private TextView tvUsername;
    private TextView tvEmail;
    private LinearLayout layoutChangePassword;
    private LinearLayout layoutLogout;
    private MaterialToolbar toolbar;
    
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        
        initViews();
        displayUserInfo();
        setupListeners();
    }
    
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tvFullName = findViewById(R.id.tvFullName);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        layoutChangePassword = findViewById(R.id.layoutChangePassword);
        layoutLogout = findViewById(R.id.layoutLogout);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    private void displayUserInfo() {
        String fullName = sessionManager.getFullName();
        String username = sessionManager.getUsername();
        String email = sessionManager.getEmail();
        
        tvFullName.setText(fullName);
        tvUsername.setText(username);
        tvEmail.setText(email);
    }
    
    private void setupListeners() {
        layoutChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                finish();
            }
        });
    }
}

