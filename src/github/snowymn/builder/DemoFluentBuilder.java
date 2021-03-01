package github.snowymn.builder;

/**
 * Builders can inherit from other builders. Need to keep it fluent. If you want a fluent interface
 * to propagate across hierarchies then  you need to have recursive generic definitions.
 */
//one builder to build name and one to build position
class Person{
    public String name;
    public String position;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

class PersonBuilder<SELF extends PersonBuilder<SELF>>{

    protected Person person = new Person();

    public SELF withName(String name){
        person.name = name;
        return self();
    }
    public Person build(){
        return person;
    }

    //do this to be able to override behavior of self in derived classes
    protected SELF self(){
        return (SELF) this;
    }

}
//builder to build up employment information want new builder to preserve func of original builder
//done with inheritance but it becomes tricky
class EmployeeBuilder extends PersonBuilder<EmployeeBuilder>{

    public EmployeeBuilder worksAt(String position){
        //person is protected not private
        person.position = position;
        return self();
    }
    @Override
    protected EmployeeBuilder self(){
        return this;
    }

}
public class DemoFluentBuilder {

    public static void main(String[] args){
        //demonstration of fluid interface
        EmployeeBuilder pb = new EmployeeBuilder();
        Person victoria = pb.withName("Victoria")
                .worksAt("NTIC")
                .build();


    }
}
