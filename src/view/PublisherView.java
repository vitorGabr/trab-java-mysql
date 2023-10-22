package src.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

import src.entity.Publisher;

public interface PublisherView {
    String getPublisherSearchName();

    void searchPublisher(ActionListener al);

    void createPublisher(ActionListener al);

    Map<String, Object> getNewPublisherInformation();

    void listPublishers(List<Publisher> publishers);

    void addTableClickListener(MouseAdapter al);

    void init();
}
