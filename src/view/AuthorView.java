package src.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import src.entity.Author;
import src.entity.BookAuthor;
import src.entity.Publisher;

public interface AuthorView {
    String getNomeBuscaAuthor();

    void buscarAuthor(ActionListener al);

    void criarAuthor(ActionListener al);

    Map<String, Object> getNewAuthorInfo();

    void listAuthors(List<BookAuthor> authors);

    void addTableListener(MouseAdapter al);

    void init(List<Publisher> publishers, List<Author> authors);
}
