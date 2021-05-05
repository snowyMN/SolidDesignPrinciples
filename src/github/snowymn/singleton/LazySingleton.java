package github.snowymn.singleton;

/**
 * Idea of lazy instantiation is that instead of creating it right away we wait until
 * someone requests it by calling getInstance() to create it.
 * Introduces a potential race condition since two threads could be in competition to
 * create this. We can protect against this by making the method getInstance() synchronized.
 * This approach has a performance implication.
 */
public class LazySingleton
{
    private static LazySingleton instance;

    private LazySingleton(){
        System.out.println("initializing a lazy singleton.");
    }

    /**
     * One way to protect against race condition is to make this method synchronized.
     * @return LazySingleton instance
     */
    //public static synchronized LazySingleton getInstance(){
    //  if(instance == null){
    //     instance = new LazySingleton();
    //  }
    //    return instance;
    //}

    /**
     * double-checked locking - outdated approach to protect against race condition
     * but should be aware of this approach
     * @return
     */
    public static LazySingleton getInstance(){
        if(instance == null){

           synchronized (LazySingleton.class){
               if(instance == null){
                   instance = new LazySingleton();
               }
           }
        }
        return instance;
    }
}
