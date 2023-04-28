import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dao.BookDaoImpl;
import dao.PersonDaoImpl;
import model.Book;
import model.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryManagementApp {
    public static void main(String[] args) {
        DataSource ds = new HikariDataSource(new HikariConfig("/db.properties"));
        PersonDaoImpl personDao = new PersonDaoImpl(ds);
        BookDaoImpl bookDao = new BookDaoImpl(ds);
        ResourceReader rr = new ResourceReader();
        String sql = "schema.sql";

        try(Connection connection = ds.getConnection()){
            rr.fileReader(sql, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Mak", "Mustermann", 1999));
        persons.add(new Person("Mia", "lannies", 2000));
        persons.add(new Person("ivan", "ivanov", 1994));
        persons.add(new Person("petya", "petin", 2020));
        persons.add(new Person("pasha", "paschin", 1998));

        personDao.create(persons);

        List<Book> books = new ArrayList<>();
        books.add(new Book("Put' Abaya", "Auezov", 1999 ));

        bookDao.create(books);


        //personDao.delete(2);
        //personDao.update(1, new Person("Dauren", "Berdibekov", 1995));

        System.out.println(personDao.getAll());

    }
}
