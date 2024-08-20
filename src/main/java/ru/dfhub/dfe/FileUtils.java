package ru.dfhub.dfe;

import java.io.*;

/**
 * Utilities for working with files
 */
public class FileUtils {

    /**
     * Read file in String format
     * @param filePath File path (absolute or relative)
     * @return File content
     */
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

    /**
     * Write String data to file
     * @param filePath File path (absolute or relative)
     * @param content Content to write
     */
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

    /**
     * Get encoded file name from normal file
     * @param filePath Normal file name
     * @return Encoded file name
     */
    public static String getEncodedFileName(String filePath) {
        return filePath.concat(".dfe");
    }

    /**
     * Get normal file name from encoded file
     * @param filePath Encoded file name
     * @return Normal file name
     */
    public static String getDecodedFileName(String filePath) {
        return filePath.replace(".dfe", "");
    }
}
