package src.dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.entity.Author;
import src.entity.BookAuthor;
import src.entity.InsertSql;
import src.entity.Publisher;

public class DaoConcreto extends DaoImplement implements Dao {

    // INICIO DA PARTE DO AUTOR
    public List<Author> findAllAuthors(String name) {
        setTable("authors");
        List<Author> autores = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String comando = "LOWER(name) LIKE LOWER(?)";
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
    // FIM DA PARTE DO AUTOR

    // INICIO DA PARTE DO LIVRO
    public List<BookAuthor> findAllBooks(String title) {
        setTable("booksauthors");

        List<BookAuthor> books = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String comando = "SELECT * " +
                "FROM books " +
                "INNER JOIN booksauthors ON books.isbn = booksauthors.isbn " +
                "INNER JOIN authors ON booksauthors.author_id = authors.author_id " +
                "WHERE LOWER(books.title) LIKE LOWER(?)";

        params.add("%" + title + "%");

        this.findAll(comando, params).forEach((r) -> {
            books.add(BookAuthor.createFromMap(r));
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
        return this.deleteBookAndRelated(id);
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
        return this.deletePublisherAndRelated(id);
    }
    // FIM DA PARTE DA EDITORA
}