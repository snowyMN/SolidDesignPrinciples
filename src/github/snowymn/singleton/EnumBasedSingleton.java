package github.snowymn.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This enum does not have the problem of being able to call the private
 * constructor using reflection. The serialization that enums are able to do is not the
 * kind that will allow you to preserve the state of the singleton. Cannot inherit from this class.
 * Can be a good solution if you do not need state persistence from this class.
 * Only thing that is serialized in an enum is the name of the enum the fields
 * are not serialized.
 */
public enum EnumBasedSingleton
{
    INSTANCE;
    private int value;

    //Enum already has a default private constructor
    //can make your own constructor though
    EnumBasedSingleton(){
        value = 42;
    }

    public int getValue()
    {
        return value;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
}

/**
 * to try to serialize and deserialize the EnumBasedSingleton
 */
class DemoEnumSingleton{
    static void saveToFile(EnumBasedSingleton singleton, String filename) throws Exception{
        try(FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)){
            out.writeObject(singleton);
        }
    }
    static EnumBasedSingleton readFromFile(String filename) throws Exception{
        try(FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn)){
            return (EnumBasedSingleton) in.readObject();
        }
    }
    public static void main(String[] args) throws Exception
    {
        String filename = "myfile.bin";
        EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
        singleton.setValue(111);
        saveToFile(singleton, filename);

        EnumBasedSingleton singletonTwo = readFromFile(filename);
        //can get a different value than 111 since only the name of the enum is serialized
        // since setValue(111) was called and then the singleton was serialized will get 111
        System.out.println(singletonTwo.getValue());

    }
}
