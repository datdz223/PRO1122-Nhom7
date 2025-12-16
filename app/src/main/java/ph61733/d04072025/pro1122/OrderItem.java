package ph61733.d04072025.pro1122;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int productId;
    private int quantity;
    private String price;
    private String productName;
    private String productCode;
    private int productImageResId;
    
    public OrderItem() {
    }
    
    public int getOrderItemId() {
        return orderItemId;
    }
    
    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
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
    
    public int getProductImageResId() {
        return productImageResId;
    }
    
    public void setProductImageResId(int productImageResId) {
        this.productImageResId = productImageResId;
    }
}


