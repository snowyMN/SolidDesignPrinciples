package github.snowymn.builder;

/**
 * Look at how you can use multiple builders working in tandem to provide functionality to build up a
 * particular object. The subclasses have to inherit from the base class builder,
 * as soon as they inherit from it they expose the works() and lives() methods.
 * Which allows you to switch from one sub-builder to the other in the same fluent API call.
 */
class PersonDFB{
    //address
    public String streetAddress, postCode, city;
    //employment
    public String companyName, position;
    public int annualIncome;

    @Override
    public String toString() {
        return "PersonDFB{" +
                "streetAddress='" + streetAddress + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}
//different builders for the address part and for the employment part
//builder facade - bulder pattern and facade pattern

/**
 * Class used to build a person
 */
class PersonBuilderDFB{
    protected PersonDFB person = new PersonDFB();
    //implement PersonAddressBuilderDFB and expose it through PersonBuilderDFB
    public PersonAddressBuilderDFB lives(){
        return new PersonAddressBuilderDFB(person);
    }
    //implement PersonJobBuilderDFB and expose it through PersonBuilderDFB
    public PersonJobBuilderDFB works(){
        return new PersonJobBuilderDFB(person);
    }

    public PersonDFB build(){
        return person;
    }
}

/**
 * Dedicated builder for building up the address of a person.
 * Extending PersonBuilderDFB is critical to having faceted builders.
 */
class PersonAddressBuilderDFB extends PersonBuilderDFB{
    //takes a refernce to the object that is being built up and redefines that reference
    public PersonAddressBuilderDFB(PersonDFB person){
        //only one person we are building up but need a reference to it in every sub builder
        this.person = person;
    }
    //fluent interface so we return the same type that we are
    public PersonAddressBuilderDFB at(String streetAddress){
        person.streetAddress = streetAddress;
        return this;
    }
    //post code of person
    public PersonAddressBuilderDFB withPostCode(String postCode){
        person.postCode = postCode;
        return this;
    }
    //city person lives in
    public PersonAddressBuilderDFB in(String city){
        person.city = city;
        return this;
    }
}

/**
 * Dedicated builder for building up the job of a person.
 * Extending PersonBuilderDFB for a faceted builder.
 */
class PersonJobBuilderDFB extends PersonBuilderDFB{
    public PersonJobBuilderDFB(PersonDFB person){
        this.person = person;
    }
    public PersonJobBuilderDFB at(String companyName){
        person.companyName = companyName;
        return this;
    }
    public PersonJobBuilderDFB asA(String position){
        person.position = position;
        return this;
    }
    public PersonJobBuilderDFB earning(int annualIncome){
        person.annualIncome = annualIncome;
        return this;
    }

}

public class DemoFacetedBuilder {
    public static void main(String[] args){
        PersonBuilderDFB pb = new PersonBuilderDFB();
        PersonDFB person = pb
                .lives()
                    .at("15934 Monroe St NE")
                    .in("Blaine")
                    .withPostCode("55005")
                .works()
                    .at("Fabrikam")
                    .asA("Engineer")
                    .earning(123000)
                .build();
        System.out.println(person);
    }
}
