package src.entity;

import java.util.HashMap;
import java.util.Map;

public class Book {
    private String title;
    private int publisher_id;
    private String isbn;
    private Float price;

    public Book(String title, int id, Float price, String isbn) {
        this.title = title;
        this.publisher_id = id;
        this.price = price;
        this.isbn = isbn;
    }

    public static Book createFromMap(Map<String, Object> map) {
        String title = map.get("title").toString();
        int id = Integer.parseInt(map.get("publisher_id").toString());
        Float price = Float.parseFloat(map.get("price").toString());
        String isbn = map.get("isbn").toString();
        return new Book(title, id, price, isbn);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", this.title);
        map.put("publisher_id", this.publisher_id);
        map.put("price", this.price);
        map.put("isbn", this.isbn);
        return map;
    }

    public int getId() {
        return publisher_id;
    }

    public Float getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setId(int id) {
        this.publisher_id = id;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
