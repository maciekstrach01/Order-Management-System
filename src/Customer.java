import java.io.Serializable;
public class Customer implements Serializable  {
    private static int nextId = 1;
    private int id;
    private String firstName;
    private String lastName;
    private String company;
    private String nip;
    private Address address;

    // Konstruktor bezparametrowy
    public Customer() { }

    // Gettery i settery dla wszystkich pól
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return this.company;
    }

    public void setCompanyName(String company) {
        this.company = company;
    }

    public String getNip() {
        return this.nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // metoda toString() do łatwego wyświetlania informacji o obiekcie Customer
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.id +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", companyName='" + this.company + '\'' +
                ", nip='" + this.nip + '\'' +
                ", address=" + this.address +
                '}';
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }
}
