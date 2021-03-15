package github.snowymn.factories;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given a class called Person, which has two fields id and name.
 * Implement a non-static PersonFactory that has a createPerson() method and takes
 * a person's name and returns a new instance of Person.
 * The id of the persno returned should be set as a 0-based index of the object
 * created by that factory. So, the first person the factory makes should have id=0, second id=1
 * and so on.
 */

public class Person
{
    public int id;
    public String name;

    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Person{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
class PersonFactory{
    private int id;
    private List<String> peopleNames = new ArrayList<>();
    public Person createPerson(String name){
        peopleNames.add(name);
        id = peopleNames.size() - 1;
        return new Person(id, name);
    }
}
class PersonDemo{
    public static void main(String[] args){
        PersonFactory personFactory = new PersonFactory();
        Person personZero = personFactory.createPerson("John");
        Person personOne = personFactory.createPerson("Becky");
        Person personTwo = personFactory.createPerson("Joe");
        System.out.println(personZero);
        System.out.println(personOne);
        System.out.println(personTwo);
    }
}

