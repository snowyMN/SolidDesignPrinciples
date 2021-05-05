package github.snowymn.singleton;

/**
 * This approach avoids the problem of synchronization. It guarantees
 * that whenever you initialize the instance you are only going to get
 * one instance no matter what happens.
 */

public class InnerStaticSingleton
{
    private InnerStaticSingleton(){

    }
    //inner classes can access private members of outer classes
    private static class Implementation{
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
    }
    public InnerStaticSingleton getInstance(){
        return Implementation.INSTANCE;
    }
}
