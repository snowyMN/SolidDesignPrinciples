package github.snowymn.solid.dip;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A. High-level modules should not depend on low-level modules. Both should depend on abstractions.
 * B. Abstractions should not depend on details. Details should depend on abstractions.
 * (Abstraction usually = abstract class or interface) If you can use abstract classes and interfaces
 * instead of concrete classes you can substitute one implementation for another.
 * Model relationships in a family between people.
 * Note that you need to add javatuples dependency to the project to create a Triplet list. This was downloaded here:
 * https://github.com/javatuples/javatuples/downloads
 */

enum Relationship{
    PARENT,
    CHILD,
    SIBLING
}

public class DIPPerson {
    public String name;

    public DIPPerson(String name){
        this.name = name;
    }
}
/**
 * Abstraction to fix the violation of DIP in Research constructor - public Research(Relationships relationships)
 */
interface RelationshipBrowser{
    List<DIPPerson> findAllChildrenOf(String name);
}
/**
 * To model the relationships between different people.
 * Low-level module b/c it is related to data storage - keeps list - no business logic of its own - manipulate list SRP
 */
class Relationships implements RelationshipBrowser{

    //list of relationships between different people
    private List<Triplet<DIPPerson, Relationship, DIPPerson>> relations = new ArrayList<>();
    //ISSUE HERE** with exposing storage method with public getter
    public List<Triplet<DIPPerson, Relationship, DIPPerson>> getRelations() {
        return relations;
    }
    public void addParentAndChild(DIPPerson parent, DIPPerson child){
        //have to complete twice to add both relationships
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }
    //doing the searching here - if you decide to change the implementation
    @Override
    public List<DIPPerson> findAllChildrenOf(String name) {
        return relations.stream().filter(x -> Objects.equals(x.getValue0().name, name)
                && x.getValue1() == Relationship.PARENT)
                //:: is used to call a method by referring to it with the help of its class directly
                //(behave like lambda expressions)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }
}

/**
 * Perform research on the relationships.
 * High-level module -  allows us to perform operations on the lower level construct - more important to the user
 * Note: learn about streams, which represent a sequence of elements and supports different kind of operations to
 * perform computations upon these elements: https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 */
class Research{
    //**ISSUE HERE** constructor that relies on low-level module - defies Dependency Inversion Principle
    //need to depend on abstraction instead.
   /* public Research(Relationships relationships){

       List<Triplet<DIPPerson, Relationship, DIPPerson>> relations = relationships.getRelations();
        //x gets the Triplet from the relations List where all the conditions are met as specified
        //getValue0 - DIPPerson.name, getValue1 - Relationship
        //forEach Triplet that meets the first two conditions print out the getValue2 - DIPPerson.name
        relations.stream().filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
        .forEach(ch -> System.out.println(
                "John has a child called " + ch.getValue2().name
        ));
    }*/

    //by using this we can change the storage from a list of Triplets without this being effected.
    public Research(RelationshipBrowser browser){
        List<DIPPerson> children = browser.findAllChildrenOf("John");
        for(DIPPerson child : children){
            System.out.println("John has a child called " + child.name);
        }
    }

}

/**
 * Set up scenario coded above to demonstrate
 */
class Demo{
    public static void main(String[] args){
        DIPPerson pJohn = new DIPPerson("John");
        DIPPerson c1Chris = new DIPPerson("Chris");
        DIPPerson c2Mary = new DIPPerson("Mary");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(pJohn, c1Chris);
        relationships.addParentAndChild(pJohn, c2Mary);

        Research research = new Research(relationships);



    }

}
