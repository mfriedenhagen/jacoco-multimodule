/*
 * Copyright Mirko Friedenhagen, https://github.com/mfriedenhagen/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mfriedenhagen.jmm.webapp;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Unit test for simple App.
 */
public class AppIT {

    final Tomcat tomcat = new Tomcat();
    final String workingDir = System.getProperty("java.io.tmpdir");
    final WebDriver driver = new HtmlUnitDriver();

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
    public void shutdownWebdriver() {
        driver.close();
    }

    @After
    public void shutdownTomcat() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }

    @Test
    public void testNoNameGiven() throws MalformedURLException, IOException {
        checkOutput("", "Hello World!");
    }

    @Test
    public void testANameGiven() throws MalformedURLException, IOException {
        checkOutput("?name=Mirko", "Hello Mirko!");
    }

    @Test
    public void testAnInvalidName() throws MalformedURLException, IOException {
        checkOutput("?name=Peter", "'Peter' is not an allowed name");
    }

    void checkOutput(final String queryString, final String needle) throws MalformedURLException {
        final int port = tomcat.getConnector().getLocalPort();
        final URL url = new URL("http://localhost:" + port + "/index.jsp" + queryString);
        driver.get(url.toString());
        final String hayStack = driver.getPageSource();
        assertThat(hayStack, containsString(needle));
    }
}
