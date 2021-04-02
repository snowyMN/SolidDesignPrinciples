package github.snowymn.prototype;

import java.util.Arrays;

/**
 * This is not an approach you would want to use.
 */

public class Address implements Cloneable
{
   public String streetName;
   public int houseNumber;

   public Address(String streetName, int houseNumber){
       this.streetName = streetName;
       this.houseNumber = houseNumber;
   }

    @Override
    public String toString()
    {
        return "Address{" + "streetName='" + streetName + '\'' + ", houseNumber=" + houseNumber + '}';
    }
    //valid deep copy mechanic - changed protected modifier to public
    @Override
    public Object clone() throws CloneNotSupportedException{
       return new Address(streetName, houseNumber);
    }
}
class Person implements Cloneable{
    public String [] names;
    public Address address;

    public Person(String[] names, Address address){
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "Person{" + "names=" + Arrays.toString(names) + ", address=" + address + '}';
    }

    /**
     * Creates a copy of the String [] names, and the Address object. Address object has a clone method.
     * @return copy of Person
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        return new Person(Arrays.copyOf(names, names.length), (Address) address.clone());
    }
}
class Demo{
    public static void main(String[] args) throws CloneNotSupportedException{

    }
}

