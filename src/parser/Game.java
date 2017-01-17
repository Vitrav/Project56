package parser;

import java.util.Date;

public class Game {

    private final int id;
    private final String name;
    private final String publisher;
    private final double price;
    private final int age;
    private final String platform;
    private final String genre;
    private final int amount;
    private final Date releaseDate;
    private final String description;
    private final String image;

    public Game(int id, String name, String publisher, double price, int age, String platform, String genre, int amount, Date releaseDate, String description, String image) {
        this.id = id;
        this.name = name;
        this.publisher = publisher;
        this.price = price;
        this.age = age;
        this.platform = platform;
        this.genre = genre;
        this.amount = amount;
        this.releaseDate = releaseDate;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPublisher() {
        return publisher;
    }

    public double getPrice() {
        return price;
    }

    public int getAge() {
        return age;
    }

    public String getPlatform() {
        return platform;
    }

    public String getGenre() {
        return genre;
    }

    public int getAmount() {
        return amount;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() { return image;}

}
