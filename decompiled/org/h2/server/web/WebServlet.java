/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.h2.server.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.h2.server.web.PageParser;
import org.h2.server.web.WebApp;
import org.h2.server.web.WebServer;
import org.h2.server.web.WebSession;
import org.h2.util.NetworkConnectionInfo;

public class WebServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private transient WebServer server;

    public void init() {
        Object object;
        ServletConfig servletConfig = this.getServletConfig();
        Enumeration enumeration = servletConfig.getInitParameterNames();
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

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String string;
        String string2;
        Object object;
        httpServletRequest.setCharacterEncoding("utf-8");
        String string3 = httpServletRequest.getPathInfo();
        if (string3 == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURI() + "/");
            return;
        }
        if (string3.startsWith("/")) {
            string3 = string3.substring(1);
        }
        string3 = this.getAllowedFile(httpServletRequest, string3);
        Properties properties = new Properties();
        Enumeration enumeration = httpServletRequest.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            object = enumeration.nextElement().toString();
            string2 = httpServletRequest.getAttribute((String)object).toString();
            properties.put(object, string2);
        }
        enumeration = httpServletRequest.getParameterNames();
        while (enumeration.hasMoreElements()) {
            object = enumeration.nextElement().toString();
            string2 = httpServletRequest.getParameter((String)object);
            properties.put(object, string2);
        }
        object = null;
        string2 = properties.getProperty("jsessionid");
        if (string2 != null) {
            object = this.server.getSession(string2);
        }
        WebApp webApp = new WebApp(this.server);
        webApp.setSession((WebSession)object, properties);
        String string4 = httpServletRequest.getHeader("if-modified-since");
        String string5 = httpServletRequest.getScheme();
        StringBuilder stringBuilder = new StringBuilder(string5).append("://").append(httpServletRequest.getServerName());
        int n = httpServletRequest.getServerPort();
        if (!(n == 80 && string5.equals("http") || n == 443 && string5.equals("https"))) {
            stringBuilder.append(':').append(n);
        }
        String string6 = stringBuilder.append(httpServletRequest.getContextPath()).toString();
        string3 = webApp.processRequest(string3, new NetworkConnectionInfo(string6, httpServletRequest.getRemoteAddr(), httpServletRequest.getRemotePort()));
        object = webApp.getSession();
        String string7 = webApp.getMimeType();
        boolean bl = webApp.getCache();
        if (bl && this.server.getStartDateTime().equals(string4)) {
            httpServletResponse.setStatus(304);
            return;
        }
        byte[] byArray = this.server.getFile(string3);
        if (byArray == null) {
            httpServletResponse.sendError(404);
            byArray = ("File not found: " + string3).getBytes(StandardCharsets.UTF_8);
        } else {
            if (object != null && string3.endsWith(".jsp")) {
                string = new String(byArray, StandardCharsets.UTF_8);
                string = PageParser.parse(string, ((WebSession)object).map);
                byArray = string.getBytes(StandardCharsets.UTF_8);
            }
            httpServletResponse.setContentType(string7);
            if (!bl) {
                httpServletResponse.setHeader("Cache-Control", "no-cache");
            } else {
                httpServletResponse.setHeader("Cache-Control", "max-age=10");
                httpServletResponse.setHeader("Last-Modified", this.server.getStartDateTime());
            }
        }
        if (byArray != null) {
            string = httpServletResponse.getOutputStream();
            string.write(byArray);
        }
    }

    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        this.doGet(httpServletRequest, httpServletResponse);
    }
}

