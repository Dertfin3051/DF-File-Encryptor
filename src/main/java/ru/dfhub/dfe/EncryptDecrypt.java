package ru.dfhub.dfe;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * The class that performs the main logic of the program and is responsible for encrypting/decrypting files
 */
public class EncryptDecrypt {

    /**
     * Encrypt file
     * @param filePath File path (absolute or relative)
     * @param key Encryption key (user input)
     * @throws Exception Any exceptions. Discarded only in exceptionally rare cases
     */
    public static void encrypt(String filePath, SecretKeySpec key) throws Exception {
        String fileContent = FileUtils.readFile(filePath);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        String encodedContent = Base64.getEncoder().encodeToString(
                cipher.doFinal(fileContent.getBytes(StandardCharsets.UTF_8))
        );

        FileUtils.writeToNewFile(
                FileUtils.getEncodedFileName(filePath),
                encodedContent
        );
    }

    /**
     * Decrypt file
     * @param filePath .dfe file path (absolute or relative)
     * @param key Encryption key (user input)
     * @throws IllegalBlockSizeException Damaged file error
     * @throws BadPaddingException Wrong encryption key error
     */
    public static void decrypt(String filePath, SecretKeySpec key) throws IllegalBlockSizeException, BadPaddingException {
        String fileContent = FileUtils.readFile(filePath);
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }


        String encryptedContent = new String(cipher.doFinal(
                Base64.getDecoder().decode(fileContent)
        ), StandardCharsets.UTF_8);

        FileUtils.writeToNewFile(
                FileUtils.getDecodedFileName(filePath),
                encryptedContent
        );
    }

}
