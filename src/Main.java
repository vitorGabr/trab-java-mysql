package src;

import src.controller.Controller;
import src.dao.DaoConcreto;
import src.view.implementaion.Janela;

public class Main {

    public static void main(String[] args) {
        new Controller(new Janela(), new DaoConcreto()).init();
    }

}
