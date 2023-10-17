package src.view.implementaion.tabs;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import src.entity.Author;
import src.entity.BookAuthor;
import src.entity.Publisher;
import src.view.implementaion.components.CreateBook;
import src.view.BookView;
import src.view.implementaion.components.Search;
import src.view.implementaion.components.Table;

public class BookTab extends JPanel implements BookView {

    private Table table = new Table(new Object[] { "ID", "Nome", "Autor", "Preço" });
    private Search search = new Search();
    private CreateBook createItem = new CreateBook();

    public void init(List<Publisher> publishers, List<Author> authors) {
        createItem.init(authors, publishers);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(search);
        add(table.getScrollPane());
        add(createItem);
    }

    public void listBooks(List<BookAuthor> books) {
        table.clearTable();
        for (BookAuthor p : books) {
            Object[] rowData = {
                    p.getBook().getIsbn(),
                    p.getBook().getTitle(),
                    p.getAuthor().getName(),
                    p.getBook().getPrice() };
            table.addRow(rowData);
        }
        this.revalidate();
    }

    public String getNomeBuscaBook() {
        return this.search.getSearchedName();
    }

    public void buscarBook(ActionListener al) {
        this.search.addSearchListener(al);
    }

    public void criarBook(ActionListener al) {
        this.createItem.retrieve(al);
    }

    public Map<String, Object> getNewBookInfo() {
        return this.createItem.getTypedValues();
    }

    public void showCreateItem(List<Publisher> publishers, List<Author> authors) {
        createItem.init(authors, publishers);
        this.revalidate();
    }

    public void addTableListener(MouseAdapter md) {
        table.addTableListener(md);
    }

}
