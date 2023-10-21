package src.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import src.entity.Author;
import src.entity.BookAuthor;
import src.entity.Publisher;

public interface AuthorView {
    String getAuthorSearchName();

    void searchAuthor(ActionListener al);

    void createAuthor(ActionListener al);

    Map<String, Object> getNewAuthorInformation();

    void listAuthors(List<BookAuthor> authors);

    void addTableClickListener(MouseAdapter al);

    void init(List<Publisher> publishers, List<Author> authors);
}
