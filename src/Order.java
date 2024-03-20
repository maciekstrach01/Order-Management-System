import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private int customerId;
    private HashMap<Integer, OrderItem> orderItems;
    private Date orderDate;
    private Address deliveryAddress;

    // Konstruktor bezparametrowy
    public Order() { }

    // Dodaj gettery i settery dla innych pól
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public HashMap<Integer, OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(HashMap<Integer, OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderGrossValue() {
        return this.getOrderItems().values().stream().map(OrderItem::getGrossTotal).reduce(0.0, Double::sum);
    }

    public Address getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + this.getId() +
                ", customerId=" + this.getCustomerId() +
                ", orderDate=" + this.getOrderDate() +
                ", orderGrossValue=" + this.getOrderGrossValue() +
                ", deliveryAddress='" + this.getDeliveryAddress() + '\'' +
                '}';
    }
}
