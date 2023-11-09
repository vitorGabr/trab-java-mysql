package src.view.implementaion.tabs;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import src.entity.Author;
import src.view.implementaion.components.CreateItem;
import src.view.AuthorView;
import src.view.implementaion.components.Search;
import src.view.implementaion.components.Table;

public class AuthorTab extends JPanel implements AuthorView {

    private Table table = new Table(new Object[] { "ID", "Nome", "Sobrenome" });
    private Search search = new Search();
    private CreateItem createItem = new CreateItem(
            Map.of(
                    "name", "Nome",
                    "fname", "Sobrenome"));

    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(search);
        add(table.getScrollPane());
        add(createItem);
    }

    public void listAuthors(List<Author> authors) {
        table.clearTable();
        for (Author p : authors) {
            Object[] rowData = {
                    p.getAuthor_id(),
                    p.getName(),
                    p.getFname() };
            table.addRow(rowData);
        }
        this.revalidate();
    }

    public String getAuthorSearchName() {
        return this.search.getSearchedName();
    }

    public void searchAuthor(ActionListener al) {
        this.search.addSearchListener(al);
    }

    public void createAuthor(ActionListener al) {
        this.createItem.retrieve(al);
    }

    public Map<String, Object> getNewAuthorInformation() {
        return this.createItem.getTypedValues();
    }

    public void addTableClickListener(MouseAdapter md) {
        table.addTableListener(md);
    }

}