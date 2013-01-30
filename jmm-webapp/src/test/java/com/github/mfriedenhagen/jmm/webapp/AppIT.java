package com.github.mfriedenhagen.jmm.webapp;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppIT {

    final Tomcat tomcat = new Tomcat();
    final String workingDir = System.getProperty("java.io.tmpdir");

    @Before
    public void instantiateTomcatInstance() throws LifecycleException {
        tomcat.setPort(0);
        tomcat.setBaseDir(workingDir);
        tomcat.getHost().setAppBase(workingDir);
        tomcat.getHost().setAutoDeploy(true);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.start();
    }

    @After
    public void shutdownTomcat() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }

    @Test
    public void justATest() {
        final int port = tomcat.getConnector().getLocalPort();
        System.err.println(port);
    }
}
