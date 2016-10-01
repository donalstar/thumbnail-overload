package main.java;

/**
 * Created by donal on 9/30/16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Thumbnail Overload!");

//        processFile("thumbnails-test.txt");

        processFile("fun-rooster-thumbnail-names.txt");
    }

    /**
     * Read the input file of image URLs
     *
     * @param fileName
     */
    private static void processFile(String fileName) {
        System.out.println("Read file " + fileName);

        FileProcessor fileProcessor = new FileProcessor();

        try {
            fileProcessor.processFile(fileName, false);
        } catch (Exception e) {
            System.err.println("Error " + e);
        }

        fileProcessor.printOutput();
    }

}



