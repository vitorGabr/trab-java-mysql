package src.view.implementaion.tabs;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import src.entity.Publisher;
import src.view.implementaion.components.CreateItem;
import src.view.PublisherView;
import src.view.implementaion.components.Search;
import src.view.implementaion.components.Table;

public class PublisherTab extends JPanel implements PublisherView {

    private Table table = new Table(new Object[] { "ID", "Nome", "Url" });
    private Search search = new Search();
    private CreateItem createItem = new CreateItem(
            Map.of(
                    "name", "Nome",
                    "url", "Url"));

    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(search);
        add(table.getScrollPane());
        add(createItem);
    }

    public void listPublishers(List<Publisher> publishers) {
        table.clearTable();
        for (Publisher p : publishers) {
            Object[] rowData = {
                    p.getId(),
                    p.getName(),
                    p.getUrl() };
            table.addRow(rowData);
        }
        this.revalidate();
    }

    public String getPublisherSearchName() {
        return this.search.getSearchedName();
    }

    public void searchPublisher(ActionListener al) {
        this.search.addSearchListener(al);
    }

    public void createPublisher(ActionListener al) {
        this.createItem.retrieve(al);
    }

    public Map<String, Object> getNewPublisherInformation() {
        return this.createItem.getTypedValues();
    }

    public void addTableClickListener(MouseAdapter md) {
        table.addTableListener(md);
    }

}
