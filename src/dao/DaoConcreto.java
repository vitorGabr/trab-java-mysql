package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.entity.Author;
import src.entity.Book;
import src.entity.InsertSql;
import src.entity.Publisher;

public class DaoConcreto extends DaoFactory implements Dao {

    // INICIO DA PARTE DO AUTOR
    public List<Author> findAllAuthors(String name) {
        setTable("authors");
        List<Author> autores = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String comando = "LOWER(name) LIKE LOWER(?) OR LOWER(fname) LIKE LOWER(?)";
        params.add("%" + name + "%");
        params.add("%" + name + "%");

        this.findAll(comando, params).forEach((r) -> {
            autores.add(Author.createFromMap(r));
        });

        return autores;

    }

    public boolean addAuthor(
            String name,
            String fname) throws SQLIntegrityConstraintViolationException {
        setTable("authors");
        List<InsertSql> data = new ArrayList<>();
        data.add(new InsertSql(this.getTable(), Map.of(
                "name", name,
                "fname", fname)));
        return this.insert(data);
    }

    public boolean deleteAuthor(int id) throws SQLException {
        Boolean result = false;
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            try {
                String deleteBookAuthorsQuery = "DELETE FROM booksauthors WHERE author_id = ?";
                try (PreparedStatement bookAuthorsStatement = c.prepareStatement(deleteBookAuthorsQuery)) {
                    bookAuthorsStatement.setInt(1, id);
                    bookAuthorsStatement.executeUpdate();
                }

                String deleteAuthorQuery = "DELETE FROM authors WHERE author_id = ?";
                try (PreparedStatement authorStatement = c.prepareStatement(deleteAuthorQuery)) {
                    authorStatement.setInt(1, id);
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
    // FIM DA PARTE DO AUTOR

    // INICIO DA PARTE DO LIVRO
    public List<Book> findAllBooks(String title) {
        setTable("books");

        List<Book> books = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String comando = "LOWER(title) LIKE LOWER(?)";

        params.add("%" + title + "%");

        this.findAll(comando, params).forEach((r) -> {
            books.add(Book.createFromMap(r));
        });
        return books;

    }

    public boolean addBook(
            String title,
            int publisher_id,
            String isbn,
            Float price,
            List<Integer> authors) throws SQLIntegrityConstraintViolationException {

        setTable("booksauthors");

        List<InsertSql> data = new ArrayList<>();
        data.add(new InsertSql("books", Map.of(
                "title", title,
                "publisher_id", publisher_id,
                "isbn", isbn,
                "price", price)));
        authors.forEach((author_id) -> {
            data.add(new InsertSql("booksauthors", Map.of(
                    "isbn", isbn,
                    "seq_no", authors.indexOf(author_id) + 1,
                    "author_id", author_id)));
        });

        return this.insert(data);
    }

    public boolean deleteBook(String id) throws SQLException {
        Boolean result = false;
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            try {
                String deleteBookAuthorsQuery = "DELETE FROM booksauthors WHERE isbn = ?";
                try (PreparedStatement bookAuthorsStatement = c.prepareStatement(deleteBookAuthorsQuery)) {
                    bookAuthorsStatement.setString(1, id);
                    bookAuthorsStatement.executeUpdate();
                }

                String deleteBookQuery = "DELETE FROM books WHERE isbn = ?";
                try (PreparedStatement bookStatement = c.prepareStatement(deleteBookQuery)) {
                    bookStatement.setString(1, id);
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
    // FIM DA PARTE DO LIVRO

    // INICIO DA PARTE DA EDITORA
    public List<Publisher> findAllPublishers(String name) {
        setTable("publishers");
        List<Publisher> publishers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String comando = "LOWER(name) LIKE LOWER(?)";
        params.add("%" + name + "%");

        this.findAll(comando, params).forEach((r) -> {
            publishers.add(Publisher.createFromMap(r));
        });
        return publishers;

    }

    public boolean addPublisher(
            String name,
            String url) throws SQLIntegrityConstraintViolationException {
        setTable("publishers");
        List<InsertSql> data = new ArrayList<>();
        data.add(new InsertSql(this.getTable(), Map.of(
                "name", name,
                "url", url)));

        return this.insert(data);
    }

    public boolean deletePublisher(int id) throws SQLException {
        Boolean result = false;
        try (Connection c = this.getConnection()) {
            c.setAutoCommit(false);
            try {
                String deleteBookAuthorsQuery = "DELETE FROM booksauthors WHERE isbn IN (SELECT isbn FROM books WHERE publisher_id = ?)";
                try (PreparedStatement bookAuthorsStatement = c.prepareStatement(deleteBookAuthorsQuery)) {
                    bookAuthorsStatement.setInt(1, id);
                    bookAuthorsStatement.executeUpdate();
                }

                String deleteBooksQuery = "DELETE FROM books WHERE publisher_id = ?";
                try (PreparedStatement booksStatement = c.prepareStatement(deleteBooksQuery)) {
                    booksStatement.setInt(1, id);
                    booksStatement.executeUpdate();
                }

                String deletePublisherQuery = "DELETE FROM publishers WHERE publisher_id = ?";
                try (PreparedStatement publisherStatement = c.prepareStatement(deletePublisherQuery)) {
                    publisherStatement.setInt(1, id);
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
    // FIM DA PARTE DA EDITORA
}
