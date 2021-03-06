package com.donal;

import com.donal.model.Resolution;
import com.donal.model.Result;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by donal on 9/30/16.
 */
public class FileProcessor {

    public static final boolean TYPE_FILE = false;
    public static final boolean TYPE_URL = true;

    private static final String PATTERN = "(.*)\\.(.*)x(.*)_(.*)_(.*)_(.*)\\.(.*)";

    private static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);


    private Map<Dimension, java.util.List<String>> map = new HashMap<Dimension, java.util.List<String>>();

    /**
     * Read the contents of the passed file (from the local file system or from the web depending on the
     * type parameter)
     * If loadFileFromWeb=true, determine image resolution by loading the image file (very slow!)
     *
     * @param fileName
     * @param type
     * @param loadFileFromWeb
     */
    public Result processFile(String fileName, boolean type, boolean loadFileFromWeb) {

        try {
            BufferedReader bufferedReader = loadFile(fileName, type);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (loadFileFromWeb) {
                    try {
                        processImageName(line);
                    } catch (Exception e) {
                        System.err.println("\nError processing image " + line + " : " + e);
                    }
                } else {
                    processImageName(line);
                }
            }
        } catch (IOException ioe) {
            System.err.println("Error " + ioe);
        }

        return getResult();
    }

    /**
     * @return
     */
    private Result getResult() {
        Result result = new Result();

        List<Resolution> resolutions = new ArrayList<Resolution>();

        for (Dimension dim : map.keySet()) {
            Resolution resolution = new Resolution();

            resolution.setCount(map.get(dim).size());
            resolution.setValue(printDimension(dim));

            resolutions.add(resolution);
        }

        result.setResolutions(resolutions);

        return result;
    }

    /**
     * Load the file from file system/web
     *
     * @param fileName
     * @param type
     * @return
     * @throws IOException
     */
    private BufferedReader loadFile(String fileName, boolean type) throws IOException {
        BufferedReader bufferedReader;

        if (type == TYPE_URL) {
            URL url = new URL(fileName);

            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        } else {
            FileReader reader = new FileReader(fileName);

            bufferedReader = new BufferedReader(reader);
        }

        return bufferedReader;
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
     * (slow!)
     *
     * @param imageLocation
     * @throws IOException
     */
    private void processImage(String imageLocation) throws IOException {
        Dimension dim = getImageDimensions(imageLocation);

        addToMap(imageLocation, dim);
    }

    /**
     * Add the image file name to a list of file names associated with the resolution dim (stored in a
     * hashmap)
     *
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
