package ph61733.d04072025.pro1122;

public class CartItem {
    private int cartId;
    private int productId;
    private int quantity;
    private String productName;
    private String productCode;
    private String productPrice;
    private int productImageResId;
    
    public CartItem() {
    }
    
    public int getCartId() {
        return cartId;
    }
    
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductCode() {
        return productCode;
    }
    
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    public String getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
    
    public int getProductImageResId() {
        return productImageResId;
    }
    
    public void setProductImageResId(int productImageResId) {
        this.productImageResId = productImageResId;
    }
}


