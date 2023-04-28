package interfaces;

import model.Person;

import java.util.ArrayList;
import java.util.List;

public interface PersonDao {
    ArrayList<Person> getAll();
    void create(List<Person> persons);
    void update(int id, Person person);
    void delete(int id);
}
