package com.chris.jpwrapper.wrapper;

import com.chris.jpwrapper.bean.HandlerResult;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

public class JarHandler {

    private static Logger logger = LoggerFactory.getLogger(JarHandler.class);

    public HandlerResult downloadJarByMaven(String projectHome, String outputPath, String mavenProgramHome) {
        if (StringUtils.isEmpty(projectHome) || StringUtils.isEmpty(outputPath) || StringUtils.isEmpty(mavenProgramHome)) {
            return HandlerResult.createInvalidResult();
        }
        InvocationRequest request = new DefaultInvocationRequest();
        // The path of the pom file to be parsed, if not written, the pom file of the current project is parsed by default
        request.setPomFile( new File( projectHome ) );
        request.setGoals( Collections.singletonList( "dependency:copy-dependencies") );
        Properties properties = new Properties();
        properties.setProperty("outputDirectory", outputPath); // redirect output to a file
        properties.setProperty("outputAbsoluteArtifactFilename", "true"); // with paths
        properties.setProperty("includeScope", "runtime"); // only runtime (scope compile + runtime)
// if only interested in scope runtime, you may replace with excludeScope = compile
        request.setProperties(properties);

        Invoker invoker = new DefaultInvoker();
        // Your maven installation path
        invoker.setMavenHome(new File(mavenProgramHome));
        try {
            invoker.execute(request);
        } catch (Exception e) {
            logger.error("downloadJarByMaven error, projectHome is {}, mavenProgramHome is {}", projectHome, mavenProgramHome, e);
        }
        HandlerResult handlerResult = new HandlerResult();
        handlerResult.setCode(200);
        handlerResult.setContent(outputPath);
        return handlerResult;
    }
}
