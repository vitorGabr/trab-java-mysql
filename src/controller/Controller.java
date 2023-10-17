package src.controller;

import src.dao.Dao;
import src.view.View;

public class Controller {

    private Dao dao;
    private View view;

    public Controller(
            View view,
            Dao dao) {
        this.view = view;
        this.dao = dao;
    }

    public void init() {
        this.view.init();
        new BookController(this.view.getBookT(), dao).init();
    }

    public Dao getDao() {
        return dao;
    }

    public View getView() {
        return view;
    }

}
