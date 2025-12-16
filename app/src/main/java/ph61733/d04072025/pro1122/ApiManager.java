package ph61733.d04072025.pro1122;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {
    private static final String TAG = "ApiManager";
    private ApiService apiService;
    private Context context;
    
    public ApiManager(Context context) {
        this.context = context;
        this.apiService = ApiClient.getInstance().getApiService();
    }
    
    // ========== USER METHODS ==========
    
    public interface ApiCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }
    
    public void register(User user, ApiCallback<User> callback) {
        Call<ApiResponse<User>> call = apiService.register(user);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Đăng ký thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e(TAG, "Register error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void login(String username, String password, ApiCallback<User> callback) {
        LoginRequest request = new LoginRequest(username, password);
        Call<ApiResponse<User>> call = apiService.login(request);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Đăng nhập thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e(TAG, "Login error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void changePassword(String username, String currentPassword, String newPassword, ApiCallback<Void> callback) {
        ChangePasswordRequest request = new ChangePasswordRequest(currentPassword, newPassword);
        Call<ApiResponse<Void>> call = apiService.changePassword(username, request);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(null);
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Đổi mật khẩu thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e(TAG, "Change password error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    // ========== PRODUCT METHODS ==========
    
    public void getAllProducts(ApiCallback<List<Product>> callback) {
        Call<ApiResponse<List<Product>>> call = apiService.getAllProducts();
        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getDataList());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể tải danh sách sản phẩm");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.e(TAG, "Get products error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void getProductById(int productId, ApiCallback<Product> callback) {
        Call<ApiResponse<Product>> call = apiService.getProductById(productId);
        call.enqueue(new Callback<ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể tải thông tin sản phẩm");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Product>> call, Throwable t) {
                Log.e(TAG, "Get product error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void searchProducts(String query, ApiCallback<List<Product>> callback) {
        Call<ApiResponse<List<Product>>> call = apiService.searchProducts(query);
        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getDataList());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Tìm kiếm thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.e(TAG, "Search products error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    // ========== CART METHODS ==========
    
    public void getCartItems(int userId, ApiCallback<List<CartItem>> callback) {
        Call<ApiResponse<List<CartItem>>> call = apiService.getCartItems(userId);
        call.enqueue(new Callback<ApiResponse<List<CartItem>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CartItem>>> call, Response<ApiResponse<List<CartItem>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getDataList());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể tải giỏ hàng");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<List<CartItem>>> call, Throwable t) {
                Log.e(TAG, "Get cart error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void addToCart(int userId, int productId, int quantity, ApiCallback<CartItem> callback) {
        CartRequest request = new CartRequest(userId, productId, quantity);
        Call<ApiResponse<CartItem>> call = apiService.addToCart(request);
        call.enqueue(new Callback<ApiResponse<CartItem>>() {
            @Override
            public void onResponse(Call<ApiResponse<CartItem>> call, Response<ApiResponse<CartItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Thêm vào giỏ hàng thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<CartItem>> call, Throwable t) {
                Log.e(TAG, "Add to cart error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void updateCartQuantity(int cartId, int quantity, ApiCallback<CartItem> callback) {
        CartUpdateRequest request = new CartUpdateRequest(quantity);
        Call<ApiResponse<CartItem>> call = apiService.updateCartQuantity(cartId, request);
        call.enqueue(new Callback<ApiResponse<CartItem>>() {
            @Override
            public void onResponse(Call<ApiResponse<CartItem>> call, Response<ApiResponse<CartItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Cập nhật giỏ hàng thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<CartItem>> call, Throwable t) {
                Log.e(TAG, "Update cart error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void removeFromCart(int cartId, ApiCallback<Void> callback) {
        Call<ApiResponse<Void>> call = apiService.removeFromCart(cartId);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(null);
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Xóa khỏi giỏ hàng thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e(TAG, "Remove from cart error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void clearCart(int userId, ApiCallback<Void> callback) {
        Call<ApiResponse<Void>> call = apiService.clearCart(userId);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(null);
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Xóa giỏ hàng thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e(TAG, "Clear cart error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void getCartItemCount(int userId, ApiCallback<Integer> callback) {
        Call<ApiResponse<Integer>> call = apiService.getCartItemCount(userId);
        call.enqueue(new Callback<ApiResponse<Integer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể lấy số lượng giỏ hàng");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Integer>> call, Throwable t) {
                Log.e(TAG, "Get cart count error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    // ========== ORDER METHODS ==========
    
    public void createOrder(int userId, String totalAmount, String address, String phone, 
                           List<CartItem> cartItems, ApiCallback<Order> callback) {
        List<OrderItemRequest> items = new ArrayList<>();
        for (CartItem item : cartItems) {
            items.add(new OrderItemRequest(item.getProductId(), item.getQuantity(), item.getProductPrice()));
        }
        
        OrderRequest request = new OrderRequest(userId, totalAmount, address, phone, items);
        Call<ApiResponse<Order>> call = apiService.createOrder(request);
        call.enqueue(new Callback<ApiResponse<Order>>() {
            @Override
            public void onResponse(Call<ApiResponse<Order>> call, Response<ApiResponse<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Tạo đơn hàng thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Order>> call, Throwable t) {
                Log.e(TAG, "Create order error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void getOrders(int userId, ApiCallback<List<Order>> callback) {
        Call<ApiResponse<List<Order>>> call = apiService.getOrders(userId);
        call.enqueue(new Callback<ApiResponse<List<Order>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Order>>> call, Response<ApiResponse<List<Order>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getDataList());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể tải danh sách đơn hàng");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<List<Order>>> call, Throwable t) {
                Log.e(TAG, "Get orders error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void getOrderById(int orderId, ApiCallback<Order> callback) {
        Call<ApiResponse<Order>> call = apiService.getOrderById(orderId);
        call.enqueue(new Callback<ApiResponse<Order>>() {
            @Override
            public void onResponse(Call<ApiResponse<Order>> call, Response<ApiResponse<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể tải thông tin đơn hàng");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Order>> call, Throwable t) {
                Log.e(TAG, "Get order error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void getOrderItems(int orderId, ApiCallback<List<OrderItem>> callback) {
        Call<ApiResponse<List<OrderItem>>> call = apiService.getOrderItems(orderId);
        call.enqueue(new Callback<ApiResponse<List<OrderItem>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<OrderItem>>> call, Response<ApiResponse<List<OrderItem>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getDataList());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể tải chi tiết đơn hàng");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<List<OrderItem>>> call, Throwable t) {
                Log.e(TAG, "Get order items error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    // ========== FAVORITES METHODS ==========
    
    public void getFavoriteProducts(int userId, ApiCallback<List<Product>> callback) {
        Call<ApiResponse<List<Product>>> call = apiService.getFavoriteProducts(userId);
        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getDataList());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể tải danh sách yêu thích");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.e(TAG, "Get favorites error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void addToFavorites(int userId, int productId, ApiCallback<Void> callback) {
        FavoriteRequest request = new FavoriteRequest(userId, productId);
        Call<ApiResponse<Void>> call = apiService.addToFavorites(request);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(null);
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Thêm vào yêu thích thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e(TAG, "Add to favorites error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void removeFromFavorites(int userId, int productId, ApiCallback<Void> callback) {
        Call<ApiResponse<Void>> call = apiService.removeFromFavorites(userId, productId);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(null);
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Xóa khỏi yêu thích thất bại");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e(TAG, "Remove from favorites error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    
    public void isFavorite(int userId, int productId, ApiCallback<Boolean> callback) {
        Call<ApiResponse<Boolean>> call = apiService.isFavorite(userId, productId);
        call.enqueue(new Callback<ApiResponse<Boolean>>() {
            @Override
            public void onResponse(Call<ApiResponse<Boolean>> call, Response<ApiResponse<Boolean>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Không thể kiểm tra trạng thái yêu thích");
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse<Boolean>> call, Throwable t) {
                Log.e(TAG, "Check favorite error: ", t);
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}


