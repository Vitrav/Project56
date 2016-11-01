package user;

import com.mongodb.client.MongoCollection;
import model.Database;
import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;

public class UserController {

    private final UserCollectionManager userCollectionManager = new UserCollectionManager();
    private final MongoCollection<Document> collection = Database.getInstance().getUserCollection();
    private UserDocumentManager userDocumentManager;
    private String username;

    public UserController(String username) {
        this.username = username;
        userDocumentManager = new UserDocumentManager(userCollectionManager.getUserDocument(username));
    }

    // Authenticate the User by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public boolean authenticate(String password) {
        if (username.isEmpty() || password.isEmpty())
            return false;
        if (!databaseHasUser())
            return false;

        String hashedPassword = BCrypt.hashpw(password, userDocumentManager.getSalt());
        return hashedPassword.equals(userDocumentManager.getPassword());
    }

    // Authenticate and change the password.
    public void chancePassword(String username, String oldPassword, String newPassword) {
        if (!authenticate(oldPassword) || !passwordIsValid(newPassword))
            return;
        String newHashedPassword = BCrypt.hashpw(BCrypt.gensalt(), newPassword);
        userDocumentManager.setPassword(newHashedPassword);
    }

    public boolean chanceUsername(String username) {
        if (!databaseHasUser() && usernameIsValid(username))
            return false;
        userDocumentManager.setName(username);
        return true;
    }

    private boolean usernameIsValid(String username) {
        if (username.length() < 4 || username.length() > 14)
            return false;
        if (!hasRequiredNumbers(username, 1, username.length() / 2) && !hasValidChars(username))
            return false;
        return true;
    }

    private boolean passwordIsValid(String password) {
        if (password.length() < 4 || password.length() > 14)
            return false;
        if (!hasRequiredNumbers(password, 2, password.length() / 2) && !hasValidChars(password))
            return false;
        return true;
    }

    private boolean hasValidChars(String string) {
        for (char c : string.toCharArray())
            if(!Character.isLetter(c) && !isNumber(Character.toString(c)))
                return false;
        return true;
    }

    private boolean hasRequiredNumbers(String string, int minimum, int maximum) {
        int count = 0;
        for (int i = 0 ; i < string.length() - 1 ; i++)
            if (Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).contains((string.substring(i, i + 1))))
                count++;
        if (count < minimum || count > maximum)
            return false;
        return true;
    }

    public boolean databaseHasUser() {
        Document query = new Document();
        query.put("username", username);
        return collection.find(query).iterator().hasNext();
    }

    private boolean isNumber(String str) {
        if (!isInteger(str))
            return false;
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).contains(Integer.parseInt(str));
    }

    private boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
        } catch (Exception e) {
            return false;
        }
       return true;
    }
}
