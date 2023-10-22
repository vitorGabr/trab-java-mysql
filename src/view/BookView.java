package src.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import src.entity.Author;
import src.entity.Book;
import src.entity.Publisher;

public interface BookView {
    String getBookSearchName();

    void searchBook(ActionListener al);

    void createBook(ActionListener al);

    Map<String, Object> getNewBookInformation();

    void listBooks(List<Book> bookAuthors);

    void addTableClickListener(MouseAdapter al);

    void init(List<Publisher> publishers, List<Author> authors);
}
