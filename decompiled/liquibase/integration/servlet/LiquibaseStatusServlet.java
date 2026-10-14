/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package liquibase.integration.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import liquibase.integration.servlet.GenericServletWrapper;
import liquibase.integration.servlet.GenericStatusServlet;

public class LiquibaseStatusServlet
extends HttpServlet {
    private static final long serialVersionUID = -5518519597664302292L;
    private final GenericStatusServlet delegate = new GenericStatusServlet();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.delegate.doGet(new JavaxHttpServletRequest(req), new JavaxHttpServletResponse(resp));
    }

    private static class JavaxHttpServletRequest
    extends GenericServletWrapper.HttpServletRequest {
        private final HttpServletRequest request;

        public JavaxHttpServletRequest(HttpServletRequest req) {
            this.request = req;
        }

        @Override
        public String getParameter(String key) {
            return this.request.getParameter(key);
        }

        @Override
        public String getRequestURI() {
            return this.request.getRequestURI();
        }
    }

    private static class JavaxHttpServletResponse
    extends GenericServletWrapper.HttpServletResponse {
        private final HttpServletResponse response;

        public JavaxHttpServletResponse(HttpServletResponse resp) {
            this.response = resp;
        }

        @Override
        public void setStatus(int status) {
            this.response.setStatus(status);
        }

        @Override
        public void setContentType(String type) {
            this.response.setContentType(type);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return this.response.getWriter();
        }
    }
}

