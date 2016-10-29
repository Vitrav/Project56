package user;

import lombok.*;

@Value // All fields are private and final. Getters (but not setters) are generated (https://projectlombok.org/features/Value.html)
public class user {
    String username;
    String salt;
    String hashedPassword;

    public user(String username, String salt, String hashedPassword) {
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
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
}
