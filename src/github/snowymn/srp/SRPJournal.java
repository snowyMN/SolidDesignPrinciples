package github.snowymn.srp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Single responsibility principle states that a class should have just one primary
 * responsibility. SRPJournal primary responsibility is to store entries.
 */

public class SRPJournal {

    private final List<String> entries = new ArrayList<>();
    // count total number of entries across all Journal's
    private static int count = 0;

    /**
     * Add an entry to the journal.
     * @param text
     */
    public void addEntry(String text){
        entries.add("" + (++count) + ": " + text);
    }

    /**
     * Remove an entry from the journal.
     * @param index the position in the Journal correlated to the entry
     */
    public void removeEntry(int index){
        entries.remove(index);
        --count;
    }
    @Override
    public String toString(){
        return String.join(System.lineSeparator(), entries);
    }


}

/**
 * Used for saving state of the SRPJournal class. These functionalities cannot be in
 * the SRPJournal class and it still adhere to the single responsibility principle.
 */
class Persistence implements Serializable {
    public void saveToFile(SRPJournal journal, String filename, boolean overwrite) {
        if (overwrite || new File(filename).exists()) {
            try (PrintStream out = new PrintStream((filename))) {
                out.println(toString());
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found!");
            }
        }
    }

    public SRPJournal load(String filePath) {
        SRPJournal journal = new SRPJournal();
        try {
            BufferedReader listOfEntries = new BufferedReader(new FileReader(filePath));
            Scanner scanner = new Scanner(listOfEntries).useDelimiter("\n");
            while (scanner.hasNext()) {
                journal.addEntry(scanner.next());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

        return journal;
    }
}

class DemoSRP{
    public static void main(String[] args) {
        SRPJournal journal = new SRPJournal();
        journal.addEntry("Crucio");
        journal.addEntry("Imperio");
        journal.addEntry("Avada Kedavra");
        System.out.println(journal);

        Persistence persistence = new Persistence();
        persistence.saveToFile(journal, "journal.txt", true);

    }

}
