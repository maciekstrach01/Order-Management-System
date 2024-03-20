import java.io.Serializable;

public class Address implements Serializable {
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String city;
    private String postalCode;
    private String voivodeship;
    private Country country;

    // Konstruktor bezparametrowy
    public Address() { }

    // Gettery i settery dla wszystkich pól
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return this.buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getApartmentNumber() {
        if (this.apartmentNumber == null) {
            return "";
        }

        return this.apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getVoivodeship() {
        return this.voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    // metoda toString() do łatwego wyświetlania informacji o obiekcie Address
    @Override
    public String toString() {
        String prettyApartmentNumber = "";
        if (!this.getApartmentNumber().equals("")) {
            prettyApartmentNumber = "/" + this.getApartmentNumber();
        }

        return this.getStreet() + " " + this.getBuildingNumber() + prettyApartmentNumber + ", " + this.getPostalCode()
                + " " + this.getCity() + ", " + this.getVoivodeship() + " " + this.getCountry();
    }
}

