package src.entity;

import java.util.HashMap;
import java.util.Map;

public class Author {
    private String name;
    private String fname;

    private Integer author_id;

    public Author(String name, Integer int1, String fname) {
        this.name = name;
        this.author_id = int1;
        this.fname = fname;
    }

    public static Author createFromMap(Map<String, Object> map) {
        String name = map.get("name").toString();
        String fname = map.get("fname").toString();
        int author_id = Integer.parseInt(map.get("author_id").toString());
        return new Author(name, author_id, fname);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("fname", this.fname);
        map.put("author_id", this.author_id);
        return map;
    }

    public String getName() {
        return name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public String getFname() {
        return fname;
    }

}
