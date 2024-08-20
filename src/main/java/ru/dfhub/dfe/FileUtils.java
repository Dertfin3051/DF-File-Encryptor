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
    public static byte[] readFile(String filePath) {
        byte[] fileBytes = new byte[0];
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            int fileSize = in.available();
            fileBytes = new byte[fileSize];
            int bytesRead = in.read(fileBytes);

            if (bytesRead != fileSize) {
                throw new IOException();
            }

        }
        catch (IOException e) {
            System.out.printf("An error occurred while reading the file (%s): %s", e, e.getMessage());
        }

        return fileBytes;
    }

    /**
     * Write String data to file
     * @param filePath File path (absolute or relative)
     * @param content Content to write
     */
    public static void writeToNewFile (String filePath, byte[] content) {
        File file = new File(filePath);

        try {
            if (!file.exists()) file.createNewFile();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            out.write(content);
            out.close();
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
