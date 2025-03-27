package csvmerger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelMerger {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java ExcelMerger <input_folder_path> <output_file_path>");
            return;
        }

        String inputFolderPath = args[0];
        String outputFilePath = args[1];

        // Create a workbook for the output file
        Workbook outputWorkbook = new XSSFWorkbook();
        Sheet outputSheet = outputWorkbook.createSheet("Merged Data");

        // Add headers to the output sheet
        Row headerRow = outputSheet.createRow(0);
        headerRow.createCell(0).setCellValue("PTIN_No");
        headerRow.createCell(1).setCellValue("Name_Address");
        headerRow.createCell(2).setCellValue("Masked_Mobile_Number");

        int outputRowNum = 1;

        // Process each file in the input folder
        File inputFolder = new File(inputFolderPath);
        File[] inputFiles = inputFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xlsx"));

        if (inputFiles != null) {
            for (File inputFile : inputFiles) {
                try (FileInputStream fis = new FileInputStream(inputFile);
                     Workbook inputWorkbook = new XSSFWorkbook(fis)) {

                    Sheet inputSheet = inputWorkbook.getSheetAt(0); // Assuming data is in the first sheet
                    Iterator<Row> rowIterator = inputSheet.iterator();
                    rowIterator.next(); // Skip the header row

                    while (rowIterator.hasNext()) {
                        Row inputRow = rowIterator.next();
                        Row outputRow = outputSheet.createRow(outputRowNum++);

                        // Copy PTIN_No
                        Cell ptinCell = inputRow.getCell(0);
                        outputRow.createCell(0).setCellValue(ptinCell != null ? ptinCell.getStringCellValue() : "");

                        // Copy Name_Address
                        Cell nameAddressCell = inputRow.getCell(1);
                        outputRow.createCell(1).setCellValue(nameAddressCell != null ? nameAddressCell.getStringCellValue() : "");

                        // Copy Masked_Mobile_Number
                        Cell maskedMobileCell = inputRow.getCell(2);
                        outputRow.createCell(2).setCellValue(maskedMobileCell != null ? maskedMobileCell.getStringCellValue() : "");
                    }
                } catch (IOException e) {
                    System.err.println("Error processing file: " + inputFile.getName());
                    e.printStackTrace();
                }
            }
        }

        // Write the output workbook to file
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            outputWorkbook.write(fos);
            System.out.println("Data merged successfully into: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing output file.");
            e.printStackTrace();
        }
    }
}