package github.snowymn.prototype;

/**
 * Demonstrates how to use copy constructors to copy a fully initialized object and make use of it.
 * This way is better at explicitly specifying that an object is copyable than
 * implementing cloneable interface.
 * - Caveat is you have to build a copy constructor for every type of object that you have.
 */

public class AddressTwo
{
    public String streetAddress, city, country;
    public AddressTwo(String streetAddress, String city, String country){
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }
    //copy constructor
    public AddressTwo(AddressTwo other){
        this(other.streetAddress, other.city, other.country);
    }

    @Override
    public String toString()
    {
        return "AddressTwo{" + "streetAddress='" + streetAddress + '\''
                + ", city='" + city + '\'' + ", country='" + country + '\'' + '}';
    }
}
class Employee{
    public String name;
    public AddressTwo address;

    public Employee(String name, AddressTwo address){
        this.name = name;
        this.address = address;
    }
    //copy constructor
    public Employee(Employee other){
        name = other.name;
        //use copy constructor of AddressTwo class
        address = new AddressTwo(other.address);
    }

    @Override
    public String toString()
    {
        return "Employee{" + "name='" + name + '\'' + ", address=" + address + '}';
    }
}
class CopyConstructorDemo{
    public static void main(String[] args)
    {
        Employee john = new Employee("John",
                new AddressTwo("123 London Road", "London", "UK"));
        Employee chris = new Employee(john);
        chris.name = "Chris";
        System.out.println(john);
        System.out.println(chris);
    }
}
