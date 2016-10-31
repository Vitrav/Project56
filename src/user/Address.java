package user;

public class Address {

//    public Document createAddressDocument(String country, String city, String street, String number, String postalcode) {

    private String country;
    private String city;
    private String street;
    private String number;
    private String postalCode;

    public Address(String country, String city, String street, String number, String postalcode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalCode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
