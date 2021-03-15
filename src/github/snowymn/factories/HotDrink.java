package github.snowymn.factories;

import org.javatuples.Pair;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Abstract Factory is a pattern which makes a correspondence between the hierarchy of objects
 * that you have and a hierarchy of factories that you have to make those object. Not used often.
 */
public interface HotDrink
{
    void consume();
}

class Tea implements HotDrink{
    @Override
    public void consume(){
       System.out.println("This tea is delicious!");
    }
}

class Coffee implements HotDrink{
    @Override
    public void consume()
    {
        System.out.println("This coffee is delicious!");
    }
}
interface HotDrinkFactory{
    HotDrink prepare(int amount);
}
class TeaFactory implements HotDrinkFactory{

    @Override
    public HotDrink prepare(int amount)
    {
        System.out.println(
                "Put in tea bag, boil water, pour "
                + amount + "ml, add lemon, enjoy!");
        return new Tea();
    }
}
class CoffeeFactory implements HotDrinkFactory{
    @Override
    public HotDrink prepare(int amount)
    {
        System.out.println(
                "Grind some beans, boil water, pour "
        + amount + "ml, add cream and sugar, enjoy!");
        return new Coffee();
    }
}

class HotDrinkMachine{
    //find all factories that can make HotDrink and put into list
    private List<Pair<String, HotDrinkFactory>> namedFactories = new ArrayList<>();

    public HotDrinkMachine() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException
    {
        //find all inheritors on the HotDrink interface using Reflections library
        Set<Class<? extends HotDrinkFactory>> types =
                new Reflections("github.snowym.factories").getSubTypesOf(HotDrinkFactory.class);
        //go through set of class definitions and instantiate each one
        for(Class<? extends HotDrinkFactory> type : types ){
            namedFactories.add(new Pair<>(
                    type.getSimpleName().replace("Factory", ""),
                    type.getDeclaredConstructor().newInstance()
                    ));
        }
    }
    //use HotDrink machine with interactive interface
    public HotDrink makeDrink() throws IOException
    {
        System.out.println("Available drinks: ");
        for(int index = 0; index < namedFactories.size(); ++index){

            Pair<String, HotDrinkFactory> item = namedFactories.get(index);
            System.out.println("" + index + ":" + item.getValue0());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String s;
            int i, amount;
            if((s = reader.readLine()) != null && (i = Integer.parseInt(s)) >= 0
            && i < namedFactories.size()){
                System.out.println("Specify amount: ");
                s = reader.readLine();
                if(s != null && (amount = Integer.parseInt(s)) > 0){
                    return namedFactories.get(i).getValue1().prepare(amount);
                }
            }
            System.out.println("Incorrect input, try again.");
        }
    }
}
class HotDrinkDemo{
    public static void main(String[] args) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, IOException
    {
        HotDrinkMachine machine = new HotDrinkMachine();
        HotDrink drink = machine.makeDrink();
        drink.consume();

    }


}
