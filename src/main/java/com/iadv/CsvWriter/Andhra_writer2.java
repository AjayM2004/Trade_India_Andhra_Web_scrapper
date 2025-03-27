package com.iadv.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Andhra_writer2 {

    // List of columns to be included in the CSV
    private static final String[] COLUMNS = {
            "Trade Owner Name", "Contract Workers Male", "Total Workers", "Email ID", 
            "Trade Subcategory", "Old License No.", "Door No", "ViewpageLink", "Election Ward",
            "Remarks", "Trade Name", "Mobile No.", "Trade Address", "Locality", "Daily Wages Female",
            "Daily Wages Male", "Trade Type", "Trade Title", "Ward", "Mandal Name", 
            "Classification of Establishment", "Mobile Number", "Revenue Ward", "Direct Workers Female",
            "Trade Category", "Ownership Type", "Direct Workers Male", "Employers", 
            "Father/Spouse Name", "Property Assessment No", "Application No.", "Contract Workers Female",
            "ShowPageLink", "License Number", "Date of Application", "Email Id", "License No.",
            "muncipalty", "Trade Commencement Date", "UOM", "Subcategory", "Trade Measurement"
    };

    public static void writeToCSV(String filePath, HashMap<String, HashMap<String, String>> outerMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write CSV header
            writer.write("firstheader,");
            for (String column : COLUMNS) {
                writer.write(column + ",");
            }
            writer.newLine();

            // Write each row
            for (Map.Entry<String, HashMap<String, String>> outerEntry : outerMap.entrySet()) {
                // Write the first header (key from outer map)
                writer.write(escapeSpecialCharacters(outerEntry.getKey()) + ",");

                // Write data from the inner map
                HashMap<String, String> innerMap = outerEntry.getValue();
                for (String column : COLUMNS) {
                    String value = innerMap.getOrDefault(column, "N/A"); // Default to "N/A" if not present
                    writer.write(escapeSpecialCharacters(value) + ",");
                }
                writer.newLine();
            }
            System.out.println("CSV file has been written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method escapes special characters (e.g., commas, double quotes, newlines) in CSV values
    private static String escapeSpecialCharacters(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");  // Escape double quotes by doubling them
            return "\"" + value + "\"";  // Enclose the entire value in double quotes
        } else {
            return value;
        }
    }
}