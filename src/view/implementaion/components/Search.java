package src.view.implementaion.components;

import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Search extends JPanel {

    private TextField textField = new TextField(40);
    private JButton searchButton = new JButton("Buscar");

    public Search() {
        init();
    }

    public void init() {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        add(textField);
        add(searchButton);
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public TextField getTextField() {
        return textField;
    }

    public String getSearchedName() {
        return textField.getText();
    }

    public void addSearchListener(ActionListener al) {
        this.searchButton.addActionListener(al);
    }
}
