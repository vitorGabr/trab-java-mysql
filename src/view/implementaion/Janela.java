package src.view.implementaion;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import src.view.BookView;
import src.view.View;
import src.view.implementaion.tabs.BookTab;

public class Janela extends JFrame implements View {

    JTabbedPane tabedPane = new JTabbedPane();
    private BookTab bookTab = new BookTab();

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

        add(tabedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public BookView getBookT() {
        return bookTab;
    }

}