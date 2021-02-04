package github.snowymn.solid.ocp;

import java.util.List;
import java.util.stream.Stream;

/**
 * Color of products.
 */
enum Color{
    RED, GREEN, BLUE
}

/**
 * Size of products.
 */
enum Size{
    SMALL, MEDIUM, LARGE, YUGE
}

/**
 * Open-Closed Principle through Specification design pattern. Open for extension
 * (inherit or implement interfaces) but closed for modification.
 */
public class OCPProduct {
    public String name;
    public Color color;
    public Size size;

    public OCPProduct(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

/**
 * Allow users to filter product. Stream of products filtered by criteria.
 *  Would violate this principle if in order to add new filters
 *  you have to modify the Product Filter class already made.
 */
class ProductFilter{
    public Stream<OCPProduct> filterByColor(List<OCPProduct> products, Color color){
        return products.stream().filter(p -> p.color == color);
    }

}
interface Specification<T>{
    boolean isSatisfied(T item);
}

/**
 * Upside to this implementation is if you want additional filters you do not have to
 * jump into existing classes and modify them you just create new that implement the existing
 * specification.
 * @param <T>
 */
interface Filter<T>{
    Stream<T> filter(List<T> items, Specification<T> spec);
}
class ColorSpecification implements Specification<OCPProduct>{
    private Color color;
    public ColorSpecification(Color color){
        this.color = color;
    }

    @Override
    public boolean isSatisfied(OCPProduct item) {
        //check whether satisfying the color specification
        return item.color == color;
    }
}
class SizeSpecification implements Specification<OCPProduct>
{
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(OCPProduct p) {
        return p.size == size;
    }
}
class BetterFilter implements Filter<OCPProduct>{

    @Override
    public Stream<OCPProduct> filter(List<OCPProduct> items, Specification<OCPProduct> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}

/**
 * Specification that combines two specifications together.
 */
class AndSpecification<T> implements Specification<T>{
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second){
        this.first = first;
        this.second = second;
    }
    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

class DemoOCP{
    public static void main(String[] args){
        OCPProduct apple = new OCPProduct("Apple", Color.GREEN, Size.SMALL);
        OCPProduct tree = new OCPProduct("Tree", Color.GREEN, Size.LARGE);
        OCPProduct house = new OCPProduct("House", Color.BLUE, Size.LARGE);

        List<OCPProduct> listOfProducts = List.of(apple, tree, house);
        ProductFilter prodFilter = new ProductFilter();
        System.out.println("Green products (old): ");
        prodFilter.filterByColor(listOfProducts, Color.GREEN).forEach(p ->
                System.out.println(" - " +p.name + " is green"));

        BetterFilter betFilter = new BetterFilter();
        System.out.println("Green products (new):");
        betFilter.filter(listOfProducts, new ColorSpecification(Color.GREEN))
                .forEach(p -> System.out.println(" - " + p.name + " is green"));
        System.out.println("Large blue items: ");
        betFilter.filter(listOfProducts, new AndSpecification<>(
                new ColorSpecification(Color.BLUE),
                new SizeSpecification(Size.LARGE))).forEach(p -> System.out.println(
                        " - " + p.name + "is blue and large"));

    }

}

