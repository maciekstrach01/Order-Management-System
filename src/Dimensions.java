import java.io.Serializable;
public class Dimensions implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double length;
    private Double width;
    private Double height;

    // Konstruktor bezparametrowy
    public Dimensions() { }

    // Konstruktor z wszystkimi parametrami
    public Dimensions(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    // Gettery i settery dla wszystkich pól
    public double getLength() {
        if (this.length == null) {
            return 0;
        }

        return this.length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        if (this.width == null) {
            return 0;
        }

        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        if (this.height == null) {
            return 0;
        }

        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // metoda toString() do łatwego wyświetlania informacji o obiekcie Dimensions
    @Override
    public String toString() {
        return "Dimensions{" +
                "length=" + this.getLength() +
                ", width=" + this.getWidth() +
                ", height=" + this.getHeight() +
                '}';
    }
}
