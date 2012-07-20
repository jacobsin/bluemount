package org.codehaus.jstestrunner.jetty;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jstestrunner.TestResultProducer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Receive test results and store them in an internal cache.
 */
public class JSTestResultHandler extends AbstractHandler {

    /**
     * Captures a test result.
     */
    public static class JSTestResult {
        public String testUrl;
        public int failures;
        public int passes;
        public String message;
        public Map<String, Boolean> tests;
    }

    /**
     * Store a mapping of test results.
     */
    private final Map<String, JSTestResult> jsTestResults = new HashMap<String, JSTestResult>();
    private final Lock jsTestResultsLock = new ReentrantLock();
    private final Condition newJsTestResults = jsTestResultsLock.newCondition();

    /**
     * Get the test result associated with the given url and block until it
     * becomes available, or we timeout.
     *
     * @param url                the url of the test.
     * @param testResultProducer Used to determine whether we are in a position to wait for
     *                           results.
     * @param time               the time to wait.
     * @param unit               the unit of time to wait.
     * @return the test result or null if it cannot be obtained.
     */
    public JSTestResult getJsTestResult(URL url,
                                        TestResultProducer testResultProducer, long time, TimeUnit unit) {
        JSTestResult jsTestResult = null;
        jsTestResultsLock.lock();
        try {
            do {
                jsTestResult = jsTestResults.get(url.toString());
                if (jsTestResult == null) {
                    boolean newJsTestResult;
                    try {
                        if (testResultProducer.isAvailable()) {
                            newJsTestResult = newJsTestResults
                                    .await(time, unit);
                        } else {
                            newJsTestResult = false;
                        }
                    } catch (InterruptedException e) {
                        newJsTestResult = false;
                    }
                    if (!newJsTestResult) {
                        break;
                    }
                }
            } while (jsTestResult == null);
        } finally {
            jsTestResultsLock.unlock();
        }
        return jsTestResult;
    }

    /**
     * Handle a test result.
     */
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (target.equals("/testResults") && request.getMethod().equals("POST")
                && request.getContentType().contains("application/json")) {

            JSTestResult jsTestResult;

            ObjectMapper mapper = new ObjectMapper();
            jsTestResult = mapper.readValue(request.getReader(), JSTestResult.class);

            // Store any failures.
            if (jsTestResult.testUrl != null && jsTestResult.message != null && jsTestResult.tests != null) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            response.setContentType("application/json");

            baseRequest.setHandled(true);

            // Minimally signal any threads to re-look at things. Store results
            // if we received them.
            jsTestResultsLock.lock();
            try {
                jsTestResults.put(jsTestResult.testUrl, jsTestResult);
                newJsTestResults.signalAll();
            } finally {
                jsTestResultsLock.unlock();
            }

        }
    }
}
