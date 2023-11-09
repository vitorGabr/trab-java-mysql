package src.view.implementaion;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import src.view.BookView;
import src.view.View;
import src.view.implementaion.tabs.AuthorTab;
import src.view.implementaion.tabs.BookTab;
import src.view.implementaion.tabs.PublisherTab;

public class Janela extends JFrame implements View {

    JTabbedPane tabedPane = new JTabbedPane();
    private BookTab bookTab = new BookTab();
    private AuthorTab authorTab = new AuthorTab();
    private PublisherTab publisherTab = new PublisherTab();

    public Janela() {
        init();
    }

    public void init() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowHeight = (int) (screenSize.getHeight() * 0.8);
        int windowWidth = 500;

        setSize(windowWidth, windowHeight);

        tabedPane.addTab("Livro", bookTab);
        tabedPane.addTab("Autor", authorTab);
        tabedPane.addTab("Editora", publisherTab);

        add(tabedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public BookView getBookT() {
        return bookTab;
    }

    public AuthorTab getAuthorT() {
        return authorTab;
    }

    public PublisherTab getPublisherT() {
        return publisherTab;
    }

}