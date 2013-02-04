package com.github.mfriedenhagen.jmm.webapp;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
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
    public void instantiateTomcatInstance() throws LifecycleException, ServletException {
        tomcat.setPort(0);
        tomcat.setBaseDir(workingDir);
        tomcat.getHost().setAppBase(workingDir);
        tomcat.getHost().setAutoDeploy(true);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.addWebapp("/", new File("target/jmm-webapp").getAbsolutePath());
        tomcat.start();
    }

    @After
    public void shutdownTomcat() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }

    @Test
    public void justATest() throws MalformedURLException, IOException {
        final int port = tomcat.getConnector().getLocalPort();
        final URL url = new URL("http://localhost:" + port + "/index.jsp");
        final InputStream index = url.openStream();
        try {
            byte[] bodyAsBytes = ByteStreams.toByteArray(index);
            final String bodyAsString = new String(bodyAsBytes, Charsets.UTF_8);
            System.err.println(bodyAsString);
        } finally {
            index.close();
        }
    }
}
