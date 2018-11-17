

# LIBGDX is displayed but loading never happens
**Error:**
Failed to load file:///C:/git/RotationFrenzy/source/html/build/dist/assets/assets.txt: Cross origin requests are only supported for protocol schemes: http, data, chrome, chrome-extension, https.

**Description:**
You can't open the index.html directly in Chrome.  There is an error if you look in the developer console

**Resolution:**
Put the static website dist folder on a web server and run it from there.

**Alternative:** 
If you are just trying to test, you can run the following:
* gradlew html:superDev

[Details:](https://github.com/libgdx/libgdx/wiki/Gradle-on-the-Commandline#running-the-html-project)
This will load a small server with the files, you can test by going to  http://localhost:8080/html

# Game loads, but level doesn't load
**Error:**
Uncaught Error: com.badlogic.gdx.utils.SerializationException: Error reading file: levels/level4.json
let me try adding the Wheel class to the GdxDefinition.gwt.xml file

**Description:**
This is because of reflection and GWT.  The code doesn't know how to use reflection because it has not referenced the class objects.

**Resolution**
Essentially each class used in the Level.java file needed to be added to the **GdxDefinition.gwt.xml**



