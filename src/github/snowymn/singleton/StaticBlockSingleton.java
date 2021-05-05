package github.snowymn.singleton;

import java.io.File;
import java.io.IOException;

/**
 * Exceptions in the singleton constructor, you have to change approach. Instead of
 * creating a variable and exposing it through getInstance(), you make a static block.
 * The static block has a try / catch within which you try to make a new instance of the
 * singleton. If the initialization of the singleton needs to be customized you can also put
 * it in this static block instead of the constructor.
 */

public class StaticBlockSingleton
{
    private static StaticBlockSingleton instance;
    private StaticBlockSingleton() throws IOException
    {

        System.out.println("Singleton is initializing");
        File.createTempFile(".",".");
    }

    static{
        try{
            instance = new StaticBlockSingleton();
        } catch (IOException e)
        {
            System.err.println("failed to create singleton");
        }
    }
    public static StaticBlockSingleton getInstance(){
        return instance;
    }

}
