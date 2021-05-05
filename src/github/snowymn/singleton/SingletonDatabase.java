package github.snowymn.singleton;

import com.google.common.collect.Iterables;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

interface Database{
    int getPopulation(String name);
}

public class SingletonDatabase
{
    private Dictionary<String, Integer> capitals = new Hashtable<>();
    private static int instanceCount = 0;

    public static int getCount(){
        return instanceCount;
    }

    private SingletonDatabase(){
        instanceCount++;
        System.out.println("Initializing database");

        try{
            //get the singleton database class and get the location of that class
            //figure out where whole thing is running
            File file = new File(SingletonDatabase.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath());
            //get full path  - combine path from file with name of file
            Path fullPath = Paths.get(file.getPath(), "capitals.txt");
            //use full path to read it
            List<String> lines = Files.readAllLines(fullPath);
            //partition the lines in groups of two and add tem to the dictionary we just made
            Iterables.partition(lines, 2).
                    forEach(kv -> capitals.put(kv.get(0).trim(),
                            Integer.parseInt(kv.get(1))
                    ));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static final SingletonDatabase INSTANCE = new SingletonDatabase();
    public static SingletonDatabase getInstance(){
        return INSTANCE;
    }
    //utility method to return the population of a particular city
    public int getPopulation(String name){
        return capitals.get(name);
    }
}
class SingletonRecordFinder{
    //issue with testability of this method since accessing the LIVE database (capitals.txt)
    public int getTotalPopulation(List<String> names){
        int result = 0;
        //dependent upon concrete class and not abstraction - refer to dependency inversion principle
        for(String name : names){
            result += SingletonDatabase.getInstance().getPopulation(name);
        }
        return result;
    }
}

/**
 * Need dummy data to do a unit test - doesnt require you to know the values in the database.
 * If testing against LIVE data if the data were to change you would have to update the unit test.
 * class can depend upon an abstraction - Database interface
 */
class ConfigurableRecordFinder{
    private Database database;
    public ConfigurableRecordFinder(Database database){
        this.database = database;
    }
    public int getTotalPopulation(List<String> names){
        int result = 0;
        //dependent upon concrete class and not abstraction - refer to dependency inversion principle
        for(String name : names){
            result += database.getPopulation(name);
        }
        return result;
    }
}

/**
 * Data added in the constructor is never going to change.
 */
class DummyDatabase implements Database{

    private Dictionary<String, Integer> data = new Hashtable<>();
    public DummyDatabase(){
        data.put("alpha", 1);
        data.put("beta", 2);
        data.put("gamma", 3);
    }
    @Override
    public int getPopulation(String name)
    {
        return data.get(name);
    }
}
class Tests{

    @Test //more of an integration test since testing against the live data
    public void singletonTotalPopulationTest(){
        SingletonRecordFinder rf = new SingletonRecordFinder();
        List<String> names = List.of("Seoul", "Mexico City");
        int tp = rf.getTotalPopulation(names);
        assertEquals(1750000+1740000, tp);
    }
    @Test
    public void dependentPopulationTest(){
       DummyDatabase db = new DummyDatabase();
       ConfigurableRecordFinder rf = new ConfigurableRecordFinder(db);
       assertEquals(4, rf.getTotalPopulation(List.of("alpha", "gamma")));
    }
}
class Stuff{
    //dependency injection - idea of providing a dependency
    public static void main(String[] args){

    }
}

