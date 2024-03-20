import java.io.Serializable;

public class OrderItem implements Serializable {
    private Product product;
    private int quantity;
    private double discount;
    private double netTotal;
    private double grossTotal;

    // Konstruktor bezparametrowy
    public OrderItem() { }

    // Gettery i settery dla wszystkich pól
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.calculateTotals(); // Po zmianie ilości, obliczenie netTotal i grossTotal
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.calculateTotals(); // Po zmianie rabatu, obliczenie netTotal i grossTotal
    }

    public double getNetTotal() {
        return this.netTotal;
    }

    public double getGrossTotal() {
        return this.grossTotal;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.calculateTotals(); // Po zmianie produktu, obliczenie netTotal i grossTotal
    }

    // Metoda do obliczenia netTotal i grossTotal
    private void calculateTotals() {
        if (this.product != null) {
            double price = this.product.getNetPrice();

            this.netTotal = this.quantity * price * (1 - this.discount);
            this.grossTotal = this.quantity * this.product.getGrossPrice() * (1 - this.discount);
        }
    }

    // metoda toString() do łatwego wyświetlania informacji o obiekcie OrderItem
    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + this.getProduct() +
                ", quantity=" + this.getQuantity() +
                ", discount=" + this.getDiscount() +
                ", netTotal=" + this.getNetTotal() +
                ", grossTotal=" + this.getGrossTotal() +
                ", product=" + this.getProduct() +
                '}';
    }
}
