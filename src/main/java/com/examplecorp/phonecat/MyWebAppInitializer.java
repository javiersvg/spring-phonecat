package com.examplecorp.phonecat;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.examplecorp.phonecat.configuration.MethodSecurityConfiguration;
import com.examplecorp.phonecat.configuration.MongoConfiguration;
import com.examplecorp.phonecat.configuration.MyWebConfig;
import com.examplecorp.phonecat.configuration.SecurityConfiguration;

public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {MongoConfiguration.class, SecurityConfiguration.class, MethodSecurityConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { MyWebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}