package ru.dfhub.dfe;

import java.io.*;

public class FileUtils {

    public static String readFile(String filePath) {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
        }
        catch (IOException e) {
            System.out.printf("An error occurred while reading the file (%s): %s", e, e.getMessage());
        }

        return fileContent.toString();
    }

    public static void writeToNewFile (String filePath, String content) {
        File file = new File(filePath);

        try {
            if (!file.exists()) file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        }
        catch (IOException e) {
            System.out.printf("An error occurred while writing the file (%s): %s", e, e.getMessage());
        }
    }

    public static String getEncodedFileName(String filePath) {
        return filePath.concat(".dfe");
    }

    public static String getDecodedFileName(String filePath) {
        return filePath.replace(".dfe", "");
    }
}
