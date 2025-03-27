package Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFileToArrayList {
    public static ArrayList<String> getZoneNWard(String filePath ) {
        // Create an ArrayList to store the lines from the file
        ArrayList<String> lines = new ArrayList<String>();

        try
        {
        // Use a Scanner to read the file
        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Read each line from the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // Get the next line
                lines.add(line); // Add the line to the ArrayList
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Print any exceptions
        }
       }
        catch (Exception e) {
        	e.printStackTrace();
		}
		return lines;
    }
}


