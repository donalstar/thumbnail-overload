package main.java;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by donal on 9/30/16.
 */
public class Main {


    public static void main(String[] args) {

        System.out.println("\nThumbnail Overload!");
        System.out.println("-------------------\n");

        if (args.length != 2) {
            System.out.println(
                    "Usage: process_thumbnails.sh [-f|-u] [FILE/URL NAME]\n\tOptions:\n\t\t-f file\n\t\t-u URL");
        }
        else {
            String typeFlag = args[0];

            boolean type = FileProcessor.TYPE_FILE;

            if (typeFlag.equals("-u")) {
                 type = FileProcessor.TYPE_URL;
            }
            else if (typeFlag.equals("-f")) {
                type = FileProcessor.TYPE_FILE;
            }
            else {
                System.out.println("Invalid flag: " + typeFlag);
            }

            String fileName = args[1];

            processFile(fileName, type);
        }
    }

    /**
     * Read the input file of image URLs
     *
     * @param fileName
     */
    private static void processFile(String fileName, boolean type) {
        System.out.println("Read file " + fileName);

        FileProcessor fileProcessor = new FileProcessor();

        try {
            fileProcessor.processFile(fileName, type, false);
        } catch (Exception e) {
            System.err.println("Error " + e);
        }

        fileProcessor.printOutput();
    }

}



