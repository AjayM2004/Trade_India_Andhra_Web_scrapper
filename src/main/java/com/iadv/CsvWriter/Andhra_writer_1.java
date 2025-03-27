package com.iadv.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Andhra_writer_1 {
	public static void writeToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Page source written to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}