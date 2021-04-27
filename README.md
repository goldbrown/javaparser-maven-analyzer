# javaparser-maven-analyzer
A tool to integrate javaparser with MavenLauncher to simplify analyzing maven project. 

When using javaparser to analyze java source file and identify symbols from jar library, we have to include the jar library files. But what we have is only a pom.xml file, how do we get the jar library files?
The idea is to use MavenLauncher parse the pom.xml, download jar files into local computer and then include these jar files when creating javaparser instance.         

# How to use it
1. Install Maven in your computer
2. Clone this repository
3. Use `mvn clean package` to get a jar file
4. Add this jar file to your classpath
5. Create a JavaParserWrapperBean instance, for example
```java
public class JavaParserWrapperTest {

    @Test
    public void createJavaParserBeanByMaven() {
        JavaParserWrapper javaParserWrapper = new JavaParserWrapper();
        String projectName = "{your_service_name}"; // my service name
        JavaParserWrapperBean javaParserBeanByMaven = javaParserWrapper.createJavaParserBeanByMaven("{your_project_path}",
                "{your_path}",
                "{your_maven_home}");
        System.out.println();
    }
}
```
