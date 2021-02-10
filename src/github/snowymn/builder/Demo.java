package github.snowymn.builder;

import java.util.ArrayList;
import java.util.Collections;

//model single HTML element for example <li>
class HtmlElement{
    public String name, text;
    public ArrayList<HtmlElement> elements = new ArrayList<>();
    private final int INDENT_SIZE = 2;
    private final String NEW_LINE = System.lineSeparator();

    public HtmlElement(){

    }
    public HtmlElement(String name, String text){
        this.name = name;
        this.text = text;
    }
    private String toStringImpl(int indent){
        StringBuilder sb = new StringBuilder();
        String i = String.join("", Collections.nCopies(indent * INDENT_SIZE, " "));
        sb.append(String.format("%s<%s>%s", i, name, NEW_LINE));
        if (text != null && !text.isEmpty())
        {
            sb.append(String.join("", Collections.nCopies(INDENT_SIZE*(indent+1), " ")))
                    .append(text)
                    .append(NEW_LINE);
        }

        for (HtmlElement e : elements)
            sb.append(e.toStringImpl(indent + 1));

        sb.append(String.format("%s</%s>%s", i, name, NEW_LINE));
        return sb.toString();
    }

    @Override
    public String toString() {
        return toStringImpl(0);
    }
}
class HtmlBuilder {

    private String rootName;
    private HtmlElement root = new HtmlElement();

    //rootName something like <p> or <ul> or <li>
    public HtmlBuilder(String rootName) {
        this.rootName = rootName;
        root.name = rootName;
    }
    public HtmlBuilder addChild(String childName, String childText){
        HtmlElement he = new HtmlElement(childName, childText);
        root.elements.add(he);
        //return a reference to the original HtmlBuilder
        return this;
    }
    public void clear(){
        root = new HtmlElement();
        root.name = rootName;
    }
    @Override
    public String toString(){
        return root.toString();
    }

}
public class Demo {
    public static void main(String[] args){
        //fluent interface allows you to write very longs chains which are useful for building things up
        HtmlBuilder hBuilder = new HtmlBuilder("ul");
        hBuilder.addChild("li", "hello").addChild("li", "world");
        System.out.println(hBuilder);
    }
}
