
JOGL Chapter 3. Picking on the Models

From the online book:
  Killer Game Programming in Java
  http://fivedots.coe.psu.ac.th/~ad/jg

Contact Address:
  Dr. Andrew Davison
  Dept. of Computer Engineering
  Prince of Songkla University
  Hat Yai, Songkhla 90112, Thailand
  E-mail: ad@fivedots.coe.psu.ac.th


If you use this code, please mention my name, and include a link
to the book's Web site.

Thanks,
  Andrew


==================================
OBJLoaderGL

This is an OBJLoader package for loading Wavefront OBJ models.

7 Java files here:
  OBJModel.java, Materials.java, Material.java, Faces.java
  FaceMaterials.java, ModelDimensions.java, Tuple3.java

and 
  compileGL.bat

This package is used by the other two examples, ModelLoaderGL
and TourModelsGL, so compile and install this first.


==================================
Requirements:

* J2SE 5.0 or later from http://java.sun.com/j2se/
  (I use its nanosecond timer, System.nanoTime(), in the CubeGL
   examples).
  I recommend the release version of Java SE 6.

* JOGL, the JSR-231 1.0.0 release version, from 
  https://jogl.dev.java.net/
  See the readme.txt file in the top-level directory for 
  details on installing it. 

* The batch file in this directory, compileGL.bat,
  assumes that the JOGL JAR and DLLs are in d:\jogl. 

-----

Compilation and installation: 

1. Use the compileGL.bat batch file.

   $ compileGL *.java

   This will create a directory OBJLoader\ containing the
   7 class files.


2. Package OBJLoader\ as the JAR OBJLoader.jar:

   $ jar cvf OBJLoader.jar OBJLoader


3. Move OBJLoader.jar to the d:\jogl directory where the other JOGL
   JARs and DLLs are stored.

-----------
Last updated: 3rd January 2007