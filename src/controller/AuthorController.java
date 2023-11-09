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
import src.entity.Author;
import src.view.AuthorView;

public class AuthorController {

    private AuthorView view;
    private Dao dao;
    private List<Author> authors;
    private ActionUpdate function;

    interface ActionUpdate {
        void execute();
    }

    public AuthorController(AuthorView view, Dao dao, ActionUpdate function) {
        authors = dao.findAllAuthors("");
        this.view = view;
        this.dao = dao;
        this.function = function;
    }

    public void init() {
        view.searchAuthor(new SearchNameAction());
        view.createAuthor(new CreateAction());
        view.addTableClickListener(new TableMouseAdapter());
        view.listAuthors(authors);
        view.init();
    }

    class SearchNameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = view.getAuthorSearchName();
            authors = dao.findAllAuthors(title);
            view.listAuthors(authors);
        }
    }

    class CreateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Map<String, Object> newAuthorInfo = view.getNewAuthorInformation();

            if (hasEmptyFields(newAuthorInfo) || authors.isEmpty()) {
                showError("Os campos são obrigatórios!");
                return;
            }

            String name = newAuthorInfo.get("name").toString();
            String fname = newAuthorInfo.get("fname").toString();

            try {
                createAuthor(name, fname);
                authors = dao.findAllAuthors("");
                showSuccessMessage("Autor criado com sucesso!");
                view.listAuthors(authors);
                function.execute();
            } catch (SQLIntegrityConstraintViolationException e) {
                showError("Autor já existe!");
            } catch (SQLException e) {
                showError("Não é possível criar um autor!");
            }
        }

        private boolean hasEmptyFields(Map<String, Object> authorInfo) {
            return authorInfo.values().stream().anyMatch(value -> value.toString().isEmpty());
        }

        private void createAuthor(String name, String fname) throws SQLException {
            if (!dao.addAuthor(name, fname)) {
                throw new SQLException();
            }
        }
    }

    class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            if (row < authors.size()) {
                int resposta = JOptionPane.showConfirmDialog(null,
                        "Deseja deletar o autor? \nSe o autor estiver associado a um livro, ele vai ser deletado também!",
                        "Deletar", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    deleteAuthor(row);
                }
            }
        }

        private void deleteAuthor(int row) {
            Author author = authors.get(row);
            try {
                if (!dao.deleteAuthor(author.getAuthor_id())) {
                    throw new SQLException();
                }
                showSuccessMessage("Autor deletado com sucesso!");
                function.execute();
            } catch (SQLException e1) {
                showError("Não é possível deletar o autor!");
            } finally {
                authors = dao.findAllAuthors("");
                view.listAuthors(authors);
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