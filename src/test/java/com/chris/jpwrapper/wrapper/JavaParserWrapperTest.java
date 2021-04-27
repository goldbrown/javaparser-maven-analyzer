package com.chris.jpwrapper.wrapper;

import com.chris.jpwrapper.bean.JavaParserWrapperBean;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @description:
 * @author: chris
 * @date: 2021/4/17
 */
public class JavaParserWrapperTest {

    @Test
    public void createJavaParserBeanByMaven() {
        JavaParserWrapper javaParserWrapper = new JavaParserWrapper();
        String projectName = "{your_service_name}"; // my service name
        JavaParserWrapperBean javaParserBeanByMaven = javaParserWrapper.createJavaParserBeanByMaven("/Users/chris/CorpCode/" + projectName,
                "{your_path}",
                "{your_maven_home}");
        System.out.println();
    }
}