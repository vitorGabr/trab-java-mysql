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

    public AuthorController(AuthorView view, Dao dao) {

        authors = dao.findAllAuthors("");

        this.view = view;
        this.dao = dao;
    }

    public void init() {
        this.view.searchAuthor(new SearchNameAction());
        this.view.createAuthor(new CreateAction());
        this.view.addTableClickListener(new TableMouseAdapter());
        this.view.listAuthors(authors);
        this.view.init();

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

            if (newAuthorInfo.values().stream().filter(value -> value.toString().isEmpty()).count() > 0
                    || authors.size() == 0) {
                JOptionPane.showMessageDialog(null, "Os campos são obrigatórios!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = newAuthorInfo.get("name").toString();
            String fname = newAuthorInfo.get("fname").toString();

            try {
                if (!dao.addAuthor(name, fname)) {
                    throw new Exception();
                }
                authors = dao.findAllAuthors("");
                JOptionPane.showMessageDialog(null, "Autor criado com sucesso!");
                view.listAuthors(authors);
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(null, "Autor já existe!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Não é possível criar um autor!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao criar autor!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            if (row < authors.size()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja deletar o autor? \n" +
                        "Se o autor estiver associado a um livro, ele vai ser deletado também!", "Deletar",
                        JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    Author book = authors.get(row);
                    try {
                        if (!dao.deleteAuthor(book.getAuthor_id())) {
                            throw new Exception();
                        }
                        JOptionPane.showMessageDialog(null, "Autor deletado com sucesso!");
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Não é possível deletar o autor!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Erro ao deletar autor!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } finally {
                        authors = dao.findAllAuthors("");
                        view.listAuthors(authors);
                    }
                }

            }
        }
    }
}
