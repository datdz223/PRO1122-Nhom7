package ph61733.d04072025.pro1122;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    // Database Info
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 2;
    
    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_ORDER_ITEMS = "order_items";
    private static final String TABLE_FAVORITES = "favorites";
    
    // User Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    
    // Product Table Columns
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_PRODUCT_CODE = "code";
    private static final String KEY_PRODUCT_PRICE = "price";
    private static final String KEY_PRODUCT_DESCRIPTION = "description";
    private static final String KEY_PRODUCT_IMAGE = "image_res_id";
    private static final String KEY_PRODUCT_CATEGORY = "category";
    
    // Cart Table Columns
    private static final String KEY_CART_ID = "cart_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_QUANTITY = "quantity";
    
    // Order Table Columns
    private static final String KEY_ORDER_ID = "order_id";
    private static final String KEY_ORDER_DATE = "order_date";
    private static final String KEY_TOTAL_AMOUNT = "total_amount";
    private static final String KEY_STATUS = "status";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";
    
    // Order Items Columns
    private static final String KEY_ORDER_ITEM_ID = "order_item_id";
    private static final String KEY_ORDER_ITEM_QUANTITY = "quantity";
    private static final String KEY_ORDER_ITEM_PRICE = "price";
    
    // Favorites Columns
    private static final String KEY_FAVORITE_ID = "favorite_id";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FULL_NAME + " TEXT,"
                + KEY_USERNAME + " TEXT UNIQUE,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
        
        // Products table
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_PRODUCT_CODE + " TEXT UNIQUE,"
                + KEY_PRODUCT_PRICE + " TEXT,"
                + KEY_PRODUCT_DESCRIPTION + " TEXT,"
                + KEY_PRODUCT_IMAGE + " INTEGER,"
                + KEY_PRODUCT_CATEGORY + " TEXT"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        
        // Cart table
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_PRODUCT_ID + " INTEGER,"
                + KEY_QUANTITY + " INTEGER,"
                + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + KEY_PRODUCT_ID + ")"
                + ")";
        db.execSQL(CREATE_CART_TABLE);
        
        // Orders table
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + KEY_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_ORDER_DATE + " TEXT,"
                + KEY_TOTAL_AMOUNT + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
        
        // Order items table
        String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE " + TABLE_ORDER_ITEMS + "("
                + KEY_ORDER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ORDER_ID + " INTEGER,"
                + KEY_PRODUCT_ID + " INTEGER,"
                + KEY_ORDER_ITEM_QUANTITY + " INTEGER,"
                + KEY_ORDER_ITEM_PRICE + " TEXT,"
                + "FOREIGN KEY(" + KEY_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + KEY_ORDER_ID + "),"
                + "FOREIGN KEY(" + KEY_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + KEY_PRODUCT_ID + ")"
                + ")";
        db.execSQL(CREATE_ORDER_ITEMS_TABLE);
        
        // Favorites table
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_PRODUCT_ID + " INTEGER,"
                + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + KEY_PRODUCT_ID + ")"
                + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
        
        // Insert sample products
        insertSampleProducts(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    
    private void insertSampleProducts(SQLiteDatabase db) {
        String[][] products = {
            {"ÁO SƠ MI - APH12", "2099-00008", "1000000", "Áo sơ mi nam cao cấp, chất liệu cotton 100%", "1", "Áo"},
            {"ÁO JACKET - APH45", "1,0500-00026", "1200000", "Áo jacket nam thời trang, chống nước", "1", "Áo"},
            {"ÁO LEN - ALG04E", "0000-00000", "850000", "Áo len ấm áp, phong cách hiện đại", "1", "Áo"},
            {"QUẦN JEANS - GJR25", "0000-00000", "950000", "Quần jeans nam form slim, chất lượng cao", "1", "Quần"},
            {"ÁO THUN NAM", "AT-001", "450000", "Áo thun nam basic, thoáng mát", "1", "Áo"},
            {"QUẦN TÂY NAM", "QT-002", "680000", "Quần tây nam công sở, form đẹp", "1", "Quần"}
        };
        
        for (String[] product : products) {
            ContentValues values = new ContentValues();
            values.put(KEY_PRODUCT_NAME, product[0]);
            values.put(KEY_PRODUCT_CODE, product[1]);
            values.put(KEY_PRODUCT_PRICE, product[2]);
            values.put(KEY_PRODUCT_DESCRIPTION, product[3]);
            values.put(KEY_PRODUCT_IMAGE, Integer.parseInt(product[4]));
            values.put(KEY_PRODUCT_CATEGORY, product[5]);
            db.insert(TABLE_PRODUCTS, null, values);
        }
    }
    
    /**
     * Add a new user to the database
     * @return true if user added successfully, false otherwise
     */
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME, user.getFullName());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        
        return result != -1;
    }
    
    /**
     * Check if username already exists
     */
    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        
        int count = cursor.getCount();
        cursor.close();
        db.close();
        
        return count > 0;
    }
    
    /**
     * Check if email already exists
     */
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        
        int count = cursor.getCount();
        cursor.close();
        db.close();
        
        return count > 0;
    }
    
    /**
     * Check user login credentials
     * @return User object if credentials are valid, null otherwise
     */
    public User checkUserLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_FULL_NAME, KEY_USERNAME, KEY_EMAIL},
                KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setEmail(cursor.getString(3));
        }
        
        cursor.close();
        db.close();
        
        return user;
    }
    
    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_FULL_NAME, KEY_USERNAME, KEY_EMAIL},
                KEY_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setEmail(cursor.getString(3));
        }
        
        cursor.close();
        db.close();
        
        return user;
    }
    
    /**
     * Update user password
     */
    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);
        
        int rowsAffected = db.update(TABLE_USERS,
                values,
                KEY_USERNAME + "=?",
                new String[]{username});
        
        db.close();
        
        return rowsAffected > 0;
    }
    
    /**
     * Verify current password for a user
     */
    public boolean verifyPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
        
        int count = cursor.getCount();
        cursor.close();
        db.close();
        
        return count > 0;
    }
    
    // ========== PRODUCT METHODS ==========
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_NAME)));
                product.setCode(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CODE)));
                product.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_PRICE)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_DESCRIPTION)));
                product.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_IMAGE)));
                product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CATEGORY)));
                products.add(product);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return products;
    }
    
    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, KEY_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(productId)}, null, null, null);
        
        Product product = null;
        if (cursor.moveToFirst()) {
            product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_ID)));
            product.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_NAME)));
            product.setCode(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CODE)));
            product.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_PRICE)));
            product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_DESCRIPTION)));
            product.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_IMAGE)));
            product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CATEGORY)));
        }
        
        cursor.close();
        db.close();
        return product;
    }
    
    public List<Product> searchProducts(String query) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_PRODUCTS, null,
                KEY_PRODUCT_NAME + " LIKE ? OR " + KEY_PRODUCT_CODE + " LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%"},
                null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_NAME)));
                product.setCode(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CODE)));
                product.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_PRICE)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_DESCRIPTION)));
                product.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_IMAGE)));
                product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CATEGORY)));
                products.add(product);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return products;
    }
    
    // ========== CART METHODS ==========
    
    public boolean addToCart(int userId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Check if item already exists in cart
        Cursor cursor = db.query(TABLE_CART, null,
                KEY_USER_ID + "=? AND " + KEY_PRODUCT_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(productId)},
                null, null, null);
        
        if (cursor.moveToFirst()) {
            // Update quantity
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY));
            ContentValues values = new ContentValues();
            values.put(KEY_QUANTITY, currentQuantity + quantity);
            db.update(TABLE_CART, values, KEY_USER_ID + "=? AND " + KEY_PRODUCT_ID + "=?",
                    new String[]{String.valueOf(userId), String.valueOf(productId)});
            cursor.close();
            db.close();
            return true;
        } else {
            // Insert new item
            ContentValues values = new ContentValues();
            values.put(KEY_USER_ID, userId);
            values.put(KEY_PRODUCT_ID, productId);
            values.put(KEY_QUANTITY, quantity);
            long result = db.insert(TABLE_CART, null, values);
            db.close();
            return result != -1;
        }
    }
    
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT c.*, p." + KEY_PRODUCT_NAME + ", p." + KEY_PRODUCT_CODE + ", p." + KEY_PRODUCT_PRICE + 
                ", p." + KEY_PRODUCT_IMAGE + " FROM " + TABLE_CART + " c " +
                "INNER JOIN " + TABLE_PRODUCTS + " p ON c." + KEY_PRODUCT_ID + " = p." + KEY_PRODUCT_ID +
                " WHERE c." + KEY_USER_ID + "=?";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        
        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setCartId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CART_ID)));
                item.setProductId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_ID)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY)));
                item.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_NAME)));
                item.setProductCode(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CODE)));
                item.setProductPrice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_PRICE)));
                item.setProductImageResId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_IMAGE)));
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return cartItems;
    }
    
    public boolean updateCartQuantity(int cartId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUANTITY, quantity);
        int result = db.update(TABLE_CART, values, KEY_CART_ID + "=?",
                new String[]{String.valueOf(cartId)});
        db.close();
        return result > 0;
    }
    
    public boolean removeFromCart(int cartId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CART, KEY_CART_ID + "=?", new String[]{String.valueOf(cartId)});
        db.close();
        return result > 0;
    }
    
    public void clearCart(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_USER_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }
    
    public int getCartItemCount(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + KEY_QUANTITY + ") FROM " + TABLE_CART + 
                " WHERE " + KEY_USER_ID + "=?", new String[]{String.valueOf(userId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
    
    // ========== ORDER METHODS ==========
    
    public long createOrder(int userId, String totalAmount, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_ORDER_DATE, java.text.SimpleDateFormat.getDateTimeInstance().format(new java.util.Date()));
        values.put(KEY_TOTAL_AMOUNT, totalAmount);
        values.put(KEY_STATUS, "Đang xử lý");
        values.put(KEY_ADDRESS, address);
        values.put(KEY_PHONE, phone);
        long orderId = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return orderId;
    }
    
    public boolean addOrderItem(long orderId, int productId, int quantity, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ORDER_ID, orderId);
        values.put(KEY_PRODUCT_ID, productId);
        values.put(KEY_ORDER_ITEM_QUANTITY, quantity);
        values.put(KEY_ORDER_ITEM_PRICE, price);
        long result = db.insert(TABLE_ORDER_ITEMS, null, values);
        db.close();
        return result != -1;
    }
    
    public List<Order> getOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_ORDERS, null, KEY_USER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, KEY_ORDER_ID + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setOrderId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ORDER_ID)));
                order.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_ID)));
                order.setOrderDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ORDER_DATE)));
                order.setTotalAmount(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TOTAL_AMOUNT)));
                order.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS)));
                order.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)));
                order.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)));
                orders.add(order);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return orders;
    }
    
    public Order getOrderById(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, KEY_ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)}, null, null, null);
        
        Order order = null;
        if (cursor.moveToFirst()) {
            order = new Order();
            order.setOrderId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ORDER_ID)));
            order.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_ID)));
            order.setOrderDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ORDER_DATE)));
            order.setTotalAmount(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TOTAL_AMOUNT)));
            order.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS)));
            order.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)));
            order.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)));
        }
        
        cursor.close();
        db.close();
        return order;
    }
    
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT oi.*, p." + KEY_PRODUCT_NAME + ", p." + KEY_PRODUCT_CODE + 
                ", p." + KEY_PRODUCT_IMAGE + " FROM " + TABLE_ORDER_ITEMS + " oi " +
                "INNER JOIN " + TABLE_PRODUCTS + " p ON oi." + KEY_PRODUCT_ID + " = p." + KEY_PRODUCT_ID +
                " WHERE oi." + KEY_ORDER_ID + "=?";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});
        
        if (cursor.moveToFirst()) {
            do {
                OrderItem item = new OrderItem();
                item.setOrderItemId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ORDER_ITEM_ID)));
                item.setOrderId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ORDER_ID)));
                item.setProductId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_ID)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ORDER_ITEM_QUANTITY)));
                item.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ORDER_ITEM_PRICE)));
                item.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_NAME)));
                item.setProductCode(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CODE)));
                item.setProductImageResId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_IMAGE)));
                items.add(item);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return items;
    }
    
    // ========== FAVORITES METHODS ==========
    
    public boolean addToFavorites(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Check if already in favorites
        Cursor cursor = db.query(TABLE_FAVORITES, null,
                KEY_USER_ID + "=? AND " + KEY_PRODUCT_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(productId)},
                null, null, null);
        
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false; // Already in favorites
        }
        
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_PRODUCT_ID, productId);
        long result = db.insert(TABLE_FAVORITES, null, values);
        cursor.close();
        db.close();
        return result != -1;
    }
    
    public boolean removeFromFavorites(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FAVORITES, KEY_USER_ID + "=? AND " + KEY_PRODUCT_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(productId)});
        db.close();
        return result > 0;
    }
    
    public boolean isFavorite(int userId, int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, null,
                KEY_USER_ID + "=? AND " + KEY_PRODUCT_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(productId)},
                null, null, null);
        boolean isFav = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isFav;
    }
    
    public List<Product> getFavoriteProducts(int userId) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT p.* FROM " + TABLE_PRODUCTS + " p " +
                "INNER JOIN " + TABLE_FAVORITES + " f ON p." + KEY_PRODUCT_ID + " = f." + KEY_PRODUCT_ID +
                " WHERE f." + KEY_USER_ID + "=?";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_NAME)));
                product.setCode(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CODE)));
                product.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_PRICE)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_DESCRIPTION)));
                product.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_IMAGE)));
                product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRODUCT_CATEGORY)));
                products.add(product);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return products;
    }
}

