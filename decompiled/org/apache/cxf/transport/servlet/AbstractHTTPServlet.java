/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterConfig
 *  javax.servlet.RequestDispatcher
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.FileUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.resource.ResourceManager;

public abstract class AbstractHTTPServlet
extends HttpServlet
implements Filter {
    private static final long serialVersionUID = -8357252743467075117L;
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractHTTPServlet.class);
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AbstractHTTPServlet.class);
    private static final List<String> KNOWN_HTTP_VERBS = Arrays.asList("POST", "GET", "PUT", "DELETE", "HEAD", "OPTIONS", "TRACE");
    private static final String STATIC_RESOURCES_PARAMETER = "static-resources-list";
    private static final String STATIC_WELCOME_FILE_PARAMETER = "static-welcome-file";
    private static final String STATIC_CACHE_CONTROL = "static-cache-control";
    private static final String STATIC_RESOURCES_MAP_RESOURCE = "/cxfServletStaticResourcesMap.txt";
    private static final String REDIRECTS_PARAMETER = "redirects-list";
    private static final String REDIRECT_SERVLET_NAME_PARAMETER = "redirect-servlet-name";
    private static final String REDIRECT_SERVLET_PATH_PARAMETER = "redirect-servlet-path";
    private static final String REDIRECT_ATTRIBUTES_PARAMETER = "redirect-attributes";
    private static final String REDIRECT_QUERY_CHECK_PARAMETER = "redirect-query-check";
    private static final String REDIRECT_WITH_INCLUDE_PARAMETER = "redirect-with-include";
    private static final String USE_X_FORWARDED_HEADERS_PARAMETER = "use-x-forwarded-headers";
    private static final String X_FORWARDED_PROTO_HEADER = "X-Forwarded-Proto";
    private static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
    private static final String X_FORWARDED_PREFIX_HEADER = "X-Forwarded-Prefix";
    private static final String X_FORWARDED_HOST_HEADER = "X-Forwarded-Host";
    private static final String X_FORWARDED_PORT_HEADER = "X-Forwarded-Port";
    private static final Map<String, String> DEFAULT_STATIC_CONTENT_TYPES = new HashMap<String, String>();
    private List<Pattern> staticResourcesList;
    private String staticWelcomeFile;
    private List<Pattern> redirectList;
    private String dispatcherServletPath;
    private String dispatcherServletName;
    private Map<String, String> redirectAttributes;
    private Map<String, String> staticContentTypes = new HashMap<String, String>(DEFAULT_STATIC_CONTENT_TYPES);
    private boolean redirectQueryCheck;
    private boolean useXForwardedHeaders;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        this.staticResourcesList = AbstractHTTPServlet.parseListSequence(servletConfig.getInitParameter(STATIC_RESOURCES_PARAMETER));
        this.staticWelcomeFile = servletConfig.getInitParameter(STATIC_WELCOME_FILE_PARAMETER);
        this.redirectList = AbstractHTTPServlet.parseListSequence(servletConfig.getInitParameter(REDIRECTS_PARAMETER));
        this.redirectQueryCheck = Boolean.valueOf(servletConfig.getInitParameter(REDIRECT_QUERY_CHECK_PARAMETER));
        this.dispatcherServletName = servletConfig.getInitParameter(REDIRECT_SERVLET_NAME_PARAMETER);
        this.dispatcherServletPath = servletConfig.getInitParameter(REDIRECT_SERVLET_PATH_PARAMETER);
        this.redirectAttributes = AbstractHTTPServlet.parseMapSequence(servletConfig.getInitParameter(REDIRECT_ATTRIBUTES_PARAMETER));
        this.useXForwardedHeaders = Boolean.valueOf(servletConfig.getInitParameter(USE_X_FORWARDED_HEADERS_PARAMETER));
    }

    public void destroy() {
        FileUtils.maybeDeleteDefaultTempDir();
    }

    protected void finalizeServletInit(ServletConfig servletConfig) throws ServletException {
        InputStream is = this.getResourceAsStream("/WEB-INF/cxfServletStaticResourcesMap.txt");
        if (is == null) {
            is = this.getResourceAsStream(STATIC_RESOURCES_MAP_RESOURCE);
        }
        if (is != null) {
            try {
                Properties props = new Properties();
                props.load(is);
                for (String name : props.stringPropertyNames()) {
                    this.staticContentTypes.put(name, props.getProperty(name));
                }
                is.close();
            }
            catch (IOException ex) {
                String message = new Message("STATIC_RESOURCES_MAP_LOAD_FAILURE", BUNDLE, new Object[0]).toString();
                LOG.warning(message);
            }
        }
    }

    protected InputStream getResourceAsStream(String path) {
        ResourceManager rm;
        InputStream is = ClassLoaderUtils.getResourceAsStream(path, AbstractHTTPServlet.class);
        if (is == null && this.getBus() != null && (rm = this.getBus().getExtension(ResourceManager.class)) != null) {
            is = rm.resolveResource(path, InputStream.class);
        }
        return is;
    }

    public final void init(final FilterConfig filterConfig) throws ServletException {
        this.init(new ServletConfig(){

            public String getServletName() {
                return filterConfig.getFilterName();
            }

            public ServletContext getServletContext() {
                return filterConfig.getServletContext();
            }

            public String getInitParameter(String name) {
                return filterConfig.getInitParameter(name);
            }

            public Enumeration<String> getInitParameterNames() {
                return filterConfig.getInitParameterNames();
            }
        });
    }

    protected static List<Pattern> parseListSequence(String values2) {
        if (values2 != null) {
            ArrayList<Pattern> list = new ArrayList<Pattern>();
            for (String value : values2.split("\\s")) {
                if (value.isEmpty()) continue;
                list.add(Pattern.compile(value));
            }
            list.trimToSize();
            return list;
        }
        return null;
    }

    protected static Map<String, String> parseMapSequence(String sequence2) {
        if (sequence2 != null) {
            sequence2 = sequence2.trim();
            HashMap<String, String> map = new HashMap<String, String>();
            for (String pair : sequence2.split("\\s")) {
                if (pair.isEmpty()) continue;
                String[] value = pair.split("=");
                if (value.length == 2) {
                    map.put(value[0], value[1]);
                    continue;
                }
                map.put(pair, "");
            }
            return map;
        }
        return Collections.emptyMap();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        this.handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        this.handleRequest(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.handleRequest(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.handleRequest(request, response);
    }

    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.handleRequest(request, response);
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.handleRequest(request, response);
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletResponse response;
        HttpServletRequest request;
        try {
            request = (HttpServletRequest)req;
            response = (HttpServletResponse)res;
        }
        catch (ClassCastException e) {
            throw new ServletException("Unrecognized HTTP request or response object");
        }
        String method2 = request.getMethod();
        if (KNOWN_HTTP_VERBS.contains(method2)) {
            super.service(request, response);
        } else {
            this.handleRequest(request, response);
        }
    }

    protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        boolean staticWelcomeFileMatch;
        if ((this.dispatcherServletPath != null || this.dispatcherServletName != null) && (this.redirectList != null && this.matchPath(this.redirectList, request) || this.redirectList == null)) {
            this.redirect(request, response, request.getPathInfo());
            return;
        }
        boolean staticResourcesMatch = this.staticResourcesList != null && this.matchPath(this.staticResourcesList, request);
        boolean bl = staticWelcomeFileMatch = this.staticWelcomeFile != null && (StringUtils.isEmpty(request.getPathInfo()) || "/".equals(request.getPathInfo()));
        if (staticResourcesMatch || staticWelcomeFileMatch) {
            this.serveStaticContent(request, response, staticWelcomeFileMatch ? this.staticWelcomeFile : request.getPathInfo());
            return;
        }
        request = this.checkXForwardedHeaders(request);
        this.invoke(request, response);
    }

    protected HttpServletRequest checkXForwardedHeaders(HttpServletRequest request) {
        if (this.useXForwardedHeaders) {
            String originalProtocol = request.getHeader(X_FORWARDED_PROTO_HEADER);
            String originalRemoteAddr = request.getHeader(X_FORWARDED_FOR_HEADER);
            String originalPrefix = request.getHeader(X_FORWARDED_PREFIX_HEADER);
            String originalHost = request.getHeader(X_FORWARDED_HOST_HEADER);
            String originalPort = request.getHeader(X_FORWARDED_PORT_HEADER);
            if (Stream.of(originalProtocol, originalRemoteAddr, originalPrefix, originalHost, originalPort).anyMatch(Objects::nonNull)) {
                return new HttpServletRequestXForwardedFilter(request, originalProtocol, originalRemoteAddr, originalPrefix, originalHost, originalPort);
            }
        }
        return request;
    }

    private boolean matchPath(List<Pattern> values2, HttpServletRequest request) {
        String queryString;
        String path = request.getPathInfo();
        if (path == null) {
            path = "/";
        }
        if (this.redirectQueryCheck && (queryString = request.getQueryString()) != null && !queryString.isEmpty()) {
            path = path + "?" + queryString;
        }
        for (Pattern pattern : values2) {
            if (!pattern.matcher(path).matches()) continue;
            return true;
        }
        return false;
    }

    protected abstract Bus getBus();

    protected void serveStaticContent(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException {
        try (InputStream is = this.getResourceAsStream(pathInfo);){
            String cacheControl;
            String type;
            if (is == null) {
                throw new ServletException("Static resource " + pathInfo + " is not available");
            }
            int ind = pathInfo.lastIndexOf(46);
            if (ind > 0 && (type = this.getStaticResourceContentType(pathInfo.substring(ind + 1))) != null) {
                response.setContentType(type);
            }
            if ((cacheControl = this.getServletConfig().getInitParameter(STATIC_CACHE_CONTROL)) != null) {
                response.setHeader("Cache-Control", cacheControl.trim());
            }
            IOUtils.copy(is, (OutputStream)response.getOutputStream());
        }
        catch (IOException ex) {
            throw new ServletException("Static resource " + pathInfo + " can not be written to the output stream");
        }
    }

    protected String getStaticResourceContentType(String extension) {
        return this.staticContentTypes.get(extension);
    }

    protected void redirect(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException {
        RequestDispatcher rd;
        boolean customServletPath = this.dispatcherServletPath != null;
        String theServletPath = customServletPath ? this.dispatcherServletPath : "/";
        ServletContext sc = super.getServletContext();
        RequestDispatcher requestDispatcher = rd = this.dispatcherServletName != null ? sc.getNamedDispatcher(this.dispatcherServletName) : sc.getRequestDispatcher((theServletPath + pathInfo).replace("//", "/"));
        if (rd == null) {
            String errorMessage = "No RequestDispatcher can be created for path " + pathInfo;
            if (this.dispatcherServletName != null) {
                errorMessage = errorMessage + ", dispatcher name: " + this.dispatcherServletName;
            }
            throw new ServletException(errorMessage);
        }
        try {
            for (Map.Entry<String, String> entry : this.redirectAttributes.entrySet()) {
                request.setAttribute(entry.getKey(), (Object)entry.getValue());
            }
            HttpServletRequestRedirectFilter servletRequest = new HttpServletRequestRedirectFilter(request, pathInfo, theServletPath, customServletPath);
            if (PropertyUtils.isTrue(this.getServletConfig().getInitParameter(REDIRECT_WITH_INCLUDE_PARAMETER))) {
                rd.include((ServletRequest)servletRequest, (ServletResponse)response);
            } else {
                rd.forward((ServletRequest)servletRequest, (ServletResponse)response);
            }
        }
        catch (Throwable ex) {
            throw new ServletException("RequestDispatcher for path " + pathInfo + " has failed", ex);
        }
    }

    protected abstract void invoke(HttpServletRequest var1, HttpServletResponse var2) throws ServletException;

    static {
        DEFAULT_STATIC_CONTENT_TYPES.put("html", "text/html");
        DEFAULT_STATIC_CONTENT_TYPES.put("txt", "text/plain");
        DEFAULT_STATIC_CONTENT_TYPES.put("css", "text/css");
        DEFAULT_STATIC_CONTENT_TYPES.put("pdf", "application/pdf");
        DEFAULT_STATIC_CONTENT_TYPES.put("xsd", "application/xml");
        DEFAULT_STATIC_CONTENT_TYPES.put("js", "application/javascript");
    }

    private static class HttpServletRequestXForwardedFilter
    extends HttpServletRequestWrapper {
        private String newProtocol;
        private String newRemoteAddr;
        private String newContextPath;
        private String newServletPath;
        private String newRequestUri;
        private StringBuffer newRequestUrl;

        HttpServletRequestXForwardedFilter(HttpServletRequest request, String originalProto, String originalRemoteAddr, String originalPrefix, String originalHost, String originalPort) {
            super(request);
            this.newProtocol = originalProto;
            if (originalRemoteAddr != null) {
                this.newRemoteAddr = originalRemoteAddr.split(",")[0].trim();
            }
            this.newRequestUri = HttpServletRequestXForwardedFilter.calculateNewRequestUri(request, originalPrefix);
            String outermostHost = originalHost != null ? originalHost.split(",", 2)[0].trim() : originalHost;
            this.newRequestUrl = HttpServletRequestXForwardedFilter.calculateNewRequestUrl(request, originalProto, originalPrefix, outermostHost, originalPort);
            this.newContextPath = HttpServletRequestXForwardedFilter.calculateNewContextPath(request, originalPrefix);
            this.newServletPath = HttpServletRequestXForwardedFilter.calculateNewServletPath(request, originalPrefix);
        }

        private static String calculateNewContextPath(HttpServletRequest request, String originalPrefix) {
            if (originalPrefix != null) {
                return originalPrefix;
            }
            return request.getContextPath();
        }

        private static String calculateNewServletPath(HttpServletRequest request, String originalPrefix) {
            String servletPath = request.getServletPath();
            if (originalPrefix != null) {
                servletPath = request.getContextPath() + servletPath;
            }
            return servletPath;
        }

        private static String calculateNewRequestUri(HttpServletRequest request, String originalPrefix) {
            String requestUri = request.getRequestURI();
            if (originalPrefix != null) {
                requestUri = originalPrefix + requestUri;
            }
            return requestUri;
        }

        private static StringBuffer calculateNewRequestUrl(HttpServletRequest request, String originalProto, String originalPrefix, String originalHost, String originalPort) {
            URI uri = URI.create(request.getRequestURL().toString());
            StringBuffer sb = new StringBuffer();
            sb.append(originalProto != null ? originalProto : uri.getScheme()).append("://").append(originalHost != null ? originalHost : uri.getHost()).append(originalPort != null && !"-1".equals(originalPort) ? ":" + originalPort : (uri.getPort() != -1 ? ":" + uri.getPort() : "")).append(originalPrefix != null ? originalPrefix : "").append(uri.getRawPath());
            String query = uri.getRawQuery();
            if (query != null) {
                sb.append('?').append(query);
            }
            return sb;
        }

        public boolean isSecure() {
            if (this.newProtocol != null) {
                return "https".equals(this.newProtocol);
            }
            return super.isSecure();
        }

        public StringBuffer getRequestURL() {
            return this.newRequestUrl;
        }

        public String getRemoteAddr() {
            if (this.newRemoteAddr != null) {
                return this.newRemoteAddr;
            }
            return super.getRemoteAddr();
        }

        public String getRequestURI() {
            return this.newRequestUri;
        }

        public String getContextPath() {
            return this.newContextPath;
        }

        public String getServletPath() {
            return this.newServletPath;
        }
    }

    private static class HttpServletRequestRedirectFilter
    extends HttpServletRequestWrapper {
        private String pathInfo;
        private String servletPath;

        HttpServletRequestRedirectFilter(HttpServletRequest request, String pathInfo, String servletPath, boolean customServletPath) {
            super(request);
            this.pathInfo = pathInfo;
            this.servletPath = servletPath;
            if ("/".equals(this.servletPath) && !customServletPath) {
                if (this.pathInfo == null) {
                    this.pathInfo = "/";
                    this.servletPath = "";
                } else {
                    this.servletPath = "";
                }
            }
        }

        public String getServletPath() {
            return this.servletPath;
        }

        public String getPathInfo() {
            return this.pathInfo;
        }

        public String getRequestURI() {
            String contextPath = this.getContextPath();
            if ("/".equals(contextPath)) {
                contextPath = "";
            }
            return contextPath + (this.servletPath + this.pathInfo).replace("//", "/");
        }

        public Object getAttribute(String name) {
            if ("http.service.redirection".equals(name)) {
                return "true";
            }
            return super.getAttribute(name);
        }
    }
}

