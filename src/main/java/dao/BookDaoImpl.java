package dao;

import interfaces.BookDao;
import model.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private final DataSource dataSource;

    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()){
            ResultSet rs;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM books")) {
                rs = ps.executeQuery();
            }
            while(rs.next()){
                Book book = new Book(rs.getInt("id"), rs.getString("name"), rs.getString("author"), rs.getInt("year"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        return books;
    }

    public void create(List<Book> books) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO books(name, author, year) VALUES (?, ?, ?)")) {

                for (Book book : books) {
                    ps.setString(1, book.getName());
                    ps.setString(2, book.getAuthor());
                    ps.setInt(3, book.getYear());
                    ps.addBatch();
                    ps.executeBatch();

                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    book.setId(rs.getInt("book_id"));
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void update(int id, Book book) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement ps = conn.prepareStatement("UPDATE books SET name=?, author=?, year=? WHERE id=?")) {

                ps.setString(1, book.getName());
                ps.setString(2, book.getAuthor());
                ps.setInt(3, book.getYear());
                ps.setInt(4, id);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void delete(int id){
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE id=?")) {
                ps.setInt(1, id);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
