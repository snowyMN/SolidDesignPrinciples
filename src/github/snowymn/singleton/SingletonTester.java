package github.snowymn.singleton;
import java.util.function.Supplier;

/**
 * Write a method called isSingleton(). This method takes a factory method that returns an object
 * and determines whether or not that object is a Singleton instance.
 */

public class SingletonTester
{
    public static boolean isSingleton(Supplier<Object> func){
        boolean isASingleton = false;

        int funcOneHash = func.get().hashCode();
        Object funcTwo =  func.get().hashCode();
        int funcTwoHash = funcTwo.hashCode();

        if(funcOneHash == funcTwoHash){
            isASingleton = true;
        }
        return isASingleton;
    }
}
