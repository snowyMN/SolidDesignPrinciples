package github.snowymn.singleton;

/**
 * Problem #1 with Singleton has to do with reflection, the private constructor can be defeated.
 * You can use reflection to get to this constructor and actually call it to make it public effectively.
 * This requires explicit use, the developer would have to know they want to defeat the use of a singleton.
 * Problem #2 with Singleton has to do with serialization. The JVM doesn't care that your constructor is private
 * it is still going to construct an object anyway.
 */
public class BasicSingleton
{
    /**
     * Make static final instance of BasicSingleton, the one and only instance you are ever going to expose.
     */
    private static final BasicSingleton INSTANCE = new BasicSingleton();
    private int value = 0;

    /**
     * Make constructor private so nobody can instantiate the singleton
     */
    private BasicSingleton(){}

    /**
     * Getter that provides access to the one and only instance of BasicSingleton
     * @return one and only instance of BasicSingleton
     */
    public static BasicSingleton getInstance(){
        return INSTANCE;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    /**
     * Giving JVM hint that whenever serialization happens it has to happen within this instance.
     * As opposed to making new objects.
     * @return BasicSingleton instance
     */
    protected Object readResolve(){
        return INSTANCE;
    }
}
class DemoBasicSingleton{
    public static void main(String[] args){
        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(123);
        System.out.println(singleton.getValue());
    }

}