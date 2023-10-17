package src.entity;

import java.util.Map;

public class BookAuthor {
    private Author author;
    private Book book;
    int seq_no;

    public BookAuthor(
            Author author,
            Book book,
            int seq_no) {
        this.author = author;
        this.book = book;
        this.seq_no = seq_no;
    }

    public static BookAuthor createFromMap(Map<String, Object> map) {
        Author author = Author.createFromMap(map);
        Book book = Book.createFromMap(map);
        int seq_no = map.get("seq_no") == null ? 0 : (int) map.get("seq_no");
        return new BookAuthor(author, book, seq_no);
    }

    public Author getAuthor() {
        return author;
    }

    public Book getBook() {
        return book;
    }

    public int getSeq_no() {
        return seq_no;
    }

}