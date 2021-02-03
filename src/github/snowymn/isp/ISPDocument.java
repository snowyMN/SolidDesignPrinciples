package github.snowymn.isp;

/**
 * Interface Segregation Principle is a recommendation on how to split interfaces
 * into smaller interfaces. Should not put more into interface than what client asks for or needs.
 */
public class ISPDocument {

}
interface Machine{
    void print(ISPDocument d);
    void fax(ISPDocument d) throws Exception;
    void scan(ISPDocument d) throws Exception;
}
class MultifunctionPrinter implements Machine{

    @Override
    public void print(ISPDocument d) {
       //
    }

    @Override
    public void fax(ISPDocument d) {
        //
    }

    @Override
    public void scan(ISPDocument d) {
        //
    }
}
/**
 * Split out interfaces
 */
interface Printer{
    void print(ISPDocument d);
}
interface Scanner{
    void scan(ISPDocument d);
}
interface Faxing{
    void fax(ISPDocument d);
}
//YAGNI - You Ain't Going to Need It
class JustAPrinter implements Printer{

    @Override
    public void print(ISPDocument d) {
        //
    }
}

/**
 * Can print and scan
 */
class Photocopier implements Printer, Scanner{

    @Override
    public void print(ISPDocument d) {
        //
    }

    @Override
    public void scan(ISPDocument d) {
        //
    }
}
/**
 * If we do actually want a multifunction interface, you can make an interface
 * that extends multiple interfaces.
 */
interface MultiFunctionDevice extends Printer, Scanner, Faxing{

}

/**
 * Example of decorator pattern
 */
class MultiFunctionMachine implements MultiFunctionDevice{

    private Printer printer;
    private Scanner scanner;
    private Faxing fax;

    public MultiFunctionMachine(Printer printer, Scanner scanner, Faxing fax){
      this.printer = printer;
      this.scanner = scanner;
      this.fax = fax;
    }

    @Override
    public void print(ISPDocument d) {
        printer.print(d);
    }

    @Override
    public void scan(ISPDocument d) {
        scanner.scan(d);
    }

    @Override
    public void fax(ISPDocument d) {
        fax.fax(d);
    }
}

/**
 * What if you have a client that only needs the print function, this is where
 * the issues can occur. The OldFashionedPrinter can print but cannot scan or fax.
 */
class OldFashionedPrinter implements Machine{

    @Override
    public void print(ISPDocument d) {

    }

    @Override
    public void fax(ISPDocument d) throws Exception {
        //could leave empty but then still leaving user this option
        //another solution would be to throw an Exception if this method is called
        //Exception specification issues - change interface methods - own source code for interface?
        throw new Exception();
    }

    @Override
    public void scan(ISPDocument d) throws Exception {
        //could leave empty but then still leaving user this option
        //another solution is to throw an Exception if method is called
        //then you run into the issue of Exception specification
        throw new Exception();
    }
}
