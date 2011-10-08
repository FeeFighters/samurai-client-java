Samurai Java Client Library
===========================

Build requirements
------------------

To build the library you need the following:

* Java SDK 6
* Maven 3.x (http://maven.apache.org)

Build steps
-----------

In the directory with the library sources invoke the following command:

    mvn install
    
This will compile the Java classes, execute the tests and pack all the library files into jar file:
    
    target/samurai-1.0.jar

You can also create a single jar file with all the library dependencies by invoking the following command:
    
    mvn assembly:assembly
    
This will result in creating file:
    
    target/samurai-1.0-jar-with-dependencies.jar    

API usage
---------

### Classpath

After compiling the library as described in the previous point, you need to add the Samurai Java Client Library to your project's classpath.

If you use Maven you can achieve that by adding the following dependency to your `pom.xml`:
 
    <dependency>
      <groupId>com.feefighters</groupId>
      <artifactId>samurai</artifactId>
      <version>1.0</version>
    </dependency>    
    
### Gateway

To use the library you have to create a new instance of `com.feefighters.SamuraiGateway`.

    com.feefighters.SamuraiGateway gateway = new om.feefighters.SamuraiGateway(merchantKey, merchantPassword, processorToken);
    

### Samurai API Reference

See the [API Reference](https://samurai.feefighters.com/developers/api-reference/java) for a full explanation of how this library works with the Samurai API.
    
