package dao;

import interfaces.PersonDao;
import model.Person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    private final DataSource dataSource;
    public PersonDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Person> getAll() {
        ArrayList<Person> persons = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()){
            ResultSet rs;
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM persons")) {
                rs = statement.executeQuery();
            }
            while(rs.next()){
                Person person = new Person(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getInt("yearofbirth"));
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        return persons;
    }

    public void create(List<Person> persons) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO persons(firstname, lastname, yearofbirth) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                for (Person person : persons) {
                    ps.setString(1, person.getFirstName());
                    ps.setString(2, person.getLastName());
                    ps.setInt(3, person.getYearOfBirth());
                    ps.addBatch();
                    ps.executeBatch();

                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    person.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void update(int id, Person person) {
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement statement = conn.prepareStatement("UPDATE persons SET firstname=?, lastname=?, yearofbirth=? WHERE id=?")) {

                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setInt(3, person.getYearOfBirth());
                statement.setInt(4, id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void delete(int id){
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM persons WHERE id=?")) {
                statement.setInt(1, id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
