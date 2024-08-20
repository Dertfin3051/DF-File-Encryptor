package ru.dfhub.dfe;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InitCheck {

    private static final Scanner input = new Scanner(System.in);

    public static Main.Action getAction() throws IOException {
        System.out.println("""
                What do you want to do?
                1. Encrypt
                2. Decrypt"""
        );

        System.out.print(">>> ");
        switch (input.nextLine()) {
            case "1" -> {
                return Main.Action.ENCRYPT;
            }
            case "2" -> {
                return Main.Action.DECRYPT;
            }
            default -> {
                throw new IOException();
            }
        }
    }

    public static String getFileName(Main.Action action) throws IOException, FileNotFoundException {
        if (action == Main.Action.ENCRYPT) {
            System.out.print("File path: ");
        } else {
            System.out.print(".dfe file path: ");
        }

        String fileName = input.nextLine();

        // If this is decryption mode, the file name must end with .dfe
        if (action == Main.Action.DECRYPT && !fileName.endsWith(".dfe")) throw new IOException();

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        return fileName;
    }

    public static SecretKeySpec getEncryptionKey() throws IOException {
        System.out.print("Password: ");

        String password = input.nextLine();

        if (password.length() > 32) throw new IOException();

        while (password.length() % 16 != 0) {
            password = password.concat("0");
        } // Password length must be a multiple of 16 (16 or 32)

        return new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), "AES");
    }
}
