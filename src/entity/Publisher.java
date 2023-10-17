package src.entity;

import java.util.HashMap;
import java.util.Map;

public class Publisher {
    private int publisher_id;
    private String name;
    private String url;

    public Publisher(int publisher_id, String name, String url) {
        this.publisher_id = publisher_id;
        this.name = name;
        this.url = url;
    }

    public static Publisher createFromMap(Map<String, Object> map) {
        int id = Integer.parseInt(map.get("publisher_id").toString());
        String name = map.get("name").toString();
        String url = map.get("url").toString();
        return new Publisher(id, name, url);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("publisher_id", this.publisher_id);
        map.put("name", this.name);
        map.put("url", this.url);
        return map;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return publisher_id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.publisher_id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
