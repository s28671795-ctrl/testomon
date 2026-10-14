/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import org.h2.server.web.PageParser;
import org.h2.server.web.WebApp;
import org.h2.server.web.WebServer;
import org.h2.server.web.WebSession;
import org.h2.util.NetworkConnectionInfo;

public class JakartaWebServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private transient WebServer server;

    @Override
    public void init() {
        Object object;
        ServletConfig servletConfig = this.getServletConfig();
        Enumeration<String> enumeration = servletConfig.getInitParameterNames();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        while (enumeration.hasMoreElements()) {
            object = enumeration.nextElement().toString();
            String string = servletConfig.getInitParameter((String)object);
            if (!object.startsWith("-")) {
                object = "-" + (String)object;
            }
            arrayList.add(object);
            if (string.length() <= 0) continue;
            arrayList.add(string);
        }
        object = arrayList.toArray(new String[0]);
        this.server = new WebServer();
        this.server.setAllowChunked(false);
        this.server.init((String)object);
    }

    @Override
    public void destroy() {
        this.server.stop();
    }

    private boolean allow(HttpServletRequest httpServletRequest) {
        if (this.server.getAllowOthers()) {
            return true;
        }
        String string = httpServletRequest.getRemoteAddr();
        try {
            InetAddress inetAddress = InetAddress.getByName(string);
            return inetAddress.isLoopbackAddress();
        }
        catch (NoClassDefFoundError | UnknownHostException throwable) {
            return false;
        }
    }

    private String getAllowedFile(HttpServletRequest httpServletRequest, String string) {
        if (!this.allow(httpServletRequest)) {
            return "notAllowed.jsp";
        }
        if (string.length() == 0) {
            return "index.do";
        }
        return string;
    }

    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        Object object;
        String string;
        Object object2;
        httpServletRequest.setCharacterEncoding("utf-8");
        String string2 = httpServletRequest.getPathInfo();
        if (string2 == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURI() + "/");
            return;
        }
        if (string2.startsWith("/")) {
            string2 = string2.substring(1);
        }
        string2 = this.getAllowedFile(httpServletRequest, string2);
        Properties properties = new Properties();
        Enumeration<String> enumeration = httpServletRequest.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            object2 = enumeration.nextElement().toString();
            string = httpServletRequest.getAttribute((String)object2).toString();
            properties.put(object2, string);
        }
        enumeration = httpServletRequest.getParameterNames();
        while (enumeration.hasMoreElements()) {
            object2 = enumeration.nextElement().toString();
            string = httpServletRequest.getParameter((String)object2);
            properties.put(object2, string);
        }
        object2 = null;
        string = properties.getProperty("jsessionid");
        if (string != null) {
            object2 = this.server.getSession(string);
        }
        WebApp webApp = new WebApp(this.server);
        webApp.setSession((WebSession)object2, properties);
        String string3 = httpServletRequest.getHeader("if-modified-since");
        String string4 = httpServletRequest.getScheme();
        StringBuilder stringBuilder = new StringBuilder(string4).append("://").append(httpServletRequest.getServerName());
        int n = httpServletRequest.getServerPort();
        if (!(n == 80 && string4.equals("http") || n == 443 && string4.equals("https"))) {
            stringBuilder.append(':').append(n);
        }
        String string5 = stringBuilder.append(httpServletRequest.getContextPath()).toString();
        string2 = webApp.processRequest(string2, new NetworkConnectionInfo(string5, httpServletRequest.getRemoteAddr(), httpServletRequest.getRemotePort()));
        object2 = webApp.getSession();
        String string6 = webApp.getMimeType();
        boolean bl = webApp.getCache();
        if (bl && this.server.getStartDateTime().equals(string3)) {
            httpServletResponse.setStatus(304);
            return;
        }
        byte[] byArray = this.server.getFile(string2);
        if (byArray == null) {
            httpServletResponse.sendError(404);
            byArray = ("File not found: " + string2).getBytes(StandardCharsets.UTF_8);
        } else {
            if (object2 != null && string2.endsWith(".jsp")) {
                object = new String(byArray, StandardCharsets.UTF_8);
                object = PageParser.parse((String)object, ((WebSession)object2).map);
                byArray = ((String)object).getBytes(StandardCharsets.UTF_8);
            }
            httpServletResponse.setContentType(string6);
            if (!bl) {
                httpServletResponse.setHeader("Cache-Control", "no-cache");
            } else {
                httpServletResponse.setHeader("Cache-Control", "max-age=10");
                httpServletResponse.setHeader("Last-Modified", this.server.getStartDateTime());
            }
        }
        if (byArray != null) {
            object = httpServletResponse.getOutputStream();
            ((OutputStream)object).write(byArray);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        this.doGet(httpServletRequest, httpServletResponse);
    }
}

