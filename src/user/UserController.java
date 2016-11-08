package user;

import com.mongodb.client.MongoCollection;
import model.Database;
import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class UserController {

    private final UserCollectionManager userCollectionManager = new UserCollectionManager();
    private final MongoCollection<Document> collection = Database.getInstance().getUserCollection();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private UserDocumentManager userDocumentManager;
    private String username;

    public UserController(String username) {
        this.username = username;
        if (userCollectionManager.getUserDocument(username) != null)
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
    public void chancePassword(String oldPassword, String newPassword) {
        if (!authenticate(oldPassword) || !passwordIsValid(newPassword))
            return;
        String newHashedPassword = BCrypt.hashpw(BCrypt.gensalt(), newPassword);
        userDocumentManager.setPassword(newHashedPassword);
    }

    public boolean chanceUsername() {
        if (!databaseHasUser() && usernameIsValid())
            return false;
        userDocumentManager.setName(username);
        return true;
    }

    public boolean usernameIsValid() {
        return usernameIsValid(username);
    }

    public boolean usernameIsValid(String name) {
        if (name.length() < 4 || name.length() > 14)
            return false;
        return true;
    }

    public boolean passwordIsValid(String password) {
        if (password.length() < 4 || password.length() > 14)
            return false;
//        if (!hasRequiredNumbers(password, 2, password.length() / 2) && !hasValidChars(password))
        return true;
    }

    private boolean hasRequiredNumbers(String string, int minimum, int maximum) {
        int count = 0;
        for (int i = 0 ; i < string.length() - 1 ; i++)
            if (Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).contains((string.substring(i, i + 1))))
                count++;
        return !(count < minimum || count > maximum);
    }

    public boolean databaseHasUser() {
        Document query = new Document();
        query.put("username", username);
        return collection.find(query).iterator().hasNext();
    }

    public boolean databaseHasUser(String name) {
        Document query = new Document();
        query.put("username", name);
        return collection.find(query).iterator().hasNext();
    }

    public boolean validEmail(String email) {
        if (email.length() == 0)
            return false;
        return !(countSymbols(email, "@") != 1 || countSymbols(email, ".") < 1);
    }

    public boolean validDob(String doB) {
        try {
            dateFormat.parse(doB);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean validPostal(String postal) {
        for (String s : Arrays.asList("-", "/", "_"))
            postal.replaceAll(s, "");
        return postal.length() == 6;
    }

    public boolean validStreetNumber(String number) {
        if (number.length() == 0)
            return false;
        int count = 0;
        for (int i : Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)) {
            String num = "";
            num += i;
            if (number.contains(num))
                count++;
        }
        return count != 0;
    }

    private boolean isNumber(String str) {
        return isInteger(str) && Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).contains(Integer.parseInt(str));
    }

    private boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
        } catch (Exception e) {
            return false;
        }
       return true;
    }

    private int countSymbols(String text, String symbol) {
        int count = 0;
        while (text.contains(symbol))  {
            text = text.replaceAll(symbol, "");
            count++;
        }
        return count;
    }

    public boolean adminStatus(){
        if (userDocumentManager.isAdmin()){
            return true;
        } else {
            return false;
        }
    }
}
