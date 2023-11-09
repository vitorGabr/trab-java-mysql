package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import src.dao.Dao;
import src.entity.Author;
import src.entity.Book;
import src.entity.Publisher;
import src.view.BookView;

public class BookController {

    private BookView view;
    private Dao dao;
    private List<Book> books;
    private List<Publisher> publishers;
    private List<Author> authors;

    public BookController(BookView view, Dao dao) {
        books = dao.findAllBooks("");
        publishers = dao.findAllPublishers("");
        authors = dao.findAllAuthors("");
        this.view = view;
        this.dao = dao;
    }

    public void init() {
        view.searchBook(new SearchNameAction());
        view.createBook(new CreateAction());
        view.addTableClickListener(new TableMouseAdapter());
        view.listBooks(books);
        view.init(publishers, authors);
    }

    class SearchNameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = view.getBookSearchName();
            books = dao.findAllBooks(title);
            view.listBooks(books);
        }
    }

    class CreateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Map<String, Object> newBookInfo = view.getNewBookInformation();

            List<Integer> authors = new ArrayList<>();
            Object authorsObj = newBookInfo.get("authors");
            if (authorsObj instanceof List<?>) {
                List<?> authorsList = (List<?>) authorsObj;
                for (Object authorObj : authorsList) {
                    if (authorObj instanceof Integer) {
                        authors.add((Integer) authorObj);
                    }
                }
            }

            if (hasEmptyFields(newBookInfo) || authors.isEmpty()) {
                showError("Os campos são obrigatórios!");
                return;
            }

            String title = newBookInfo.get("title").toString();
            int publisher_id = Integer.parseInt(newBookInfo.get("publisher").toString());
            String isbn = newBookInfo.get("isbn").toString();
            Float price = Float.parseFloat(newBookInfo.get("price").toString());

            try {
                createBook(title, publisher_id, isbn, price, authors);
                books = dao.findAllBooks("");
                showSuccessMessage("Livro criado com sucesso!");
                view.listBooks(books);
            } catch (SQLIntegrityConstraintViolationException e) {
                showError("ISBN já cadastrado!");
            } catch (SQLException e) {
                showError("Não é possível criar um livro!");
            }
        }

        private boolean hasEmptyFields(Map<String, Object> bookInfo) {
            return bookInfo.values().stream().anyMatch(value -> value.toString().isEmpty());
        }

        private void createBook(String title, int publisher_id, String isbn, Float price, List<Integer> authors)
                throws SQLException {
            if (!dao.addBook(title, publisher_id, isbn, price, authors)) {
                throw new SQLException();
            }
        }
    }

    class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            if (row < books.size()) {
                int resposta = JOptionPane.showConfirmDialog(null,
                        "Deseja deletar o livro? \nAo deletar o livro, todas relações \ncom autores serão deletadas!",
                        "Deletar livro?", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    deleteBook(row);
                }
            }
        }

        private void deleteBook(int row) {
            Book book = books.get(row);
            try {
                if (!dao.deleteBook(book.getIsbn())) {
                    throw new SQLException();
                }
                showSuccessMessage("Livro deletado com sucesso!");

            } catch (SQLException e1) {
                showError("Não é possível deletar o livro!");
            } finally {
                books = dao.findAllBooks("");
                view.listBooks(books);
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void update() {
        books = dao.findAllBooks("");
        publishers = dao.findAllPublishers("");
        authors = dao.findAllAuthors("");
        view.init(publishers, authors);
    }

}
