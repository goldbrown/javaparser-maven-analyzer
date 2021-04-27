package com.chris.jpwrapper.wrapper;

import com.chris.jpwrapper.bean.HandlerResult;
import com.chris.jpwrapper.bean.JavaParserWrapperBean;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.SourceRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JavaParserWrapper {
    private static final Logger logger = LoggerFactory.getLogger(JavaParserWrapper.class);

    public JavaParserWrapperBean createJavaParserBean(String projectPath, String jarPath) {
        JavaParserWrapperBean context = new JavaParserWrapperBean();

        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        // jre
        TypeSolver jreTypeSolver = new ReflectionTypeSolver();
        combinedTypeSolver.add(jreTypeSolver);

        // java project
        List<SourceRoot> sourceRoots = new SymbolSolverCollectionStrategy().collect(Paths.get(projectPath)).getSourceRoots();
        sourceRoots.stream().forEach(sourceRoot -> {
            JavaParserTypeSolver javaParserTypeSolver = new JavaParserTypeSolver(sourceRoot.getRoot());
            combinedTypeSolver.add(javaParserTypeSolver);
        });
        JavaParserTypeSolver javaParserTypeSolver = new JavaParserTypeSolver(projectPath);
        combinedTypeSolver.add(javaParserTypeSolver);

        // jar type solver
        addJarTypeSolver(combinedTypeSolver, jarPath);

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        ParserConfiguration configuration = new ParserConfiguration();
        configuration.setSymbolResolver(symbolSolver);
        context.setJavaParser(new JavaParser(configuration));

        return context;
    }

    public JavaParserWrapperBean createJavaParserBeanByMaven(String projectHome, String outputPath, String mavenProgramHome) {
        JarHandler jarHandler = new JarHandler();
        HandlerResult handlerResult = jarHandler.downloadJarByMaven(projectHome, outputPath, mavenProgramHome);
        String jarPath = handlerResult.getContent();
        JavaParserWrapperBean javaParserBean = createJavaParserBean(projectHome, jarPath);
        return javaParserBean;
    }

    private static void addJarTypeSolver(CombinedTypeSolver combinedTypeSolver, String jarPath) {
        Path path = Paths.get(jarPath);
        try {
            Files.list(path).forEach(file -> {
                try {
                    combinedTypeSolver.add(new JarTypeSolver(file));
                } catch (Exception e) {
                    logger.error("add solver error, file is {}", file, e);
                }
            });
        } catch (Exception e) {
            logger.error("list file error, jarPath is {}", jarPath, e);
        }
    }


}
