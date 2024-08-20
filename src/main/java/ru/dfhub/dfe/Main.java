package ru.dfhub.dfe;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public enum Action {
        ENCRYPT, DECRYPT
    }

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Action action;
        String fileName;
        SecretKeySpec key;

        System.out.println("Welcome to DFE!");

        try {
            action = InitCheck.getAction();
        }
        catch (IOException e) {
            System.out.println("You entered an incorrect action!");
            return;
        } // Wrong action

        try {
            fileName = InitCheck.getFileName(action);
        }
        catch (FileNotFoundException e) {
            System.out.println("The specified file does not exist!");
            return;
        } // File not exists
        catch (IOException e) {
            System.out.println("The specified file is not in .dfe format!");
            return;
        } // File to decrypt is not .dfe

        try {
            key = InitCheck.getEncryptionKey();
        }
        catch (IOException e) {
            System.out.println("Password length must be less than 32 characters");
            return;
        } // If key length >32

        switch (action) {
            case ENCRYPT -> {
                try {
                    EncryptDecrypt.encrypt(fileName, key);
                    System.out.println("Success!");
                }
                catch (Exception e) {
                    System.out.println("An unknown error has occurred!");
                }
            }
            case DECRYPT -> {
                try {
                    EncryptDecrypt.decrypt(fileName, key);
                }
                catch (IllegalBlockSizeException | BadPaddingException e) {
                    System.out.println("Your encryption key is incorrect or the file has been damaged!");
                }
            }
        }
    }
}