import java.io.Serializable;
public class Product implements Serializable{
    private int id;
    private String name;
    private String description;
    private String sku;
    private double netPrice;
    private double grossPrice;
    private Dimensions dimensions;
    private Double weight;

    // Konstruktur bezparametrowy
    public Product() { }

    // Gettery i settery dla wszystkich pól
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if (this.description == null) {
            return "";
        }

        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getNetPrice() {
        return this.netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public double getGrossPrice() {
        return this.grossPrice;
    }

    public void setGrossPrice(double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public Dimensions getDimensions() {
        if (this.dimensions == null) {
            return new Dimensions();
        }

        return this.dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public double getWeight() {
        if (this.weight == null) {
            return 0;
        }

        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // metoda toString() do łatwego wyświetlania informacji o obiekcie Product
    @Override
    public String toString() {
        return "Product{" +
                "productID=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", sku='" + this.getSku() + '\'' +
                ", netPrice=" + this.getNetPrice() +
                ", grossPrice=" + this.getGrossPrice() +
                ", dimensions=" + this.getDimensions() +
                ", weight=" + this.getWeight() +
                '}';
    }
}

