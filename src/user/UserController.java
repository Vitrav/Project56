package user;

import org.mindrot.jbcrypt.BCrypt;

public class UserController {

    // Authenticate the User by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User User = UserDao.getUserByUsername(username);
        if (User == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, User.getSalt());
        return hashedPassword.equals(User.getHashedPassword());
    }

    // This method doesn't do anything, it's just included as an example
    public static void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the User salt and password
        }
    }
}
