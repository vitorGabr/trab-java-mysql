package src.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import src.entity.Author;
import src.entity.BookAuthor;
import src.entity.Publisher;

public interface BookView {
    String getNomeBuscaBook();

    void buscarBook(ActionListener al);

    void criarBook(ActionListener al);

    Map<String, Object> getNewBookInfo();

    void listBooks(List<BookAuthor> authors);

    void addTableListener(MouseAdapter al);

    void init(List<Publisher> publishers, List<Author> authors);
}
