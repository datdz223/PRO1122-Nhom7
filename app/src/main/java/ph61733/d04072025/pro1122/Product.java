package ph61733.d04072025.pro1122;

public class Product {
    private int id;
    private String name;
    private String code;
    private String price;
    private String description;
    private int imageResId;
    private String category;
    
    public Product() {
    }
    
    public Product(String name, String code, String price, int imageResId) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.imageResId = imageResId;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getImageResId() {
        return imageResId;
    }
    
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}
