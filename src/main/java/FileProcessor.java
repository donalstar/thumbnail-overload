package main.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by donal on 9/30/16.
 */
public class FileProcessor {

    private static final String PATTERN = "(.*)\\.(.*)x(.*)_(.*)_(.*)_(.*)\\.(.*)";

    private static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);


    private Map<Dimension, java.util.List<String>> map = new HashMap<Dimension, java.util.List<String>>();

    public void processFile(String fileName, boolean loadFileFromWeb) {

        try {
            FileReader reader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                count++;

                if (loadFileFromWeb) {
                    try {
                        processImageName(line);
                    } catch (Exception e) {
                        System.err.println("\nError processing image " + line + " : " + e);
                    }
                } else {
                    processImageName(line);
                }

                System.out.print(".");

                if (count % 150 == 0) {
                    System.out.println("");
                }

            }


        } catch (IOException ioe) {
            System.err.println("Error " + ioe);
        }
    }

    /**
     * Parse the image name & get the dimensions from the name
     * e.g. http://static1.therooster.co/media/thumbs/attachments/IMG_42681_1.JPG.135x135_q85_crop_upscale.JPG
     *
     * @param imageName
     */
    private void processImageName(String imageName) {

        // Now create matcher object.
        Matcher m = REGEX_PATTERN.matcher(imageName);
        if (m.find()) {
            String width = m.group(2);
            String height = m.group(3);

            if (width != null && height != null) {
                int widthValue = Integer.parseInt(width);
                int heightValue = Integer.parseInt(height);

                Dimension dim = new Dimension(widthValue, heightValue);

                addToMap(imageName, dim);
            }
        } else {
            System.out.println("Failed to match file name " + imageName);
        }
    }

    /**
     * Get image dimensions (by loading image from web)  & add to map of dimensions <-> list of image locations
     *
     * @param imageLocation
     * @throws IOException
     */
    private void processImage(String imageLocation) throws IOException {
        Dimension dim = getImageDimensions(imageLocation);

        addToMap(imageLocation, dim);
    }

    /**
     * @param name
     * @param dim
     */
    private void addToMap(String name, Dimension dim) {

        java.util.List<String> matches = map.get(dim);

        if (matches == null) {
            matches = new ArrayList<String>();
            map.put(dim, matches);
        }

        matches.add(name);
    }

    /**
     * Get the dimensions of the image
     *
     * @param imageUrl
     * @return
     * @throws IOException
     */
    private Dimension getImageDimensions(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);

        BufferedImage image = ImageIO.read(url);

        int height = image.getHeight();
        int width = image.getWidth();

        return new Dimension(width, height);
    }


    /**
     * Print results to console
     */
    public void printOutput() {
        System.out.println("");

        int total = 0;

        for (Dimension dim : map.keySet()) {
            int count = map.get(dim).size();

            System.out.println(printDimension(dim) + " thumbnail file occurs " + count + " times");

            total += count;
        }

        System.out.println("Total: " + total + " images");
    }

    private String printDimension(Dimension dimension) {
        return dimension.width + " x " + dimension.height;
    }
}
