package src.view.implementaion.components;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

public class CreateItem extends JPanel {

    private HashMap<String, JTextField> textFieldMap = new HashMap<>();
    JButton retrieveButton = new JButton("Criar");

    public CreateItem(JPanel panel, Map<String, Object> keyValues) {

        for (String key : keyValues.keySet()) {
            JPanel panelField = new JPanel();
            panelField.setLayout(new FlowLayout(FlowLayout.LEADING));

            JLabel label = new JLabel(keyValues.get(key).toString());
            JTextField textField = new JTextField(40);
            textFieldMap.put(key, textField);

            panelField.add(label);
            panelField.add(textField);

            add(panelField);
        }

        add(retrieveButton);

    }

    public Map<String, String> getTypedValues() {
        Map<String, String> data = new HashMap<>();
        for (String key : textFieldMap.keySet()) {
            String value = textFieldMap.get(key).getText();
            textFieldMap.get(key).setText("");
            data.put(key, value);
        }
        return data;
    }

    public void retrieve(ActionListener al) {
        this.retrieveButton.addActionListener(al);
    }

}