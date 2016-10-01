# thumbnail-overload

* ``Main.java`` - Java entry-point file
* ``FileProcessor.java`` - processes input file:
  * Loads input URL/file
  * Iterates through the list of image file URLs
  * Parses each file name & determines resolution from file name (or - optionally - by loading each image file)
  * Saves image file names, by resolution, in a map
  * Provides print method to allow report of resolution frequencies to be output

## To Run:

* cd ``/bin``
* ``process_thumbnails.sh -f thumbnails-test.txt`` (parses local test file)
* ``process_thumbnails.sh -u http://paloalto.therooster.co/static/fun-rooster-thumbnail-names.txt`` (loads list of image files from the web)

_(Program has been tested using Java JRE v 1.8)_

I was a bit concerned that, if you dont have the correct JDK/JRE installed, you might have trouble setting this up & running the test, so I also created a simple web service & installed it on AWS.

### To install/run the web service locally, you can do the following: _(you'll need to have Apache Maven build tool installed)_

* Start the web service -- ``cd java`` then ``mvn jetty:run`` _(runs a local server at port #8000)_
* Start the AngularJS client web app -- ``cd angular`` then ``./start_server.sh``

### To test the web app installed on AWS:

* From your browser, go to: ``http://thumbnail-overload.s3-website-us-east-1.amazonaws.com/``
* In the entry field, type in the location for an images file _(e.g. http://paloalto.therooster.co/static/fun-rooster-thumbnail-names.txt)_
* Press ""process" -- you should see the results on the page
