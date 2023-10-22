package src.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import src.entity.Author;

public interface AuthorView {
    String getAuthorSearchName();

    void searchAuthor(ActionListener al);

    void createAuthor(ActionListener al);

    Map<String, Object> getNewAuthorInformation();

    void listAuthors(List<Author> authors);

    void addTableClickListener(MouseAdapter al);

    void init();
}
