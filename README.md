# thumbnail-overload

* Main.java - Java entry-point file
* FileProcessor.java - processes input file:
  * Loads input URL/file
  * Iterates through the list of image file URLs
  * Parses each file name & determines resolution from file name (or - optionally - by loading each image file)
  * Saves image file names, by resolution, in a map
  * Provides print method to allow report of resolution frequencies to be output

* To Run:
