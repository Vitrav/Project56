package user;

import java.util.Date;

public class User {

    private String username;
    private String salt;
    private String hashedPassword;
    private Address address;
    private int age;
    private Date dateOfBirth;
    private String email;
    private boolean isAdmin;
    private boolean hasPrivateWishList;

    public User(String username, String salt, String hashedPassword, Address address, int age, Date dateOfBirth, String email, boolean isAdmin, boolean hasPrivateWishList) {
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.address = address;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.isAdmin = isAdmin;
        this.hasPrivateWishList = hasPrivateWishList;
    }

    public String getUsername() {
        return username;
    }

    public String getSalt(){
        return salt;
    }

    public String getHashedPassword(){
        return hashedPassword;
    }

    public Address getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean getHasPrivateWishList() {
        return hasPrivateWishList;
    }
}
