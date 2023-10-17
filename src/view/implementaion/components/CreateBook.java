package src.view.implementaion.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import src.entity.Author;
import src.entity.Publisher;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreateBook extends JPanel {

    private HashMap<String, JTextField> textFieldMap = new HashMap<>();
    private JComboBox<String> authorBox = new JComboBox<>();
    private JComboBox<String> publisherBox = new JComboBox<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> list = new JList<>(listModel);
    private JScrollPane scrollPane = new JScrollPane(list);
    private Map<Integer, String> authorMap = new HashMap<>();
    private Map<Integer, String> publisherMap = new HashMap<>();
    private Map<String, String> keyValues;

    JButton retrieveButton = new JButton("Criar");

    public void init(List<Author> authors, List<Publisher> publishers) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        keyValues = Map.of(
                "title", "Título",
                "isbn", "ISBN",
                "price", "Preço");
        for (Author author : authors) {
            authorMap.put(author.getAuthor_id(), author.getName() + " - " + author.getAuthor_id());
        }

        for (Publisher publisher : publishers) {
            publisherMap.put(publisher.getId(), publisher.getName() + " - " + publisher.getId());
        }

        for (String key : keyValues.keySet()) {
            JPanel panelField = new JPanel();
            panelField.setLayout(new FlowLayout(FlowLayout.LEADING));

            JLabel label = new JLabel(keyValues.get(key).toString());
            JTextField textField = new JTextField(30);
            if (key.equals("price")) {
                textField.setText("0.00");
                textField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE
                                || c == '.'))) {
                            e.consume();
                        }
                    }
                });
            }
            textFieldMap.put(key, textField);
            panelField.add(label);
            panelField.add(textField);

            add(panelField);
        }

        for (String value : authorMap.values()) {
            authorBox.addItem(value);
        }

        for (String value : publisherMap.values()) {
            publisherBox.addItem(value);
        }

        this.authorBox.addActionListener(e -> {
            String selectedKey = (String) authorBox.getSelectedItem();
            if (listModel.contains(selectedKey)) {
                return;
            }
            listModel.addElement(selectedKey);
            revalidate();
        });

        list.addListSelectionListener(e -> {
            String selectedKey = list.getSelectedValue();
            listModel.removeElement(selectedKey);
            revalidate();
        });

        JPanel authorPanel = new JPanel();
        authorPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        authorPanel.add(new JLabel("Autores"));
        authorPanel.add(authorBox);
        add(authorPanel);

        JPanel publisherPanel = new JPanel();
        publisherPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        publisherPanel.add(new JLabel("Editora"));
        publisherPanel.add(publisherBox);

        add(publisherPanel);
        add(scrollPane);
        add(retrieveButton);
    }

    public Map<String, Object> getTypedValues() {
        Map<String, Object> data = new HashMap<>();
        textFieldMap.forEach((key, value) -> {
            String text = value.getText();
            if (text.length() == 0) {
                data.put(key, "");
            } else {
                data.put(key, text);
            }
            textFieldMap.get(key).setText("");
        });

        List<Integer> authors = new ArrayList<>();
        int size = listModel.size();
        for (int i = 0; i < size; i++) {
            String selectedKey = listModel.getElementAt(i);
            authorMap.forEach((key, value) -> {
                if (value.equals(selectedKey)) {
                    authors.add(key);
                }
            });
        }
        publisherMap.forEach((key, value) -> {
            if (value.equals(publisherBox.getSelectedItem())) {
                data.put("publisher", key);
            }
        });
        listModel.clear();
        data.put("authors", authors);
        return data;
    }

    public void retrieve(ActionListener al) {
        this.retrieveButton.addActionListener(al);
    }

}