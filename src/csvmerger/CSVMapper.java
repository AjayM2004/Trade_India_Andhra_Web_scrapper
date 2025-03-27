package csvmerger;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class CSVMapper {

    public static void main(String[] args) {
        // Check if correct number of arguments are provided
        if (args.length < 2) {
            System.out.println("Please provide the paths of both CSV files as arguments.");
            return;
        }

        String jhRanchiTradePath = args[0];
        String testOutputPath = args[1];

        try {
            // Read both CSV files
            List<Map<String, String>> jhRanchiData = readCSV(jhRanchiTradePath);
            List<Map<String, String>> testOutputData = readCSV(testOutputPath);

            // Create a map from JH_Ranchi_Trade.csv with Licence_No as the key
            Map<String, Map<String, String>> jhRanchiMap = new HashMap<>();

            for (Map<String, String> row : jhRanchiData) {
                String licenceNo = row.get("Licence_No");
                if (licenceNo != null && !licenceNo.isEmpty()) {
                    jhRanchiMap.put(licenceNo, row);
                }
            }

            // Create a result map to store merged data
            Map<String, Map<String, String>> resultMap = new HashMap<>();

            // Merge data from TestOutput.csv into jhRanchiMap based on Licence Number
            for (Map<String, String> testOutputRow : testOutputData) {
                String licenceNumber = testOutputRow.get("Licence Number");
                if (licenceNumber != null && !licenceNumber.isEmpty()) {
                    if (jhRanchiMap.containsKey(licenceNumber)) {
                        // Get corresponding row from JH_Ranchi_Trade.csv
                        Map<String, String> mergedRow = new HashMap<>(jhRanchiMap.get(licenceNumber));

                        // Add fields from TestOutput.csv
                        mergedRow.put("Licence Number", testOutputRow.get("Licence Number"));
                        mergedRow.put("Valid Upto Date", testOutputRow.get("Valid Upto Date"));

                        // Add merged row to resultMap
                        resultMap.put(licenceNumber, mergedRow);
                    } else {
                        // Licence number found in TestOutput.csv but not in JH_Ranchi_Trade.csv
                        System.out.println("Warning: Licence Number " + licenceNumber + " from TestOutput.csv does not exist in JH_Ranchi_Trade.csv.");
                    }
                }
            }

            // Print the merged data
            for (Map.Entry<String, Map<String, String>> entry : resultMap.entrySet()) {
                System.out.println("Licence_No: " + entry.getKey());
                for (Map.Entry<String, String> rowEntry : entry.getValue().entrySet()) {
                    System.out.print(rowEntry.getKey() + ": " + rowEntry.getValue() + ", ");
                }
                System.out.println("\n--------------------");
            }

        } catch (Exception e) {
            System.err.println("Error reading or processing the files: " + e.getMessage());
        }
    }

    // Function to read CSV and return list of maps
    private static List<Map<String, String>> readCSV(String path) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));

        if (lines.isEmpty()) {
            return data;
        }

        String[] headers = lines.get(0).split(",");

        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            Map<String, String> row = new HashMap<>();
            for (int j = 0; j < headers.length; j++) {
                row.put(headers[j].trim(), values[j].trim());
            }
            data.add(row);
        }
        return data;
    }
}
