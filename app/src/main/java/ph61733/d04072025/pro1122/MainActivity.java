package ph61733.d04072025.pro1122;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private BottomNavigationView bottomNavigation;
    private ImageView btnMenu;
    
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check if user is logged in
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        initViews();
        setupRecyclerView();
        setupBottomNavigation();
        setupMenuButton();
        loadProducts();
    }
    
    private void initViews() {
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        btnMenu = findViewById(R.id.btnMenu);
    }
    
    private void setupRecyclerView() {
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                Toast.makeText(MainActivity.this, "Clicked: " + product.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewProducts.setLayoutManager(layoutManager);
        recyclerViewProducts.setAdapter(productAdapter);
    }
    
    private void setupBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    // Already on home
                    return true;
                } else if (itemId == R.id.nav_list) {
                    Toast.makeText(MainActivity.this, "Danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_orders) {
                    Toast.makeText(MainActivity.this, "Đơn hàng", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_messages) {
                    Toast.makeText(MainActivity.this, "Tin nhắn", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_account) {
                    showAccountInfo();
                    return true;
                }
                return false;
            }
        });
    }
    
    private void setupMenuButton() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }
    
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());
        
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_change_password) {
                    Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_logout) {
                    sessionManager.logout();
                    return true;
                }
                return false;
            }
        });
        
        popup.show();
    }
    
    private void showAccountInfo() {
        String fullName = sessionManager.getFullName();
        String username = sessionManager.getUsername();
        String email = sessionManager.getEmail();
        
        String message = "Tên: " + fullName + "\nTài khoản: " + username + "\nEmail: " + email;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    private void loadProducts() {
        // Sử dụng API để lấy danh sách sản phẩm từ MongoDB
        ApiManager apiManager = new ApiManager(this);
        
        apiManager.getAllProducts(new ApiManager.ApiCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> data) {
                productList.clear();
                productList.addAll(data);
                productAdapter.notifyDataSetChanged();
            }
            
            @Override
            public void onError(String error) {
                // Nếu API lỗi, sử dụng dữ liệu mẫu
                loadSampleProducts();
                Toast.makeText(MainActivity.this, "Không thể tải từ API: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void loadSampleProducts() {
        // Dữ liệu mẫu khi API không khả dụng
        productList.clear();
        productList.add(new Product("ÁO SƠ MI - APH12", "2099-00008", "1,000,000đ", android.R.drawable.ic_menu_gallery));
        productList.add(new Product("ÁO JACKET - APH45", "1,0500-00026", "1,200,000đ", android.R.drawable.ic_menu_gallery));
        productList.add(new Product("ÁO LEN - ALG04E", "0000-00000", "850,000đ", android.R.drawable.ic_menu_gallery));
        productList.add(new Product("QUẦN JEANS - GJR25", "0000-00000", "950,000đ", android.R.drawable.ic_menu_gallery));
        productList.add(new Product("ÁO THUN NAM", "AT-001", "450,000đ", android.R.drawable.ic_menu_gallery));
        productList.add(new Product("QUẦN TÂY NAM", "QT-002", "680,000đ", android.R.drawable.ic_menu_gallery));
        productAdapter.notifyDataSetChanged();
    }
}
