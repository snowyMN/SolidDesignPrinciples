package github.snowymn.builder;

import java.util.ArrayList;

/**
 * Coding Exercise 1: Builder Coding Exercise.
 * You are asked to implement the Builder design pattern for rendering simple chunks of code.
 */

public class CodeBuilder {
    private String className;
    private FieldElement field = new FieldElement();

    public CodeBuilder(String className){
        this.className = className;
    }

    public CodeBuilder addField(String nameOfField, String typeofField){
        FieldElement fe = new FieldElement(nameOfField, typeofField);
        field.fields.add(fe);
        return this;
    }
    @Override
    public String toString(){
        return
        "public class " + className + "\n" +
        "{" + "\n" +
        field.toString() + "\n" +
        "}";
    }

}
class FieldElement{
    public String nameOfField, typeOfField;
    public ArrayList<FieldElement> fields = new ArrayList<>();
    public FieldElement(){

    }
    public FieldElement(String nameOfField, String typeOfField){
        this.nameOfField = nameOfField;
        this.typeOfField = typeOfField;
    }
    private String toStringImpl(){
        StringBuilder sb = new StringBuilder();
        for(FieldElement e : fields){
            sb.append("    ");
            sb.append("public ");
            sb.append(e.typeOfField);
            sb.append(" ");
            sb.append(e.nameOfField);
            sb.append(";");
            sb.append("\n");
        }
        return sb.toString();
    }
    @Override
    public String toString(){
        return toStringImpl();
    }
}
class CodeBuilderDemo{
    public static void main(String[] args){
        CodeBuilder cb = new CodeBuilder("Person")
                .addField("name", "String")
                .addField("age", "int");
        System.out.println(cb);
    }
}