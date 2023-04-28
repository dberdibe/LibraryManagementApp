package interfaces;

import model.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    void create(List<Book> books);
    void update(int id, Book book);
    void delete(int id);
}
