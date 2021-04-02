package github.snowymn.prototype;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;


/**
 * Can create a deep copy of the object using Serialization.
 * In this approach you dont have to specify what you are copying.
 * Used apache commons lang library
 * https://commons.apache.org/proper/commons-lang/download_lang.cgi
 */

public class Foo implements Serializable
{
    public int stuff;
    public String whatever;

    public Foo(int stuff, String whatever)
    {
        this.stuff = stuff;
        this.whatever = whatever;
    }

    @Override
    public String toString()
    {
        return "Foo{" + "stuff=" + stuff + ", whatever='" + whatever + '\'' + '}';
    }
}
class DemoSerial{
    public static void main(String[] args){
        Foo foo = new Foo(42, "life");

        //roundtrip() kind of like copy by value
        Foo foo2 = SerializationUtils.roundtrip(foo);
        foo2.whatever = "xyz";

        System.out.println(foo);
        System.out.println(foo2);

    }
}
