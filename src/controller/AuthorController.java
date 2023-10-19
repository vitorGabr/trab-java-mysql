package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import src.dao.Dao;
import src.entity.Author;
import src.entity.BookAuthor;
import src.entity.Publisher;
import src.view.BookView;

public class AuthorController {

    private BookView view;
    private Dao dao;
    private List<BookAuthor> books;
    private List<Publisher> publishers;
    private List<Author> authors;

    public AuthorController(BookView view, Dao dao) {

        books = dao.findAllBooks("");
        publishers = dao.findAllPublishers("");
        authors = dao.findAllAuthors("");

        this.view = view;
        this.dao = dao;
    }

    public void init() {
        this.view.buscarBook(new SearchNameAction());
        this.view.criarBook(new CreateAction());
        this.view.addTableListener(new TableMouseAdapter());
        this.view.listBooks(books);
        this.view.init(publishers, authors);

    }

    class SearchNameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String title = view.getNomeBuscaBook();
            books = dao.findAllBooks(title);

            view.listBooks(books);
        }
    }

    class CreateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Map<String, Object> newBookInfo = view.getNewBookInfo();

            @SuppressWarnings("unchecked")
            List<Integer> authors = (List<Integer>) newBookInfo.get("authors");

            if (newBookInfo.values().stream().filter(value -> value.toString().isEmpty()).count() > 0
                    || authors.size() == 0) {
                JOptionPane.showMessageDialog(null, "Os campos são obrigatórios!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String title = newBookInfo.get("title").toString();
            int publisher_id = Integer.parseInt(newBookInfo.get("publisher").toString());
            String isbn = newBookInfo.get("isbn").toString();
            Float price = Float.parseFloat(newBookInfo.get("price").toString());

            try {
                if (!dao.addBook(title, publisher_id, isbn, price, authors)) {
                    throw new Exception();
                }
                books = dao.findAllBooks("");
                JOptionPane.showMessageDialog(null, "Livro criado com sucesso!");
                view.listBooks(books);
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(null, "ISBN já cadastrado!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao criar livro!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            if (row < books.size()) {
                int resposta = JOptionPane.showConfirmDialog(
                        null,
                        "Ao deletar um livro, todos as relações\n" +
                                "com autores serão deletadas também.\n",
                        "Deletar livro?",
                        JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    BookAuthor book = books.get(row);
                    try {
                        if (!dao.deleteBook(book.getBook().getIsbn())) {
                            throw new Exception();
                        }
                        JOptionPane.showMessageDialog(null, "Livro deletado com sucesso!");

                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Não é possível deletar um livro!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Erro ao deletar livro!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } finally {
                        books = dao.findAllBooks("");
                        view.listBooks(books);
                    }
                }

            }
        }
    }
}