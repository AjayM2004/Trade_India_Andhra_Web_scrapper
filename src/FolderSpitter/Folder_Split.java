package FolderSpitter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Folder_Split {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take folder path and number of parts as input
        System.out.print("Enter folder path: ");
        String folderPath = args[0];
        
        System.out.print("Enter number of parts to divide the folder into: ");
        int n = scanner.nextInt();
        scanner.close();

        File folder = new File(folderPath);

        // Check if the folder exists
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path.");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("The folder is empty.");
            return;
        }

        // Calculate number of files per part
        int filesPerPart = (int) Math.ceil((double) files.length / n);
        
        List<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }

        // Create subfolders and move files
        int count = 0;
        for (int i = 0; i < n; i++) {
            File partFolder = new File(folder, "part_" + (i + 1));
            partFolder.mkdir();

            for (int j = 0; j < filesPerPart && count < fileList.size(); j++, count++) {
                File file = fileList.get(count);
                File targetFile = new File(partFolder, file.getName());

                try {
                    Files.move(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Error moving file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Folder divided into " + n + " parts successfully.");
    }
}
