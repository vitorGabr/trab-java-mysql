package src.dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import src.entity.Author;
import src.entity.BookAuthor;
import src.entity.Publisher;

public interface Dao {

        // INICIO DO AUTOR
        public List<Author> findAllAuthors(String name);

        public boolean addAuthor(String name, String fname) throws SQLIntegrityConstraintViolationException;
        // FIM DO AUTOR

        // INICIO DO LIVRO
        public List<BookAuthor> findAllBooks(String title);

        public boolean addBook(String title,
                        int publisher_id,
                        String isbn,
                        Float price,
                        List<Integer> authors) throws SQLIntegrityConstraintViolationException;

        public boolean deleteBook(String id) throws SQLException;
        // FIM DO LIVRO

        // INICIO DA EDITORA
        public List<Publisher> findAllPublishers(String name);

        public boolean addPublisher(
                        String name,
                        String url) throws SQLIntegrityConstraintViolationException;

        public boolean deletePublisher(int id) throws SQLException;
        // FIM DA EDITORA

}
