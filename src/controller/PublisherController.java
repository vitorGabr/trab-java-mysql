package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

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
        this.view.searchPublisher(new SearchNameAction());
        this.view.createPublisher(new CreateAction());
        this.view.addTableClickListener(new TableMouseAdapter());
        this.view.listPublishers(publishers);
        this.view.init();

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

            if (newPublisherInfo.values().stream().filter(value -> value.toString().isEmpty()).count() > 0
                    || publishers.size() == 0) {
                JOptionPane.showMessageDialog(null, "Os campos são obrigatórios!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = newPublisherInfo.get("name").toString();
            String url = newPublisherInfo.get("url").toString();

            try {
                if (!dao.addPublisher(name, url)) {
                    throw new Exception();
                }
                publishers = dao.findAllPublishers("");
                JOptionPane.showMessageDialog(null, "Editora criada com sucesso!");
                view.listPublishers(publishers);
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(null, "Editora já existe!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Não é possível criar uma editora!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao criar editora!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            if (row < publishers.size()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja deletar a editora? \n" +
                        "Se a editora for deletada, todos os livros relacionados a ela serão deletados também.",
                        "Deletar",
                        JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    Publisher publisher = publishers.get(row);
                    try {
                        if (!dao.deletePublisher(publisher.getId())) {
                            throw new Exception();
                        }
                        JOptionPane.showMessageDialog(null, "Editora deletada com sucesso!");
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Não é possível deletar a editora!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Erro ao deletar editora!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } finally {
                        publishers = dao.findAllPublishers("");
                        view.listPublishers(publishers);
                    }
                }

            }
        }
    }
}
