package user;

import java.util.Date;

//Simple class to capture User information, is used by the UserCollectionManager to insert a new user.
public class User {

    private String username,salt, email;
    private Address address;
    private int age;
    private Date dateOfBirth;
    private boolean isAdmin,hasPrivateWishList,isBlocked;
    public static String hashedPassword;

    public User(String username, String salt, String hashedPassword, Address address, int age, Date dateOfBirth, String email, boolean isAdmin, boolean hasPrivateWishList, boolean isBlocked) {
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.address = address;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.isAdmin = isAdmin;
        this.hasPrivateWishList = hasPrivateWishList;
        this.isBlocked = isBlocked;
    }

    public String getUsername() {
        return username;
    }

    public String getSalt(){
        return salt;
    }

    public static String getHashedPassword(){
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

    public boolean getIsBlocked() {
        return isBlocked;
    }
}
