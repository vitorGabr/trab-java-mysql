package src;

import src.controller.Controller;
import src.dao.DaoConcreto;
import src.view.implementaion.Janela;

public class Main {

    public static void main(String[] args) {
        new Controller(new Janela(), new DaoConcreto()).init();
        // Contro daoBooks = new DaoBooks();
        // try {
        // if (daoBooks.addBook("Teste de livro", 201, "teste5", (float) 30, 435, 1)) {
        // System.out.println("Livro adicionado com sucesso!");
        // } else {
        // System.out.println("Livro não adicionado!");
        // }
        // } catch (SQLIntegrityConstraintViolationException e) {
        // e.printStackTrace();
        // }
    }

}
