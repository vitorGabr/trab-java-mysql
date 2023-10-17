package src.entity;

import java.util.Map;

public class InsertSql {
    private String table;
    private Map<String, Object> data;

    public InsertSql(String table, Map<String, Object> data) {
        this.table = table;
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getTable() {
        return table;
    }

}
