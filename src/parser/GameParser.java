package parser;

import model.Database;
import model.collection.GameCollectionManager;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
//reads a file and puts the game in the database
public class GameParser {

    private final List<String> lines = new ArrayList<>();
    private final List<Game> games = new ArrayList<>();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public void fillLines() {
        if (!lines.isEmpty())
            return;

        String filePath = new File("").getAbsolutePath();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath + "/src/parser/games.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line != null)
                    lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGames() {
        if (lines.isEmpty())
            fillLines();

        for (int i = 0; i < lines.size(); i++ ) {
            String line = lines.get(i);
            List<String> lineParts = new ArrayList<>();

            while (line.length() > 1) {
                String part = line.substring(0, line.contains(";") ? line.indexOf(";") : line.length());
                if (part.length() > 10 && part.substring(0, 6).contains("http:")) {
                    lineParts.add(part.replaceAll(" ", ""));
                } else
                    lineParts.add(part);

                if (!line.contains(";")) {
                    break;
                }

                line = removeFromLine(line, part);
            }

            try {
                games.add(new Game(Integer.parseInt(lineParts.get(0)), lineParts.get(1), lineParts.get(2),
                        Double.parseDouble(lineParts.get(3)), Integer.parseInt(lineParts.get(4)), lineParts.get(5), lineParts.get(6),
                        20, dateFormat.parse(reverseDate(lineParts.get(7))), lineParts.get(8), lineParts.get(9))
                );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception: game parsing went wrong." );
            }
        }
    }

    public void addGamesToDB() {
        if (games.isEmpty())
            addGames();
        Database.getInstance().getGameCollection().drop();
        GameCollectionManager manager = new GameCollectionManager();
        games.forEach(game -> manager.insertNewGame(game));
    }

    public String reverseDate(String date) {
        String newDate = "";
        newDate += date.substring(date.lastIndexOf("-") + 1, date.length());
        newDate += date.substring(date.indexOf("-"), date.lastIndexOf("-"));
        newDate += "-" + date.substring(0, date.indexOf("-"));
        return newDate.replaceAll("-", "/");
    }

    public String removeFromLine(String line, String part) {
        line = line.replaceFirst(part, "");
        return line.replaceFirst(";", "");
    }

    public List<String> getLines() {
        return lines;
    }

    public List<Game> getGames() {
        return games;
    }
}
