package src.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.entity.InsertSql;

public abstract class DaoImplement {

    private final static String USER = "root";
    private final static String PASS = "";
    private final static String DATABASE = "livraria";
    private final static String URL = "jdbc:mysql://localhost:3306/" + DATABASE;

    private String table;

    public void setTable(String table) {
        this.table = table;
    }

    protected String getTable() {
        return table;
    }

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    protected void parseParams(PreparedStatement s, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            if (params.get(i) == null)
                continue;
            if (params.get(i) instanceof String)
                s.setString(i + 1, (String) params.get(i));
            else if (params.get(i) instanceof Integer)
                s.setInt(i + 1, (Integer) params.get(i));
            else if (params.get(i) instanceof Float)
                s.setFloat(i + 1, (Float) params.get(i));
        }
    }

    protected List<Map<String, Object>> findAll(String query, List<Object> params) {
        List<Map<String, Object>> resultSets = new ArrayList<>();
        try (Connection c = getConnection()) {

            String comando = query.contains("SELECT") ? query : "SELECT * FROM " + table + " WHERE " + query;

            PreparedStatement s = c.prepareStatement(comando);
            parseParams(s, params);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Map<String, Object> resultSet = new HashMap<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    resultSet.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                resultSets.add(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSets;
    }

    protected boolean insert(List<InsertSql> data) throws SQLIntegrityConstraintViolationException {
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            for (InsertSql e : data) {
                String comando = "INSERT INTO " + e.getTable() + " SET ";
                List<Object> list = e.getData().values().stream().filter(v -> v != null).toList();
                int count = 0;
                int totalItems = list.size();

                for (Map.Entry<String, Object> entry : e.getData().entrySet()) {
                    String key = entry.getKey();
                    if (entry.getValue() != null) {
                        comando += key + " = ? ";
                        if (count < totalItems - 1) {
                            comando += ", ";
                        }
                    }
                    count++;
                }
                PreparedStatement s = c.prepareStatement(comando);
                parseParams(s, list);
                s.executeUpdate();
            }
            c.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean delete(String id, String value) throws SQLIntegrityConstraintViolationException {

        try (Connection c = getConnection()) {
            String comando = "DELETE FROM " + table + " WHERE " + id + " = ?";

            PreparedStatement s = c.prepareStatement(comando);
            s.setString(1, value);
            s.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBookAndRelated(String idToDelete) throws SQLException {
        Boolean result = false;
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            try {
                String deleteBookAuthorsQuery = "DELETE FROM booksauthors WHERE isbn = ?";
                try (PreparedStatement bookAuthorsStatement = c.prepareStatement(deleteBookAuthorsQuery)) {
                    bookAuthorsStatement.setString(1, idToDelete);
                    bookAuthorsStatement.executeUpdate();
                }

                String deleteBookQuery = "DELETE FROM books WHERE isbn = ?";
                try (PreparedStatement bookStatement = c.prepareStatement(deleteBookQuery)) {
                    bookStatement.setString(1, idToDelete);
                    bookStatement.executeUpdate();
                }
                result = true;
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteAuthorAndRelated(String idToDelete) throws SQLException {
        Boolean result = false;
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            try {
                String deleteBookAuthorsQuery = "DELETE FROM booksauthors WHERE author_id = ?";
                try (PreparedStatement bookAuthorsStatement = c.prepareStatement(deleteBookAuthorsQuery)) {
                    bookAuthorsStatement.setString(1, idToDelete);
                    bookAuthorsStatement.executeUpdate();
                }

                String deleteAuthorQuery = "DELETE FROM authors WHERE author_id = ?";
                try (PreparedStatement authorStatement = c.prepareStatement(deleteAuthorQuery)) {
                    authorStatement.setString(1, idToDelete);
                    authorStatement.executeUpdate();
                }
                result = true;
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deletePublisherAndRelated(int idToDelete) throws SQLException {
        Boolean result = false;
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            try {
                String deleteBookAuthorsQuery = "DELETE FROM booksauthors WHERE isbn IN (SELECT isbn FROM books WHERE publisher_id = ?)";
                try (PreparedStatement bookAuthorsStatement = c.prepareStatement(deleteBookAuthorsQuery)) {
                    bookAuthorsStatement.setInt(1, idToDelete);
                    bookAuthorsStatement.executeUpdate();
                }

                String deleteBooksQuery = "DELETE FROM books WHERE publisher_id = ?";
                try (PreparedStatement booksStatement = c.prepareStatement(deleteBooksQuery)) {
                    booksStatement.setInt(1, idToDelete);
                    booksStatement.executeUpdate();
                }

                String deletePublisherQuery = "DELETE FROM publishers WHERE publisher_id = ?";
                try (PreparedStatement publisherStatement = c.prepareStatement(deletePublisherQuery)) {
                    publisherStatement.setInt(1, idToDelete);
                    publisherStatement.executeUpdate();
                }
                result = true;
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}