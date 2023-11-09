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
        BookController bookController = new BookController(this.view.getBookT(), dao);
        bookController.init();

        new AuthorController(
                this.view.getAuthorT(),
                dao,
                () -> {
                    bookController.update();
                }).init();

        new PublisherController(
                this.view.getPublisherT(),
                dao, () -> {
                    bookController.update();
                }).init();
    }

    public Dao getDao() {
        return dao;
    }

    public View getView() {
        return view;
    }

}
