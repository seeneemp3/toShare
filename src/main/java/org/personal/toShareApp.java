package org.personal;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class toShareApp {
    public static void main(String[] args) throws LifecycleException {
        var tomcat = new Tomcat();
        tomcat.setSilent(true);
        tomcat.getConnector().setPort(8080);
        Context tomCont = tomcat.addContext("", null);

        var context = new AnnotationConfigWebApplicationContext();
        context.setServletContext(tomCont.getServletContext());
        context.scan("org.personal");
        context.refresh();

        var dispatcherServlet = new DispatcherServlet(context);
        Wrapper dispWrapper = Tomcat.addServlet(tomCont, "dispatcher", dispatcherServlet);
        dispWrapper.addMapping("/");
        dispWrapper.setLoadOnStartup(1);

        tomcat.start();
    }
}