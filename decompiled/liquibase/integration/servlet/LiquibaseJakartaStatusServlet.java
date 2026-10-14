/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import liquibase.integration.servlet.GenericServletWrapper;
import liquibase.integration.servlet.GenericStatusServlet;

public class LiquibaseJakartaStatusServlet
extends HttpServlet {
    private static final long serialVersionUID = -6601471901927004592L;
    private final GenericStatusServlet delegate = new GenericStatusServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.delegate.doGet(new JakartaHttpServletRequest(req), new JakartaHttpServletResponse(resp));
    }

    private static class JakartaHttpServletRequest
    extends GenericServletWrapper.HttpServletRequest {
        private final HttpServletRequest request;

        public JakartaHttpServletRequest(HttpServletRequest req) {
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

    private static class JakartaHttpServletResponse
    extends GenericServletWrapper.HttpServletResponse {
        private final HttpServletResponse response;

        public JakartaHttpServletResponse(HttpServletResponse resp) {
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

