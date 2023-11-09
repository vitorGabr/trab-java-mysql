package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import src.dao.Dao;
import src.entity.Publisher;
import src.view.PublisherView;

public class PublisherController {

    private PublisherView view;
    private Dao dao;
    private List<Publisher> publishers;

    public PublisherController(PublisherView view, Dao dao) {
        publishers = dao.findAllPublishers("");
        this.view = view;
        this.dao = dao;
    }

    public void init() {
        setupListeners();
        view.listPublishers(publishers);
        view.init();
    }

    private void setupListeners() {
        view.searchPublisher(new SearchNameAction());
        view.createPublisher(new CreateAction());
        view.addTableClickListener(new TableMouseAdapter());
    }

    class SearchNameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getPublisherSearchName();
            publishers = dao.findAllPublishers(name);
            view.listPublishers(publishers);
        }
    }

    class CreateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

            Map<String, Object> newPublisherInfo = view.getNewPublisherInformation();

            if (hasEmptyFields(newPublisherInfo) || publishers.isEmpty()) {
                showError("Os campos são obrigatórios!");
                return;
            }

            String name = newPublisherInfo.get("name").toString();
            String url = newPublisherInfo.get("url").toString();
            String URL_REGEX = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
            boolean b = Pattern.matches(URL_REGEX, url);

            if (!b) {
                showError("URL inválida!");
                return;
            }

            try {
                createPublisher(name, url);
                publishers = dao.findAllPublishers("");
                showSuccessMessage("Editora criada com sucesso!");
                view.listPublishers(publishers);
            } catch (SQLIntegrityConstraintViolationException e) {
                showError("Editora já existe!");
            } catch (SQLException e) {
                showError("Não é possível criar uma editora!");
            }
        }

        private boolean hasEmptyFields(Map<String, Object> publisherInfo) {
            return publisherInfo.values().stream().anyMatch(value -> value.toString().isEmpty());
        }

        private void createPublisher(String name, String url) throws SQLException {
            if (!dao.addPublisher(name, url)) {
                throw new SQLException();
            }
        }
    }

    class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            if (row < publishers.size()) {
                int resposta = JOptionPane.showConfirmDialog(null,
                        "Deseja deletar a editora? \nSe a editora for deletada, todos os livros relacionados a ela serão deletados também.",
                        "Deletar", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    deletePublisher(row);
                }
            }
        }

        private void deletePublisher(int row) {
            Publisher publisher = publishers.get(row);
            try {
                if (!dao.deletePublisher(publisher.getId())) {
                    throw new SQLException();
                }
                showSuccessMessage("Editora deletada com sucesso!");
            } catch (SQLException e1) {
                showError("Não é possível deletar a editora!");
            } finally {
                publishers = dao.findAllPublishers("");
                view.listPublishers(publishers);
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

}
